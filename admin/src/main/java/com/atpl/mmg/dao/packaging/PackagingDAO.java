package com.atpl.mmg.dao.packaging;

import java.math.BigInteger;
import java.util.List;

import com.atpl.mmg.domain.dashboard.DashboardDomain;
import com.atpl.mmg.domain.packaging.PackagingDomain;

public interface PackagingDAO {

	public String savePackaging(PackagingDomain packagingDomain) throws Exception;

	public List<PackagingDomain> getPackagingList(int lowerBound,int upperBound) throws Exception;

	public PackagingDomain getPackageDetails(BigInteger packagingId) throws Exception;
	
	public DashboardDomain getPackageDetailsCount() throws Exception;

	public String updatePackagingStatus(PackagingDomain packagingDomain) throws Exception;

	public String deletePackaging(BigInteger packagingId) throws Exception;

	public String updatePackagingName(PackagingDomain packagingDomain) throws Exception;

}
