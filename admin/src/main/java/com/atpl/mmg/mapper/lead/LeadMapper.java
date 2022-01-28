package com.atpl.mmg.mapper.lead;

import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.lead.LeadDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.lead.LeadModel;

@Component
public class LeadMapper extends AbstractModelMapper<LeadModel, LeadDomain> {

	@Override
	public Class<LeadModel> entityType() {
		return LeadModel.class;
	}

	@Override
	public Class<LeadDomain> modelType() {
		return LeadDomain.class;
	}

}
