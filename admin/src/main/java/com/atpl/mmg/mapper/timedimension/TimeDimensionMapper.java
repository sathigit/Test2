package com.atpl.mmg.mapper.timedimension;

import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.timedimension.TimeDimensionDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.timedimension.TimeDimensionModel;

@Component
public class TimeDimensionMapper extends AbstractModelMapper<TimeDimensionModel, TimeDimensionDomain>  {

	@Override
	public Class<TimeDimensionModel> entityType() {
		// TODO Auto-generated method stub
		return TimeDimensionModel.class;
	}

	@Override
	public Class<TimeDimensionDomain> modelType() {
		// TODO Auto-generated method stub
		return TimeDimensionDomain.class;
	}

}
