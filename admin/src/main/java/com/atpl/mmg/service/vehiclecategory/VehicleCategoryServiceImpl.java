package com.atpl.mmg.service.vehiclecategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.dao.vehiclecategory.VehicleCategoryDAO;
import com.atpl.mmg.domain.vehiclecategory.VehicleCategoryDomain;
import com.atpl.mmg.domain.weight.WeightDomain;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.T_C_REQ_PARAM_VALIDATE;
import com.atpl.mmg.exception.MmgRestException.VEHICLECATEGORY_AlREADY_EXISTS;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;
import com.atpl.mmg.mapper.vehiclecategory.VehicleCategoryMapper;
import com.atpl.mmg.model.vehiclecategory.VehicleCategoryModel;
import com.atpl.mmg.model.vehiclecategory.WeightModel;
import com.atpl.mmg.utils.CommonUtils;
import com.atpl.mmg.utils.ListDto;

@Service("vehicleCategoryService")
public class VehicleCategoryServiceImpl implements VehicleCategoryService, Constants {

	@Autowired
	VehicleCategoryDAO vehicleCategoryDAO;

	@Autowired
	VehicleCategoryMapper vehicleCategoryMapper;

	private static final Logger logger = LoggerFactory.getLogger(VehicleCategoryServiceImpl.class);

	public VehicleCategoryServiceImpl() {
		// constructor
	}

	private void validate(VehicleCategoryModel vehicleCategoryModel) throws Exception {
		if (null == vehicleCategoryModel.getCategory() || vehicleCategoryModel.getCategory().isEmpty())
			throw new NOT_FOUND("Please mention the category!!");
		if (null == vehicleCategoryModel.getGoodsTypeId() || vehicleCategoryModel.getCategory().isEmpty())
			throw new NOT_FOUND("Please mention the goodsTypeId!!");
		if (0 >= vehicleCategoryModel.getWeightId())
			throw new NOT_FOUND("Please mention the weightId!!");
		if (0.0 >= vehicleCategoryModel.getPricePerKm())
			throw new NOT_FOUND("Please mention the Priceperkm!!");
		if (0.0 >= vehicleCategoryModel.getBaseFare())
			throw new NOT_FOUND("Please mention the baseFare!!");
		if (0.0 >= vehicleCategoryModel.getWaitingTimeCharge())
			throw new NOT_FOUND("Please mention the waiting time charge!!");
		if (0.0 >= vehicleCategoryModel.getMinimumBookingKm())
			throw new NOT_FOUND("Please mention the minimum booking km!!");
		VehicleCategoryDomain vehicleCategory = vehicleCategoryDAO.getVehicleCategoryByNameAndWeight(
				vehicleCategoryModel.getCategory(), vehicleCategoryModel.getWeightId());
		if (null != vehicleCategory)
			throw new VEHICLECATEGORY_AlREADY_EXISTS("Vehicle category already exists!!");
	}

	public String saveVehicleCategory(VehicleCategoryModel vehicleCategoryModel) throws Exception {
		VehicleCategoryDomain vehicleCategoryDomain = new VehicleCategoryDomain();
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.VEHICLE_CATEGORY.name(), SeverityTypes.DEBUG.ordinal(),
				"Validation for saveVehicleCategory in VehicleCategoryServiceImpl ")+JsonUtil.toJsonString(vehicleCategoryModel));

		validate(vehicleCategoryModel);
		BeanUtils.copyProperties(vehicleCategoryModel, vehicleCategoryDomain);
		vehicleCategoryDomain
				.setGoodsTypeId(CommonUtils.listOfIntgerToStringConversion(vehicleCategoryModel.getGoodsTypeId()));
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.VEHICLE_CATEGORY.name(), SeverityTypes.DEBUG.ordinal(),
				"saveVehicleCategory in VehicleCategoryServiceImpl ")+JsonUtil.toJsonString(vehicleCategoryModel));
		return vehicleCategoryDAO.saveVehicleCategory(vehicleCategoryDomain);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ListDto getVehicleCategory(Map<String,String> reqParam) throws Exception {
		int lowerBound = 0;
		int upperBound = 0;
		int totalSize = 0;
		List<VehicleCategoryDomain> vehicleCategories = new ArrayList<VehicleCategoryDomain>();
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.VEHICLE_CATEGORY.name(), SeverityTypes.DEBUG.ordinal(),
				"getVehicleCategory in VehicleCategoryServiceImpl "));
		if (reqParam.size() == 0) {
			 vehicleCategories = vehicleCategoryDAO.getVehicleCategory(lowerBound,upperBound);
			 totalSize = vehicleCategoryDAO.getVehicleCategoryCount().getTotal();
		}
		else
			if (reqParam.size() > 0) {
				Map<String, Integer> paginationCount = CommonUtils.getPagination(reqParam);
				if (paginationCount.size() > 0) {
					lowerBound = paginationCount.get("lowerBound");
					upperBound = paginationCount.get("upperBound");
				}
				if (reqParam.containsKey("weightId")) {
					String weight = reqParam.get("weightId");
					Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.VEHICLE_CATEGORY.name(), SeverityTypes.DEBUG.ordinal(),
							"getVehicleCategoryOnWeightId in VehicleCategoryServiceImpl ")+JsonUtil.toJsonString(weight));
					vehicleCategories = vehicleCategoryDAO.getVehicleCategorybyWeight(weight,lowerBound,upperBound);
					totalSize = vehicleCategoryDAO.getVehicleCategorybyWeightCount(weight).getTotal();
				} 
				else
				{
					Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.VEHICLE_CATEGORY.name(), SeverityTypes.DEBUG.ordinal(),
							"getAllVehicleCategorys in VehicleCategoryServiceImpl "));
					 vehicleCategories = vehicleCategoryDAO.getVehicleCategory(lowerBound,upperBound);
					 totalSize = vehicleCategoryDAO.getVehicleCategoryCount().getTotal();
				}
			}
		List<VehicleCategoryModel> vehicleCategoryModel = vehicleCategoryMapper.entityList(vehicleCategories);
		ListDto listDto = new ListDto();
		listDto.setList(vehicleCategoryModel);
		listDto.setTotalSize(totalSize);
		return listDto;
	}

	@Override
	public String updateVehicleCategory(VehicleCategoryModel vehicleCategoryId) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.VEHICLE_CATEGORY.name(), SeverityTypes.DEBUG.ordinal(),
				" updateVehicleCategoryByvehicleCategoryId in VehicleCategoryServiceImpl ")+JsonUtil.toJsonString(vehicleCategoryId));
		VehicleCategoryDomain vehicleCategoryDomain = new VehicleCategoryDomain();
		BeanUtils.copyProperties(vehicleCategoryId, vehicleCategoryDomain);
		vehicleCategoryDomain
		.setGoodsTypeId(CommonUtils.listOfIntgerToStringConversion(vehicleCategoryId.getGoodsTypeId()));
		return vehicleCategoryDAO.updateVehicleCategory(vehicleCategoryDomain);
	}

	@Override
	public String deleteVehicleCategory(int vehicleCategoryId) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.VEHICLE_CATEGORY.name(), SeverityTypes.DEBUG.ordinal(),
				"deleteVehicleCategoryByvehicleCategoryId in VehicleCategoryServiceImpl ")+JsonUtil.toJsonString(vehicleCategoryId));
		 vehicleCategoryDAO.enableDisableVehicleCategory(vehicleCategoryId,false);
		return "Deleted successfully";

	}

	@Override
	public String enableVehicleCategory(int vehicleCategoryId) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.VEHICLE_CATEGORY.name(), SeverityTypes.DEBUG.ordinal(),
				"enableVehicleCategoryByvehicleCategoryId in VehicleCategoryServiceImpl ")+JsonUtil.toJsonString(vehicleCategoryId));
		return vehicleCategoryDAO.enableDisableVehicleCategory(vehicleCategoryId,true);
	}

	
	@Override
	public VehicleCategoryModel getVehicleCategorybyId(Integer vehicleCategoryId) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.VEHICLE_CATEGORY.name(), SeverityTypes.DEBUG.ordinal(),
				"getVehicleCategoryByvehicleCategoryId in VehicleCategoryServiceImpl ")+JsonUtil.toJsonString(vehicleCategoryId));
		VehicleCategoryDomain vehicleCategoryDomain = vehicleCategoryDAO.getVehicleCategorybyId(vehicleCategoryId);
		VehicleCategoryModel vehicleCategoryModel = new VehicleCategoryModel();
		if (vehicleCategoryDomain == null)
			throw new NOT_FOUND("VehicleCategory not found");
		BeanUtils.copyProperties(vehicleCategoryDomain, vehicleCategoryModel);
		return vehicleCategoryModel;
	}

	@Override
	public VehicleCategoryModel getVehicleCategorybyId() throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.VEHICLE_CATEGORY.name(), SeverityTypes.DEBUG.ordinal(),
				"get latest  VehicleCategoryId among vehiclecategories sorted in descending order in VehicleCategoryServiceImpl "));
		VehicleCategoryDomain vehicleCategoryDomain = vehicleCategoryDAO.getVehicleCategorybyId();
		VehicleCategoryModel vehicleCategoryModel = new VehicleCategoryModel();
		if(vehicleCategoryDomain == null)
			throw new NOT_FOUND("VehicleCategory not found");
		BeanUtils.copyProperties(vehicleCategoryDomain, vehicleCategoryModel);
		return vehicleCategoryModel;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ListDto getVehicle(String goodsTypeId, int kerbWeightId,Map<String,String> reqParam) throws Exception {
		int lowerBound = 0;
		int upperBound = 0;
		int totalSize = 0;
		if (reqParam.size() > 0) {
			Map<String, Integer> paginationCount = CommonUtils.getPagination(reqParam);
			if (paginationCount.size() > 0) {
				lowerBound = paginationCount.get("lowerBound");
				upperBound = paginationCount.get("upperBound");
			}
		}
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.VEHICLE_CATEGORY.name(), SeverityTypes.DEBUG.ordinal(),
				"getVehicle on goodsType and weightId in VehicleCategoryServiceImpl "));
		List<VehicleCategoryDomain> newVehicleDomain = vehicleCategoryDAO.getVehicle(goodsTypeId, kerbWeightId,lowerBound, upperBound);
		List<VehicleCategoryModel> vehicleCategoryModel = vehicleCategoryMapper.entityList(newVehicleDomain);
		ListDto listDto = new ListDto();
		listDto.setList(vehicleCategoryModel);
		totalSize = vehicleCategoryDAO.getVehicleCount(goodsTypeId, kerbWeightId).getTotal();
		listDto.setTotalSize(totalSize);
		return listDto;
	}

	@Override
	public List<VehicleCategoryModel> getVehiclesByDirectBooking() throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.VEHICLE_CATEGORY.name(), SeverityTypes.DEBUG.ordinal(),
				"getVehiclesForDirectBooking in VehicleCategoryServiceImpl "));
		List<VehicleCategoryDomain> newVehicleDomain = vehicleCategoryDAO
				.getVehiclesByDirectBooking(Constants.KERBWEIGHT);
		return vehicleCategoryMapper.entityList(newVehicleDomain);
	}

	@Override
	public List<VehicleCategoryModel> getVehiclesByKerbweightId(WeightModel weightModel) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.VEHICLE_CATEGORY.name(), SeverityTypes.DEBUG.ordinal(),
				"getVehiclesByKerbweightId in VehicleCategoryServiceImpl ")+JsonUtil.toJsonString(weightModel));
		List<VehicleCategoryDomain> newVehicleDomain = new ArrayList<VehicleCategoryDomain>();
		for (int i = 0; i <= weightModel.getWeightId().size() - 1; i++) {
			int weightId = weightModel.getWeightId().get(i);
			List<VehicleCategoryDomain> vehicleDomain = vehicleCategoryDAO.getVehiclesByKerbweightId(weightId);
			newVehicleDomain.addAll(vehicleDomain);
		}
		return vehicleCategoryMapper.entityList(newVehicleDomain);
	}

}
