package com.atpl.mmg.AandA.dao.profile;

import com.atpl.mmg.AandA.domain.profile.OperationalTeamDomain;

public interface OperationalTeamDao {
	OperationalTeamDomain save(OperationalTeamDomain operationalTeamDomain) throws Exception;
	String update(OperationalTeamDomain operationalTeamDomain) throws Exception;
	OperationalTeamDomain getOperationalTeamByProfileId(String profileId) throws Exception;
}
