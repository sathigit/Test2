package com.atpl.mmg.service.admin;

import java.util.List;

import com.atpl.mmg.model.admin.CatalogTransactionFeeModel;

public interface CatalogTransactionFeeService {

	public List<CatalogTransactionFeeModel> getModificationFee(CatalogTransactionFeeModel catalogTransactionFeeModel) throws Exception;
	
	public String saveModificationFee(CatalogTransactionFeeModel catalogTransactionFeeModel) throws Exception;
	
}
