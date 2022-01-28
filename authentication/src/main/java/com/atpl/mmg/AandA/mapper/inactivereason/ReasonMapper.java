package com.atpl.mmg.AandA.mapper.inactivereason;

import org.springframework.stereotype.Component;

import com.atpl.mmg.AandA.domain.inactivereason.ReasonDomain;
import com.atpl.mmg.AandA.mapper.AbstractModelMapper;
import com.atpl.mmg.AandA.model.reason.ReasonModel;

@Component
public class ReasonMapper extends AbstractModelMapper<ReasonModel, ReasonDomain> {

	@Override
	public Class<ReasonModel> entityType() {
		return ReasonModel.class;
	}

	@Override
	public Class<ReasonDomain> modelType() {
		return ReasonDomain.class;
	}

}
