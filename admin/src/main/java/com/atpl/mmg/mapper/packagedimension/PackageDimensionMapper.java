package com.atpl.mmg.mapper.packagedimension;

import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.packageDimension.PackageDimensionDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.packagedimension.PackageDimensionModel;

@Component
public class PackageDimensionMapper extends AbstractModelMapper<PackageDimensionModel, PackageDimensionDomain>{

	@Override
	public Class<PackageDimensionModel> entityType() {
		return PackageDimensionModel.class;
	}

	@Override
	public Class<PackageDimensionDomain> modelType() {
		return PackageDimensionDomain.class;
	}
	
}
