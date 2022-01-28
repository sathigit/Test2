package com.atpl.mmg.AandA.service.reason;

import java.util.Map;

import com.atpl.mmg.AandA.model.profile.ListDto;
import com.atpl.mmg.AandA.model.profile.Profile;
import com.atpl.mmg.AandA.model.reason.ReasonModel;

@SuppressWarnings("rawtypes")
public interface ReasonService {
	
	public String save(ReasonModel reasonModel,boolean isFromProfile) throws Exception;
	
	public Profile validateReason(ReasonModel reasonModel) throws Exception;
	
	public ListDto getReasonsByRoleId(int roleId, Map<String, String> reqParam) throws Exception;
	
	public ListDto getReasonsByRoleAndProfileId(int roleId,String profileId,Map<String, String> reqParam) throws Exception;
	
	public ReasonModel getReasonByUuid(String uuid) throws Exception;

	public String sendEmail(ReasonModel reasonModel,Profile profile) throws Exception;
}
