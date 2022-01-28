package com.atpl.mmg.AandA.service.customerlead;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.atpl.mmg.AandA.constant.CustomLeadStatus;
import com.atpl.mmg.AandA.dao.customerlead.CustomerLeadDAO;
import com.atpl.mmg.AandA.dao.dashboard.DashboardDAO;
import com.atpl.mmg.AandA.dao.profile.ProfileDAOV2;
import com.atpl.mmg.AandA.domain.DBUpdate;
import com.atpl.mmg.AandA.domain.customerlead.CustomerLeadDomain;
import com.atpl.mmg.AandA.domain.dashboard.DashboardDomain;
import com.atpl.mmg.AandA.domain.dashboard.LeadDashboardDomain;
import com.atpl.mmg.AandA.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.AandA.mapper.customerlead.CustomerLeadMapper;
import com.atpl.mmg.AandA.model.customerlead.CustomerLeadModel;
import com.atpl.mmg.AandA.model.customerlead.CustomerLeadTemplateModel;
import com.atpl.mmg.AandA.model.profile.ListDto;
import com.atpl.mmg.AandA.service.DBUpdateService;
import com.atpl.mmg.AandA.service.profile.AdminCommonService;
import com.atpl.mmg.AandA.service.profile.ProfileUtil;
import com.atpl.mmg.AandA.utils.CommonUtils;

@Service("customerLeadService")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CustomerLeadServiceImpl implements CustomerLeadService {

	@Autowired
	CustomerLeadDAO customerLeadDAO;

	@Autowired
	CustomerLeadMapper customerLeadMapper;

	@Autowired
	DBUpdateService dbUpdateService;

	@Autowired
	AdminCommonService adminCommonService;

	@Autowired
	ProfileDAOV2 profileDAOV2;

	@Autowired
	DashboardDAO dashboardDAO;
	
	@Autowired
	ProfileUtil profileUtil;

	@Override
	public String uploadCustomerLeadsFile(MultipartFile file, String bdmId) throws Exception {
		hasExcelFormat(file);
		if (bdmId.isEmpty() || null == bdmId)
			throw new NOT_FOUND("Please mention uploaded BdmId");
		List<CustomerLeadDomain> customerLeadDomainList = covertDocToList(file, bdmId);
		return customerLeadDAO.save(customerLeadDomainList);
	}

	public static boolean hasExcelFormat(MultipartFile file) throws Exception {
		String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		if (!TYPE.equals(file.getContentType())) {
			throw new NOT_FOUND("Please upload the excel file");
		}
		return true;
	}

	private List<CustomerLeadDomain> covertDocToList(MultipartFile file, String bdmId) throws Exception {
		CustomerLeadTemplateModel customerLeadTemplateModel = new CustomerLeadTemplateModel();
		List<CustomerLeadDomain> customerLeadDomainList = new ArrayList<CustomerLeadDomain>();
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
						customerLeadTemplateModel.setState(currentCell.getStringCellValue());
						break;
					case 1:
						customerLeadTemplateModel.setPlatform(currentCell.getStringCellValue());
						break;
					case 2:
						customerLeadTemplateModel.setFirstName(currentCell.getStringCellValue());
						break;
					case 3:
						customerLeadTemplateModel.setLastName(currentCell.getStringCellValue());
						break;
					case 4:
						String mobileNumber = NumberToTextConverter.toText(currentCell.getNumericCellValue());
						BigInteger mobileNo = new BigInteger(mobileNumber);
						customerLeadTemplateModel.setMobileNumber(mobileNo);
						break;
					default:
						break;
					}
					cellIdx++;
				}

				int stateId = adminCommonService.getStateId(customerLeadTemplateModel.getState());
				CustomerLeadDomain customerLeadDomain = new CustomerLeadDomain(CommonUtils.generateRandomId(),
						customerLeadTemplateModel.getPlatform(), customerLeadTemplateModel.getFirstName(),
						customerLeadTemplateModel.getLastName(), bdmId, stateId,
						customerLeadTemplateModel.getMobileNumber(), CustomLeadStatus.PENDING.name());
				customerLeadDomainList.add(customerLeadDomain);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customerLeadDomainList;
	}

	@Override
	public CustomerLeadModel getCustomerLeadsByUuId(String uuid) throws Exception {
		CustomerLeadDomain customerLeadDomain = customerLeadDAO.getCustomerLeadsByUuId(uuid);
		CustomerLeadModel customerLeadodel = new CustomerLeadModel();
		BeanUtils.copyProperties(customerLeadDomain, customerLeadodel);
		return customerLeadodel;
	}

	@Override
	public String update(CustomerLeadModel customerLeadodel) throws Exception {
		validateCustomerLead(customerLeadodel);
		customerLeadDAO.getCustomerLeadsByUuId(customerLeadodel.getUuid());
		CustomerLeadDomain customerLeadDomain = new CustomerLeadDomain();
		BeanUtils.copyProperties(customerLeadodel, customerLeadDomain);
		return customerLeadDAO.update(customerLeadDomain);
	}

	@Override
	public ListDto getCustomerLeadsByStsAndAssignedId(String status, String assignedId,Map<String, String> reqParam) throws Exception {
		int lowerBound = 0;
		int upperBound = 0;
		ListDto listDto = new ListDto();
		if (reqParam.size() > 0) {
			Map<String, Integer> paginationCount = profileUtil.getPagination(reqParam);
			if (paginationCount.size() > 0) {
				lowerBound = paginationCount.get("lowerBound");
				upperBound = paginationCount.get("upperBound");
			}
		}
		List<CustomerLeadDomain> customerLeadDomain = customerLeadDAO.getCustomerLeadsByStsAndAssignedId(status,
				assignedId,lowerBound,upperBound);
		List<CustomerLeadModel> customerLeadModel = customerLeadMapper.entityList(customerLeadDomain);
		DashboardDomain dashboardDomain = dashboardDAO.getCustomerLeadCountByStsAndAssignedId(status, assignedId);
		listDto.setList(customerLeadModel);
		listDto.setTotalSize(dashboardDomain.getTotal());
		return listDto;
	}

	public void validateCustomerLead(CustomerLeadModel customerLeadModel) {
		if (null == customerLeadModel.getUuid() || customerLeadModel.getUuid().isEmpty())
			throw new NOT_FOUND("Please enter uuid");
		if (null == customerLeadModel.getPlatform())
			throw new NOT_FOUND("Please enter platform");
		if (null == customerLeadModel.getFirstName())
			throw new NOT_FOUND("Please enter firstName");
		if (0 == customerLeadModel.getCityId())
			throw new NOT_FOUND("Please enter city");
		if (0 == customerLeadModel.getStateId())
			throw new NOT_FOUND("Please enter state");
		if (0 == customerLeadModel.getCountryId())
			throw new NOT_FOUND("Please enter country");
		if (null == customerLeadModel.getPincode())
			throw new NOT_FOUND("Please enter pincode");
		if (null == customerLeadModel.getEmailId())
			throw new NOT_FOUND("Please enter emailId");
		if (null == customerLeadModel.getLeadStatusId())
			throw new NOT_FOUND("Please select lead status");
		if (null == customerLeadModel.getLeadRemarksId())
			throw new NOT_FOUND("Please select lead remarks");
		if (null == customerLeadModel.getLeadProfessionId())
			throw new NOT_FOUND("Please select lead profession");
		if (null == customerLeadModel.getCallDate())
			throw new NOT_FOUND("Please enter call Date");
//		if (!DateUtility.isThisDateValid(customerLeadModel.getCallDate(), DateUtility.DATE_FORMAT_DD_MM_YYYY))
//			throw new NOT_FOUND("Date formate not valid");
	}

	@Override
	public ListDto getCustomerLeadsByStsAndUploadedById(String status, Map<String, String> reqParam)
			throws Exception {
		String uploadedById = null;
		int lowerBound = 0;
		int upperBound = 0;
		int totalSize = 0;
		if (reqParam.size() > 0) {
			if (reqParam.containsKey("uploadedById")) {
				uploadedById = reqParam.get("uploadedById");
			} 
			Map<String, Integer> paginationCount = profileUtil.getPagination(reqParam);
			if (paginationCount.size() > 0) {
				lowerBound = paginationCount.get("lowerBound");
				upperBound = paginationCount.get("upperBound");
			}
		}
		List<CustomerLeadDomain> customerLeadDomain = new ArrayList<CustomerLeadDomain>();
		List<CustomerLeadModel> customerLeadList = new ArrayList<CustomerLeadModel>();
		ListDto listDto = new ListDto();
		if (status.equalsIgnoreCase("all") && uploadedById == null) {
			customerLeadDomain = customerLeadDAO.getCustomerLeads(lowerBound,upperBound);
			LeadDashboardDomain dashboardTotalDomain = dashboardDAO.getCustomerLead();
			totalSize = dashboardTotalDomain.getTotal();
		}
		else {
			customerLeadDomain = customerLeadDAO.getCustomerLeadsByStsAndUploadedById(status, uploadedById,lowerBound,upperBound);
			DashboardDomain dashboardDomain = dashboardDAO.getCustomerLeadsByStsAndAssignedIdAndUploadedId(status,null,uploadedById);
			totalSize = dashboardDomain.getTotal();
		}
		customerLeadList = customerLeadMapper.entityList(customerLeadDomain);
		listDto.setTotalSize(totalSize);
		listDto.setList(customerLeadList);
		return listDto;
	}

	@Override
	public String businessExecutiveAssignByListOfUuid(CustomerLeadTemplateModel customerLeadModel) throws Exception {
		validatebusinessExceutiveAssigned(customerLeadModel);
		profileDAOV2.getProfileDetails(customerLeadModel.getAssignedId());
//		commonGenericApi.getProfileDetails(customerLeadModel.getAssignedId());
		String response = null;
		for (String uuid : customerLeadModel.getUuid()) {
			customerLeadDAO.getCustomerLeadsByUuId(uuid);
			CustomerLeadDomain customerLeadDomain = new CustomerLeadDomain();
			BeanUtils.copyProperties(customerLeadModel, customerLeadDomain);
			customerLeadDomain.setUuid(uuid);
			response = customerLeadDAO.updateAssignDetailsByUuid(customerLeadDomain);
		}
		return response;
	}

	private boolean validatebusinessExceutiveAssigned(CustomerLeadTemplateModel customerLeadModel) {
		if (customerLeadModel.getUuid().isEmpty())
			throw new NOT_FOUND("Please mention uuid");
		if (null == customerLeadModel.getAssignedId() || customerLeadModel.getAssignedId().isEmpty())
			throw new NOT_FOUND("Please mention assigned Id");
		if (null == customerLeadModel.getAssignedDate())
			throw new NOT_FOUND("Please mention assigned Date");
		if (CommonUtils.isNullCheck(customerLeadModel.getStatus()))
			throw new NOT_FOUND("Please mention the status");
		else {
			checkStatus(customerLeadModel.getStatus());
		}
//		if (!DateUtility.isThisDateValid(customerLeadModel.getAssignedDate(), DateUtility.DATE_FORMAT_DD_MM_YYYY))
//			throw new NOT_FOUND("Date formate not valid");
		return true;
	}

	private void checkStatus(String status) {
		Set<String> enumNames = new HashSet<String>();
		for (CustomLeadStatus keyValue : CustomLeadStatus.values()) {
			enumNames.add(keyValue.name());
		}
		if (!enumNames.contains(status)) {
			throw new NOT_FOUND("Please mention the proper status");
		}
	}

	@Override
	public ListDto getCustomerLeadsByStsAndAssignedIdAndUploadedId(String status, String assignedId, String uploadedId,Map<String, String> reqParam)
			throws Exception {
		int lowerBound = 0;
		int upperBound = 0;
		ListDto listDto = new ListDto();
		if (reqParam.size() > 0) {
			Map<String, Integer> paginationCount = profileUtil.getPagination(reqParam);
			if (paginationCount.size() > 0) {
				lowerBound = paginationCount.get("lowerBound");
				upperBound = paginationCount.get("upperBound");
			}
		}
		List<CustomerLeadDomain> customerLeadDomain = customerLeadDAO
				.getCustomerLeadsByStsAndAssignedIdAndUploadedId(status, assignedId, uploadedId,lowerBound,upperBound);
		List<CustomerLeadModel> customerLeadModel = customerLeadMapper.entityList(customerLeadDomain);
		DashboardDomain dashboardDomain = dashboardDAO.getCustomerLeadsByStsAndAssignedIdAndUploadedId(status,
				assignedId, uploadedId);
		listDto.setList(customerLeadModel);
		listDto.setTotalSize(dashboardDomain.getTotal());
		return listDto;
	}

	@Override
	public String updateStsAndCallDateByUuid(CustomerLeadModel customerLeadModel) throws Exception {
		if (null == customerLeadModel.getUuid() || customerLeadModel.getUuid().isEmpty())
			throw new NOT_FOUND("Please mention uuid");
		if (null == customerLeadModel.getCallDate())
			throw new NOT_FOUND("Please mention Call Date");
//		if (!DateUtility.isThisDateValid(customerLeadModel.getCallDate(), DateUtility.DATE_FORMAT_DD_MM_YYYY))
//			throw new NOT_FOUND("Date formate not valid");
		if (null == customerLeadModel.getStatus())
			throw new NOT_FOUND("Please Enter status");

		DBUpdate dbUpdate = new DBUpdate();
		dbUpdate.setTableName("customerlead");

		Map<String, Object> expression = new HashMap<String, Object>();
		expression.put("status", customerLeadModel.getStatus());
		expression.put("callDate", customerLeadModel.getCallDate());
		dbUpdate.setExpression(expression);

		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("uuid", customerLeadModel.getUuid());
		dbUpdate.setConditions(conditions);

		String dbUpdateResp = dbUpdateService.dbUpdate(dbUpdate);
		return dbUpdateResp;
	}
}
