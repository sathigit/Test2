package com.atpl.mmg.AandA.service.profile;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import com.atpl.mmg.AandA.domain.otp.Otp;
import com.atpl.mmg.AandA.model.auth.AuthModel;
import com.atpl.mmg.AandA.model.profile.ListDto;
import com.atpl.mmg.AandA.model.profile.Profile;
import com.atpl.mmg.AandA.model.profile.ProfileEdit;
import com.atpl.mmg.AandA.model.profile.RouteTag;
import com.atpl.mmg.AandA.model.profilerole.ProfileRole;
import com.atpl.mmg.AandA.model.reason.ReasonModel;

@SuppressWarnings("rawtypes")
public interface ProfileServiceV2 {

	public Profile save(Profile profile, String profileSecurityHeader) throws Exception;

	public String sendOtp(Profile profile,Map<String, String> reqParam) throws Exception;
	
//	public Profile validateOtp(String mobileNumber, String otp) throws Exception;
	public Profile validateOtp(Otp otp,boolean profileEdit) throws Exception;
	
	public String update(Profile profile,String profileSecurityHeader) throws Exception;
	
	public Profile getProfileDetails(String id, Map<String, String> reqParam) throws Exception;
	
	public Profile getProfileDetByMobileNo(Map<String, String> reqParam,boolean isRequired) throws Exception;
	
	public String updateToken(ProfileRole profileRole) throws Exception;
	
	public String profileActivateOrDeactivate(ReasonModel reasonModel) throws Exception;
	
	public ListDto getActiveOrInactiveProfiles(Integer roleId, boolean status,Map<String, String> reqParam) throws Exception;
	
	public List<Profile> usersSessionActiveOrInActiveListByCityId(boolean isActive, boolean status, Integer roleId,Map<String, String> reqParam) throws Exception;
	
	public ListDto getProfileDetailsByRoleId(Integer roleId,Map<String, String> reqParam) throws Exception;
	
	public String forgotPassword(String mobileNo) throws Exception;
	
	public String updatePassword(Profile profile) throws Exception;
	
	public String sendConfirmPasswordEmail(Profile profile) throws Exception;
	
	public ListDto getProfileByBdmId(int roleId, String bdmId,Map<String, String> reqParam) throws Exception;
	
	public ListDto getProfileByChannelPartnerId(int roleId, String cpId,Map<String, String> reqParam) throws Exception;
	
	public ListDto getProfileByRoleAndFranchiseId(int roleId, boolean status, String franchiseId,boolean isFlag, Map<String, String> reqParam) throws Exception;
	
	public Profile getProfileDetailsByCompanyProfileId(@RequestParam Map<String, String> reqParam)throws Exception;
	
	public ListDto getOTDetailsByOtStateId(int otAssignedStateId,@RequestParam Map<String, String> reqParam) throws Exception;
	
	public String tagVendor(RouteTag routeTag) throws Exception;
/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public String saveRole(ProfileRole profileRole) throws Exception;
	
	public byte[] downloadProfileList(boolean status,int roleId) throws Exception;
	
	public ListDto profilesSearch(Map<String, String> reqParam) throws Exception;
	
	public String updateAddress() throws Exception;
	
	public AuthModel getAuthDetails(AuthModel authModel) throws Exception;
	
	public AuthModel getProfileDetailsOnRole(String roles,Map<String, String> reqParam) throws Exception;

	public String profileMobileEmailSendOtp(ProfileEdit profileEdit, Map<String, String> reqParam)throws Exception;

	public String profileMobileEmailEditValidateOtp(ProfileEdit profileEdit, Map<String, String> reqParam) throws Exception;
	
	public String profileMobileEmailEdit(ProfileEdit profile, Map<String, String> reqParam)throws Exception;

}
