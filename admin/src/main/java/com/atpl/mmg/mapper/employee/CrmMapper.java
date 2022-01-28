package com.atpl.mmg.mapper.employee;

import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.employee.CrmDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.employee.CrmModel;

@Component
public class CrmMapper extends AbstractModelMapper<CrmModel, CrmDomain>{

	@Override
	public Class<CrmModel> entityType() {
		// TODO Auto-generated method stub
		return CrmModel.class;
	}

	@Override
	public Class<CrmDomain> modelType() {
		// TODO Auto-generated method stub
		return CrmDomain.class	;
	}

}
