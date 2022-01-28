package com.atpl.mmg.AandA.dao.profile;

import com.atpl.mmg.AandA.domain.profile.BDODomain;

public interface BdoDAO {
	BDODomain save(BDODomain bdoDomain) throws Exception;
	String update(BDODomain bdoDomain) throws Exception;
	BDODomain getBDOByProfileId(String profileId) throws Exception;	
	public String updateFranchiseId(BDODomain bdoDomain) throws Exception;
}
