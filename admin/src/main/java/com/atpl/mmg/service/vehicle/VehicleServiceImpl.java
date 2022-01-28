package com.atpl.mmg.service.vehicle;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.dao.vehicle.VehicleDAO;
import com.atpl.mmg.dao.vehiclecategory.VehicleCategoryDAO;
import com.atpl.mmg.domain.packaging.PackagingDomain;
import com.atpl.mmg.domain.vehicle.VehicleDomain;
import com.atpl.mmg.domain.vehiclecategory.VehicleCategoryDomain;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;
import com.atpl.mmg.mapper.vehicle.VehicleMapper;
import com.atpl.mmg.model.packaging.PackagingModel;
import com.atpl.mmg.model.vehicle.VehicleModel;
import com.atpl.mmg.utils.CommonUtils;
import com.atpl.mmg.utils.ListDto;

@Service("vehicleService")
@SuppressWarnings("rawtypes")
public class VehicleServiceImpl implements VehicleService, Constants {

	@Autowired
	VehicleDAO vehicleDAO;

	@Autowired
	VehicleCategoryDAO vehicleCategoryDAO;

	@Autowired
	VehicleMapper vehicleMapper;

	private static final Logger logger = LoggerFactory.getLogger(VehicleServiceImpl.class);

	public VehicleServiceImpl() {
		// constructor
	}

	@SuppressWarnings("unchecked")
	@Override
	public ListDto getVehicle(Map<String, String> reqParam) throws Exception {
		int lowerBound = 0;
		int upperBound = 0;
		if (reqParam.size() > 0) {
			Map<String, Integer> paginationCount = CommonUtils.getPagination(reqParam);
			if (paginationCount.size() > 0) {
				lowerBound = paginationCount.get("lowerBound");
				upperBound = paginationCount.get("upperBound");
			}
		}
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PACKAGE.name(), SeverityTypes.DEBUG.ordinal(),
				" getPackagingList in PackagingServiceImpl "));
		List<VehicleDomain> vehicles = vehicleDAO.getVehicle(lowerBound, upperBound);
		List<VehicleModel> vehicleModel = vehicleMapper.entityList(vehicles);
		ListDto listDto = new ListDto();
		listDto.setList(vehicleModel);
		listDto.setTotalSize(vehicleDAO.getVehicleCount().getTotal());
		return listDto;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public ListDto getVehicle(String goodsTypeId, String kerbWeightId,Map<String, String> reqParam) throws Exception {
		int lowerBound = 0;
		int upperBound = 0;
		if (reqParam.size() > 0) {
			Map<String, Integer> paginationCount = CommonUtils.getPagination(reqParam);
			if (paginationCount.size() > 0) {
				lowerBound = paginationCount.get("lowerBound");
				upperBound = paginationCount.get("upperBound");
			}
		}
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PACKAGE.name(), SeverityTypes.DEBUG.ordinal(),
				" getPackagingList in PackagingServiceImpl "));
		List<VehicleDomain> vehicles = vehicleDAO.getVehicle(goodsTypeId, kerbWeightId,lowerBound, upperBound);
		List<VehicleModel> vehicleModel = vehicleMapper.entityList(vehicles);
		ListDto listDto = new ListDto();
		listDto.setList(vehicleModel);
		listDto.setTotalSize(vehicleDAO.getVehicleByTypeAndKerbweightCount(goodsTypeId, kerbWeightId).getTotal());
		return listDto;
	}

	@Override
	public String deleteVehicle(int id) throws Exception {
		return vehicleDAO.deleteVehicle(id);
	}

	@Override
	public List<VehicleModel> getVehicle(String vehicleCategoryId) throws Exception {
		List<VehicleDomain> newVehicleDomain = vehicleDAO.getVehicle(vehicleCategoryId);
		return vehicleMapper.entityList(newVehicleDomain);
	}

	@Override
	public String saveVehicle(VehicleModel vehicleModel) throws Exception {
		VehicleDomain vehicleCategoryDomain = new VehicleDomain();
		VehicleCategoryDomain vehicleCategoryDomains = vehicleCategoryDAO
				.getVehicleCategorybyId(vehicleModel.getVehicleCategoryId());
		vehicleModel.setCategory(vehicleCategoryDomains.getCategory());
		BeanUtils.copyProperties(vehicleModel, vehicleCategoryDomain);
		return vehicleDAO.saveVehicle(vehicleCategoryDomain);

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ListDto getVehicleByKerbWeightId(String kerbWeightId,Map<String, String> reqParam) throws Exception {
		int lowerBound = 0;
		int upperBound = 0;
		if (reqParam.size() > 0) {
			Map<String, Integer> paginationCount = CommonUtils.getPagination(reqParam);
			if (paginationCount.size() > 0) {
				lowerBound = paginationCount.get("lowerBound");
				upperBound = paginationCount.get("upperBound");
			}
		}
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PACKAGE.name(), SeverityTypes.DEBUG.ordinal(),
				" getPackagingList in PackagingServiceImpl "));
		List<VehicleDomain> vehicles = vehicleDAO.getVehicleByKerbWeightId(kerbWeightId,lowerBound, upperBound);
		List<VehicleModel> vehicleModel = vehicleMapper.entityList(vehicles);
		ListDto listDto = new ListDto();
		listDto.setList(vehicleModel);
		listDto.setTotalSize(vehicleDAO.getVehicleByKerbWeightIdCount(kerbWeightId).getTotal());
		return listDto;

	}

}
