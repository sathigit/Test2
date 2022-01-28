package com.atpl.mmg.service.specialvehicles;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.dao.specialvehicles.SpecialVehiclesDAO;
import com.atpl.mmg.domain.specialvehicles.SpecialVehiclesDomain;
import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.mapper.specialvehicles.SpecialVehiclesMapper;
import com.atpl.mmg.model.specialvehicles.SpecialVehiclesModel;

@Service("SpecialVehiclesService")
@SuppressWarnings("rawtypes")
public class SpecialVehiclesServiceImpl implements SpecialVehiclesService {

	@Autowired
	SpecialVehiclesDAO specialVehiclesDAO;

	@Autowired
	SpecialVehiclesMapper specialVehiclesMapper;

	private static final Logger logger = LoggerFactory.getLogger(SpecialVehiclesServiceImpl.class);

	/**
	 * Author:jayaram Modified Date: 25/2/2020 done by vidya
	 */
	@Override
	public SpecialVehiclesModel addSpecialVehicle(SpecialVehiclesModel specialVehiclesModel) throws Exception {
		SpecialVehiclesDomain specialVehiclesDomain = new SpecialVehiclesDomain();
		BeanUtils.copyProperties(specialVehiclesModel, specialVehiclesDomain);
		specialVehiclesDomain.setStatus(true);
		specialVehiclesDAO.addSpecialVehicle(specialVehiclesDomain);
		BeanUtils.copyProperties(specialVehiclesDomain, specialVehiclesModel);
		return specialVehiclesModel;
	}

	@Override
	public String UpdateSpecialVehicle(SpecialVehiclesModel specialVehiclesModel) throws Exception {
		SpecialVehiclesDomain specialVehiclesDomain = new SpecialVehiclesDomain();
		BeanUtils.copyProperties(specialVehiclesModel, specialVehiclesDomain);
		return specialVehiclesDAO.UpdateSpecialVehicle(specialVehiclesDomain);
	}

	@Override
	public List<SpecialVehiclesModel> getSpecialVehicle() throws Exception {
		List<SpecialVehiclesDomain> specialVehiclesDomain = specialVehiclesDAO.getSpecialVehicle();
		return specialVehiclesMapper.entityList(specialVehiclesDomain);

	}

	@Override
	public String deleteSpecialVehicle(int vehicleCategoryId) throws Exception {
		return specialVehiclesDAO.deleteSpecialVehicle(vehicleCategoryId);
	}

}
