package com.atpl.mmg.dao.blood;

import java.util.List;

import com.atpl.mmg.domain.blood.BloodDomain;
import com.atpl.mmg.domain.dashboard.DashboardDomain;


public interface BloodDAO {

	List<BloodDomain> getBloodGroup(int lowerBound, int upperBound) throws Exception;
	
	DashboardDomain getBloodGroupCount() throws Exception;
	
	public BloodDomain getBloodGroupById(int id) throws Exception;
}

