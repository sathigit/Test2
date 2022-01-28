package com.atpl.mmg.AandA.mapper.dashboard;

import org.springframework.stereotype.Component;

import com.atpl.mmg.AandA.domain.dashboard.DashboardDomain;
import com.atpl.mmg.AandA.mapper.AbstractModelMapper;
import com.atpl.mmg.AandA.model.dashboard.DashboardModel;

@Component
public class DashboardMapper extends AbstractModelMapper<DashboardModel, DashboardDomain> {

	@Override
	public Class<DashboardModel> entityType() {

		return DashboardModel.class;
	}

	@Override
	public Class<DashboardDomain> modelType() {

		return DashboardDomain.class;
	}

}