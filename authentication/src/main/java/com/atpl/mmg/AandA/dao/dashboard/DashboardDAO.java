package com.atpl.mmg.AandA.dao.dashboard;

import java.util.List;

import com.atpl.mmg.AandA.domain.dashboard.DashboardDomain;
import com.atpl.mmg.AandA.domain.dashboard.LeadDashboardDomain;
import com.atpl.mmg.AandA.domain.dashboard.ProfileDashboardDomain;

public interface DashboardDAO {

	public List<ProfileDashboardDomain> getProfileCount(boolean status) throws Exception;

	public List<ProfileDashboardDomain> getProfileCountByCityId(boolean status, int cityId) throws Exception;

	public List<ProfileDashboardDomain> getProfileCountByStateId(boolean status, int stateId) throws Exception;

	public DashboardDomain getProfileCountByRoleAndSts(int roleId, boolean status,boolean isStatus,int customerTypeId) throws Exception;

	public DashboardDomain getProfileCountByRoleAndSts(int roleId, boolean status, int cityId,boolean isStatus,Boolean isTag,int customerTypeId) throws Exception;

	public DashboardDomain getProfileCountByRoleAndStsAndStateId(int roleId, boolean status, int stateId,boolean isStatus,Boolean isTag,int customerTypeId)
			throws Exception;

	public DashboardDomain getProfileCountByFranchiseId(String franchiseId) throws Exception;
	
	public DashboardDomain getProfilCounteByRoleAndFranchiseId(int roleId, boolean status, String franchiseId,String searchText) throws Exception;

	public DashboardDomain getProfileByChannelPartnerId(int roleId, boolean status, String cpId,boolean isStatus) throws Exception;
	
	public DashboardDomain getDashboardByBdmId(String roleId, String bdmId,boolean status,boolean isStatus,String searchText) throws Exception;
	
	public DashboardDomain getProfilesCountByOtState(int otStateId, boolean status) throws Exception;

	public LeadDashboardDomain getCustomerLead() throws Exception;

	public LeadDashboardDomain getCustomerLead(String assignedId) throws Exception;

	public LeadDashboardDomain getCustomerLeadByStsId(String leadStatusId, String assignedId) throws Exception;

	public LeadDashboardDomain getCustomerLeadByRmksId(String leadRemarksId, String assignedId) throws Exception;

	public LeadDashboardDomain getCustomerLeadByUploadedById(String uploadedById) throws Exception;

	public LeadDashboardDomain getCustomerLeadByAssignedId(String assignedId) throws Exception;
	
	public DashboardDomain getCustomerLeadCountByStsAndAssignedId(String status,String assignedId) throws Exception;

	public DashboardDomain getCustomerLeadsByStsAndAssignedIdAndUploadedId(String status, String assignedId,String uploadedById) throws Exception;
	
	public DashboardDomain  getReasonsCountByRoleByProfile(int roleId,String profileId,boolean checkRequestedBy,boolean isFlag) throws Exception;
	
	public DashboardDomain getProfileSearchByRoleAndSts(int roleId, boolean status,boolean isStatus,String searchText,int customerTypeId) throws Exception;

	public DashboardDomain getProfileSearchCountByRoleAndSts(int roleId, boolean status, int cityId,boolean isStatus,String searchText,int customerTypeId) throws Exception;

	public DashboardDomain getProfileSearchCountByRoleAndStsAndStateId(int roleId, boolean status, int stateId,boolean isStatus,String searchText,int customerTypeId)
			throws Exception;
}