package com.atpl.mmg.AandA.service.datamigration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.config.MMGProperties;
import com.atpl.mmg.AandA.constant.AddressType;
import com.atpl.mmg.AandA.constant.EnquiryRole;
import com.atpl.mmg.AandA.constant.EnquiryStatus;
import com.atpl.mmg.AandA.constant.Role;
import com.atpl.mmg.AandA.dao.boardingRequest.BoardingRequestDAO;
import com.atpl.mmg.AandA.dao.profile.ProfileDAOV2;
import com.atpl.mmg.AandA.dao.profileImage.ProfileImageDAO;
import com.atpl.mmg.AandA.dao.role.RoleDAO;
import com.atpl.mmg.AandA.domain.DBUpdate;
import com.atpl.mmg.AandA.domain.boardingRequest.BoardingRequestDomain;
import com.atpl.mmg.AandA.domain.profile.ProfileDomainV2;
import com.atpl.mmg.AandA.domain.profileImage.ProfileImageDomain;
import com.atpl.mmg.AandA.domain.role.RoleDomain;
import com.atpl.mmg.AandA.exception.MmgRestException.FILE_UPLOAD_FAILED;
import com.atpl.mmg.AandA.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.AandA.exception.MmgRestException.ROLE_NOT_FOUND;
import com.atpl.mmg.AandA.log.EventsLogUtil;
import com.atpl.mmg.AandA.log.Loggly;
import com.atpl.mmg.AandA.log.ServiceTypes;
import com.atpl.mmg.AandA.log.SeverityTypes;
import com.atpl.mmg.AandA.model.bankaccount.BankAccountModel;
import com.atpl.mmg.AandA.model.boardingRequest.BoardingRequestModel;
import com.atpl.mmg.AandA.model.datamigration.BoardingRequestTemplateModel;
import com.atpl.mmg.AandA.model.datamigration.ProfileImageTemplateModel;
import com.atpl.mmg.AandA.model.datamigration.ProfileTemplateModel;
import com.atpl.mmg.AandA.model.profile.Address;
import com.atpl.mmg.AandA.model.profile.Customer;
import com.atpl.mmg.AandA.model.profile.Driver;
import com.atpl.mmg.AandA.model.profile.Franchise;
import com.atpl.mmg.AandA.model.profile.Profile;
import com.atpl.mmg.AandA.service.DBUpdateService;
import com.atpl.mmg.AandA.service.profile.AuthFranchiseCommonService;
import com.atpl.mmg.AandA.service.profile.ProfileCommonService;
import com.atpl.mmg.AandA.utils.CommonUtils;
import com.atpl.mmg.AandA.utils.DateUtility;
import com.atpl.mmg.AandA.utils.IDGeneration;

@Service("dataMigrationService")
public class MigrationServiceImpl implements MigrationService {

	@Autowired
	IDGeneration idGeneration;

	@Autowired
	MMGProperties mmgProperties;

	@Autowired
	DBUpdateService dbUpdateService;

	@Autowired
	AuthFranchiseCommonService franchiseCommonService;

	@Autowired
	ProfileCommonService driverService;

	@Autowired
	ProfileCommonService profileCommonService;

	@Autowired
	ProfileCommonService companyProfileService;

	@Autowired
	BoardingRequestDAO boardingRequestDAO;

	@Autowired
	ProfileDAOV2 profileDAOV2;

	@Autowired
	RoleDAO roleDAO;

	@Autowired
	ProfileImageDAO profileImageDAO;

	@Autowired
	MigrationUtil migrationUtil;

	@Override
	public String saveProfilesByRoles(MultipartFile file, String roleId) throws Exception {
		MigrationUtil.hasExcelFormat(file);
		Role role = Role.getRole(roleId);
		if (null == role)
			throw new ROLE_NOT_FOUND(roleId + "");
		Profile profile = new Profile();
		List<ProfileTemplateModel> profileTemplateModel = null;
		switch (role) {
		case CUSTOMER:
			profileTemplateModel = covertDocToList(file, role);
			profile = saveCustomer(file, role, profileTemplateModel);
			break;
		case FRANCHISE:
			profileTemplateModel = covertDocToList(file, role);
			profile = saveFranchise(file, role, profileTemplateModel);
			break;
		case DRIVER:
			profileTemplateModel = covertDocToList(file, role);
			profile = saveDriver(file, role, profileTemplateModel);
			break;
		case FLEET_OPERATOR:
		case ENTERPRISE:
		case WAREHOUSE:
			break;
		default:
			break;
		}
		return "Success";
	}

	private Profile saveCustomer(MultipartFile file, Role role, List<ProfileTemplateModel> customerTemplateModelList)
			throws Exception {
		Profile profile = null;
		List<Address> addressList = null;
		for (ProfileTemplateModel customerTemplateModel : customerTemplateModelList) {
			addressList = new ArrayList<Address>();
			profile = new Profile();
			Customer customer = new Customer();
			Address address = null;
			ProfileDomainV2 existingProfileDomain = new ProfileDomainV2();
			BeanUtils.copyProperties(customerTemplateModel, profile);
			if (customerTemplateModel.getDob() != null || !customerTemplateModel.getDob().equals("0")) {
				profile.setDob(customerTemplateModel.getDob());
			}
			if (customerTemplateModel.getCountryId() != 0 && customerTemplateModel.getStateId() != 0
					&& customerTemplateModel.getCityId() != 0) {
				address = new Address();
				String doorNo = "", streetName = "";
				BeanUtils.copyProperties(customerTemplateModel, address);
				if (!customerTemplateModel.getDoorNumber().equals("null"))
					doorNo = customerTemplateModel.getDoorNumber();
				if (!customerTemplateModel.getStreet().equals("null"))
					streetName = customerTemplateModel.getStreet();
				if (!doorNo.isEmpty() && !streetName.isEmpty())
					address.setAddress1(doorNo + "," + streetName);
				else if (!doorNo.isEmpty())
					address.setAddress1(doorNo);
				else if (!streetName.isEmpty())
					address.setAddress1(streetName);
				address.setType(AddressType.HOME.name());
				addressList.add(address);
			}
			profile.setProfileSource("web");
			if (customerTemplateModel.getTermsAndConditionsId() != null)
				customer.setTermsAndConditionsId(customerTemplateModel.getTermsAndConditionsId());
			customer.setIsTermsAndCondition(true);
			profile.setAddress(addressList);
			profile.setCustomer(customer);
			profile = profileCommonService.save(profile, existingProfileDomain);
			// Update method
			DBUpdate dbUpdate = new DBUpdate();
			dbUpdate.setTableName("profilerole");

			Map<String, Object> expression = new HashMap<String, Object>();
			if (customerTemplateModel.getTokenId() != null || !customerTemplateModel.getTokenId().isEmpty()) {
				if (!customerTemplateModel.getTokenId().equals("null"))
					expression.put("appTokenId", customerTemplateModel.getTokenId());
			}
			if (customerTemplateModel.getWebToken() != null || !customerTemplateModel.getWebToken().isEmpty()) {
				if (!customerTemplateModel.getWebToken().equals("null"))
					expression.put("webTokenId", customerTemplateModel.getWebToken());
			}
			dbUpdate.setExpression(expression);

			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("profileId", profile.getId());
			dbUpdate.setConditions(conditions);
			dbUpdateService.dbUpdate(dbUpdate);
			createdAndModifiedDate(customerTemplateModel, profile.getId());
			createdAndModifiedDateByRole(customerTemplateModel, profile.getId(), role);
		}
		return profile;
	}

	private Profile saveFranchise(MultipartFile file, Role role, List<ProfileTemplateModel> profileTemplateModelList)
			throws Exception {
		Profile profile = null;
		List<Address> addressList = null;
		List<BankAccountModel> bankList = null;
		for (ProfileTemplateModel profileTemplateModel : profileTemplateModelList) {
			profile = new Profile();
			Franchise franchise = new Franchise();
			Address address = null;
			BankAccountModel bank = new BankAccountModel();
			addressList = new ArrayList<Address>();
			bankList = new ArrayList<BankAccountModel>();
			ProfileDomainV2 existingProfileDomain = new ProfileDomainV2();
			BeanUtils.copyProperties(profileTemplateModel, profile);
			if (profileTemplateModel.getDob() != null || !profileTemplateModel.getDob().equals("0")) {
				profile.setDob(profileTemplateModel.getDob());
			}
			if (profileTemplateModel.getLastName().equals("null"))
				profile.setLastName("");
			profile.setProfileSource("web");
			if (profileTemplateModel.getCountryId() != 0 && profileTemplateModel.getStateId() != 0
					&& profileTemplateModel.getCityId() != 0) {
				address = new Address();
				String doorNo = "", streetName = "";
				BeanUtils.copyProperties(profileTemplateModel, address);
				if (!profileTemplateModel.getDoorNumber().equals("null"))
					doorNo = profileTemplateModel.getDoorNumber();
				if (!profileTemplateModel.getStreet().equals("null"))
					streetName = profileTemplateModel.getStreet();
				if (!doorNo.isEmpty() && !streetName.isEmpty())
					address.setAddress1(doorNo + "," + streetName);
				else if (!doorNo.isEmpty())
					address.setAddress1(doorNo);
				else if (!streetName.isEmpty())
					address.setAddress1(streetName);
				address.setType(AddressType.OFFICE.name());
				addressList.add(address);
			}
			BoardingRequestDomain boardingRequestDomain = boardingRequestDAO.getRequest(
					profileTemplateModel.getCityId(), profileTemplateModel.getMobileNumber(),
					profileTemplateModel.getEmailId(), Integer.parseInt(Role.FRANCHISE.getCode()));
			if (boardingRequestDomain == null) {
				BoardingRequestModel boardingRequestModel = new BoardingRequestModel();
				BeanUtils.copyProperties(profileTemplateModel, boardingRequestModel);
				boardingRequestModel.setEmail(profileTemplateModel.getEmailId());
				boardingRequestModel.setOnHoldReason("DEFAULT");
				String requestNumber = saveRequest(boardingRequestModel);
				boardingRequestDomain = boardingRequestDAO.getRequest(requestNumber);
			}
			boardingRequestDAO.updateEnquiryStatus(boardingRequestDomain.getUuid(), EnquiryRole.INPROCESS.name());
			BeanUtils.copyProperties(profileTemplateModel.getFranchise(), franchise);
			BeanUtils.copyProperties(profileTemplateModel.getBank(), bank);
			franchise.setBoardingEnquiryId(boardingRequestDomain.getUuid());
			franchise.setReason("Registeration");
			bankList.add(bank);
			profile.setFranchise(franchise);
			profile.setBankAccount(bankList);
			profile.setAddress(addressList);
			profile = companyProfileService.save(profile, existingProfileDomain);
			createdAndModifiedDate(profileTemplateModel, profile.getId());
			createdAndModifiedDateByRole(profileTemplateModel, profile.getId(), role);
			if (profileTemplateModel.isStatus()) {
				DBUpdate dbUpdate = new DBUpdate();
				dbUpdate.setTableName("profilerole");
				Map<String, Object> expression = new HashMap<String, Object>();
				expression.put("isActive", true);
				dbUpdate.setExpression(expression);
				Map<String, Object> conditions = new HashMap<String, Object>();
				conditions.put("profileId", profile.getId());
				conditions.put("roleId", Role.FRANCHISE.getCode());
				dbUpdate.setConditions(conditions);
				dbUpdateService.dbUpdate(dbUpdate);
			}
		}
		return profile;
	}

	private Profile saveDriver(MultipartFile file, Role role, List<ProfileTemplateModel> profileTemplateModelList)
			throws Exception {
		Profile profile = null;
		Profile profileModel = null;
		List<Address> addressList = null;
		List<BankAccountModel> bankList = null;
		Driver driver = null;
		String status;
		String requestedBy;
		for (ProfileTemplateModel customerTemplateModel : profileTemplateModelList) {
			profile = new Profile();
			profileModel = new Profile();
			Address address = new Address();
			BankAccountModel bank = new BankAccountModel();
			addressList = new ArrayList<Address>();
			bankList = new ArrayList<BankAccountModel>();
			ProfileDomainV2 existingProfileDomain = new ProfileDomainV2();
			BeanUtils.copyProperties(customerTemplateModel, profile);
			if (customerTemplateModel.getDob() != null || !customerTemplateModel.getDob().equals("0")) {
				profile.setDob(customerTemplateModel.getDob());
			}
			if (customerTemplateModel.getLastName().equals("null"))
				profile.setLastName("");
			profile.setProfileSource("web");
			if (customerTemplateModel.getCountryId() != 0 && customerTemplateModel.getStateId() != 0
					&& customerTemplateModel.getCityId() != 0) {
				String doorNo = "", streetName = "";
				BeanUtils.copyProperties(customerTemplateModel, address);
				if (!customerTemplateModel.getDoorNumber().equals("null"))
					doorNo = customerTemplateModel.getDoorNumber();
				if (!customerTemplateModel.getStreet().equals("null"))
					streetName = customerTemplateModel.getStreet();
				if (!doorNo.isEmpty() && !streetName.isEmpty())
					address.setAddress1(doorNo + "," + streetName);
				else if (!doorNo.isEmpty())
					address.setAddress1(doorNo);
				else if (!streetName.isEmpty())
					address.setAddress1(streetName);
				address.setType(AddressType.HOME.name());
				addressList.add(address);
			}
			BeanUtils.copyProperties(customerTemplateModel.getBank(), bank);
			bankList.add(bank);
			profile.setBankAccount(bankList);
			profile.setAddress(addressList);
			profile.setDriver(customerTemplateModel.getDriver());
			status = customerTemplateModel.getDriver().getStatus();
			requestedBy = customerTemplateModel.getDriver().getRequestedBy();
			profileModel = driverService.save(profile, existingProfileDomain);
			createdAndModifiedDate(customerTemplateModel, profileModel.getId());
			createdAndModifiedDateForDriver(profile, status, requestedBy, profile.getId());

			DBUpdate dbUpdate = new DBUpdate();
			dbUpdate.setTableName("profilerole");
			Map<String, Object> expression = new HashMap<String, Object>();
			expression.put("isActive", customerTemplateModel.isStatus());
			SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
			if (customerTemplateModel.getCreationDate() != null) {
				String date = dateFormat.format(customerTemplateModel.getCreationDate());
				expression.put("createdDate", date);
			}
			if (customerTemplateModel.getModificationDate() != null) {
				String date = dateFormat.format(customerTemplateModel.getModificationDate());
				expression.put("modifiedDate", date);
			}
			if (customerTemplateModel.getTokenId() != null || !customerTemplateModel.getTokenId().isEmpty()) {
				if (!customerTemplateModel.getTokenId().equals("null"))
					expression.put("appTokenId", customerTemplateModel.getTokenId());
			}
			if (customerTemplateModel.getWebToken() != null || !customerTemplateModel.getWebToken().isEmpty()) {
				if (!customerTemplateModel.getWebToken().equals("null"))
					expression.put("webTokenId", customerTemplateModel.getWebToken());
			}

			dbUpdate.setExpression(expression);
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("profileId", profile.getId());
			conditions.put("roleId", Role.DRIVER.getCode());
			dbUpdate.setConditions(conditions);
			dbUpdateService.dbUpdate(dbUpdate);
		}
		return profile;
	}

	private void createdAndModifiedDate(ProfileTemplateModel customerTemplateModel, String profileId) throws Exception {
		// Update method
		SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
		DBUpdate dbUpdate = new DBUpdate();
		dbUpdate.setTableName("profile");

		Map<String, Object> expression = new HashMap<String, Object>();
		if (customerTemplateModel.getCreationDate() != null) {
			String date = dateFormat.format(customerTemplateModel.getCreationDate());
			expression.put("createdDate", date);
		}
		if (customerTemplateModel.getModificationDate() != null) {
			String date = dateFormat.format(customerTemplateModel.getModificationDate());
			expression.put("modifiedDate", date);
		}
		dbUpdate.setExpression(expression);
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("id", profileId);
		dbUpdate.setConditions(conditions);
		dbUpdateService.dbUpdate(dbUpdate);
	}

	private void createdAndModifiedDateForDriver(Profile profile, String status, String requestedBy, String profileId)
			throws Exception {
		// Update method
		Driver driver = profile.getDriver();

		SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
		DBUpdate dbUpdate = new DBUpdate();
		dbUpdate.setTableName("drivers");

		Map<String, Object> expression = new HashMap<String, Object>();
		if (driver.getCreatedDate() != null) {
			String date = dateFormat.format(driver.getCreatedDate());
			expression.put("createdDate", date);
		}
		if (driver.getModifiedDate() != null) {
			String date = dateFormat.format(driver.getModifiedDate());
			expression.put("modifiedDate", date);
		}
		expression.put("status", status);
		expression.put("requestedBy", requestedBy);
		dbUpdate.setExpression(expression);
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("profileId", profileId);
		dbUpdate.setConditions(conditions);
		franchiseCommonService.dbUpdate(dbUpdate);
	}

	private void createdAndModifiedDateByRole(ProfileTemplateModel customerTemplateModel, String profileId, Role role)
			throws Exception {
		// Update method
		SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
		DBUpdate dbUpdate = new DBUpdate();
		if (role.equals(Role.CUSTOMER))
			dbUpdate.setTableName("customer");
		else if (role.equals(Role.FRANCHISE))
			dbUpdate.setTableName("franchise");
		else if (role.equals(Role.DRIVER))
			dbUpdate.setTableName("driver");
		Map<String, Object> expression = new HashMap<String, Object>();
		if (customerTemplateModel.getCreationDate() != null) {
			String date = dateFormat.format(customerTemplateModel.getCreationDate());
			expression.put("createdDate", date);
		}
		if (customerTemplateModel.getModificationDate() != null) {
			String date = dateFormat.format(customerTemplateModel.getModificationDate());
			expression.put("modifiedDate", date);
		}
		dbUpdate.setExpression(expression);
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("profileId", profileId);
		dbUpdate.setConditions(conditions);
		dbUpdateService.dbUpdate(dbUpdate);
	}

	private List<ProfileTemplateModel> covertDocToList(MultipartFile file, Role role) throws Exception {
		ProfileTemplateModel commonemplateModel = new ProfileTemplateModel();
		Franchise franchise = new Franchise();
		Driver driver = new Driver();
		Driver driverModel = null;
		Franchise franchiseModel = null;
		BankAccountModel bank = new BankAccountModel();
		BankAccountModel bankModel = null;
		ProfileTemplateModel customerTemplateModel = null;
		List<ProfileTemplateModel> customerList = new ArrayList<ProfileTemplateModel>();
		try {
			Workbook workbook = new XSSFWorkbook(file.getInputStream());
			Sheet sheet = workbook.getSheet(workbook.getSheetName(0));
			Row row = null;
			Iterator<Row> rowIterator = sheet.iterator();
			int rowNumber = 0;
			while (rowIterator.hasNext()) {
				row = rowIterator.next();
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				Iterator<Cell> cellIterator = row.cellIterator();
				int cellIdx = 0;
				System.out.println(cellIdx);
				while (cellIterator.hasNext()) {
					Cell currentCell = cellIterator.next();
					switch (role) {
					case CUSTOMER:
						if (cellIdx >= 0 && cellIdx <= 19)
							migrationUtil.commonProfile(cellIdx, currentCell, commonemplateModel);
						if (cellIdx == 20)
							migrationUtil.customer(cellIdx, currentCell, commonemplateModel);
						break;
					case FRANCHISE:
						if (cellIdx >= 0 && cellIdx <= 19)
							migrationUtil.commonProfile(cellIdx, currentCell, commonemplateModel);
						if (cellIdx >= 20 && cellIdx <= 34)
							migrationUtil.franchise(cellIdx, currentCell, commonemplateModel, franchise, bank);
						break;
					case DRIVER:
						if (cellIdx >= 0 && cellIdx <= 19)
							migrationUtil.commonProfile(cellIdx, currentCell, commonemplateModel);
						if (cellIdx >= 20 && cellIdx <= 49)
							driver(cellIdx, currentCell, commonemplateModel, driver, bank);
						break;
					default:
						break;
					}
					cellIdx++;
				}
				if (Role.CUSTOMER.equals(role)) {
					customerTemplateModel = new ProfileTemplateModel(commonemplateModel.getFirstName(),
							commonemplateModel.getLastName(), commonemplateModel.getMiddleName(),
							commonemplateModel.getRoleId(), commonemplateModel.getStateId(),
							commonemplateModel.getCityId(), commonemplateModel.getCountryId(),
							commonemplateModel.getDoorNumber(), commonemplateModel.getStreet(),
							commonemplateModel.getMobileNumber(), commonemplateModel.getAlternativeNumber(),
							commonemplateModel.getEmailId(), commonemplateModel.getPassword(),
							commonemplateModel.getConfirmPassword(), commonemplateModel.getPincode(),
							commonemplateModel.getDob(), commonemplateModel.getGenderId(),
							commonemplateModel.getTokenId(), commonemplateModel.getWebToken(),
							commonemplateModel.getTermsAndConditionsId(), commonemplateModel.getCreationDate(),
							commonemplateModel.getModificationDate());
				}
				if (Role.FRANCHISE.equals(role)) {
					customerTemplateModel = new ProfileTemplateModel(commonemplateModel.getFirstName(),
							commonemplateModel.getLastName(), commonemplateModel.getMiddleName(),
							commonemplateModel.getRoleId(), commonemplateModel.getStateId(),
							commonemplateModel.getCityId(), commonemplateModel.getCountryId(),
							commonemplateModel.getDoorNumber(), commonemplateModel.getStreet(),
							commonemplateModel.getMobileNumber(), commonemplateModel.getAlternativeNumber(),
							commonemplateModel.getEmailId(), commonemplateModel.getPassword(),
							commonemplateModel.getConfirmPassword(), commonemplateModel.getPincode(),
							commonemplateModel.getDob(), commonemplateModel.getGenderId(),
							commonemplateModel.getTokenId(), commonemplateModel.getWebToken(),
							commonemplateModel.getCreationDate(), commonemplateModel.getModificationDate(),
							commonemplateModel.isStatus(), commonemplateModel.getPanNumber());
					franchiseModel = new Franchise();
					bankModel = new BankAccountModel();
					BeanUtils.copyProperties(franchise, franchiseModel);
					customerTemplateModel.setFranchise(franchiseModel);
					BeanUtils.copyProperties(bank, bankModel);
					customerTemplateModel.setBank(bankModel);
				}
				if (Role.DRIVER.equals(role)) {
					customerTemplateModel = new ProfileTemplateModel(commonemplateModel.getFirstName(),
							commonemplateModel.getLastName(), commonemplateModel.getMiddleName(),
							commonemplateModel.getRoleId(), commonemplateModel.getStateId(),
							commonemplateModel.getCityId(), commonemplateModel.getCountryId(),
							commonemplateModel.getDoorNumber(), commonemplateModel.getStreet(),
							commonemplateModel.getMobileNumber(), commonemplateModel.getAlternativeNumber(),
							commonemplateModel.getEmailId(), commonemplateModel.getPassword(),
							commonemplateModel.getConfirmPassword(), commonemplateModel.getPincode(),
							commonemplateModel.getDob(), commonemplateModel.getGenderId(),
							commonemplateModel.getTokenId(), commonemplateModel.getWebToken(),
							commonemplateModel.getCreationDate(), commonemplateModel.getModificationDate(),
							commonemplateModel.isStatus(), commonemplateModel.getPanNumber(),
							commonemplateModel.getAadharNumber());
					driverModel = new Driver();
					bankModel = new BankAccountModel();
					BeanUtils.copyProperties(driver, driverModel);
					customerTemplateModel.setDriver(driverModel);
					BeanUtils.copyProperties(bank, bankModel);
					customerTemplateModel.setBank(bankModel);
				}
				customerList.add(customerTemplateModel);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customerList;
	}

	@SuppressWarnings("unlikely-arg-type")
	private void driver(int cellIdx, Cell currentCell, ProfileTemplateModel customerModel, Driver driver,
			BankAccountModel bank) throws Exception {
		switch (cellIdx) {

		case 20:
			if (currentCell.getNumericCellValue() != 0.0)
				customerModel.setStatus(true);
			else
				customerModel.setStatus(false);
			break;
		case 21:
			if (currentCell.getNumericCellValue() != 0.0)
				driver.setDriverId(String.valueOf((int) currentCell.getNumericCellValue()));
			else
				driver.setDriverId(null);
			break;
		case 22:
			if (currentCell.getNumericCellValue() != 0.0)
				driver.setFranchiseId(String.valueOf((int) currentCell.getNumericCellValue()));
			else
				driver.setFranchiseId(null);
			break;
		case 23:
			if (currentCell.getNumericCellValue() != 0.0)
				driver.setBloodId((int) currentCell.getNumericCellValue());
			else
				driver.setBloodId(1);
			break;
		case 24:
			if (currentCell.getNumericCellValue() != 0.0)
				driver.setDriverTypeId((int) currentCell.getNumericCellValue());
			break;

		case 25:
			driver.setWeight(currentCell.getNumericCellValue());
			break;
		case 26:
			driver.setHeight(currentCell.getNumericCellValue());
			break;
		case 27:

			if (currentCell.getNumericCellValue() != 0.0) {
				BigInteger aadharNo = BigDecimal.valueOf(currentCell.getNumericCellValue()).toBigInteger();
				customerModel.setAadharNumber(aadharNo);
			} else
				customerModel.setAadharNumber(BigInteger.ZERO);
			break;
		case 28:
			if (!currentCell.getStringCellValue().equalsIgnoreCase("null")) {
				driver.setIdentificationMark(currentCell.getStringCellValue());
			}
			break;
		case 29:
			if (!currentCell.getStringCellValue().equalsIgnoreCase("null")) {
				driver.setCompanyName(currentCell.getStringCellValue());
			}
			break;
		case 30:
			if (!currentCell.getStringCellValue().equalsIgnoreCase("null")) {
				driver.setCompanyAddress(currentCell.getStringCellValue());
			}
			break;
		case 31:
			if (!currentCell.getStringCellValue().equalsIgnoreCase("null")) {
				driver.setDlNumber(currentCell.getStringCellValue());
			} else
				driver.setDlNumber(null);
			break;
		case 32:
			if (null != currentCell.getDateCellValue()) {
				driver.setDlIssueDate(currentCell.getDateCellValue());
			} else
				driver.setDlIssueDate(null);
			break;
		case 33:
			if (null != currentCell.getDateCellValue()) {
				driver.setDlExpiryDate(currentCell.getDateCellValue());
			} else
				driver.setDlExpiryDate(null);
			break;
		case 34:
			System.out.println(currentCell.getStringCellValue());
			if (!currentCell.getStringCellValue().equalsIgnoreCase("null")) {
				driver.setBadgeNumber(currentCell.getStringCellValue());
			} else
				driver.setBadgeNumber(null);
			break;
		case 35:
			driver.setBadgeExpiryDate(currentCell.getDateCellValue());
			break;
		case 36:
			driver.setIssueRto((int) currentCell.getNumericCellValue());
			break;
		case 37:
			driver.setLicenceCategory((int) currentCell.getNumericCellValue());
			break;
		case 38:
			if (currentCell.getNumericCellValue() != 0.0) {
				BigInteger accountNumber = BigDecimal.valueOf(currentCell.getNumericCellValue()).toBigInteger();
				bank.setAccountNumber(accountNumber);
			}
			break;
		case 39:
			if (currentCell.getNumericCellValue() != 0.0)
				bank.setBankId((int) currentCell.getNumericCellValue());
			break;
		case 40:
			if (!currentCell.getStringCellValue().equalsIgnoreCase("null"))
				bank.setIfscCode(currentCell.getStringCellValue());
			break;
		case 41:
			if (!currentCell.getStringCellValue().equalsIgnoreCase("null"))
				bank.setBranchName(currentCell.getStringCellValue());
			break;
		case 42:
			if (!currentCell.getStringCellValue().equalsIgnoreCase("null"))
				customerModel.setPanNumber(currentCell.getStringCellValue());
			break;
		case 43:
			if (null != currentCell.getDateCellValue()) {
				driver.setCreatedDate(currentCell.getDateCellValue());
			}
			break;
		case 44:
			if (null != currentCell.getDateCellValue()) {
				driver.setModifiedDate(currentCell.getDateCellValue());
			}
			break;
		case 45:
			if (!currentCell.getStringCellValue().equalsIgnoreCase("null")) {
				driver.setStatus(currentCell.getStringCellValue());

			}
			break;
		case 46:
			if (!currentCell.getStringCellValue().equalsIgnoreCase("null")) {
				driver.setRequestedBy(currentCell.getStringCellValue());
			}
			break;
		case 47:
			if (!currentCell.getStringCellValue().equalsIgnoreCase("null")) {
				driver.setRefferenceCode(currentCell.getStringCellValue());
			} else
				driver.setRefferenceCode(null);
			break;
		case 48:
			if (currentCell.getNumericCellValue() != 0.0) {
				driver.setLocationlatitude(currentCell.getNumericCellValue());
			}
			break;
		case 49:
			if (currentCell.getNumericCellValue() != 0.0) {
				driver.setLocationlongitude(currentCell.getNumericCellValue());
			}
			break;
		default:
			break;
		}
	}

	@Override
	public String saveOnboardRequest(MultipartFile file) throws Exception {
		MigrationUtil.hasExcelFormat(file);
		List<BoardingRequestTemplateModel> boardingRequestTemplateModelList = covertOnboardDocToList(file);
		for (BoardingRequestTemplateModel boardingRequestTemplateModel : boardingRequestTemplateModelList) {
			BoardingRequestModel boardingRequestModel = new BoardingRequestModel();
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.DB_UPDATE_SERVICE.name(),
					SeverityTypes.INFORMATIONAL.ordinal(),
					"Exception in update  data " + JsonUtil.toJsonString(boardingRequestModel)));
			BeanUtils.copyProperties(boardingRequestTemplateModel, boardingRequestModel);
			boardingRequestModel.setFirstName(boardingRequestTemplateModel.getName());
			if (boardingRequestTemplateModel.getStatus() == 0)
				boardingRequestModel.setStatus(EnquiryStatus.PENDING.name());
			else if (boardingRequestTemplateModel.getStatus() == 2 || boardingRequestTemplateModel.getStatus() == 1)
				boardingRequestModel.setStatus(EnquiryStatus.INPROCESS.name());
			else if (boardingRequestTemplateModel.getStatus() == 3)
				boardingRequestModel.setStatus(EnquiryStatus.COMPLETED.name());
			else if (boardingRequestTemplateModel.getStatus() == 4)
				boardingRequestModel.setStatus(EnquiryStatus.ONHOLD.name());
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.DB_UPDATE_SERVICE.name(),
					SeverityTypes.INFORMATIONAL.ordinal(),
					"saveRequest to table" + JsonUtil.toJsonString(boardingRequestModel)));

			String requestNumber = saveRequest(boardingRequestModel);
			if (!requestNumber.isEmpty()) {
				DBUpdate dbUpdate = new DBUpdate();
				dbUpdate.setTableName("boardingrequest");

				Map<String, Object> expression = new HashMap<String, Object>();
				if (boardingRequestTemplateModel.getCreationDate() != null)
					expression.put("createdDate", boardingRequestTemplateModel.getCreationDate());
				System.out.println("boardingRequestModel.getStatus()" + boardingRequestModel.getStatus());
				expression.put("status", boardingRequestModel.getStatus());
				dbUpdate.setExpression(expression);
				Map<String, Object> conditions = new HashMap<String, Object>();
				conditions.put("requestNumber", requestNumber);
				dbUpdate.setConditions(conditions);
				dbUpdateService.dbUpdate(dbUpdate);
			}
		}
		return "Success";
	}

	private String saveRequest(BoardingRequestModel boardingRequestModel) throws Exception {
		BoardingRequestDomain boardingRequestDomain = new BoardingRequestDomain();
		String requestNumber = idGeneration.generateRequestNumber();
		boardingRequestModel.setRequestNumber(requestNumber);
		BeanUtils.copyProperties(boardingRequestModel, boardingRequestDomain);
		boardingRequestDomain.setUuid(CommonUtils.generateRandomId());
		boardingRequestDAO.saveRequest(boardingRequestDomain);
		return requestNumber;
	}

	private List<BoardingRequestTemplateModel> covertOnboardDocToList(MultipartFile file) throws Exception {
		BoardingRequestTemplateModel boardingRequestTemplateModel = new BoardingRequestTemplateModel();
		List<BoardingRequestTemplateModel> boardingRequestTemplateModelList = new ArrayList<BoardingRequestTemplateModel>();
		try {
			Workbook workbook = new XSSFWorkbook(file.getInputStream());
			Sheet sheet = workbook.getSheet(workbook.getSheetName(0));
			Row row = null;
			Iterator<Row> rowIterator = sheet.iterator();
			int rowNumber = 0;
			while (rowIterator.hasNext()) {
				row = rowIterator.next();
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				Iterator<Cell> cellIterator = row.cellIterator();
				int cellIdx = 0;
				while (cellIterator.hasNext()) {
					Cell currentCell = cellIterator.next();
					switch (cellIdx) {
					case 0:
						if (!currentCell.getStringCellValue().isEmpty())
							boardingRequestTemplateModel.setMobileNumber(currentCell.getStringCellValue());
						break;
					case 1:
						if (!currentCell.getStringCellValue().isEmpty())
							boardingRequestTemplateModel.setName(currentCell.getStringCellValue());
						break;
					case 2:
						if (!currentCell.getStringCellValue().isEmpty())
							boardingRequestTemplateModel.setEmail(currentCell.getStringCellValue());
						break;
					case 3:
						if (!currentCell.getStringCellValue().isEmpty())
							boardingRequestTemplateModel.setMessage(currentCell.getStringCellValue());
						break;
					case 4:
						if (currentCell.getNumericCellValue() != 0.0) {
							String status = NumberToTextConverter.toText(currentCell.getNumericCellValue());
							boardingRequestTemplateModel.setStatus(Integer.parseInt(status));
						} else
							boardingRequestTemplateModel.setStatus(0);
						break;
					case 5:
						if (currentCell.getNumericCellValue() != 0.0) {
							String country = NumberToTextConverter.toText(currentCell.getNumericCellValue());
							boardingRequestTemplateModel.setCountryId(Integer.parseInt(country));
						} else
							boardingRequestTemplateModel.setCountryId(0);
						break;
					case 6:
						if (currentCell.getNumericCellValue() != 0.0) {
							String state = NumberToTextConverter.toText(currentCell.getNumericCellValue());
							boardingRequestTemplateModel.setStateId(Integer.parseInt(state));
						} else
							boardingRequestTemplateModel.setStateId(0);
						break;
					case 7:
						if (currentCell.getNumericCellValue() != 0.0) {
							String city = NumberToTextConverter.toText(currentCell.getNumericCellValue());
							boardingRequestTemplateModel.setCityId(Integer.parseInt(city));
						} else
							boardingRequestTemplateModel.setCityId(0);
						break;
					case 8:
						if (!currentCell.getStringCellValue().isEmpty())
							boardingRequestTemplateModel.setRequestNumber(currentCell.getStringCellValue());
						break;
					case 9:
						String roleId = NumberToTextConverter.toText(currentCell.getNumericCellValue());
						boardingRequestTemplateModel.setRoleId(Integer.parseInt(roleId));
						break;
					case 10:
						if (!currentCell.getStringCellValue().isEmpty())
							boardingRequestTemplateModel.setOnHoldReason(currentCell.getStringCellValue());
						break;
					case 11:
						if (!currentCell.getStringCellValue().isEmpty())
							boardingRequestTemplateModel.setRefferenceCode(currentCell.getStringCellValue());
						break;
					case 12:
						if (!currentCell.getStringCellValue().isEmpty())
							boardingRequestTemplateModel.setCreationDate(currentCell.getStringCellValue());
						break;
					default:
						break;
					}
					cellIdx++;
				}
				BoardingRequestTemplateModel boardingRTemplateModel = new BoardingRequestTemplateModel(
						boardingRequestTemplateModel.getMobileNumber(), boardingRequestTemplateModel.getName(),
						boardingRequestTemplateModel.getEmail(), boardingRequestTemplateModel.getMessage(),
						boardingRequestTemplateModel.getRoleId(), boardingRequestTemplateModel.getCountryId(),
						boardingRequestTemplateModel.getStateId(), boardingRequestTemplateModel.getCityId(),
						boardingRequestTemplateModel.getStatus(), boardingRequestTemplateModel.getRequestNumber(),
						boardingRequestTemplateModel.getCreationDate(), boardingRequestTemplateModel.getOnHoldReason(),
						boardingRequestTemplateModel.getRefferenceCode());
				boardingRequestTemplateModelList.add(boardingRTemplateModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boardingRequestTemplateModelList;
	}

	@Override
	public List<ProfileImageTemplateModel> saveImagesByRole(MultipartFile file, String roleId) throws Exception {
		MigrationUtil.hasExcelFormat(file);
		Role role = Role.getRole(roleId);
		if (null == role)
			throw new ROLE_NOT_FOUND(roleId + "");
		List<ProfileImageTemplateModel> profileImageTemplateModelList = null, profileImageTemplateModels = null;
		switch (role) {
		case CUSTOMER:
			break;
		case FRANCHISE:
			profileImageTemplateModelList = migrationUtil.covertDocImagesToList(file, role);
			profileImageTemplateModels = franchiseImage(profileImageTemplateModelList);
			break;
		case DRIVER:
			break;
		case FLEET_OPERATOR:
		case ENTERPRISE:
		case WAREHOUSE:
			break;
		default:
			break;
		}
		return profileImageTemplateModels;
	}

	private List<ProfileImageTemplateModel> franchiseImage(
			List<ProfileImageTemplateModel> profileImageTemplateModelList) throws Exception {
		int roleId = 0;
		List<ProfileImageTemplateModel> profileImageTemplateModels = new ArrayList<ProfileImageTemplateModel>();
		for (ProfileImageTemplateModel profileImageTemplateModel : profileImageTemplateModelList) {
			ProfileImageDomain profileImageDomain = new ProfileImageDomain();
			roleId = Integer.parseInt(Role.FRANCHISE.getCode());
			ProfileDomainV2 profileDomain = profileDAOV2.getProfileDetailsByCompanyProfileId(roleId,
					profileImageTemplateModel.getFranchiseId());
			if (profileDomain != null) {
				String fileExt = "";
				int ver = 1;
				String version = String.valueOf(ver);
				RoleDomain role = roleDAO.getRoleName(roleId);
				fileExt = CommonUtils.getLastString(profileImageTemplateModel.getType(), "/");
				final String SUFFIX = "/";
				String folderName = null;
				String roleName = role.getRoleName().replaceAll("\\s", "_");
				folderName = roleName;
				String fName = roleName + "_" + profileDomain.getFirstName() + "_"
						+ profileImageTemplateModel.getCategory() + "_" + version + "." + fileExt;
				BeanUtils.copyProperties(profileImageTemplateModel, profileImageDomain);
				profileImageDomain.setRoleId(roleId);
				profileImageDomain.setProfileId(profileDomain.getId());
				profileImageDomain.setName(fName);
				String fileName = folderName + SUFFIX + profileImageDomain.getProfileId() + SUFFIX
						+ profileImageDomain.getCategory() + SUFFIX + profileImageDomain.getName();
				String bucketName = mmgProperties.getBucketName();
				Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PROFILE_SERVICE.name(),
						SeverityTypes.DEBUG.ordinal(),
						"To fetch the image from aws s3 bucket by passing the image path "
								+ JsonUtil.toJsonString(profileImageTemplateModel.getPath()) + "filename"
								+ JsonUtil.toJsonString(fName)));
				MultipartFile multipartFile = getFileAndUploadImage("tieups-mmg", profileImageTemplateModel.getPath(),
						profileImageTemplateModel.getName());
				if (multipartFile != null) {
					profileImageDomain.setSize((double) multipartFile.getSize());
					profileImageDomain.setName(fName);
					profileImageDomain.setPath(fileName);
					profileImageDomain.setIsActive(true);
					profileImageDAO.saveImage(profileImageDomain);
					uploadFile(fileName, multipartFile, bucketName);
				} else
					profileImageTemplateModels.add(profileImageTemplateModel);
			}
		}
		return profileImageTemplateModels;
	}

	private MultipartFile getFileAndUploadImage(String bucketName, String pathName, String FileName)
			throws IOException {
		MultipartFile multipartFile = null;
		try {
			AWSCredentials credentials = new BasicAWSCredentials(mmgProperties.getAccessKey(),
					mmgProperties.getSecretKey());
			@SuppressWarnings("deprecation")
			AmazonS3 s3client = new AmazonS3Client(credentials);
			s3client.setRegion(Region.getRegion(Regions.AP_SOUTH_1));
			S3Object s3Object = s3client.getObject(bucketName, pathName);
			if (s3Object != null) {
				S3ObjectInputStream inputStream = s3Object.getObjectContent();
				File file = new File(bucketName);
				try (OutputStream outputStream = new FileOutputStream(file)) {
					IOUtils.copy(inputStream, outputStream);
				} catch (FileNotFoundException e) {
					throw new NOT_FOUND("File not found");
				}
				FileInputStream fileInputStream = new FileInputStream(file);
				multipartFile = new MockMultipartFile(file.getName(), file.getName(),
						s3Object.getObjectMetadata().getContentType(), fileInputStream);
				file.delete();
			}
			return multipartFile;
		} catch (Exception e) {
			return multipartFile;
		}
	}

	private void uploadFile(String fileName, MultipartFile file, String bucketName) throws IOException {
		try {
			AWSCredentials credentials = new BasicAWSCredentials(mmgProperties.getAccessKey(),
					mmgProperties.getSecretKey());
			@SuppressWarnings("deprecation")
			AmazonS3 s3client = new AmazonS3Client(credentials);
			s3client.setRegion(Region.getRegion(Regions.AP_SOUTH_1));

			ObjectMetadata md = new ObjectMetadata();
			md.setContentLength(file.getSize());
			md.setContentType(file.getContentType());

			InputStream is = file.getInputStream();
			s3client.putObject(new PutObjectRequest(bucketName, fileName, is, md));
		} catch (Exception e) {
			throw new FILE_UPLOAD_FAILED();
		}

	}

}
