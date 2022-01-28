package com.atpl.mmg.dao.specialvehicles;

import java.util.List;

import com.atpl.mmg.domain.specialvehicles.SpecialCategoryDomain;

public interface SpecialCategoryDAO {

	public SpecialCategoryDomain addSpecialCategory(SpecialCategoryDomain specialCategoryDomain) throws Exception;

	public String UpdateSpecialCategory(SpecialCategoryDomain specialCategoryDomain) throws Exception;

	public List<SpecialCategoryDomain> getSpecialCategory() throws Exception;

	public String deleteSpecialCategory(int specialCategoryId) throws Exception;
}
