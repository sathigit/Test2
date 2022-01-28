package com.atpl.mmg.mapper.admin;

import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.admin.CatalogTransactionFeeDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.admin.CatalogTransactionFeeModel;

@Component
public class CatalogTransactionFeeMapper extends AbstractModelMapper<CatalogTransactionFeeModel, CatalogTransactionFeeDomain> {

	@Override
	public Class<CatalogTransactionFeeModel> entityType() {
		return CatalogTransactionFeeModel.class;
	}

	@Override
	public Class<CatalogTransactionFeeDomain> modelType() {
		return CatalogTransactionFeeDomain.class;

	}

}
