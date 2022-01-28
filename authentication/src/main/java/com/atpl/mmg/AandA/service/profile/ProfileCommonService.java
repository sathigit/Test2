package com.atpl.mmg.AandA.service.profile;

import com.atpl.mmg.AandA.domain.profile.ProfileDomainV2;
import com.atpl.mmg.AandA.model.profile.Profile;

public interface ProfileCommonService {
	
	Profile save(Profile profile,ProfileDomainV2 existingProfileDomain) throws Exception;
	String update(Profile profile) throws Exception;
	
}
