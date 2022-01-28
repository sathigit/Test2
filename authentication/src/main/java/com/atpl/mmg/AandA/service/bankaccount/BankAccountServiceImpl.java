package com.atpl.mmg.AandA.service.bankaccount;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.constant.Role;
import com.atpl.mmg.AandA.dao.bankaccount.BankAccountDAO;
import com.atpl.mmg.AandA.dao.otp.OtpDAO;
import com.atpl.mmg.AandA.dao.profile.ProfileDAOV2;
import com.atpl.mmg.AandA.dao.role.RoleDAO;
import com.atpl.mmg.AandA.domain.bankaccount.BankAccountDomain;
import com.atpl.mmg.AandA.domain.otp.Otp;
import com.atpl.mmg.AandA.domain.profile.ProfileDomainV2;
import com.atpl.mmg.AandA.exception.MmgRestException.ACCOUNT_NUMBER_ALREADY_EXIST;
import com.atpl.mmg.AandA.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.AandA.exception.MmgRestException.OTP_VALIDATE_NOT_CHECKED;
import com.atpl.mmg.AandA.exception.MmgRestException.VALIDATE_FILE;
import com.atpl.mmg.AandA.log.EventsLogUtil;
import com.atpl.mmg.AandA.log.Loggly;
import com.atpl.mmg.AandA.log.ServiceTypes;
import com.atpl.mmg.AandA.log.SeverityTypes;
import com.atpl.mmg.AandA.mapper.bankaccount.BankAccountMapper;
import com.atpl.mmg.AandA.model.bankaccount.BankAccountModel;
import com.atpl.mmg.AandA.model.bankaccount.BankAccountModelDTO;
import com.atpl.mmg.AandA.model.profile.BankModel;
import com.atpl.mmg.AandA.model.reason.ReasonModel;
import com.atpl.mmg.AandA.service.profile.AdminCommonService;
import com.atpl.mmg.AandA.service.profile.ProfileUtil;
import com.atpl.mmg.AandA.utils.CommonUtils;

@Service("bankAccountService")
public class BankAccountServiceImpl implements BankAccountService {

	@Autowired
	BankAccountDAO bankAccountDAO;

	@Autowired
	BankAccountMapper bankAccountMapper;

	@Autowired
	AdminCommonService adminCommonService;

	@Autowired
	OtpDAO otpDAO;

	@Autowired
	ProfileDAOV2 profileDAOV2;

	@Autowired
	RoleDAO roleDAO;

	@Autowired
	ProfileUtil profileUtil;

	@Override
	public String save(BankAccountModel bankAccountModel) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BANK_ACCOUNT_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"Bank Account Request: " + JsonUtil.toJsonString(bankAccountModel)));
		validateBank(bankAccountModel, false);
		BankAccountDomain bankAccountDomain = bankAccountDAO.checkAccountNumber(bankAccountModel.getAccountNumber());
		if (null != bankAccountDomain)
			throw new ACCOUNT_NUMBER_ALREADY_EXIST();
		bankAccountDomain = new BankAccountDomain();
		BeanUtils.copyProperties(bankAccountModel, bankAccountDomain);
		bankAccountDomain.setUuid(CommonUtils.generateRandomId());
		return bankAccountDAO.save(bankAccountDomain);
	}

	@Override
	public List<BankAccountModel> getBankAccounts(String profileId, Map<String, String> reqParam) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BANK_ACCOUNT_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "getBankAccounts Request: " + JsonUtil.toJsonString(profileId)));
		Boolean status = null;
		if (reqParam.size() > 0) {
			if (reqParam.containsKey("status"))
				status = Boolean.valueOf(reqParam.get("status"));
		}
		Map<Integer, BankModel> bank = adminCommonService.getAllBanks();
		List<BankAccountDomain> bankAccountDomain = bankAccountDAO.getAllBankAccountsByProfileId(profileId, status);
		for (BankAccountDomain bankAccount : bankAccountDomain) {
			bankAccount.setBankName(bank.get(bankAccount.getBankId()).name);
		}
		return bankAccountMapper.entityList(bankAccountDomain);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public String updateBank(BankAccountModelDTO bankAccountModel) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BANK_ACCOUNT_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"Update Bank Account Request: " + JsonUtil.toJsonString(bankAccountModel)));
		BankAccountModel bankAccount = new BankAccountModel();
		BeanUtils.copyProperties(bankAccountModel, bankAccount);
		validateBank(bankAccount, true);
		if (0 >= bankAccountModel.getUserRoleId())
			throw new NOT_FOUND("Please mention the User roleId!!");
		else
		{
			if(!(bankAccountModel.getUserRoleId() == Integer.valueOf(Role.TIEUPS.getCode()))  && !(bankAccountModel.getUserRoleId() == Integer.valueOf(Role.OWNER.getCode())) )
				throw new NOT_FOUND("Access denied!!");
		}
		if (CommonUtils.isNullCheck(bankAccountModel.getUserId()))
			throw new NOT_FOUND("Please mention the userId!!");
		else {
			ProfileDomainV2 profileDomain = profileDAOV2.getProfileByIdAndRole(bankAccountModel.getUserId(),
					bankAccountModel.getUserRoleId());
			if (null == profileDomain)
				throw new NOT_FOUND("Please mention the proper userId!!");
		}
		if (0 >= bankAccountModel.getRoleId())
			throw new NOT_FOUND("Please mention the roleId!!");
		else
			roleDAO.getRoleName(bankAccountModel.getRoleId());
		ProfileDomainV2 profile = profileDAOV2.getProfileDetails(bankAccountModel.getProfileId());
		if (null != profile) {
			Otp otpObj = otpDAO.getOtp(String.valueOf(profile.getMobileNumber()),null);
			if (null == otpObj || !otpObj.isIsChecked())
				throw new OTP_VALIDATE_NOT_CHECKED();
			BankAccountDomain bankDomain = new BankAccountDomain();
			BankAccountDomain bankAccountDomain = bankAccountDAO
					.checkAccountNumber(bankAccountModel.getAccountNumber());
			if (null != bankAccountDomain) {
				if (!bankAccountDomain.getProfileId().equalsIgnoreCase(bankAccountDomain.getProfileId()))
					throw new ACCOUNT_NUMBER_ALREADY_EXIST();
			}
			BeanUtils.copyProperties(bankAccountModel, bankDomain);
			if (bankAccountModel.isStatus()) {
				List<BankAccountDomain> bankDomainList = bankAccountDAO
						.getAllBankAccountsByProfileId(bankAccountModel.getProfileId(), null);
				if (!bankDomainList.isEmpty()) {
					for (BankAccountDomain domain : bankDomainList) {
						if (domain.isStatus()) {
							bankAccountDAO.updateBankStatus(domain.getUuid(), false);
						}
					}
				}
			}
			bankAccountDAO.updateBank(bankDomain);
			ReasonModel reasonModel = new ReasonModel();
			reasonModel.setProfileId(bankAccountModel.getProfileId());
			reasonModel.setRoleId(bankAccountModel.getRoleId());
			reasonModel.setReason("Bank details Changes");
			reasonModel.setUserId(bankAccountModel.getUserId());
			reasonModel.setUserRoleId(bankAccountModel.getUserRoleId());
			profileUtil.saveReason(reasonModel, true,false);
			otpDAO.deleteOtp(otpObj.getId());
		}
		return "Updated successfully";
	}

	private void validateBank(BankAccountModel bankAccountModel, boolean isUpdate) throws Exception {
		if (isUpdate) {
			if (CommonUtils.isNullCheck(bankAccountModel.getUuid()))
				throw new VALIDATE_FILE("Please mention the uuid");
			BankAccountDomain bandAccountDomain = bankAccountDAO.getBankAccountsByUuid(bankAccountModel.getUuid());
			if (null == bandAccountDomain)
				throw new NOT_FOUND("Bank not found");
		}
		if (null == bankAccountModel.getProfileId() || bankAccountModel.getProfileId().isEmpty())
			throw new VALIDATE_FILE("Please mention the profileId");
		if (null == bankAccountModel.getAccountNumber())
			throw new VALIDATE_FILE("Please mention the accountNumber");
		if (0 == bankAccountModel.getBankId())
			throw new VALIDATE_FILE("Please mention the bankId");
	}

	@Override
	public BankAccountModel getBankAccountsByUuid(String uuid) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BANK_ACCOUNT_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"getBankAccountsByUuid Request: " + JsonUtil.toJsonString(uuid)));
		BankAccountDomain bandAccountDomain = bankAccountDAO.getBankAccountsByUuid(uuid);
		BankAccountModel bankAccountModel = new BankAccountModel();
		if (null == bandAccountDomain)
			throw new NOT_FOUND("Bank not found");
		bandAccountDomain.setBankName(adminCommonService.bankNameById(bandAccountDomain.getBankId()));
		BeanUtils.copyProperties(bandAccountDomain, bankAccountModel);
		return bankAccountModel;
	}

	@Override
	public String activateDeactivateBank(String uuid, boolean status) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BANK_ACCOUNT_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "activateDeactivateBank: " + JsonUtil.toJsonString(uuid)));
		BankAccountDomain bandAccountDomain = bankAccountDAO.getBankAccountsByUuid(uuid);
		if (null == bandAccountDomain)
			throw new NOT_FOUND("Bank not found");
		if (status) {
			List<BankAccountDomain> bankDomainList = bankAccountDAO
					.getAllBankAccountsByProfileId(bandAccountDomain.getProfileId(), null);
			if (!bankDomainList.isEmpty()) {
				for (BankAccountDomain domain : bankDomainList) {
					if (domain.isStatus()) {
						bankAccountDAO.updateBankStatus(domain.getUuid(), false);
					}
				}
			}
		}
		bankAccountDAO.updateBankStatus(uuid, status);
		return "Updated Successfully";
	}

}
