package com.atpl.mmg.AandA.mapper.profileImage;

import org.springframework.stereotype.Component;

import com.atpl.mmg.AandA.domain.profileImage.ProfileImageDomain;
import com.atpl.mmg.AandA.mapper.AbstractModelMapper;
import com.atpl.mmg.AandA.model.profileImage.ProfileImageModel;

@Component
public class ProfileImageMapper extends AbstractModelMapper<ProfileImageModel, ProfileImageDomain> {

	@Override
	public Class<ProfileImageModel> entityType() {
		// TODO Auto-generated method stub
		return ProfileImageModel.class;
	}

	@Override
	public Class<ProfileImageDomain> modelType() {
		// TODO Auto-generated method stub
		return ProfileImageDomain.class;
	}

}
