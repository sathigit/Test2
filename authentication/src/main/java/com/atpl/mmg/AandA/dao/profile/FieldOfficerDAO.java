package com.atpl.mmg.AandA.dao.profile;

import com.atpl.mmg.AandA.domain.profile.FieldOfficerDomain;

public interface FieldOfficerDAO {
	FieldOfficerDomain save(FieldOfficerDomain fieldOfficerDomain) throws Exception;
	String update(FieldOfficerDomain fieldOfficerDomain) throws Exception;
	FieldOfficerDomain getFieldOffcierByProfileId(String profileId) throws Exception;
}
