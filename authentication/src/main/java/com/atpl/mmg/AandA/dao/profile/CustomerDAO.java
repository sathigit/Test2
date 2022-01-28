package com.atpl.mmg.AandA.dao.profile;

import com.atpl.mmg.AandA.domain.profile.CustomerDomain;

public interface CustomerDAO {
	CustomerDomain save(CustomerDomain customerDomain) throws Exception;
	String update(CustomerDomain customerDomain) throws Exception;
	CustomerDomain getCustomerByProfileId(String profileId) throws Exception;
	CustomerDomain checkGstNumber(String gstNumber) throws Exception;
}

