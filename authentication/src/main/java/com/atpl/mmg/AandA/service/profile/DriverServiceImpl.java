package com.atpl.mmg.AandA.service.profile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atpl.mmg.AandA.constant.AddressType;
import com.atpl.mmg.AandA.constant.DriverRequestedType;
import com.atpl.mmg.AandA.constant.EnquiryRole;
import com.atpl.mmg.AandA.constant.Role;
import com.atpl.mmg.AandA.dao.profile.ProfileDAOV2;
import com.atpl.mmg.AandA.domain.profile.DriverDomain;
import com.atpl.mmg.AandA.domain.profile.ProfileDomainV2;
import com.atpl.mmg.AandA.exception.MmgRestException.ROLE_NOT_FOUND;
import com.atpl.mmg.AandA.model.profile.Address;
import com.atpl.mmg.AandA.model.profile.Driver;
import com.atpl.mmg.AandA.model.profile.Profile;
import com.atpl.mmg.AandA.utils.CommonUtils;
import com.atpl.mmg.AandA.utils.EmailValidator;

@Service("driverService")
@Qualifier("driverService")
public class DriverServiceImpl implements ProfileCommonService {

	@Autowired
	AuthFranchiseCommonService franchiseCommonService;

	@Autowired
	ProfileUtil profileUtil;

	@Autowired
	ProfileDAOV2 profileDAOV2;
	
	@Autowired
	EmailValidator emailValidator;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Profile save(Profile profile,ProfileDomainV2 existingProfileDomain) throws Exception {
		Profile profileObject = new Profile();
		Role role = Role.getRole(profile.getRoleId() + "");
		if (null == role)
			throw new ROLE_NOT_FOUND(profile.getRoleId() + "");
		
		profileObject = profileUtil.saveProfileRole(profile,existingProfileDomain);
		profileUtil.saveBank(profile);
		
		Driver driver = profile.getDriver();
		DriverDomain driverDomain = new DriverDomain();
		
		if(null == driver)
			driver = new Driver();
		
		driver.setProfileId(profileObject.getId());
		
		if (!CommonUtils.isNullCheck(driver.getFranchiseId())) {
			driver.setStatus(EnquiryRole.INACTIVE.name());
			driver.setRequestedBy(DriverRequestedType.FRANCHISE.name());
		} else {
			driver.setRequestedBy(DriverRequestedType.SELF.name());
			driver.setStatus(EnquiryRole.PENDING.name());
			driver.setFranchiseId(null);
		}
		
		BeanUtils.copyProperties(driver, driverDomain);
		franchiseCommonService.saveDriver(driverDomain);
		
		if(driver.getRequestedBy().equalsIgnoreCase(DriverRequestedType.SELF.name())) {
			int stateId =0;
			for(Address address:profile.getAddress()) {
				if(address.getType().equalsIgnoreCase(AddressType.OFFICE.getCode()))
					stateId = address.getStateId();
			}
			sendSMSToOpsTeam(stateId,Role.DRIVER.name());
		}
		
		/**
		 * Send Email
		 */
		if (null != profile.getEmailId() && profile.getEmailId().trim().length() > 0) {
			Map<String, Object> variables = new HashMap<>();
			variables.put("firstName", profileObject.getFirstName());
			variables.put("driverId", driver.getDriverId());
			variables.put("roleName", Role.DRIVER.name());
			String body = emailValidator.generateMailHtml("driver", variables);
			emailValidator.sendEmail("Move My Goods", profileObject.getEmailId(), body);
		}
		return profileObject;

	}

	@Override
	public String update(Profile profile) throws Exception {
		if(null != profile.getDriver()) {
			franchiseCommonService.updateDriver(profile.getDriver());
		}
		
		return "updated successfully";
	}
	
	private void sendSMSToOpsTeam(int stateId, String roleName) throws Exception {
		List<ProfileDomainV2> operationalTeamDomain = profileDAOV2.getProfilesByOtState(stateId,true,0,0);
		if (!operationalTeamDomain.isEmpty()) {
			String message = "Onboard " + roleName + " Request has been initiated. Please look into this";
			for (ProfileDomainV2 profileDomain : operationalTeamDomain) {
				String phNo = "91" + profileDomain.getMobileNumber();
				emailValidator.sendSMSMessage(phNo, null,message,false);
			}
		}
	}

}
