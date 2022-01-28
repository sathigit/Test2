package com.atpl.mmg.mapper.route;

import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.route.RouteDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.route.RouteModel;

@Component
public class RouteMapper extends AbstractModelMapper<RouteModel, RouteDomain> {

	@Override
	public Class<RouteModel> entityType() {
		// TODO Auto-generated method stub
		return RouteModel.class;
	}

	@Override
	public Class<RouteDomain> modelType() {
		// TODO Auto-generated method stub
		return RouteDomain.class;
	}

}
