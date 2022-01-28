package com.atpl.mmg.service.employee;

import java.util.List;
import java.util.Map;

import com.atpl.mmg.model.employee.BdoModel;
import com.atpl.mmg.utils.ListDto;

/* Author:Sindhu
 * creationDate:17-11-2019
 * Description:Bdo Mapping to Bdm*/

@SuppressWarnings("unused")
public interface BdoService {

	public String saveBdo(BdoModel BdoModel) throws Exception;

	@SuppressWarnings("rawtypes")
	public ListDto getBdoList(int bdmId,Map<String,String> reqParam) throws Exception;

	public BdoModel getbdoCount(int bdmId) throws Exception;

	public BdoModel getbdoFranchise(int bdoId) throws Exception;

	public BdoModel getbdo(String franchiseId) throws Exception;
}