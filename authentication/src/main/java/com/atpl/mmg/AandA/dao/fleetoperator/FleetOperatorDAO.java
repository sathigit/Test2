package com.atpl.mmg.AandA.dao.fleetoperator;

import java.util.List;

import com.atpl.mmg.AandA.domain.profile.FleetOperatorDomain;

public interface FleetOperatorDAO {
	
	public FleetOperatorDomain save(FleetOperatorDomain fleetOperatorDomain) throws Exception;
	
	public FleetOperatorDomain checkGstNumber(String gstNumber) throws Exception;
	
	public String update(FleetOperatorDomain fleetOperatorDomain) throws Exception;
	
	public FleetOperatorDomain getFleetByProfileId(String profileId) throws Exception;
	
	public List<FleetOperatorDomain> getFleetOperators() throws Exception;

}
