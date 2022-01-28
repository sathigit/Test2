package com.atpl.mmg.AandA.mapper.profile;

import org.springframework.stereotype.Component;

import com.atpl.mmg.AandA.domain.profile.ProfileDomainV2;
import com.atpl.mmg.AandA.mapper.AbstractModelMapper;
import com.atpl.mmg.AandA.model.profile.Profile;

@Component
public class ProfileMapperV2 extends AbstractModelMapper<Profile, ProfileDomainV2> {

	@Override
	public Class<Profile> entityType() {
		// TODO Auto-generated method stub
		return Profile.class;
	}

	@Override
	public Class<ProfileDomainV2> modelType() {
		// TODO Auto-generated method stub
		return ProfileDomainV2.class;
	}

}
