package com.atpl.mmg.model.admin;

import java.io.Serializable;
import java.util.List;

import com.atpl.mmg.model.route.RouteModel;

public class AdminDetails implements Serializable {

	private static final long serialVersionUID = -2196266153406082047L;
	
	List<RouteModel> routes;

	public List<RouteModel> getRoutes() {
		return routes;
	}

	public void setRoutes(List<RouteModel> routes) {
		this.routes = routes;
	}
	
	

}
