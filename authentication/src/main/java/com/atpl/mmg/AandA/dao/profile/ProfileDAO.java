package com.atpl.mmg.AandA.dao.profile;

import java.math.BigInteger;
import java.util.List;

import com.atpl.mmg.AandA.domain.profile.ProfileDomain;

public interface ProfileDAO {

	public ProfileDomain saveProfile(ProfileDomain profileDomain) throws Exception;

	public ProfileDomain saveProfileCustomer(ProfileDomain profileDomain) throws Exception;

	public String saveProfileforAndriod(ProfileDomain profileDomain) throws Exception;

	public ProfileDomain getProfileId(String mobileNumber) throws Exception;

	public ProfileDomain getCustomerProfileId(String mobileNumber) throws Exception;

	public ProfileDomain getProfile(String id) throws Exception;

	public ProfileDomain getProfileCustomer(String id) throws Exception;

	public ProfileDomain getProfileFreqeuntCustomer(String id) throws Exception;

	public ProfileDomain getProfileRefferCode(String id,String offerCode) throws Exception;

	public List<ProfileDomain> getAllProfileCustomer() throws Exception;

	public ProfileDomain getProfileByEmailId(String emailId) throws Exception;

	public ProfileDomain getPasswordById(String id) throws Exception;

	public ProfileDomain getPasswordByIdCustomer(String id) throws Exception;

	public String updateProfile(ProfileDomain profileDomain) throws Exception;
	
	public String updateDriverProfile(ProfileDomain profileDomain) throws Exception;

	public String updateCustomerProfile(ProfileDomain profileDomain) throws Exception;

	public String updateCustomerCreditAmount(ProfileDomain profileDomain) throws Exception;

	public String Activation(ProfileDomain profileDomain) throws Exception;

	public String disableFranchise(ProfileDomain profileDomain) throws Exception;

	public String updatePassword(ProfileDomain profileDomain) throws Exception;

	public String updatePasswordCustomer(ProfileDomain profileDomain) throws Exception;

	public String updatePasswordById(ProfileDomain profileDomain) throws Exception;

	public String updatePasswordByIdCustomer(ProfileDomain profileDomain) throws Exception;

	public List<ProfileDomain> getProfilebyRole(int roleId) throws Exception;

	public List<ProfileDomain> customerActiveOrInActive(boolean status) throws Exception;

	public List<ProfileDomain> getCustomerCSVDetails() throws Exception;

	public List<ProfileDomain> getCustomerbyCity(int cityId, boolean status) throws Exception;

	public List<ProfileDomain> getCustomerByState(int stateId, boolean status) throws Exception;

	public String customerActivation(ProfileDomain profileDomain) throws Exception;

	public String disableCustomer(ProfileDomain profileDomain) throws Exception;

	public String deleteProfile(int id) throws Exception;
	
	public String deleteCustomer(int id) throws Exception;

	public ProfileDomain getProfileById(String id) throws Exception;

	public ProfileDomain getProfileByCityId(int cityId, String id) throws Exception;

	public String updateCompany(ProfileDomain profileDomain) throws Exception;

	public String updateProfileTokenId(ProfileDomain profileDomain) throws Exception;

	public String updateWebToken(ProfileDomain profileDomain) throws Exception;

	public String updateTokenIdCustomer(ProfileDomain profileDomain) throws Exception;

	public ProfileDomain getProfileDetails(BigInteger mobileNumber) throws Exception;

	public String deleteDriver(String mobileNumber) throws Exception;

	public List<ProfileDomain> profileDetailsByCity(String roleId,int cityId) throws Exception;

	public List<ProfileDomain> activeBDMsOrBDOs(String roleId) throws Exception;

	public List<ProfileDomain> enrolledActiveBdmsOrBDOs(String roleId) throws Exception;

	public String updateCrmEnrolled(ProfileDomain profileDomain) throws Exception;

}
