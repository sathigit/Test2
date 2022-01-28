package com.atpl.mmg.dao.admin;

import java.util.List;

import com.atpl.mmg.domain.admin.CatalogTransactionFeeDomain;

public interface CatalogTransactionFeeDao {

	public List<CatalogTransactionFeeDomain> getModificationFee(CatalogTransactionFeeDomain catalogTransactionFeeDomain) throws Exception;
	
	public String saveModificationFee(CatalogTransactionFeeDomain catalogTransactionFeeDomain) throws Exception;
	
}
