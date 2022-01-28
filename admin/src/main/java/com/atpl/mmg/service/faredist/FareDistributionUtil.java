package com.atpl.mmg.service.faredist;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import com.atpl.mmg.constant.BookingStatus;
import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.constant.Role;
import com.atpl.mmg.dao.city.CityDAO;
import com.atpl.mmg.dao.country.CountryDAO;
import com.atpl.mmg.dao.faredist.FareDistributionDAO;
import com.atpl.mmg.dao.faredisttype.FareDistributionTypeDAO;
import com.atpl.mmg.dao.fdtransdetail.FareDistributionTransactionDetailDAO;
import com.atpl.mmg.dao.state.StateDAO;
import com.atpl.mmg.domain.city.CityDomain;
import com.atpl.mmg.domain.country.CountryDomain;
import com.atpl.mmg.domain.faredist.FareDistributionDomain;
import com.atpl.mmg.domain.faredisttype.FareDistributionTypeDomain;
import com.atpl.mmg.domain.fdtrans.FareDistributionTransactionDomain;
import com.atpl.mmg.domain.fdtransdetail.FareDistributionTransactionDetailDomain;
import com.atpl.mmg.domain.state.StateDomain;
import com.atpl.mmg.exception.MmgRestException.ALREADY_EXCEEDED;
import com.atpl.mmg.exception.MmgRestException.ALREADY_EXIST;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.UPDATE_OTHER_ROLE_PERCENTAGE;
import com.atpl.mmg.model.booking.BookingModel;
import com.atpl.mmg.model.faredist.EarningWithTypeModel;
import com.atpl.mmg.model.faredist.EarningsModel;
import com.atpl.mmg.model.faredist.FareDistributionDTo;
import com.atpl.mmg.model.faredist.FareDistributionModel;
import com.atpl.mmg.model.faredist.FareDistributionRolePercentage;
import com.atpl.mmg.model.faredist.FareDistributionRolePercentageDTo;
import com.atpl.mmg.model.faredist.RolePercentage;
import com.atpl.mmg.model.faredist.RolePercentageDTo;
import com.atpl.mmg.model.faredisttype.FareDistributionTypeModel;
import com.atpl.mmg.model.profile.FranchiseModel;
import com.atpl.mmg.model.profile.ProfileModel;
import com.atpl.mmg.model.profile.RoleModel;
import com.atpl.mmg.service.auth.AdminAuthService;
import com.atpl.mmg.utils.CommonUtils;
import com.atpl.mmg.utils.DateUtility;

@Component
public class FareDistributionUtil {

	@Autowired
	AdminAuthService authService;

	@Autowired
	FareDistributionTypeDAO fareDistributionTypeDAO;

	@Autowired
	FareDistributionDAO fareDistributionDAO;

	@Autowired
	FareDistributionTransactionDetailDAO fDTransactionDetailDAO;

	@Autowired
	BookingCommonService bookingCommonService;

	@Autowired
	CountryDAO countryDAO;

	@Autowired
	StateDAO stateDao;

	@Autowired
	CityDAO cityDao;

	public boolean validateFareDistribution(FareDistributionModel fareDistributionModel,
			FareDistributionDTo fareDistributionDTo, boolean isUpdate) throws Exception {
		if (isUpdate) {
			if (fareDistributionDTo.getFareDistribution().isEmpty())
				throw new NOT_FOUND("Please mention fare distribution type and Role and Percentages");
			validateFranchiseId(fareDistributionDTo.getFranchiseId());
		} else {
			if (fareDistributionModel.getFareDistribution().isEmpty())
				throw new NOT_FOUND("Please mention fare distribution type and Role and Percentages");
			validateFranchiseId(fareDistributionModel.getFranchiseId());
		}

		return true;
	}

	private boolean validateFranchiseId(String franchiseId) throws Exception {
		if (null == franchiseId || franchiseId.isEmpty())
			throw new NOT_FOUND("Please mention franchise");
		if (null != franchiseId || !franchiseId.isEmpty())
			authService.getDetails(Role.FRANCHISE.getCode(), franchiseId, "franchiseId");
		return true;
	}

	public void validateFdTypeAndRoleAndCheckPercentage(FareDistributionModel fareDistributionModel,
			FareDistributionDTo fareDistributionDTo, boolean isUpdate) throws Exception {
		List<FareDistributionTypeDomain> fareDistributionTypeDomainList = new ArrayList<FareDistributionTypeDomain>();
		if (isUpdate) {
			if (!fareDistributionDTo.getFareDistribution().isEmpty()) {
				for (FareDistributionRolePercentageDTo fDRolePercentageDto : fareDistributionDTo
						.getFareDistribution()) {
					FareDistributionTypeDomain fareDistributionTypeDomain = validateFdTypeAndIsPercentage(
							fDRolePercentageDto.getFareDistributionTypeId(), fDRolePercentageDto.getIsPercentage());
					if (null != fareDistributionTypeDomain) {
						if (fDRolePercentageDto.getRoleBasedDistribution().isEmpty())
							throw new NOT_FOUND("Please pass percentage/fixed cost corresponding values");
						else
							checkAllRoleWithType(fDRolePercentageDto.getRoleBasedDistribution(),
									fDRolePercentageDto.getIsPercentage(), fareDistributionTypeDomain.getType());
						checkPercentage(fareDistributionTypeDomain, null, fDRolePercentageDto.getIsPercentage(),
								fDRolePercentageDto.getRoleBasedDistribution(), true);

					}
				}
			}
		} else {
			if (!fareDistributionModel.getFareDistribution().isEmpty()) {
				for (FareDistributionRolePercentage fDRolePercentage : fareDistributionModel.getFareDistribution()) {
					if (fDRolePercentage.getRoleBasedDistribution().length == 0) {
						throw new NOT_FOUND("Please pass percentage/fixed cost values");
					}
					FareDistributionTypeDomain fareDistributionTypeDomain = validateFdTypeAndIsPercentage(
							fDRolePercentage.getFareDistributionTypeId(), fDRolePercentage.isIsPercentage());
					if (null != fareDistributionTypeDomain) {
						fareDistributionTypeDomainList.add(fareDistributionTypeDomain);
						List<FareDistributionDomain> fDDomainList = fareDistributionDAO
								.getFareDistributioneByTypeAndFranchiseId(fDRolePercentage.getFareDistributionTypeId(),
										fareDistributionModel.getFranchiseId());
						if (!fDDomainList.isEmpty())
							throw new ALREADY_EXIST(fareDistributionTypeDomain.getType(), true);
					}
					checkPercentage(fareDistributionTypeDomain, fDRolePercentage.getRoleBasedDistribution(),
							fDRolePercentage.isIsPercentage(), null, false);
				}
			}
		}
	}

	private boolean checkAllRoleWithType(List<RolePercentageDTo> rolePercentageDtoList, boolean isPercentage,
			String type) {
		for (RolePercentageDTo rolePercentageDto : rolePercentageDtoList) {
			if (isPercentage)
				validateRolePercentage(rolePercentageDto.getRoleId(), rolePercentageDto.getPercentage(),
						rolePercentageDto.getUuid(), Constants.IS_PERCENTAGE, type);
			else
				validateRolePercentage(rolePercentageDto.getRoleId(), rolePercentageDto.getFixedCost(),
						rolePercentageDto.getUuid(), Constants.FIXED_COST, type);
		}
		return isPercentage;
	}

	private void validateRolePercentage(int role, double percentage, String uuid, String type, String fDType) {
		if (0 == role)
			throw new NOT_FOUND("Please mention role");
		if (0.0 == percentage)
			throw new NOT_FOUND("Please mention value for " + type + " in this " + fDType + " type");
		if (null == uuid || uuid.isEmpty())
			throw new NOT_FOUND("Please mention uuid in this " + fDType + " type");
	}

	private FareDistributionTypeDomain validateFdTypeAndIsPercentage(String fareDistributionTypeId,
			Boolean isIsPercentage) throws Exception {
		if (null == fareDistributionTypeId || fareDistributionTypeId.isEmpty())
			throw new NOT_FOUND("Please mention fare distribution type");
		FareDistributionTypeDomain fareDistributionTypeDomain = new FareDistributionTypeDomain();
		if (null == isIsPercentage)
			throw new NOT_FOUND("Please mention isPercentage");
		else
			fareDistributionTypeDomain = fareDistributionTypeDAO.getFareDistributionType(fareDistributionTypeId);
		return fareDistributionTypeDomain;
	}

	public boolean checkPercentage(FareDistributionTypeDomain fareDistributionTypeDomain, double[][] fdRolePercentages,
			boolean isPercentage, List<RolePercentageDTo> rolePercentageList, boolean isUpdate) throws Exception {
		RolePercentage rolePercentage = new RolePercentage();
		List<Integer> fdTypeRoleList = CommonUtils
				.stringToListOfIntegerConversion(fareDistributionTypeDomain.getRole());
		if (isUpdate) {
			if (!rolePercentageList.isEmpty()) {
				rolePercentage = rolesPercentageListByUpdate(rolePercentageList, fdTypeRoleList, isPercentage);
			}
		} else
			rolePercentage = rolesPercentageListByPost(fdRolePercentages, fdTypeRoleList);
		if (isPercentage)
			check(rolePercentage.getPercentage(), rolePercentage.getRole(), fareDistributionTypeDomain.getType());
		return true;
	}

	public RolePercentage rolesPercentageListByPost(double[][] fDPercentage, List<Integer> fdTypeRoleList) {
		RolePercentage rolePercentage = null;
		List<Integer> rolesList = null;
		List<Double> percentageList = null;
		percentageList = new ArrayList<Double>();
		rolesList = new ArrayList<Integer>();
		rolePercentage = new RolePercentage();
		for (double[] fDRPercentage : fDPercentage) {
			List<Double> list = Arrays.stream(fDRPercentage).boxed().collect(Collectors.toList());
			for (int i = 0; i <= list.size(); i++) {
				if (i == 0) {
					if (!fdTypeRoleList.isEmpty()) {
						if (fdTypeRoleList.contains(list.get(i).intValue()))
							rolesList.add(list.get(i).intValue());
					} else
						rolesList.add(list.get(i).intValue());
				} else if (i == 1)
					percentageList.add(Double.valueOf(list.get(i)));

			}
		}
		rolePercentage.setPercentage(percentageList);
		rolePercentage.setRole(rolesList);
		return rolePercentage;
	}

	public RolePercentage rolesPercentageListByUpdate(List<RolePercentageDTo> rolePercentageList,
			List<Integer> fdTypeRoleList, boolean isPercentage) {
		RolePercentage rolePercentage = null;
		List<Integer> rolesList = null;
		List<Double> percentageList = null;
		List<String> uuidList = null;
		percentageList = new ArrayList<Double>();
		rolesList = new ArrayList<Integer>();
		uuidList = new ArrayList<String>();
		rolePercentage = new RolePercentage();
		for (RolePercentageDTo rolePercentageDTo : rolePercentageList) {
			rolesList.add(rolePercentageDTo.getRoleId());
			uuidList.add(rolePercentageDTo.getUuid());
			if (isPercentage)
				percentageList.add(rolePercentageDTo.getPercentage());
			else
				percentageList.add(rolePercentageDTo.getFixedCost());
		}
		rolePercentage.setRole(rolesList);
		rolePercentage.setUuid(uuidList);
		rolePercentage.setPercentage(percentageList);
		return rolePercentage;

	}

	private boolean check(List<Double> percentageList, List<Integer> rolesList, String typeName) {
		double totalPercentage = percentageList.stream().mapToDouble(f -> f.doubleValue()).sum();
		if (totalPercentage > Constants.PERCENTAGE)
			throw new ALREADY_EXCEEDED(typeName);
		if (totalPercentage < Constants.PERCENTAGE) {
			String roles = "";
			if (!rolesList.isEmpty()) {
				for (Integer roleId : rolesList) {
					Role role = Role.getRole("" + roleId);
					roles = roles.concat(role.name() + ", ");
				}
				throw new UPDATE_OTHER_ROLE_PERCENTAGE(roles, typeName);
			}
		}
		return false;
	}

	public List<FareDistributionDTo> getCommonFDDetails(List<FareDistributionDomain> fareDistributionDomainList)
			throws Exception {
		Map<Integer, RoleModel> roleMap = authService.getRolesList();
		List<FareDistributionDTo> fareDistributionDToList = new ArrayList<FareDistributionDTo>();
		List<FareDistributionRolePercentageDTo> fDRolePercentage = new ArrayList<FareDistributionRolePercentageDTo>();
		for (FareDistributionDomain fareDistributionDomain : fareDistributionDomainList) {
			// conversion String to list
			List<String> fDTypeList = CommonUtils
					.stringToListOfStringConversion(fareDistributionDomain.getFareDistributionTypeId());
			List<Integer> fDRoleList = CommonUtils.stringToListOfIntegerConversion(fareDistributionDomain.getRole());
			List<Double> fDPercentageList = CommonUtils
					.stringToListOfDoubleConversion(fareDistributionDomain.getPercentages());
			List<Double> fDFixedCostList = CommonUtils
					.stringToListOfDoubleConversion(fareDistributionDomain.getFixedCosts());
			List<String> uuidList = new ArrayList<String>(Arrays.asList(fareDistributionDomain.getUuids().split(",")));
			List<String> fDisActiveList = new ArrayList<String>(
					Arrays.asList(fareDistributionDomain.getIsActives().split(",")));
			FareDistributionDTo fareDistributionDTo = new FareDistributionDTo();
			BeanUtils.copyProperties(fareDistributionDomain, fareDistributionDTo);
			for (String typeRole : fDTypeList) {
				List<RolePercentageDTo> rolePercentageDToList = new ArrayList<RolePercentageDTo>();
				FareDistributionRolePercentageDTo fDTypeDTo = new FareDistributionRolePercentageDTo();
				fDTypeDTo.setFareDistributionTypeId(typeRole);
				fDTypeDTo.setIsPercentage(fareDistributionDomain.isIsPercentage());
				for (int i = 0; i < fDRoleList.size(); i++) {
					RolePercentageDTo rolePercentageDTo = null;
					RoleModel roleModel = new RoleModel(roleMap.get(fDRoleList.get(i)).getRoleName());
					if (fDTypeDTo.getIsPercentage())
						rolePercentageDTo = new RolePercentageDTo(fDRoleList.get(i), roleModel.getRoleName(),
								fDPercentageList.get(i), uuidList.get(i));
					else {
						rolePercentageDTo = new RolePercentageDTo(fDRoleList.get(i), roleModel.getRoleName(),
								uuidList.get(i));
						rolePercentageDTo.setFixedCost(fDFixedCostList.get(i));
					}
					rolePercentageDToList.add(rolePercentageDTo);
				}
				fDTypeDTo.setRoleBasedDistribution(rolePercentageDToList);
				fDRolePercentage.add(fDTypeDTo);
				FareDistributionTypeDomain fareDistributionTypeDomain = fareDistributionTypeDAO
						.getFareDistributionType(typeRole);
				FareDistributionTypeModel fareDistributionTypeModel = new FareDistributionTypeModel(
						fareDistributionTypeDomain.getName());
				fDTypeDTo.setFareDistributionType(fareDistributionTypeModel);
				fareDistributionDTo.setFareDistribution(fDRolePercentage);
			}
			fareDistributionDToList.add(fareDistributionDTo);
			Set deptSet = new HashSet<>();
			// directly removing the elements from list if already existed in set
			fareDistributionDToList.removeIf(p -> !deptSet.add(p.getFranchiseId()));
		}
		return fareDistributionDToList;

	}

	public String fdTransactionDetail(String fDType, FareDistributionTransactionDomain fDTransactionDomain,
			String franchiseId, double amount, boolean fdApply, BigInteger bookingId) throws Exception {
		if (!fdApply) {
			List<FareDistributionTransactionDetailDomain> fDTDetailDomainList = fDTransactionDetailDAO
					.getFareDistributioneTransDetail(fDType, franchiseId, bookingId);
			if (fDTDetailDomainList.isEmpty()) {
				List<FareDistributionDomain> fareDistributionDomainList = fareDistributionDAO
						.getFDTypeAndFareDistributiones(fDType, true, franchiseId);
				if (!fareDistributionDomainList.isEmpty()) {
					for (FareDistributionDomain fareDistributionDomain : fareDistributionDomainList) {
						FareDistributionTransactionDetailDomain fDTransactionDetailDomain = new FareDistributionTransactionDetailDomain(
								CommonUtils.generateRandomId(), fDTransactionDomain.getUuid(),
								fareDistributionDomain.getUuid(),
								CommonUtils.amountSplit(fareDistributionDomain.getPercentage(), amount), true);
						fDTransactionDetailDAO.save(fDTransactionDetailDomain);
					}
				}
			}
		} else {
			List<FareDistributionTransactionDetailDomain> fDTDetailDomainList = fDTransactionDetailDAO
					.getFareDistributioneTransDetail(fDType, franchiseId, bookingId);
			if (!fDTDetailDomainList.isEmpty()) {
				for (FareDistributionTransactionDetailDomain fdTDetailDomain : fDTDetailDomainList) {
					FareDistributionTransactionDetailDomain fdTDetailsDomain = null;
					fdTDetailsDomain = new FareDistributionTransactionDetailDomain(fdTDetailDomain.getUuid(),
							CommonUtils.amountSplit(fdTDetailDomain.getPercentage(), amount));
					fDTransactionDetailDAO.update(fdTDetailsDomain);
				}
			} else {
				List<FareDistributionDomain> fareDistributionDomainList = fareDistributionDAO
						.getFDTypeAndFareDistributiones(fDType, true, franchiseId);
				if (!fareDistributionDomainList.isEmpty()) {
					for (FareDistributionDomain fareDistributionDomain : fareDistributionDomainList) {
						FareDistributionTransactionDetailDomain fDTransactionDetailDomain = new FareDistributionTransactionDetailDomain(
								CommonUtils.generateRandomId(), fDTransactionDomain.getUuid(),
								fareDistributionDomain.getUuid(),
								CommonUtils.amountSplit(fareDistributionDomain.getPercentage(), amount), true);
						fDTransactionDetailDAO.save(fDTransactionDetailDomain);
					}
				}
			}

		}
		return "Succes";
	}

	public String checkCompanyProfileId(Role role, @RequestParam Map<String, String> reqParam) throws Exception {
		String companyProfileId = null;
		if (role.equals(Role.DRIVER)) {
			if (reqParam.containsKey("driverId")) {
				companyProfileId = reqParam.get("driverId");
				authService.getDetails(role.getCode(), companyProfileId, "driverId");
			} else
				throw new NOT_FOUND("Please mention request parameter as 'driverId' ");
		}
		if (role.equals(Role.FRANCHISE)) {
			if (reqParam.containsKey("franchiseId")) {
				companyProfileId = reqParam.get("franchiseId");
				authService.getDetails(role.getCode(), companyProfileId, "franchiseId");
			} else
				throw new NOT_FOUND("Please mention request parameter as 'franchiseId' ");
		}
		return companyProfileId;
	}

	public int checkLimit(@RequestParam Map<String, String> reqParam) throws Exception {
		String limit = null;
		int limits = 0;
		if (reqParam.size() > 0)
			if (reqParam.containsKey("limit")) {
				limit = reqParam.get("limit");
				limits = Integer.parseInt(limit);
			}
		return limits;
	}

	public Date validateDateByDateType(@RequestParam Map<String, String> reqParam, String dateType) {
		String startDate = null, endDate = null;
		Date startsDate = null;
		Date endsDate = null;
		if (reqParam.size() != 0) {
			if (dateType.equals(Constants.START_DATE)) {
				if (reqParam.containsKey("startDate")) {
					startDate = reqParam.get("startDate");
					startsDate = checkDateFormat(startDate);
					return startsDate;
				}
			}
			if (dateType.equals(Constants.END_DATE)) {
				if (reqParam.containsKey("endDate")) {
					endDate = reqParam.get("endDate");
					endsDate = checkDateFormat(endDate);
					return endsDate;
				}
			}
		}
		return null;
	}

	public Date validateMendatoryDateByDateType(@RequestParam Map<String, String> reqParam, String dateType) {
		String startDate = null, endDate = null;
		Date startsDate = null;
		Date endsDate = null;
		if (reqParam.size() != 0) {
			if (dateType.equals(Constants.START_DATE)) {
				if (reqParam.containsKey("startDate")) {
					startDate = reqParam.get("startDate");
					startsDate = checkDateFormat(startDate);
					return startsDate;
				} else
					throw new NOT_FOUND("Please mention request parameter as 'startDate' ");
			}
			if (dateType.equals(Constants.END_DATE)) {
				if (reqParam.containsKey("endDate")) {
					endDate = reqParam.get("endDate");
					endsDate = checkDateFormat(endDate);
					return endsDate;
				} else
					throw new NOT_FOUND("Please mention request parameter as 'endDate' ");
			}
		} else
			throw new NOT_FOUND("Please mention request parameter as 'startDate','endDate' ");
		return null;
	}

	public void checkStartDateAndEndDate(Date startDate, Date endDate) {
		if (startDate.after(new Date()))
			throw new NOT_FOUND("Start date should not be greater than today's date");
		if (startDate.after(endDate))
			throw new NOT_FOUND("Start date should not be greater than end date");
	}

	private Date checkDateFormat(String date) {
		long dateIn = Long.parseLong(date);
		Date currentDate = new Date(dateIn);
		return currentDate;
	}

	public EarningsModel getEarning(List<FareDistributionTransactionDomain> fDTDomainList, Role role) throws Exception {
		List<EarningsModel> earningsModelList = new ArrayList<EarningsModel>();
		EarningsModel earningModel = null;
		List<EarningWithTypeModel> earningWithTypeModelList = null;
		double sum = 0.0;
		Map<BigInteger, BookingModel> bookingMap = bookingCommonService
				.getBookingByStatus(BookingStatus.COMPLETED.name());
		Map<BigInteger, EarningsModel> map = new HashMap<BigInteger, EarningsModel>();
		if (!fDTDomainList.isEmpty()) {
			for (FareDistributionTransactionDomain fDTDomain : fDTDomainList) {
				if (null != fDTDomain.getFareDistributionTypeId()) {
					FareDistributionTypeDomain fDTypeDomain = fareDistributionTypeDAO
							.getFareDistributionType(fDTDomain.getFareDistributionTypeId());
					EarningWithTypeModel earningWithTypeModel = null;
					if (null != fDTypeDomain) {
						earningWithTypeModel = new EarningWithTypeModel(fDTDomain.getEarning(), fDTypeDomain.getType());
					}
					EarningsModel earningsModel = null;
					for (EarningsModel i : earningsModelList)
						map.put(i.getBookingId(), i);
					if (map.get(fDTDomain.getBookingId()) == null) {
						if (bookingMap.get(fDTDomain.getBookingId()) != null) {
							BookingModel bookingModel = new BookingModel(
									bookingMap.get(fDTDomain.getBookingId()).getSource(),
									bookingMap.get(fDTDomain.getBookingId()).getDestination());
							earningsModel = new EarningsModel(fDTDomain.getBookingId(), fDTDomain.getBookingDate(),
									fDTDomain.getTotalCost(), bookingModel.getSource(), bookingModel.getDestination());
							earningWithTypeModelList = new ArrayList<EarningWithTypeModel>();
							earningWithTypeModelList.add(earningWithTypeModel);
							earningsModel.setType(earningWithTypeModelList);
							earningsModelList.add(earningsModel);
						}
					} else if (map.get(fDTDomain.getBookingId()) != null) {
						EarningsModel erngsModel = new EarningsModel();
						erngsModel = map.get(fDTDomain.getBookingId());
						if (erngsModel.getBookingId().equals(fDTDomain.getBookingId())
								&& (erngsModel.getBookingDate().equals(fDTDomain.getBookingDate()))) {
							earningsModel = new EarningsModel();
							earningWithTypeModelList.add(earningWithTypeModel);
							earningsModel.setType(earningWithTypeModelList);
						}
					}
				}
			}
			for (int i = 0; i < fDTDomainList.size(); i++) {
				sum = sum + fDTDomainList.get(i).getAmount();
			}
		}
		earningModel = new EarningsModel();
		if (!earningsModelList.isEmpty()) {
			earningModel.setEarnings(earningsModelList);
			/*
			 * with using Floor Method (Math.floor(sum))
			 */
			earningModel.setTotalEarnings((Math.round(sum * 100.0) / 100.0));
			earningModel.setRole(role.name());
		}
		return earningModel;
	}

	public EarningsModel generateEarningData(List<FareDistributionTransactionDomain> fDTDomainList, Role role,
			ProfileModel profileModel) throws Exception {
		List<EarningsModel> earningsModelList = new ArrayList<EarningsModel>();
		EarningsModel earningModel = null;
		double sum = 0.0;
		Map<BigInteger, BookingModel> bookingMap = bookingCommonService
				.getBookingByStatus(BookingStatus.COMPLETED.name());
		if (!fDTDomainList.isEmpty()) {
			for (FareDistributionTransactionDomain fDTDomain : fDTDomainList) {
				if (null != fDTDomain.getFareDistributionTypeId()) {
					FareDistributionTypeDomain fDTypeDomain = fareDistributionTypeDAO
							.getFareDistributionType(fDTDomain.getFareDistributionTypeId());
					EarningWithTypeModel earningWithTypeModel = null;
					if (null != fDTypeDomain) {
						earningWithTypeModel = new EarningWithTypeModel(fDTDomain.getEarning(), fDTypeDomain.getType());
					}
					EarningsModel earningsModel = null;

					if (bookingMap.get(fDTDomain.getBookingId()) != null) {
						BookingModel bookingModel = new BookingModel(
								bookingMap.get(fDTDomain.getBookingId()).getsCity() + ","
										+ bookingMap.get(fDTDomain.getBookingId()).getsState() + ","
										+ bookingMap.get(fDTDomain.getBookingId()).getsCountry(),
								bookingMap.get(fDTDomain.getBookingId()).getdCity() + ","
										+ bookingMap.get(fDTDomain.getBookingId()).getdState() + ","
										+ bookingMap.get(fDTDomain.getBookingId()).getdCountry());

						earningsModel = new EarningsModel(fDTDomain.getBookingId(),
								DateUtility.getDateByStringFormat(fDTDomain.getBookingDate(),
										DateUtility.DATE_FORMAT_YYYY_MM_DD),
								fDTDomain.getTotalCost(), bookingModel.getSource(), bookingModel.getDestination(),
								fDTDomain.getEarning(), earningWithTypeModel.getName(), fDTDomain.getDriverId(),
								fDTDomain.getFranchiseId());
						earningsModelList.add(earningsModel);

					}
				}
			}
			for (int i = 0; i < fDTDomainList.size(); i++) {
				sum = sum + fDTDomainList.get(i).getAmount();
			}
		}
		earningModel = new EarningsModel();
		if (!earningsModelList.isEmpty()) {
			earningModel.setEarnings(earningsModelList);
			earningModel.setTotalEarnings(Math.ceil(sum));
			earningModel.setRole(role.name());
			earningModel.setProfile(profileModel);
		}
		return earningModel;
	}

	public EarningsModel getTotalEarning(List<FareDistributionTransactionDomain> fDTDomainList, Role role,
			ProfileModel profileModel) throws Exception {
		EarningsModel earningModel = null, earningsModel = null;
		double totalEranings = 0.0;
		if (!fDTDomainList.isEmpty()) {
			earningModel = new EarningsModel();
			earningModel = generateEarningData(fDTDomainList, role, profileModel);
			totalEranings = earningModel.getTotalEarnings();
		}
		earningsModel = new EarningsModel(totalEranings, role.name(), profileModel);
		return earningsModel;
	}

	public ProfileModel getProfileInformationById(Role role, @RequestParam Map<String, String> reqParam,
			boolean isAddress) throws Exception {
		ProfileModel profileModel = new ProfileModel();
		ProfileModel profileModels = new ProfileModel();
		FranchiseModel franchiseModel = null;
		String companyProfileId = null;
		if (role.getCode().equals(Role.DRIVER.getCode())) {
			if (reqParam.containsKey("driverId")) {
				companyProfileId = reqParam.get("driverId");
				profileModels = authService.getDetails(role.getCode(), companyProfileId, "driverId");
				ProfileModel profileFranchiseModel = authService.getDetails(role.getCode(),
						profileModels.getDriver().getFranchiseId(), "franchiseId");
				franchiseModel = new FranchiseModel(profileFranchiseModel.getFranchise().getFranchiseId(),
						profileFranchiseModel.getFranchise().getCompanyName());
			} else
				throw new NOT_FOUND("Please mention request parameter as 'driverId' ");
		}
		if (role.getCode().equals(Role.FRANCHISE.getCode())) {
			if (reqParam.containsKey("franchiseId")) {
				companyProfileId = reqParam.get("franchiseId");
				profileModels = authService.getDetails(role.getCode(), companyProfileId, "franchiseId");
				franchiseModel = new FranchiseModel(profileModels.getFranchise().getFranchiseId(),
						profileModels.getFranchise().getCompanyName());
			} else
				throw new NOT_FOUND("Please mention request parameter as 'franchiseId' ");
		}
		if (isAddress)
			profileModel = new ProfileModel(profileModels.getFirstName(), profileModels.getLastName(),
					profileModels.getMobileNumber(), profileModels.getEmailId(), franchiseModel,
					profileModels.getAddr(), profileModels.getDriver());
		else
			profileModel = new ProfileModel(profileModels.getFirstName(), profileModels.getLastName(),
					profileModels.getMobileNumber(), profileModels.getEmailId(), franchiseModel);
		return profileModel;

	}

	public String getAddress(String address1, String address2, int cityId, int stateId, int countryId,
			BigInteger pincode) throws Exception {
		String address = null;
		address = address1 + ",";
		if (address2 != null)
			address += address2 + ",";
		CityDomain cityDomain = cityDao.getCity(cityId);
		if (cityDomain != null)
			address += cityDomain.getName() + ",";
		StateDomain stateDomain = stateDao.getState(stateId);
		if (stateDomain != null)
			address += stateDomain.getName() + ",";
		CountryDomain countryDomain = countryDAO.getCountry(countryId);
		if (countryDomain != null)
			address += countryDomain.getName() + " " + pincode;
		return address;
	}
}
