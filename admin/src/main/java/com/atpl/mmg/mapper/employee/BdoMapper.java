package com.atpl.mmg.mapper.employee;

import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.employee.BdoDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.employee.BdoModel;

@Component
public class BdoMapper  extends AbstractModelMapper<BdoModel, BdoDomain>{

	@Override
	public Class<BdoModel> entityType() {
		// TODO Auto-generated method stub
		return BdoModel.class;
	}

	@Override
	public Class<BdoDomain> modelType() {
		// TODO Auto-generated method stub
		return BdoDomain.class	;
	}

}
