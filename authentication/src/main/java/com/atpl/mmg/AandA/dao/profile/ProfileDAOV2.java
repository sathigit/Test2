package com.atpl.mmg.AandA.dao.profile;

import java.math.BigInteger;
import java.util.List;

import com.atpl.mmg.AandA.domain.profile.ProfileDomainV2;
import com.atpl.mmg.AandA.domain.profilerole.ProfileRoleDomain;
import com.atpl.mmg.AandA.model.profile.CoordinatorDetail;

public interface ProfileDAOV2 {

	public ProfileDomainV2 getProfileDetByPanOrAadharNo(String panNumber, BigInteger aadharNumber) throws Exception;

	public ProfileDomainV2 getProfileByMobileNumberOrEmailId(String mobileNumber, String emailId, int roleId)
			throws Exception;

	public List<ProfileDomainV2> getProfileByMobileNumberOrEmailId(String mobileNumber, String emailId)
			throws Exception;

	public String updateCommonProfile(ProfileDomainV2 profileDomainV2) throws Exception;

	public ProfileDomainV2 save(ProfileDomainV2 profileDomainV2) throws Exception;

	public ProfileDomainV2 getProfileDetails(String id) throws Exception;

	public String updateProfile(ProfileDomainV2 profileDomain, int roleId) throws Exception;

	public ProfileDomainV2 getProfileByIdAndRole(String id, int roleId) throws Exception;

	public ProfileDomainV2 getProfileDetByMobileOrEmail(String mobileOrEmail, int roleId) throws Exception;

	public String updateToken(ProfileRoleDomain profileRoleDomain, String profileSource) throws Exception;

	public String profileActivateOrDeactivate(boolean status, String profileId, Integer roleId) throws Exception;

	public List<ProfileDomainV2> userActiveOrInActiveList(Integer roleId, boolean status, int lowerBound,
			int upperBound, int cityId, int stateId, String addressType,int customerTypeId,String searchText) throws Exception;

//	public List<ProfileDomainV2> getProfilesByCityAndSts(Integer cityId, boolean status, Integer roleId,String type,int lowerBound,int upperBound) throws Exception;

//	public List<ProfileDomainV2> getProfilesByStateAnsSts(Integer stateId, boolean status, Integer roleId, String type,int lowerBound,int upperBound) throws Exception;

	public List<ProfileDomainV2> getProfilesByCity(Integer cityId, Integer roleId, String type,Boolean isTag, String searchText, int lowerBound,
			int upperBound) throws Exception;

	public List<ProfileDomainV2> getProfilesByState(Integer stateId, Integer roleId, String type,Boolean isTag,String searchText, int lowerBound,
			int upperBound) throws Exception;

	public List<ProfileDomainV2> usersSessionActiveOrInActiveListByCityId(boolean isActive, Integer cityId,
			boolean status, Integer roleId, String addressType) throws Exception;

	public List<ProfileDomainV2> getProfileDetailsByRoleId(Integer roleId,Boolean isTag, String searchText, int lowerBound, int upperBound)
			throws Exception;

	public String updatePasswordById(ProfileDomainV2 profileDomain) throws Exception;

	public List<ProfileDomainV2> getProfileByBdmId(int roleId, String bdmId,String searchText, int lowerBound, int upperBound)
			throws Exception;

	public List<ProfileDomainV2> getProfileByCpId(int roleId, String cpId, String status, int lowerBound,
			int upperBound) throws Exception;

	public List<ProfileDomainV2> getProfileByRoleIdAndStsAndBdmId(boolean status, int roleId, String bdmId,String searchText,
			int lowerBound, int upperBound) throws Exception;

	public List<ProfileDomainV2> getProfileByRoleAndFranchiseId(int roleId, boolean status, String franchiseId,String searchText,
			int lowerBound, int upperBound) throws Exception;

	public List<ProfileDomainV2> getProfileByRoleAndFranchiseId(int roleId, String franchiseId, int lowerBound,
			int upperBound) throws Exception;

	public ProfileDomainV2 getProfileDetailsByCompanyProfileId(int roleId, String companyProfileId) throws Exception;

	public List<ProfileDomainV2> getProfilesByOtState(int otStateId, boolean status, int lowerBound, int upperBound)
			throws Exception;
	
	CoordinatorDetail getCoordinatorByCoordinatorId(String coordinatorId) throws Exception;
	
	public String updateEmailAndMobileNo(String email,String mobileNumber,String profileId) throws Exception;


	/************************************************************************************************************************************************************************/

	// public ProfileDomain getPasswordById(String id) throws Exception;


}
