package com.atpl.mmg.service.report;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.dao.report.DirectCostDAO;
import com.atpl.mmg.domain.report.DirectCostDomain;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.mapper.report.DirectCostMapper;
import com.atpl.mmg.model.report.DirectCostModel;

@Service("directCostService")
@SuppressWarnings("rawtypes")
public class DirectCostServiceImpl implements DirectCostService, Constants {

	@Autowired
	DirectCostDAO directCostDAO;

	@Autowired
	DirectCostMapper directCostMapper;

	private static final Logger logger = LoggerFactory.getLogger(DirectCostServiceImpl.class);

	/**
	 * Author:pooja Modified Date: 25/2/2020 done by vidya
	 */
	@Override
	public String addDirectCost(DirectCostModel directCostModel) throws Exception {
		DirectCostDomain directCostDomain = new DirectCostDomain();
		BeanUtils.copyProperties(directCostModel, directCostDomain);
		return directCostDAO.addDirectCost(directCostDomain);
	}

	@Override
	public String addExpenseSource(DirectCostModel directCostModel) throws Exception {
		DirectCostDomain directCostDomain = new DirectCostDomain();
		BeanUtils.copyProperties(directCostModel, directCostDomain);
		return directCostDAO.addDirectCostSource(directCostDomain);

	}

	@Override
	public DirectCostModel getDirectCostSource(int id) throws Exception {
		DirectCostDomain directCostDomain = directCostDAO.getDirectCostSource(id);
		DirectCostModel directCostModel = new DirectCostModel();
		if (directCostDomain == null)
			throw new NOT_FOUND("DirectCost not found");
		BeanUtils.copyProperties(directCostDomain, directCostModel);
		return directCostModel;
	}

	@Override
	public String updateDirectCost(DirectCostModel directCostModel) throws Exception {
		DirectCostDomain directCostDomain = new DirectCostDomain();
		BeanUtils.copyProperties(directCostModel, directCostDomain);
		return directCostDAO.updateDirectCost(directCostDomain);
	}

	@Override
	public String deleteDirectCost(int id) throws Exception {
		return directCostDAO.deleteDirectCost(id);
	}

	@Override
	public List<DirectCostModel> getDirectCostSource() throws Exception {
		List<DirectCostDomain> directCostDomain = directCostDAO.getDirectCostSource();
		return directCostMapper.entityList(directCostDomain);

	}

}
