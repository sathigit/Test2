package com.atpl.mmg.AandA.dao.profile;

import com.atpl.mmg.AandA.domain.profile.CoordinatorDomain;

public interface CoordinatorDAO {
	CoordinatorDomain save(CoordinatorDomain coordinatorDomain) throws Exception;
	String update(CoordinatorDomain coordinatorDomain) throws Exception;
	CoordinatorDomain getCoordinatorByProfileId(String profileId) throws Exception;
	
}
