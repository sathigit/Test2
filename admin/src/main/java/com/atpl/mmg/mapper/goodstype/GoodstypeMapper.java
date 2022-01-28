package com.atpl.mmg.mapper.goodstype;


import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.goodstype.GoodstypeDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.goodstype.GoodstypeModel;

@Component
public class GoodstypeMapper extends AbstractModelMapper<GoodstypeModel, GoodstypeDomain> {

	@Override
	public Class<GoodstypeModel> entityType() {
		return GoodstypeModel.class;
	}

	@Override
	public Class<GoodstypeDomain> modelType() {
		return GoodstypeDomain.class;
	}

}