package com.atpl.mmg.AandA.controller.profile;

import static com.atpl.mmg.AandA.exception.HttpResponseUtils.prepareSuccessResponse;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atpl.mmg.AandA.constant.Constants;
import com.atpl.mmg.AandA.domain.otp.Otp;
import com.atpl.mmg.AandA.exception.GenericRes;
import com.atpl.mmg.AandA.model.auth.AuthModel;
import com.atpl.mmg.AandA.model.profile.Profile;
import com.atpl.mmg.AandA.model.profile.ProfileEdit;
import com.atpl.mmg.AandA.model.profile.RouteTag;
import com.atpl.mmg.AandA.model.profilerole.ProfileRole;
import com.atpl.mmg.AandA.model.reason.ReasonModel;
import com.atpl.mmg.AandA.service.profile.ProfileServiceV2;

@RestController
@RequestMapping("/v2")
@SuppressWarnings("rawtypes")
public class ProfileControllerV2 {

	@Autowired
	ProfileServiceV2 profileServiceV2;

	@RequestMapping(value = "/profile", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<GenericRes> save(@RequestBody Profile profile,
			@RequestHeader(name = Constants.PROFILE_SECURITY_HEADER, required = false) String profileSecurityHeader)
			throws Exception {
		return prepareSuccessResponse(profileServiceV2.save(profile, profileSecurityHeader));
	}

	@RequestMapping(value = "/profile/sms/otp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> sendOtp(@RequestBody Profile profile, @RequestParam Map<String, String> reqParam)
			throws Exception {
		return prepareSuccessResponse(profileServiceV2.sendOtp(profile, reqParam));
	}

	@RequestMapping(value = "/profile/validateOtp", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> validateOtp(@RequestBody Otp otp) throws Exception {
		return prepareSuccessResponse(profileServiceV2.validateOtp(otp, false));
	}

	@RequestMapping(value = "/profile", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateProfile(@RequestBody Profile profile,
			@RequestHeader(name = Constants.PROFILE_SECURITY_HEADER, required = false) String profileSecurityHeader)
			throws Exception {
		return prepareSuccessResponse(profileServiceV2.update(profile, profileSecurityHeader));
	}

	@RequestMapping(value = "/profile/{profileId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getProfileDetails(@PathVariable("profileId") String profileId,
			@RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(profileServiceV2.getProfileDetails(profileId, reqParam));
	}

	@RequestMapping(value = "/profile/details", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getProfileDetByMobileNo(@RequestParam Map<String, String> reqParam,
			@RequestParam(name = "isRequired", required = false) boolean isRequired) throws Exception {
		return prepareSuccessResponse(profileServiceV2.getProfileDetByMobileNo(reqParam, isRequired));
	}

	@RequestMapping(value = "/profile/token", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateToken(@RequestBody ProfileRole profileRole) throws Exception {
		return prepareSuccessResponse(profileServiceV2.updateToken(profileRole));
	}

	@RequestMapping(value = "/profile/status", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> profileActivateOrDeactivate(@RequestBody ReasonModel reasonModel)
			throws Exception {
		return prepareSuccessResponse(profileServiceV2.profileActivateOrDeactivate(reasonModel));
	}

	@RequestMapping(value = "/profile/role/{roleId}/status/{status}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getActiveOrInactiveProfiles(@PathVariable("roleId") Integer roleId,
			@PathVariable("status") boolean status, @RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(profileServiceV2.getActiveOrInactiveProfiles(roleId, status, reqParam));
	}

	@RequestMapping(value = "/profile/role/{roleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getProfileDetailsByRoleId(@PathVariable("roleId") Integer roleId,
			@RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(profileServiceV2.getProfileDetailsByRoleId(roleId, reqParam));
	}

	@RequestMapping(value = "/profile/session/{isActive}/status/{status}/role/{roleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> usersSessionActiveOrInActiveListByCityId(
			@PathVariable("isActive") boolean isActive, @PathVariable("status") boolean status,
			@PathVariable("roleId") Integer roleId, @RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(
				profileServiceV2.usersSessionActiveOrInActiveListByCityId(isActive, status, roleId, reqParam));
	}

	@RequestMapping(value = "/profile/forgotpassword/mobile/{mobileNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> forgotPassword(@PathVariable("mobileNo") String mobileNo) throws Exception {
		return prepareSuccessResponse(profileServiceV2.forgotPassword(mobileNo));
	}

	@RequestMapping(value = "/profile/password", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updatePassword(@RequestBody Profile profile) throws Exception {
		return prepareSuccessResponse(profileServiceV2.updatePassword(profile));
	}

	@RequestMapping(value = "/profile/confirmPasswordMail", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> sendConfirmPasswordEmail(@RequestBody Profile profile) throws Exception {
		return prepareSuccessResponse(profileServiceV2.sendConfirmPasswordEmail(profile));
	}

	@RequestMapping(value = "/profile/role/{roleId}/bdm/profile/{profileId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getProfileByBdmId(@PathVariable("roleId") int roleId,
			@PathVariable("profileId") String profileId, @RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(profileServiceV2.getProfileByBdmId(roleId, profileId, reqParam));
	}

	@RequestMapping(value = "/profile/role/{roleId}/channelPartnerId/{cpId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getProfileByChannelPartnerId(@PathVariable("roleId") int roleId,
			@PathVariable("cpId") String cpId, @RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(profileServiceV2.getProfileByChannelPartnerId(roleId, cpId, reqParam));
	}

	@RequestMapping(value = "/profile/role/{roleId}/status/{status}/franchise/{franchiseId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getProfileByRoleAndFranchiseId(@PathVariable("roleId") int roleId,
			@PathVariable("status") boolean status, @PathVariable("franchiseId") String franchiseId,
			@RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(
				profileServiceV2.getProfileByRoleAndFranchiseId(roleId, status, franchiseId, true, reqParam));
	}

	@RequestMapping(value = "/profile/companyProfile", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getProfileDetailsByFranchiseId(@RequestParam Map<String, String> reqParam)
			throws Exception {
		return prepareSuccessResponse(profileServiceV2.getProfileDetailsByCompanyProfileId(reqParam));
	}

	@RequestMapping(value = "/profile/operationalTeam/{otAssignedStateId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getOTDetailsByOtStateId(@PathVariable("otAssignedStateId") int otAssignedStateId,
			@RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(profileServiceV2.getOTDetailsByOtStateId(otAssignedStateId, reqParam));
	}

	/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(value = "/profile/role", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<GenericRes> saveRole(@RequestBody ProfileRole profileRole) throws Exception {
		return prepareSuccessResponse(profileServiceV2.saveRole(profileRole));
	}

	@RequestMapping(value = "/profile/id/{profileId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getProfileDetailsBy(@PathVariable("profileId") String profileId,
			@RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(profileServiceV2.getProfileDetails(profileId, reqParam));
	}

	@RequestMapping(value = "/profile/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> profilesSearch(@RequestParam Map<String, String> reqParam, HttpServletRequest req)
			throws Exception {
		return prepareSuccessResponse(profileServiceV2.profilesSearch(reqParam));
	}

	@RequestMapping(value = "/alterAddress", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateAddress() throws Exception {
		return prepareSuccessResponse(profileServiceV2.updateAddress());
	}
	
	@RequestMapping(value = "/tag/vendor", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> tagVendor(@RequestBody RouteTag routeTag) throws Exception {
		return prepareSuccessResponse(profileServiceV2.tagVendor(routeTag));
	}

	@RequestMapping(value = "/profile/edit/email/mobile/validate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<GenericRes> profileMobileEmailSendOtp(@RequestBody ProfileEdit profileEdit, @RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(profileServiceV2.profileMobileEmailSendOtp(profileEdit,reqParam));
	}

	@RequestMapping(value = "/profile/edit/email/mobile", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<GenericRes> profileMobileEmailEditValidateOtp(@RequestBody ProfileEdit profileEdit, @RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(profileServiceV2.profileMobileEmailEditValidateOtp(profileEdit,reqParam));
	}

	/**********************************************
	 * Generic Apis to reduce performance issue
	 **********************************************/
	@RequestMapping(value = "/profile/details", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<GenericRes> getFranchiseDetails(@RequestBody AuthModel authModel) throws Exception {
		return prepareSuccessResponse(profileServiceV2.getAuthDetails(authModel));
	}

	@RequestMapping(value = "/profile/details/role/{roles}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<GenericRes> getProfileDetailsOnRole(@PathVariable("roles") String roles,
			@RequestParam Map<String, String> reqParam) throws Exception {
		return prepareSuccessResponse(profileServiceV2.getProfileDetailsOnRole(roles, reqParam));
	}


}
