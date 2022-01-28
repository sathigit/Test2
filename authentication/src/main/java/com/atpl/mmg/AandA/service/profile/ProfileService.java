package com.atpl.mmg.AandA.service.profile;

import java.util.List;

import com.atpl.mmg.AandA.model.profile.ProfileModel;

public interface ProfileService {

	public ProfileModel saveProfile(ProfileModel profileModel) throws Exception;

	public String saveProfileforAndriod(ProfileModel profileModel) throws Exception;

	public ProfileModel getProfile(String id) throws Exception;

	public ProfileModel getProfileCustomer(String id) throws Exception;

	public ProfileModel getProfileFreqeuntCustomer(String id) throws Exception;

	public ProfileModel getProfileRefferCode(String id, String offercode) throws Exception;

	public List<ProfileModel> getProfilebyRole(int roleId) throws Exception;

	public ProfileModel getProfileId(String mobileNumber, boolean status) throws Exception;

	public String updateProfileAndCustomer(ProfileModel profileModel) throws Exception;

	public String updateCustomerCreditAmount(ProfileModel profileModel) throws Exception;

	public String awsSmsOTP(ProfileModel profileModel) throws Exception;

	public String forgotPasswordCustomerSmsOTP(ProfileModel profileModel) throws Exception;

	public ProfileModel getSmsOtp(String otp, String mobileNumber) throws Exception;

	public String Activation(ProfileModel profileModel) throws Exception;

	public String disableFranchise(ProfileModel profileModel) throws Exception;

	public String updatePassword(ProfileModel profileModel) throws Exception;

	public String updatePasswordById(ProfileModel profileModel) throws Exception;

	public List<ProfileModel> getActiveCustomer() throws Exception;

	public List<ProfileModel> getCustomerCSVDetails() throws Exception;

	public List<ProfileModel> getDisableCustomer() throws Exception;

	public List<ProfileModel> getCustomerbyCity(int cityId, boolean status) throws Exception;

	public List<ProfileModel> getCustomerByState(int cityId, boolean status) throws Exception;

	public String customerActivation(ProfileModel profileModel) throws Exception;

	public String disableCustomer(ProfileModel profileModel) throws Exception;

	public List<ProfileModel> getprofileIdbyCityid(int cityId) throws Exception;

	public String deleteProfile(int id) throws Exception;

	public String deleteCustomer(int id) throws Exception;

	public ProfileModel getProfileById(String id) throws Exception;

	public String updateTokenId(ProfileModel profileDomain) throws Exception;

	public String updateWebToken(ProfileModel profileDomain) throws Exception;

	public boolean isMobileNumberExists(ProfileModel profileModel) throws Exception;

	public boolean isEmailIdExists(ProfileModel profileModel) throws Exception;

	public boolean mobileNumberAlrdyExitOrNot(ProfileModel profileModel) throws Exception;

	public String deleteDriver(String mobileNumber) throws Exception;

	public List<ProfileModel> getAllProfileCustomer() throws Exception;

	public List<ProfileModel> getbdmProfileListonCity(int cityId) throws Exception;

	public List<ProfileModel> getbdoProfileListonCity(int cityId) throws Exception;

	public List<ProfileModel> getOPProfileListonCity(int cityId) throws Exception;

	public List<ProfileModel> getActiveBdms() throws Exception;

	public List<ProfileModel> getEnrolledActiveBdms() throws Exception;

	public List<ProfileModel> getActiveBdos() throws Exception;

	public List<ProfileModel> getEnrolledActiveBdos() throws Exception;

	public String updateCrmEnrolled(ProfileModel ProfileModel) throws Exception;

	public List<ProfileModel> getDriverNumber(int cityId) throws Exception;

	public ProfileModel saveProfileDetails(ProfileModel profileModel) throws Exception;

}
