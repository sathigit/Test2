package com.atpl.mmg.mapper.employee;

import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.employee.EmployeeDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.employee.EmployeeModel;

@Component
public class EmployeeMapper extends AbstractModelMapper<EmployeeModel, EmployeeDomain> {

	@Override
	public Class<EmployeeModel> entityType() {
		return EmployeeModel.class;
	}

	@Override
	public Class<EmployeeDomain> modelType() {
		return EmployeeDomain.class;
	}

}
