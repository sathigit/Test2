package com.atpl.mmg.service.specialvehicles;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.dao.specialvehicles.SpecialCategoryDAO;
import com.atpl.mmg.domain.specialvehicles.SpecialCategoryDomain;
import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.mapper.specialvehicles.SpecialCategoryMapper;
import com.atpl.mmg.model.specialvehicles.SpecialCategoryModel;

@Service("SpecialCategoryService")
public class SpecialCategoryServiceImpl implements SpecialCategoryService {

	@Autowired
	SpecialCategoryDAO specialCategoryDAO;

	@Autowired
	SpecialCategoryMapper specialCategoryMapper;

	private static final Logger logger = LoggerFactory.getLogger(SpecialCategoryServiceImpl.class);

	/**
	 * Author:jayaram Modified Date: 25/2/2020 done by vidya
	 */
	@Override
	public SpecialCategoryModel addSpecialCategory(SpecialCategoryModel specialCategoryModel) throws Exception {
		SpecialCategoryDomain specialCategoryDomain = new SpecialCategoryDomain();
		BeanUtils.copyProperties(specialCategoryModel, specialCategoryDomain);
		specialCategoryDomain.setStatus(true);
		specialCategoryDAO.addSpecialCategory(specialCategoryDomain);
		BeanUtils.copyProperties(specialCategoryDomain, specialCategoryModel);
		return specialCategoryModel;
	}

	@Override
	public String UpdateSpecialCategory(SpecialCategoryModel specialCategoryModel) throws Exception {
		SpecialCategoryDomain specialCategoryDomain = new SpecialCategoryDomain();
		BeanUtils.copyProperties(specialCategoryModel, specialCategoryDomain);
		return specialCategoryDAO.UpdateSpecialCategory(specialCategoryDomain);
	}

	@Override
	public List<SpecialCategoryModel> getSpecialCategory() throws Exception {
		List<SpecialCategoryDomain> specialCategoryDomain = specialCategoryDAO.getSpecialCategory();
		return specialCategoryMapper.entityList(specialCategoryDomain);
	}

	@Override
	public String deleteSpecialCategory(int specialCategoryId) throws Exception {
		return specialCategoryDAO.deleteSpecialCategory(specialCategoryId);
	}

}
