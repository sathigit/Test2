package com.atpl.mmg.AandA.dao.franchise;

import java.util.List;

import com.atpl.mmg.AandA.domain.profile.FranchiseDomainV2;

public interface FranchiseDAO {
	public FranchiseDomainV2 save(FranchiseDomainV2 franchiseDomainV2) throws Exception;
	
	public FranchiseDomainV2 checkGstNumber(String gstNumber) throws Exception;
	
	public FranchiseDomainV2 checkLicenseNumber(String licenseNumber) throws Exception;
	
	public String update(FranchiseDomainV2 franchiseDomain,boolean status) throws Exception;
	
	public FranchiseDomainV2 getFranchiseByProfileId(String profileId) throws Exception;
	
	public FranchiseDomainV2 getFranchiseByFranchiseId(String franchiseId) throws Exception;
	
	public List<FranchiseDomainV2>  getFranchises()  throws Exception;
	
	public String updateTag(boolean isTag) throws Exception;

}
