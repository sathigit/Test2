package com.atpl.mmg.mapper.packagetype;

import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.packagetype.PackageTypeDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.packagetype.PackageTypeModel;

@Component
public class PackageTypeMapper extends AbstractModelMapper<PackageTypeModel, PackageTypeDomain> {

	@Override
	public Class<PackageTypeModel> entityType() {
		return PackageTypeModel.class;
	}

	@Override
	public Class<PackageTypeDomain> modelType() {
		return PackageTypeDomain.class;
	}
}
