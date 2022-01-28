package com.atpl.mmg.AandA.controller.profile;

import static com.atpl.mmg.AandA.exception.HttpResponseUtils.prepareSuccessResponse;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atpl.mmg.AandA.exception.GenericRes;
import com.atpl.mmg.AandA.model.profile.ProfileModel;
import com.atpl.mmg.AandA.service.profile.ProfileService;

@RestController
@RequestMapping("/v1")
@SuppressWarnings("rawtypes")
@Deprecated
public class ProfileController {

	@Autowired
	ProfileService profileService;

	@RequestMapping(value = "/profile", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> saveProfile(@RequestBody ProfileModel profileModel) throws Exception {
		return prepareSuccessResponse(profileService.saveProfile(profileModel));
	}

	@RequestMapping(value = "/profiles", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> saveProfileforAndriod(@RequestBody ProfileModel profileModel) throws Exception {
		return prepareSuccessResponse(profileService.saveProfileforAndriod(profileModel));
	}

	@RequestMapping(value = "/getprofile/{profileId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getprofile(@PathVariable("profileId") String profileId) throws Exception {
		return prepareSuccessResponse(profileService.getProfile(profileId));
	}

	@RequestMapping(value = "/profile/customer/{profileId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getProfileCustomer(@PathVariable("profileId") String profileId)
			throws Exception {
		return prepareSuccessResponse(profileService.getProfileCustomer(profileId));
	}

	@RequestMapping(value = "/profile/freqeunt/customer/{profileId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getProfileFreqeuntCustomer(@PathVariable("profileId") String profileId)
			throws Exception {
		return prepareSuccessResponse(profileService.getProfileFreqeuntCustomer(profileId));
	}

	@RequestMapping(value = "/profile/refferCode/{id}/{refferCode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getprofile(@PathVariable("id") String id,
			@PathVariable("refferCode") String refferCode) throws Exception {
		return prepareSuccessResponse(profileService.getProfileRefferCode(id, refferCode));
	}

	@RequestMapping(value = "/deleteProfile/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> deleteProfile(@PathVariable("id") Integer id) throws Exception {
		return prepareSuccessResponse(profileService.deleteCustomer(id));
	}

	@RequestMapping(value = "/getProfileId/{mobileNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getProfileId(@PathVariable("mobileNumber") String mobileNumber)
			throws Exception {
		return prepareSuccessResponse(profileService.getProfileId(mobileNumber, false));
	}

	@RequestMapping(value = "/updateProfile", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateProfile(@RequestBody ProfileModel profileModel) throws Exception {
		return prepareSuccessResponse(profileService.updateProfileAndCustomer(profileModel));
	}

	@RequestMapping(value = "/updatetoken", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateTokenId(@RequestBody ProfileModel profileModel) throws Exception {
		return prepareSuccessResponse(profileService.updateTokenId(profileModel));
	}

	@RequestMapping(value = "/customer/creditAmount", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateCustomerCreditAmount(@RequestBody ProfileModel profileModel)
			throws Exception {
		return prepareSuccessResponse(profileService.updateCustomerCreditAmount(profileModel));
	}

	@RequestMapping(value = "/web/token", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateWebToken(@RequestBody ProfileModel profileModel) throws Exception {
		return prepareSuccessResponse(profileService.updateWebToken(profileModel));
	}

	@RequestMapping(value = "/updatePassword", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updatePassword(@RequestBody ProfileModel profileModel) throws Exception {
		return prepareSuccessResponse(profileService.updatePassword(profileModel));

	}

	@RequestMapping(value = "/updatePassword/id", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updatePasswordById(@RequestBody ProfileModel profileModel) throws Exception {
		return prepareSuccessResponse(profileService.updatePasswordById(profileModel));

	}

	@RequestMapping(value = "/verifyMobileId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> awsSmsOTP(@RequestBody ProfileModel profileModel) throws Exception {
		return prepareSuccessResponse(profileService.awsSmsOTP(profileModel));
	}

	@RequestMapping(value = "/forgotPassword/verfiyMobileId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> forgotPasswordSmsOTP(@RequestBody ProfileModel profileModel) throws Exception {
		return prepareSuccessResponse(profileService.forgotPasswordCustomerSmsOTP(profileModel));

	}

	@RequestMapping(value = "/profile/forgotPassword/verfiyMobileId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> forgotPasswordAllSmsOTP(@RequestBody ProfileModel profileModel) throws Exception {
		return prepareSuccessResponse(profileService.forgotPasswordCustomerSmsOTP(profileModel));
	}

	@RequestMapping(value = "/mobileNumber/validation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> mobileNumberAlrdyExitOrNot(@RequestBody ProfileModel profileModel)
			throws Exception {
		return prepareSuccessResponse(profileService.mobileNumberAlrdyExitOrNot(profileModel));
	}

	@RequestMapping(value = "/getSmsOtp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getSmsOtp(@RequestBody ProfileModel profileModel) throws Exception {
		return prepareSuccessResponse(profileService.getSmsOtp(profileModel.getOtp(), profileModel.getMobileNumber()));
	}

	@RequestMapping(value = "/franchiseActivate", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> Activation(@RequestBody ProfileModel profileModel) throws Exception {
		return prepareSuccessResponse(profileService.Activation(profileModel));
	}

	@RequestMapping(value = "/disableFranchises", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> disableFranchise(@RequestBody ProfileModel profileModel) throws Exception {
		return prepareSuccessResponse(profileService.disableFranchise(profileModel));
	}

	@RequestMapping(value = "/getProfilebyRole/{roleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getProfilebyRole(@PathVariable("roleId") int roleId) throws Exception {
		return prepareSuccessResponse(profileService.getProfilebyRole(roleId));
	}

	@RequestMapping(value = "/customer/profile", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getAllProfileCustomer() throws Exception {
		return prepareSuccessResponse(profileService.getAllProfileCustomer());
	}

	@RequestMapping(value = "/active/Customer", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getActiveCustomer() throws Exception {
		return prepareSuccessResponse(profileService.getActiveCustomer());
	}

	@RequestMapping(value = "/disable/Customer", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getDisableCustomer() throws Exception {
		return prepareSuccessResponse(profileService.getDisableCustomer());
	}

	@RequestMapping(value = "/customer/city/{cityId}/{status}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getActiveCustomerbyCity(@PathVariable("cityId") int cityId,
			@PathVariable("status") boolean status) throws Exception {
		return prepareSuccessResponse(profileService.getCustomerbyCity(cityId, status));
	}

	@RequestMapping(value = "/customer/state/{stateId}/{status}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getDisableCustomerbyCity(@PathVariable("stateId") int stateId,
			@PathVariable("status") boolean status) throws Exception {
		return prepareSuccessResponse(profileService.getCustomerByState(stateId, status));
	}

	@RequestMapping(value = "/customerActivate", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> customerActivation(@RequestBody ProfileModel profileModel) throws Exception {
		return prepareSuccessResponse(profileService.customerActivation(profileModel));
	}

	@RequestMapping(value = "/disableCustomer", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> disableCustomer(@RequestBody ProfileModel profileModel) throws Exception {
		return prepareSuccessResponse(profileService.disableCustomer(profileModel));
	}

	@RequestMapping(value = "/profileId/by/cityId/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getprofileIdbyCityid(@PathVariable("id") int id) throws Exception {
		return prepareSuccessResponse(profileService.getprofileIdbyCityid(id));
	}

	@RequestMapping(value = "/getOnboardprofile/{profileId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getProfileById(@PathVariable("profileId") String profileId) throws Exception {
		return prepareSuccessResponse(profileService.getProfileById(profileId));
	}

	@RequestMapping(value = "/updateCompany", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateCompany(@RequestBody ProfileModel profileModel) throws Exception {
		return prepareSuccessResponse(profileService.updateProfileAndCustomer(profileModel));
	}

	@RequestMapping(value = "/companyProfile", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> companyProfile(@RequestBody ProfileModel profileModel) throws Exception {
		return prepareSuccessResponse(profileService.saveProfile(profileModel));
	}

	@RequestMapping(value = "/companyProfile2", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> saveProfile1(@RequestBody ProfileModel profileModel) throws Exception {
		return prepareSuccessResponse(profileService.saveProfileDetails(profileModel));
	}

	@RequestMapping(value = "/mobileNumberIsexists", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> IsMobileNumberExists(@RequestBody ProfileModel profileModel) throws Exception {
		return prepareSuccessResponse(profileService.isMobileNumberExists(profileModel));
	}

	@RequestMapping(value = "/isEmailIdExists", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> isEmailIdExists(@RequestBody ProfileModel profileModel) throws Exception {
		return prepareSuccessResponse(profileService.isEmailIdExists(profileModel));
	}

	@RequestMapping(value = "/delete/driver/{mobileNumber}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> deleteDriver(@PathVariable("mobileNumber") String mobileNumber)
			throws Exception {
		return prepareSuccessResponse(profileService.deleteDriver(mobileNumber));
	}

	@RequestMapping(value = "/deleteAuthProfile/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> deleteAuthProfile(@PathVariable("id") Integer id) throws Exception {
		return prepareSuccessResponse(profileService.deleteProfile(id));
	}

	@RequestMapping(value = "/getbdmProfileList/{cityId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getbdmProfileListonCity(@PathVariable("cityId") int cityId) throws Exception {
		return prepareSuccessResponse(profileService.getbdmProfileListonCity(cityId));
	}

	@RequestMapping(value = "/profile/bdo/{cityId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getbdoProfileListonCity(@PathVariable("cityId") int cityId) throws Exception {
		return prepareSuccessResponse(profileService.getbdoProfileListonCity(cityId));
	}

	@RequestMapping(value = "/profile/operational/{cityId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getOPProfileListonCity(@PathVariable("cityId") int cityId) throws Exception {
		return prepareSuccessResponse(profileService.getOPProfileListonCity(cityId));
	}

	@RequestMapping(value = "/getActiveBdms", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getActiveBdms() throws Exception {
		return prepareSuccessResponse(profileService.getActiveBdms());
	}

	@RequestMapping(value = "/getActiveBdos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getActiveBdos() throws Exception {
		return prepareSuccessResponse(profileService.getActiveBdos());
	}

	@RequestMapping(value = "/getEnrolledActiveBdos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getEnrolledActiveBdos() throws Exception {
		return prepareSuccessResponse(profileService.getEnrolledActiveBdos());
	}

	@RequestMapping(value = "/getEnrolledActiveBdms", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getEnrolledActiveBdms() throws Exception {
		return prepareSuccessResponse(profileService.getEnrolledActiveBdms());
	}

	@RequestMapping(value = "/updateCrmEnrolled", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateCrmEnrolled(@RequestBody ProfileModel profileModel) throws Exception {
		return prepareSuccessResponse(profileService.updateCrmEnrolled(profileModel));
	}

	@RequestMapping(value = "/getCustomerCSVDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getCustomerCSVDetails() throws Exception {
		return prepareSuccessResponse(profileService.getCustomerCSVDetails());
	}

	@RequestMapping(value = "/driver/number/{cityId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getDriverNumber(@PathVariable("cityId") int cityId) throws Exception {
		return prepareSuccessResponse(profileService.getDriverNumber(cityId));
	}

	@RequestMapping(value = "/customerProfileId/{mobileNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getCustomerProfileId(@PathVariable("mobileNumber") String mobileNumber)
			throws Exception {
		return prepareSuccessResponse(profileService.getProfileId(mobileNumber, true));
	}

}
