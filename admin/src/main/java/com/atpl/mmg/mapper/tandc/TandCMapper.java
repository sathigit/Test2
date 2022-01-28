package com.atpl.mmg.mapper.tandc;

import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.termsandcondition.TandCDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.termsandcondition.TandCModel;
@Component
public class TandCMapper extends AbstractModelMapper<TandCModel,TandCDomain>{

	@Override
	public Class<TandCModel> entityType() {
		
		return TandCModel.class;
	}

	@Override
	public Class<TandCDomain> modelType() {
		
		return TandCDomain.class;
	}

}
