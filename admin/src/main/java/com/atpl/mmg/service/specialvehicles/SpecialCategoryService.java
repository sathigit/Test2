package com.atpl.mmg.service.specialvehicles;

import java.util.List;

import com.atpl.mmg.model.specialvehicles.SpecialCategoryModel;

public interface SpecialCategoryService {

	public SpecialCategoryModel addSpecialCategory(SpecialCategoryModel specialCategoryModel) throws Exception;

	public String UpdateSpecialCategory(SpecialCategoryModel specialCategoryModel) throws Exception;

	public List<SpecialCategoryModel> getSpecialCategory() throws Exception;

	public String deleteSpecialCategory(int specialCategoryId) throws Exception;

}
