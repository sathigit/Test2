package com.atpl.mmg.AandA.mapper.dashboard;

import org.springframework.stereotype.Component;

import com.atpl.mmg.AandA.domain.dashboard.ProfileDashboardDomain;
import com.atpl.mmg.AandA.mapper.AbstractModelMapper;
import com.atpl.mmg.AandA.model.dashboard.ProfileDashboardModel;

@Component
public class ProfileDashboardMapper extends AbstractModelMapper<ProfileDashboardModel, ProfileDashboardDomain> {

	@Override
	public Class<ProfileDashboardModel> entityType() {

		return ProfileDashboardModel.class;
	}

	@Override
	public Class<ProfileDashboardDomain> modelType() {

		return ProfileDashboardDomain.class;
	}

}