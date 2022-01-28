package com.atpl.mmg.AandA.service.dashboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.atpl.mmg.AandA.constant.Constants;
import com.atpl.mmg.AandA.constant.Role;
import com.atpl.mmg.AandA.dao.dashboard.DashboardDAO;
import com.atpl.mmg.AandA.dao.profile.BdmDAO;
import com.atpl.mmg.AandA.domain.dashboard.DashboardDomain;
import com.atpl.mmg.AandA.domain.dashboard.LeadDashboardDomain;
import com.atpl.mmg.AandA.domain.dashboard.ProfileDashboardDomain;
import com.atpl.mmg.AandA.domain.profile.BDMDomain;
import com.atpl.mmg.AandA.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.AandA.exception.MmgRestException.ROLE_NOT_FOUND;
import com.atpl.mmg.AandA.mapper.dashboard.DashboardMapper;
import com.atpl.mmg.AandA.mapper.dashboard.ProfileDashboardMapper;
import com.atpl.mmg.AandA.model.dashboard.DashboardModel;
import com.atpl.mmg.AandA.model.dashboard.LeadDashboardModel;
import com.atpl.mmg.AandA.model.dashboard.ProfileDashboardModel;
import com.atpl.mmg.AandA.model.profile.Driver;
import com.atpl.mmg.AandA.service.profile.AuthFranchiseCommonService;
import com.atpl.mmg.AandA.utils.CommonUtils;

@Service("dashboardService")
public class DashboardServiceImpl implements DashboardService, Constants {

	@Autowired
	DashboardDAO dashboardDAO;

	@Autowired
	DashboardMapper dashboardMapper;
	
	@Autowired
	ProfileDashboardMapper profileDashboardMapper;

	@Autowired
	BdmDAO bdmDAO;

	@Autowired
	AuthFranchiseCommonService franchiseCommonService;

	/**
	 * Author:Vidya S K Modified Date: 2/20/2020 Description: Customer count
	 * 
	 */
	@Override
	public List<ProfileDashboardModel> getProfileCount(Map<String, String> reqParam) throws Exception {
		String status = null;
		int cityId = 0;
		int stateId = 0;
        boolean isActive =false;
		if (reqParam.size() > 0) {
			if (reqParam.containsKey("status"))
				status = reqParam.get("status");
			if (reqParam.containsKey("cityId"))
				cityId = Integer.parseInt(reqParam.get("cityId"));
			if (reqParam.containsKey("stateId"))
				stateId = Integer.parseInt(reqParam.get("stateId"));
		}
		if(!CommonUtils.isNullCheck(status))
	       if(status.equalsIgnoreCase("true"))
	        	isActive = true;

 		List<ProfileDashboardDomain> profileDomain = new ArrayList<ProfileDashboardDomain>();

		if (cityId > 0)
			profileDomain = dashboardDAO.getProfileCountByCityId(isActive, cityId);
		else if (stateId > 0)
			profileDomain = dashboardDAO.getProfileCountByStateId(isActive, stateId);
		else
			profileDomain = dashboardDAO.getProfileCount(isActive);

		return profileDashboardMapper.entityList(profileDomain);
	}

	@Override
	public int getProfileCountByRoleAndSts(int roleId, boolean status, int cityId, int stateId,int customerTypeId) throws Exception {
		DashboardDomain dashboardDomain = new DashboardDomain();
		if (cityId > 0)
			dashboardDomain = dashboardDAO.getProfileCountByRoleAndSts(roleId, status, cityId, true,false,customerTypeId);
		else if (stateId > 0)
			dashboardDomain = dashboardDAO.getProfileCountByRoleAndStsAndStateId(roleId, status, stateId, true,false,customerTypeId);
		else
			dashboardDomain = dashboardDAO.getProfileCountByRoleAndSts(roleId, status, true,customerTypeId);
		return dashboardDomain.getTotal();
	}

	@Override
	public int getProfileCountByRole(int roleId, int cityId, int stateId,Boolean isTag) throws Exception {
		boolean status = false;
		DashboardDomain dashboardDomain = new DashboardDomain();
		if (cityId > 0)
			dashboardDomain = dashboardDAO.getProfileCountByRoleAndSts(roleId, status, cityId, false,isTag,0);
		else if (stateId > 0)
			dashboardDomain = dashboardDAO.getProfileCountByRoleAndStsAndStateId(roleId, status, stateId, false,isTag,0);
		else
			dashboardDomain = dashboardDAO.getProfileCountByRoleAndSts(roleId, status, false,0);
		return dashboardDomain.getTotal();
	}

	@Override
	public DashboardModel getprofileCountByFranchiseId(String franchiseId) throws Exception {
		DashboardDomain dashboardDomain = dashboardDAO.getProfileCountByFranchiseId(franchiseId);
		DashboardModel dashboardModel = new DashboardModel();
		BeanUtils.copyProperties(dashboardDomain, dashboardModel);

		Driver driver = franchiseCommonService.getDriverCountByFranchiseId(franchiseId);
		dashboardModel.setDriver(driver.getTotal());
		return dashboardModel;
	}

	@Override
	public DashboardModel getDashboardByBdmId(int roleId, String profileId) throws Exception {
		Role role = Role.getRole(roleId + "");
		if (role == null)
			throw new ROLE_NOT_FOUND(roleId + "");
		BDMDomain bdmDomain = bdmDAO.getBDMByProfileId(profileId);
		DashboardDomain profileDomain = dashboardDAO.getDashboardByBdmId(role.getCode(), bdmDomain.getBdmId(),false,false,null);
		DashboardModel ProfileModel = new DashboardModel();
		BeanUtils.copyProperties(profileDomain, ProfileModel);
		return ProfileModel;
	}

	@Override
	public LeadDashboardModel getCustomerLeadByStsId(@RequestParam Map<String, String> reqParam) throws Exception {
		String leadStatusId = null, leadRemarksId = null;
		if (reqParam.size() > 0) {
			if (reqParam.containsKey("leadStatusId")) {
				leadStatusId = reqParam.get("leadStatusId");
			}
			if (reqParam.containsKey("leadRemarksId")) {
				leadRemarksId = reqParam.get("leadRemarksId");
			}
		}
		LeadDashboardDomain dashboardDomain = new LeadDashboardDomain();
		LeadDashboardDomain dashboardTotalDomain = dashboardDAO.getCustomerLead();
		if (leadStatusId != null) {
			LeadDashboardDomain dashboardLeadStsDomain = dashboardDAO.getCustomerLeadByStsId(leadStatusId, null);
			if (leadRemarksId != null)
				dashboardDomain = dashboardDAO.getCustomerLeadByRmksId(leadRemarksId, null);
			dashboardDomain.setLeadStatus(dashboardLeadStsDomain.getLeadStatus());
		}
		dashboardDomain.setTotal(dashboardTotalDomain.getTotal());
		LeadDashboardModel dashboardModel = new LeadDashboardModel();
		BeanUtils.copyProperties(dashboardDomain, dashboardModel);
		return dashboardModel;
	}

	@Override
	public LeadDashboardModel getCustomerLead(String uploadedById) throws Exception {
		LeadDashboardDomain dashboardDomain = dashboardDAO.getCustomerLeadByUploadedById(uploadedById);
		LeadDashboardModel dashboardModel = new LeadDashboardModel();
		BeanUtils.copyProperties(dashboardDomain, dashboardModel);
		return dashboardModel;
	}

	@Override
	public LeadDashboardModel getCustomerLeadByAssignedId(String assignedId, @RequestParam Map<String, String> reqParam)
			throws Exception {
		String leadStatusId = null, leadRemarksId = null;
		if (reqParam.size() > 0) {
			if (reqParam.containsKey("leadStatusId")) {
				leadStatusId = reqParam.get("leadStatusId");
			} else
				throw new NOT_FOUND("Please mention request parameter as 'leadStatusId' ");
			if (reqParam.containsKey("leadRemarksId")) {
				leadRemarksId = reqParam.get("leadRemarksId");
			} else
				throw new NOT_FOUND("Please mention request parameter as 'leadRemarksId' ");
		}
		LeadDashboardDomain dashboardDomain = new LeadDashboardDomain();
		if (leadStatusId != null && leadRemarksId != null) {
			dashboardDomain = dashboardDAO.getCustomerLead(assignedId);
			LeadDashboardDomain dashboardLeadStatusDomain = dashboardDAO.getCustomerLeadByStsId(leadStatusId,
					assignedId);
			LeadDashboardDomain dashboardLeadRemarksDomain = dashboardDAO.getCustomerLeadByRmksId(leadRemarksId,
					assignedId);
			dashboardDomain.setLeadStatus(dashboardLeadStatusDomain.getLeadStatus());
			dashboardDomain.setLeadRemarks(dashboardLeadRemarksDomain.getLeadRemarks());
		} else
			dashboardDomain = dashboardDAO.getCustomerLeadByAssignedId(assignedId);
		LeadDashboardModel dashboardModel = new LeadDashboardModel();
		BeanUtils.copyProperties(dashboardDomain, dashboardModel);
		return dashboardModel;
	}

	@Override
	public int getProfilCounteByRoleAndFranchiseId(int roleId, boolean status, String franchiseId,String searchText) throws Exception {
		DashboardDomain dashboardDomain = dashboardDAO.getProfilCounteByRoleAndFranchiseId(roleId, status, franchiseId,searchText);
		return dashboardDomain.getTotal();
	}

	@Override
	public int getProfileCountByChannelPartnerId(int roleId, boolean status, String cpId, boolean isStatus)
			throws Exception {
		DashboardDomain dashboardDomain = new DashboardDomain();
		if (isStatus)
			dashboardDomain = dashboardDAO.getProfileByChannelPartnerId(roleId, status, cpId, isStatus);
		else
			dashboardDomain = dashboardDAO.getProfileByChannelPartnerId(roleId, status, cpId, isStatus);
		return dashboardDomain.getTotal();
	}

	@Override
	public int getProfileSearchCountByRole(int roleId,boolean status, int cityId, int stateId,String searchText,int customerTypeId) throws Exception {
		DashboardDomain dashboardDomain = new DashboardDomain();
		if (cityId > 0)
			dashboardDomain = dashboardDAO.getProfileSearchCountByRoleAndSts(roleId, status, cityId, true,searchText,customerTypeId);
		else if (stateId > 0)
			dashboardDomain = dashboardDAO.getProfileSearchCountByRoleAndStsAndStateId(roleId, status, stateId, true,searchText,customerTypeId);
		else
			dashboardDomain = dashboardDAO.getProfileSearchByRoleAndSts(roleId, status, true,searchText,customerTypeId);
		return dashboardDomain.getTotal();
	}

}
