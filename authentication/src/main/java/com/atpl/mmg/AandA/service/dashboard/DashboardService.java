package com.atpl.mmg.AandA.service.dashboard;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import com.atpl.mmg.AandA.model.dashboard.DashboardModel;
import com.atpl.mmg.AandA.model.dashboard.LeadDashboardModel;
import com.atpl.mmg.AandA.model.dashboard.ProfileDashboardModel;

public interface DashboardService {

	public List<ProfileDashboardModel> getProfileCount(Map<String, String> reqParam) throws Exception;
	
	public DashboardModel getprofileCountByFranchiseId(String franchiseId) throws Exception;
	
	public DashboardModel getDashboardByBdmId(int roleId,String profileId) throws Exception;
	
	public int getProfileCountByRoleAndSts(int roleId,boolean status,int cityId,int stateId,int customerTypeId) throws Exception;
	
	public int getProfileCountByRole(int roleId,int cityId,int stateId,Boolean isTag) throws Exception;

	public int getProfileCountByChannelPartnerId(int roleId, boolean status, String cpId,boolean isStatus) throws Exception;
	
	public LeadDashboardModel getCustomerLeadByStsId(@RequestParam Map<String, String> reqParam) throws Exception;

	public LeadDashboardModel getCustomerLead(String uploadedById) throws Exception;

	public LeadDashboardModel getCustomerLeadByAssignedId(String assignedId,@RequestParam Map<String, String> reqParam) throws Exception;
	
	public int getProfilCounteByRoleAndFranchiseId(int roleId, boolean status, String franchiseId,String searchText) throws Exception;
	
	public int getProfileSearchCountByRole(int roleId,boolean status,int cityId,int stateId,String searchText, int customerTypeId) throws Exception;
}
