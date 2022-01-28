package com.atpl.mmg.mapper.organization;


import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.organization.OrganizationDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.organization.OrganizationModel;

@Component
public class OrganizationMapper extends AbstractModelMapper<OrganizationModel, OrganizationDomain> {

	@Override
	public Class<OrganizationModel> entityType() {
		return OrganizationModel.class;
	}

	@Override
	public Class<OrganizationDomain> modelType() {
		return OrganizationDomain.class;
	}

}