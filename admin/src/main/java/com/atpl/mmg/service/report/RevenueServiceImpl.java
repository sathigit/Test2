package com.atpl.mmg.service.report;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.dao.report.RevenueDAO;
import com.atpl.mmg.domain.report.RevenueDomain;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.mapper.report.RevenueMapper;
import com.atpl.mmg.model.report.RevenueModel;
import com.atpl.mmg.service.country.CountryServiceImpl;

@Service("revenueService")
public class RevenueServiceImpl implements RevenueService, Constants {

	@Autowired
	RevenueDAO revenueDAO;

	@Autowired
	RevenueMapper revenueMapper;

	private static final Logger logger = LoggerFactory.getLogger(CountryServiceImpl.class);

	/**
	 * Author:pooja 
	 * Modified Date: 25/2/2020 done by vidya
	 */
	@Override
	public String addrevenue(RevenueModel revenueModel) throws Exception {
		RevenueDomain revenueDomain = new RevenueDomain();
		BeanUtils.copyProperties(revenueModel, revenueDomain);
		return revenueDAO.addRevenue(revenueDomain);
	}

	@Override
	public String addrevenueSource(RevenueModel revenueModel) throws Exception {
		RevenueDomain revenueDomain = new RevenueDomain();
		BeanUtils.copyProperties(revenueModel, revenueDomain);
		return revenueDAO.addRevenueSource(revenueDomain);

	}

	@Override
	public RevenueModel getRevenueSource(int id) throws Exception {
		RevenueDomain revenueDomain = revenueDAO.getRevenueSource(id);
		RevenueModel revenueModel = new RevenueModel();
		if (revenueDomain == null)
			throw new NOT_FOUND("RevenueSource not found");
		BeanUtils.copyProperties(revenueDomain, revenueModel);
		return revenueModel;
	}

	@Override
	public String updateRevenueSource(RevenueModel revenueModel) throws Exception {
		RevenueDomain revenueDomain = new RevenueDomain();
		BeanUtils.copyProperties(revenueModel, revenueDomain);
		return revenueDAO.updateRevenueSource(revenueDomain);

	}

	@Override
	public String deleteRevenueSource(int id) throws Exception {
		return revenueDAO.deleteRevenueSource(id);
	}

	@Override
	public List<RevenueModel> getRevenueSource() throws Exception {
		List<RevenueDomain> revenueDomain = revenueDAO.getRevenueSource();
		return revenueMapper.entityList(revenueDomain);

	}
}
