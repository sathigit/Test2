package com.atpl.mmg.mapper.report;

import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.report.RevenueDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.report.RevenueModel;

@Component
public class RevenueMapper extends AbstractModelMapper<RevenueModel, RevenueDomain> {

	@Override
	public Class<RevenueModel> entityType() {
		return RevenueModel.class;
	}

	@Override
	public Class<RevenueDomain> modelType() {
		return RevenueDomain.class;
	}

}
