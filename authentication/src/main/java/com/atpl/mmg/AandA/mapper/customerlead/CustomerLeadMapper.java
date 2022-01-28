package com.atpl.mmg.AandA.mapper.customerlead;

import org.springframework.stereotype.Component;

import com.atpl.mmg.AandA.domain.customerlead.CustomerLeadDomain;
import com.atpl.mmg.AandA.mapper.AbstractModelMapper;
import com.atpl.mmg.AandA.model.customerlead.CustomerLeadModel;

@Component
public class CustomerLeadMapper extends AbstractModelMapper<CustomerLeadModel, CustomerLeadDomain>{

	@Override
	public Class<CustomerLeadModel> entityType() {
		return CustomerLeadModel.class;
	}

	@Override
	public Class<CustomerLeadDomain> modelType() {
		return CustomerLeadDomain.class;
	}

}
