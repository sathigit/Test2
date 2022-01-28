package com.atpl.mmg.mapper.report;

import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.report.DirectCostDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.report.DirectCostModel;

@Component
public class DirectCostMapper extends AbstractModelMapper<DirectCostModel, DirectCostDomain> {

	@Override
	public Class<DirectCostModel> entityType() {
		return DirectCostModel.class;
	}

	@Override
	public Class<DirectCostDomain> modelType() {
		return DirectCostDomain.class;
	}

}
