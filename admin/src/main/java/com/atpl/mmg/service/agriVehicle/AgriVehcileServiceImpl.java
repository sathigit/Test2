package com.atpl.mmg.service.agriVehicle;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.dao.AgriVehicle.AgriVehcileDAO;
import com.atpl.mmg.domain.agriVehicle.AgriVehicleDomain;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.mapper.agriVehicle.AgriVehicleMapper;
import com.atpl.mmg.model.agriVehicle.AgriVehicleModel;

@Service("AgriVehicleService")
@SuppressWarnings("rawtypes")
public class AgriVehcileServiceImpl implements AgriVehicleService {

	@Autowired
	AgriVehcileDAO agriVehcileDAO;

	@Autowired
	AgriVehicleMapper agriVehicleMapper;

	private static final Logger logger = LoggerFactory.getLogger(AgriVehcileServiceImpl.class);

	/**
	 * Author:jayaram Modified Date: 25/2/2020 done by vidya
	 */
	@Override
	public String saveAgriVehcile(AgriVehicleModel agriVehicleModel) throws Exception {
		AgriVehicleDomain agriVehicleDomain = new AgriVehicleDomain();
		BeanUtils.copyProperties(agriVehicleModel, agriVehicleDomain);
		agriVehicleDomain.setStatus(true);
		return agriVehcileDAO.saveAgriVehcile(agriVehicleDomain);
	}

	@Override
	public List<AgriVehicleModel> getAgriVehcile() throws Exception {
		List<AgriVehicleDomain> agriVehicleDomain = agriVehcileDAO.getAgriVehcile();
		return agriVehicleMapper.entityList(agriVehicleDomain);
	}

	@Override
	public AgriVehicleModel agriVehcile(int vehicleCategoryId) throws Exception {
		AgriVehicleDomain agriVehicleDomain = agriVehcileDAO.agriVehcile(vehicleCategoryId);
		AgriVehicleModel agriVehicleModel = new AgriVehicleModel();
		if (agriVehicleDomain == null)
			throw new NOT_FOUND("AgriVehcile not found");
		BeanUtils.copyProperties(agriVehicleDomain, agriVehicleModel);
		return agriVehicleModel;
	}

	@Override
	public String updateAgriVehicle(AgriVehicleModel agriVehicleModel) throws Exception {
		AgriVehicleDomain agriVehicleDomain = new AgriVehicleDomain();
		BeanUtils.copyProperties(agriVehicleModel, agriVehicleDomain);
		return agriVehcileDAO.updateAgriVehicle(agriVehicleDomain);

	}

	@Override
	public String deleteAgriVehicleCategory(int vehicleCategoryId) throws Exception {
		return agriVehcileDAO.deleteAgriVehicleCategory(vehicleCategoryId);
	}

}
