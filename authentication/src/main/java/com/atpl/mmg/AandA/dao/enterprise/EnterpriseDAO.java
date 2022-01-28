package com.atpl.mmg.AandA.dao.enterprise;

import java.util.List;

import com.atpl.mmg.AandA.domain.profile.EnterpriseDomain;

public interface EnterpriseDAO {
	public EnterpriseDomain save(EnterpriseDomain enterpriseDomain) throws Exception;
	
	public String update(EnterpriseDomain enterpriseDomain) throws Exception;
	
	public EnterpriseDomain checkLicenseNumber(String licenseNumber) throws Exception;

	public EnterpriseDomain checkGstNumber(String gstNumber) throws Exception;
	
	public EnterpriseDomain getEnterpriseByProfileId(String profileId) throws Exception;
	
	public List<EnterpriseDomain> getEnterprises() throws Exception;
}
