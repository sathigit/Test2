package com.atpl.mmg.AandA.mapper.role;

import org.springframework.stereotype.Component;

import com.atpl.mmg.AandA.domain.role.RoleDomain;
import com.atpl.mmg.AandA.mapper.AbstractModelMapper;
import com.atpl.mmg.AandA.model.role.RoleModel;

@Component
public class RoleMapper extends AbstractModelMapper<RoleModel, RoleDomain> {

	@Override
	public Class<RoleModel> entityType() {
		// TODO Auto-generated method stub
		return RoleModel.class;
	}

	@Override
	public Class<RoleDomain> modelType() {
		// TODO Auto-generated method stub
		return RoleDomain.class;
	}

}
