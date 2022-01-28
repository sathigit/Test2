package com.atpl.mmg.service.termsandcondition;

import java.util.Map;

import com.atpl.mmg.model.termsandcondition.TandCDto;
import com.atpl.mmg.model.termsandcondition.TandCModel;
import com.atpl.mmg.utils.ListDto;

@SuppressWarnings("rawtypes")
public interface TandCService {
	
	public TandCDto saveTandC(TandCModel tandCModel) throws Exception;
	
	public ListDto getTandCListByRoleAndStatus(int roleId,Map<String,String> reqParam) throws Exception;
	
	public TandCModel getTandCById(String uuid) throws Exception;
	
	public ListDto getTandCList(boolean isActive,Map<String,String> reqParam) throws Exception;
	
    public String activateTandC(String uuid)throws Exception;
    
    public String deActivateTandC(String uuid)throws Exception;
}
