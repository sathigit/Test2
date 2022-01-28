package com.atpl.mmg.mapper.admin;



import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.admin.AdminDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.admin.AdminModel;

@Component
public class AdminMapper extends AbstractModelMapper<AdminModel, AdminDomain> {

	@Override
	public Class<AdminModel> entityType() {
		return AdminModel.class;
	}
	

	@Override
	public Class<AdminDomain> modelType() {
		return AdminDomain.class;
	
	}
}

	