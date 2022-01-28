package com.atpl.mmg.dao.termsandcondition;

import java.util.List;

import com.atpl.mmg.domain.dashboard.DashboardDomain;
import com.atpl.mmg.domain.termsandcondition.TandCDomain;

public interface TandCDAO {
	
	public TandCDomain saveTandC(TandCDomain tandCDomain) throws Exception;

	public TandCDomain getTandCByRole(int roleId,boolean status) throws Exception;
	
	public List<TandCDomain> getTandCListByRole(int roleId) throws Exception;
	
	public List<TandCDomain> getAllTandCListByRole(int roleId,int lowerBound,int upperBound) throws Exception;
	
	public DashboardDomain getAllTandCListByRoleCount(int roleId) throws Exception;
	
	public TandCDomain getTandCById(String uuid) throws Exception;
	
	public List<TandCDomain> getTandCList(boolean isActive,int lowerBound,int upperBound) throws Exception;
	
	public DashboardDomain getTandCListCount(boolean isActive) throws Exception;
	
	public List<TandCDomain> getTandCListByRoleAndStatus(int roleId,boolean isActive) throws Exception;
	
	public DashboardDomain getTandCListByRoleAndStatusCount(int roleId,boolean isActive) throws Exception;

	public String updateTandC(String uuid,String termsAndConditions) throws Exception;
	
	public String updateTermsAndCondition(String uuid, boolean isActive) throws Exception;

}
