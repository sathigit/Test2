package com.atpl.mmg.mapper.packaging;

import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.packaging.PackagingDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.packaging.PackagingModel;

@Component
public class PackagingMapper extends AbstractModelMapper<PackagingModel, PackagingDomain> {

	@Override
	public Class<PackagingModel> entityType() {
		// TODO Auto-generated method stub
		return PackagingModel.class;
	}

	@Override
	public Class<PackagingDomain> modelType() {
		// TODO Auto-generated method stub
		return PackagingDomain.class;
	}

}
