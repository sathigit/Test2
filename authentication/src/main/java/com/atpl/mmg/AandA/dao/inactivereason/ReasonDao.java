package com.atpl.mmg.AandA.dao.inactivereason;

import java.util.List;

import com.atpl.mmg.AandA.domain.inactivereason.ReasonDomain;

public interface ReasonDao {
	
	public String save(ReasonDomain reasonDomain) throws Exception;
	
	public List<ReasonDomain>  getReasonsByRoleId(int roleId) throws Exception;

	public List<ReasonDomain>  getReasonsByRoleByProfile(int roleId,String profileId,int lowerBound,int upperBound) throws Exception;
	
	public ReasonDomain  getReasonByUuid(String uuid) throws Exception;
	
	public List<ReasonDomain> getReasonsByRoleByProfileAndStatus(int roleId, String profileId,boolean checkRequestedBy,int lowerBound,int upperBound)
			throws Exception;

}
