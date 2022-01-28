package com.atpl.mmg.AandA.dao.profile;

import com.atpl.mmg.AandA.domain.profile.BDMDomain;

public interface BdmDAO {
	BDMDomain save(BDMDomain bdmDomain) throws Exception;
	String update(BDMDomain bdmDomain) throws Exception;
	BDMDomain getBDMByProfileId(String profileId) throws Exception;
}
