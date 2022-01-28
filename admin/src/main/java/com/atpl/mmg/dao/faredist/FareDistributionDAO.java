package com.atpl.mmg.dao.faredist;

import java.util.List;

import com.atpl.mmg.domain.faredist.FareDistributionDomain;

public interface FareDistributionDAO {

	public String save(FareDistributionDomain fareDistributionDomain) throws Exception;

	public String update(FareDistributionDomain fareDistributionDomain) throws Exception;

	public List<FareDistributionDomain> getFareDistributiones(boolean isActive, String franchiseId) throws Exception;
	
	public List<FareDistributionDomain> getFDTypeAndFareDistributiones(String dfType,boolean isActive, String franchiseId) throws Exception;

	public List<FareDistributionDomain> getFareDistributiones() throws Exception;

	public FareDistributionDomain getFareDistribution(String uuid) throws Exception;

	public List<FareDistributionDomain> getFareDistributiones(String fareDistributionTypeId, String franchiseId)
			throws Exception;
	
	public List<FareDistributionDomain> getFareDistributioneByTypeAndFranchiseId(String fareDistributionTypeId, String franchiseId)
			throws Exception;

	public List<FareDistributionDomain> getFareDistributiones(String franchiseId) throws Exception;
	
	public List<FareDistributionDomain> getFareDistributioneByFDTypeId(String franchiseId) throws Exception;

	public String updateIsActiveByFDTypeId(boolean isActive, String FDTypeId) throws Exception;

	public String delete(String uuid) throws Exception;
}
