package com.atpl.mmg.mapper.customertype;

import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.customertype.CustomerTypeDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.customertype.CustomerTypeModel;

@Component
public class CustomerTypeMapper extends AbstractModelMapper<CustomerTypeModel, CustomerTypeDomain> {

	@Override
	public Class<CustomerTypeModel> entityType() {
		return CustomerTypeModel.class;
	}

	@Override
	public Class<CustomerTypeDomain> modelType() {
		return CustomerTypeDomain.class;
	}

}

