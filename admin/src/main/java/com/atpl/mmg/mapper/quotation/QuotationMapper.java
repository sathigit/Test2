package com.atpl.mmg.mapper.quotation;

import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.quotation.QuotationDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.quotation.QuotationModel;

@Component
public class QuotationMapper extends AbstractModelMapper<QuotationModel, QuotationDomain> {

	@Override
	public Class<QuotationModel> entityType() {
		// TODO Auto-generated method stub
		return QuotationModel.class;
	}

	@Override
	public Class<QuotationDomain> modelType() {
		// TODO Auto-generated method stub
		return QuotationDomain.class;
	}

}
