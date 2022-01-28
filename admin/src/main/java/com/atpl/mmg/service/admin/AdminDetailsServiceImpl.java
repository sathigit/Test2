package com.atpl.mmg.service.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.model.admin.AdminDetails;
import com.atpl.mmg.model.route.RouteModel;
import com.atpl.mmg.service.route.RouteService;

@Service("adminDetailsService")
public class AdminDetailsServiceImpl implements AdminDetailsService {
	
	@Autowired	
	RouteService routeService;

	@Override
	public AdminDetails fetchAdminDetails(AdminDetails adminDetails) throws Exception {
		if(!adminDetails.getRoutes().isEmpty()) {
			List<RouteModel> routeList = new ArrayList<RouteModel>();
			for(RouteModel routeModel:adminDetails.getRoutes()) {
				RouteModel route = new RouteModel();
				route = routeService.getRouteDetailsById(routeModel.getUuid());
				routeList.add(route);
			}
			adminDetails.setRoutes(routeList);
		}
		return adminDetails;
	}

}
