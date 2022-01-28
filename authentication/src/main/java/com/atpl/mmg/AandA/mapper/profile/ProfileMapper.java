package com.atpl.mmg.AandA.mapper.profile;

import org.springframework.stereotype.Component;

import com.atpl.mmg.AandA.domain.profile.ProfileDomain;
import com.atpl.mmg.AandA.mapper.AbstractModelMapper;
import com.atpl.mmg.AandA.model.profile.ProfileModel;

@Component
public class ProfileMapper extends AbstractModelMapper<ProfileModel, ProfileDomain> {

	@Override
	public Class<ProfileModel> entityType() {
		return ProfileModel.class;
	}

	@Override
	public Class<ProfileDomain> modelType() {
		return ProfileDomain.class;
	}

}
