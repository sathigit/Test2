package com.atpl.mmg.service.fdtrans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.constant.FDType;
import com.atpl.mmg.constant.Role;
import com.atpl.mmg.dao.faredist.FareDistributionDAO;
import com.atpl.mmg.dao.faredisttype.FareDistributionTypeDAO;
import com.atpl.mmg.dao.fdtrans.FareDistributionTransactionDAO;
import com.atpl.mmg.dao.fdtransdetail.FareDistributionTransactionDetailDAO;
import com.atpl.mmg.domain.fdtrans.FareDistributionTransactionDomain;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.model.faredist.EarningsModel;
import com.atpl.mmg.model.fdtrans.FareDistributionTransactionModel;
import com.atpl.mmg.model.profile.ProfileModel;
import com.atpl.mmg.service.faredist.BookingCommonService;
import com.atpl.mmg.service.faredist.FareDistributionUtil;
import com.atpl.mmg.utils.CommonUtils;
import com.atpl.mmg.utils.DateUtility;

@Service("fareDistributionTransactionService")
public class FareDistributionTransactionServiceImpl implements FareDistributionTransactionService {

	@Autowired
	BookingCommonService bookingCommonService;

	@Autowired
	FareDistributionTypeDAO fareDistributionTypeDAO;

	@Autowired
	FareDistributionDAO fareDistributionDAO;

	@Autowired
	FareDistributionTransactionDAO fareDistributionTransactionDAO;

	@Autowired
	FareDistributionTransactionDetailDAO fDTransactionDetailDAO;

	@Autowired
	FareDistributionUtil fareDistributionUtil;

	@Override
	public String save(FareDistributionTransactionModel fareDistributionTransactionModel) throws Exception {
		FareDistributionTransactionDomain fDTransactionDomain = new FareDistributionTransactionDomain();
		fDTransactionDomain = fareDistributionTransactionDAO
				.getFareDistributionTrans(fareDistributionTransactionModel.getBookingId());
		if (null != fDTransactionDomain) {
			saveFDData(fareDistributionTransactionModel, fDTransactionDomain, true);
		} else {
			FareDistributionTransactionDomain fDTDomain = new FareDistributionTransactionDomain();
			BeanUtils.copyProperties(fareDistributionTransactionModel, fDTDomain);
			fDTDomain.setUuid(CommonUtils.generateRandomId());
			fDTransactionDomain = fareDistributionTransactionDAO.save(fDTDomain);
			saveFDData(fareDistributionTransactionModel, fDTransactionDomain, false);
		}
		return "Success";
	}

	private String saveFDData(FareDistributionTransactionModel fareDistributionTransactionModel,
			FareDistributionTransactionDomain fDTransactionDomain, boolean isUpdate) throws Exception {
		if (fDTransactionDomain.getBookingAmount() > 0.0) {
			if (fDTransactionDomain.getBookingAmount() != fareDistributionTransactionModel.getBookingAmount()) {
				fareDistributionUtil.fdTransactionDetail(FDType.TRIP.getCode(), fDTransactionDomain,
						fDTransactionDomain.getFranchiseId(), fareDistributionTransactionModel.getBookingAmount(), true,
						fareDistributionTransactionModel.getBookingId());
			} else
				fareDistributionUtil.fdTransactionDetail(FDType.TRIP.getCode(), fDTransactionDomain,
						fDTransactionDomain.getFranchiseId(), fDTransactionDomain.getBookingAmount(), false,
						fDTransactionDomain.getBookingId());
		}
		if (fDTransactionDomain.getInsuranceAmount() > 0.0) {
			if (fDTransactionDomain.getInsuranceAmount() != fareDistributionTransactionModel.getInsuranceAmount()) {
				fareDistributionUtil.fdTransactionDetail(FDType.INSURANCE.getCode(), fDTransactionDomain,
						fDTransactionDomain.getFranchiseId(), fareDistributionTransactionModel.getInsuranceAmount(),
						true, fareDistributionTransactionModel.getBookingId());
			} else
				fareDistributionUtil.fdTransactionDetail(FDType.INSURANCE.getCode(), fDTransactionDomain,
						fDTransactionDomain.getFranchiseId(), fDTransactionDomain.getInsuranceAmount(), false,
						fDTransactionDomain.getBookingId());

		}
		if (fDTransactionDomain.getLabourAmount() > 0.0) {
			if (fDTransactionDomain.getLabourAmount() != fareDistributionTransactionModel.getLabourAmount()) {
				fareDistributionUtil.fdTransactionDetail(FDType.LABOUR.getCode(), fDTransactionDomain,
						fDTransactionDomain.getFranchiseId(), fareDistributionTransactionModel.getLabourAmount(), true,
						fareDistributionTransactionModel.getBookingId());
			} else
				fareDistributionUtil.fdTransactionDetail(FDType.LABOUR.getCode(), fDTransactionDomain,
						fDTransactionDomain.getFranchiseId(), fDTransactionDomain.getLabourAmount(), false,
						fDTransactionDomain.getBookingId());
		}
		if (isUpdate) {
			FareDistributionTransactionDomain fDisTransactionDomain = new FareDistributionTransactionDomain();
			BeanUtils.copyProperties(fareDistributionTransactionModel, fDisTransactionDomain);
			fDisTransactionDomain.setUuid(fDTransactionDomain.getUuid());
			fareDistributionTransactionDAO.update(fDisTransactionDomain);
		}

		bookingCommonService.updateStatusByBookingId(fDTransactionDomain.getBookingId());
		return "Success";
	}

	@SuppressWarnings("unused")
	@Override
	public EarningsModel getEarnings(int roleId, @RequestParam Map<String, String> reqParam) throws Exception {
		String companyProfileId;
		int limit = 0;
		String startDate = null, endDate = null;
		Date startsDate = null, endsDate = null;
		List<FareDistributionTransactionDomain> fDTDomainList = new ArrayList<FareDistributionTransactionDomain>();
		if (0 == roleId)
			throw new NOT_FOUND("Please mention role");
		Role role = Role.getRole(roleId + "");
		if (null == role)
			throw new NOT_FOUND("Role not found" + roleId);
		limit = fareDistributionUtil.checkLimit(reqParam);
		switch (role) {
		case DRIVER:
			companyProfileId = fareDistributionUtil.checkCompanyProfileId(role, reqParam);
			startsDate = fareDistributionUtil.validateDateByDateType(reqParam, Constants.START_DATE);
			endsDate = fareDistributionUtil.validateDateByDateType(reqParam, Constants.END_DATE);
			if (null != startsDate && null != endsDate) {
				fareDistributionUtil.checkStartDateAndEndDate(startsDate, endsDate);
				startDate = DateUtility.getDateByStringFormat(startsDate, DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
				endDate = DateUtility.getDateByStringFormat(endsDate, DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
				fDTDomainList = fareDistributionTransactionDAO.getFDTransDetails(companyProfileId, role, startDate,
						endDate);
			} else
				fDTDomainList = fareDistributionTransactionDAO.getFDTransDetails(companyProfileId, role, limit);
			break;
		case FRANCHISE:
			companyProfileId = fareDistributionUtil.checkCompanyProfileId(role, reqParam);
			startsDate = fareDistributionUtil.validateDateByDateType(reqParam, Constants.START_DATE);
			endsDate = fareDistributionUtil.validateDateByDateType(reqParam, Constants.END_DATE);
			if (null != startsDate && null != endsDate) {
				fareDistributionUtil.checkStartDateAndEndDate(startsDate, endsDate);
				startDate = DateUtility.getDateByStringFormat(startsDate, DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
				endDate = DateUtility.getDateByStringFormat(endsDate, DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
				fDTDomainList = fareDistributionTransactionDAO.getFDTransDetails(companyProfileId, role, startDate,
						endDate);
			} else
				fDTDomainList = fareDistributionTransactionDAO.getFDTransDetails(companyProfileId, role, limit);
			break;
		case ATPL:
			startsDate = fareDistributionUtil.validateDateByDateType(reqParam, Constants.START_DATE);
			endsDate = fareDistributionUtil.validateDateByDateType(reqParam, Constants.END_DATE);
			if (null != startsDate && null != endsDate) {
				fareDistributionUtil.checkStartDateAndEndDate(startsDate, endsDate);
				startDate = DateUtility.getDateByStringFormat(startsDate, DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
				endDate = DateUtility.getDateByStringFormat(endsDate, DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
				fDTDomainList = fareDistributionTransactionDAO.getFDTransDetails(null, role, startDate, endDate);
			} else
				fDTDomainList = fareDistributionTransactionDAO.getFDTransDetails(null, role, limit);
			break;
		}
		EarningsModel earningsModel = new EarningsModel();
		if (!fDTDomainList.isEmpty())
			earningsModel = fareDistributionUtil.getEarning(fDTDomainList, role);
		return earningsModel;
	}

	@Override
	public EarningsModel getEarningsDataForDownload(int roleId, Map<String, String> reqParam, String startDate,
			String endDate) throws Exception {
		ProfileModel profileModel = new ProfileModel();
		int limit = 0;
		List<FareDistributionTransactionDomain> fDTDomainList = new ArrayList<FareDistributionTransactionDomain>();
		if (0 == roleId)
			throw new NOT_FOUND("Please mention role");
		Role role = Role.getRole(roleId + "");
		if (null == role)
			throw new NOT_FOUND("Role not found" + roleId);

		switch (role) {
		case DRIVER:
			profileModel = fareDistributionUtil.getProfileInformationById(role, reqParam, true);
			if (limit != 0)
				fDTDomainList = fareDistributionTransactionDAO.getFDTransDetails(profileModel.getDriver().getDriverId(),
						role, limit);
			else
				fDTDomainList = fareDistributionTransactionDAO.getFDTransDetails(profileModel.getDriver().getDriverId(),
						role, startDate, endDate);
			break;
		case FRANCHISE:
			profileModel = fareDistributionUtil.getProfileInformationById(role, reqParam, true);
			if (limit != 0)
				fDTDomainList = fareDistributionTransactionDAO
						.getFDTransDetails(profileModel.getFranchise().getFranchiseId(), role, limit);
			else
				fDTDomainList = fareDistributionTransactionDAO
						.getFDTransDetails(profileModel.getFranchise().getFranchiseId(), role, startDate, endDate);
			break;
		case ATPL:
			profileModel.setFirstName(Role.ATPL.name());
			profileModel.setLastName("");
			profileModel.setMobileNumber(Constants.ATPL_HEPL_NUMBER);
			profileModel.setAddr(Constants.ATPL_ADDRESS);
			profileModel.setEmailId(Constants.ATPL_EMAILID);
			if (limit != 0)
				fDTDomainList = fareDistributionTransactionDAO.getFDTransDetails(null, role, limit);
			else
				fDTDomainList = fareDistributionTransactionDAO.getFDTransDetails(null, role, startDate, endDate);
			break;
		}
		EarningsModel earningsModel = new EarningsModel();
		if (!fDTDomainList.isEmpty())
			earningsModel = fareDistributionUtil.generateEarningData(fDTDomainList, role,profileModel);
		return earningsModel;
	}

	@Override
	public EarningsModel getTotalEarningAndDetails(int roleId, Map<String, String> reqParam) throws Exception {
		ProfileModel profileModel = new ProfileModel();
		int limit = 0;
		List<FareDistributionTransactionDomain> fDTDomainList = new ArrayList<FareDistributionTransactionDomain>();
		if (0 == roleId)
			throw new NOT_FOUND("Please mention role");
		Role role = Role.getRole(roleId + "");
		if (null == role)
			throw new NOT_FOUND("Role not found" + roleId);
		switch (role) {
		case DRIVER:
			profileModel = fareDistributionUtil.getProfileInformationById(role, reqParam, true);
			String driverId = profileModel.getDriver().getDriverId();
			profileModel.setDriver(null);
			profileModel.setAddr(null);
			fDTDomainList = fareDistributionTransactionDAO.getFDTransDetails(driverId,
					role, limit);
			break;
		case FRANCHISE:
			profileModel = fareDistributionUtil.getProfileInformationById(role, reqParam, false);
			fDTDomainList = fareDistributionTransactionDAO
					.getFDTransDetails(profileModel.getFranchise().getFranchiseId(), role, limit);
			break;
		case ATPL:
			profileModel.setFirstName(Role.ATPL.name());
			profileModel.setLastName("");
			profileModel.setMobileNumber(Constants.ATPL_HEPL_NUMBER);
			fDTDomainList = fareDistributionTransactionDAO.getFDTransDetails(null, role, limit);
			break;
		}
		EarningsModel earningsModel = new EarningsModel();
		earningsModel = fareDistributionUtil.getTotalEarning(fDTDomainList, role, profileModel);
		return earningsModel;
	}

}
