package com.atpl.mmg.service.faredisttype;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.atpl.mmg.constant.FDType;
import com.atpl.mmg.dao.faredist.FareDistributionDAO;
import com.atpl.mmg.dao.faredisttype.FareDistributionTypeDAO;
import com.atpl.mmg.domain.faredist.FareDistributionDomain;
import com.atpl.mmg.domain.faredisttype.FareDistributionTypeDomain;
import com.atpl.mmg.domain.vehiclecategory.VehicleCategoryDomain;
import com.atpl.mmg.exception.MmgRestException.ALREADY_EXIST;
import com.atpl.mmg.exception.MmgRestException.FARE_DISTRBUTION_ALREADY_EXIST;
import com.atpl.mmg.exception.MmgRestException.FARE_DISTRIBUTION_TYPE_NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;
import com.atpl.mmg.mapper.faredisttype.FareDistributionTypeMapper;
import com.atpl.mmg.model.faredisttype.FareDistributionTypeDto;
import com.atpl.mmg.model.faredisttype.FareDistributionTypeModel;
import com.atpl.mmg.model.profile.RoleModel;
import com.atpl.mmg.model.vehiclecategory.VehicleCategoryModel;
import com.atpl.mmg.service.auth.AdminAuthService;
import com.atpl.mmg.service.faredist.FareDistributionService;
import com.atpl.mmg.utils.CommonUtils;
import com.atpl.mmg.utils.ListDto;

@Service("fareDistributionTypeService")
public class FareDistributionTypeServiceImpl implements FareDistributionTypeService {

	@Autowired
	FareDistributionTypeDAO fareDistributionTypeDAO;

	@Autowired
	FareDistributionTypeMapper fareDistributionTypeMapper;

	@Autowired
	FareDistributionService fareDistributionService;

	@Autowired
	FareDistributionDAO fareDistributionDAO;

	@Autowired
	AdminAuthService authService;

	private void validateFDType(FareDistributionTypeModel fareDistributionTypeModel) {
		if (null == fareDistributionTypeModel.getName() || fareDistributionTypeModel.getName().isEmpty())
			throw new NOT_FOUND("Please enter name");
		if (fareDistributionTypeModel.getRole().isEmpty())
			throw new NOT_FOUND("Please mention role");
		if(null == fareDistributionTypeModel.getType() || fareDistributionTypeModel.getType().isEmpty())
			throw new NOT_FOUND("Please mention Type");
		FDType fadFdType = FDType.getFDType(fareDistributionTypeModel.getType());
		if (null == fadFdType)
			throw new NOT_FOUND("Please Mention valid Fare distribution Type names are " + FDType.TRIP.getCode()
					+ "," + FDType.INSURANCE.getCode() + "," + FDType.LABOUR.getCode());
	}

	@Override
	public String save(FareDistributionTypeModel fareDistributionTypeModel) throws Exception {
		validateFDType(fareDistributionTypeModel);
		FareDistributionTypeDomain fareDTDomain = fareDistributionTypeDAO.getFareDistributionTypeByType(fareDistributionTypeModel.getType());
		if(fareDTDomain != null)
			throw new ALREADY_EXIST(fareDTDomain.getType());
		FareDistributionTypeDomain fareDistributionTypeDomain = new FareDistributionTypeDomain();
		BeanUtils.copyProperties(fareDistributionTypeModel, fareDistributionTypeDomain);
		for (int role : fareDistributionTypeModel.getRole()) {
			authService.getRoleInfo(role);
		}
		fareDistributionTypeDomain.setUuid(CommonUtils.generateRandomId());
		fareDistributionTypeDomain
				.setRole(CommonUtils.listOfIntgerToStringConversion(fareDistributionTypeModel.getRole()));
		return fareDistributionTypeDAO.save(fareDistributionTypeDomain);
	}

	@Override
	public String update(FareDistributionTypeModel fareDistributionTypeModel) throws Exception {
		if (null == fareDistributionTypeModel.getUuid() || fareDistributionTypeModel.getUuid().isEmpty())
			throw new NOT_FOUND("Please mention uuid");
		fareDistributionTypeDAO.getFareDistributionType(fareDistributionTypeModel.getUuid());
		validateFDType(fareDistributionTypeModel);
		FareDistributionTypeDomain fareDistributionTypeDomain = new FareDistributionTypeDomain();
		BeanUtils.copyProperties(fareDistributionTypeModel, fareDistributionTypeDomain);
		fareDistributionTypeDomain
				.setRole(CommonUtils.listOfIntgerToStringConversion(fareDistributionTypeModel.getRole()));
		return fareDistributionTypeDAO.update(fareDistributionTypeDomain);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ListDto getFareDistributionTypes(@RequestParam Map<String, String> reqParam)
			throws Exception {
		Boolean isActive = null;
		int lowerBound = 0;
		int upperBound = 0;
		int totalSize = 0;
		if (reqParam.size() > 0) {
			Map<String, Integer> paginationCount = CommonUtils.getPagination(reqParam);
			if (paginationCount.size() > 0) {
				lowerBound = paginationCount.get("lowerBound");
				upperBound = paginationCount.get("upperBound");
			}
			if (reqParam.containsKey("isActive")) {
				String active = null;
				active = reqParam.get("isActive");
				isActive = Boolean.parseBoolean(active);
			}
			}
		List<FareDistributionTypeDto> fareDistributionTypeModels = null;
		List<FareDistributionTypeDomain> fareDistributionTypeDomainList = new ArrayList<FareDistributionTypeDomain>();
		if (null != isActive) {
			fareDistributionTypeDomainList = fareDistributionTypeDAO.getFareDistributionTypes(isActive,lowerBound,upperBound);
			totalSize = fareDistributionTypeDAO.getFareDistributionTypesCount(isActive).getTotal();
		}
		else {
			fareDistributionTypeDomainList = fareDistributionTypeDAO.getFareDistributionTypes(lowerBound,upperBound);
			totalSize = fareDistributionTypeDAO.getFareDistributionTypesCount().getTotal();
		}
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.FARE_DISTRIBUTION_TYPE.name(), SeverityTypes.DEBUG.ordinal(),
				"getFareDistributionTypes in FareDistributionTypeServiceImpl "));

		fareDistributionTypeModels = new ArrayList<FareDistributionTypeDto>();
		Map<Integer, RoleModel> roleMap = authService.getRolesList();
		for (FareDistributionTypeDomain fDTypeDomain : fareDistributionTypeDomainList) {
			FareDistributionTypeDto fareDistributionDTo = new FareDistributionTypeDto();
			BeanUtils.copyProperties(fDTypeDomain, fareDistributionDTo);
			List<Integer> roles = CommonUtils.stringToListOfIntegerConversion(fDTypeDomain.getRole());
			List<RoleModel> roleList = new  ArrayList<RoleModel>();
			for(int role:roles) {
				RoleModel roleModel = new RoleModel(roleMap.get(role).getId(),roleMap.get(role).getRoleName());
				roleList.add(roleModel);
			}
			fareDistributionDTo.setRole(roleList);
			fareDistributionTypeModels.add(fareDistributionDTo);
		}
//		return fareDistributionTypeModels;
		ListDto listDto = new ListDto();
		listDto.setList(fareDistributionTypeModels);
		listDto.setTotalSize(totalSize);
		return listDto;
	}

	@Override
	public FareDistributionTypeDto getFareDistributionType(String uuid) throws Exception {
		FareDistributionTypeDomain fareDistributionTypeDomain = fareDistributionTypeDAO.getFareDistributionType(uuid);
		if (null == fareDistributionTypeDomain)
			throw new FARE_DISTRIBUTION_TYPE_NOT_FOUND();
		Map<Integer, RoleModel> roleMap = authService.getRolesList();
		FareDistributionTypeDto fareDistributionTypeModel = new FareDistributionTypeDto();
		BeanUtils.copyProperties(fareDistributionTypeDomain, fareDistributionTypeModel);
		List<Integer> roles = CommonUtils.stringToListOfIntegerConversion(fareDistributionTypeDomain.getRole());
		List<RoleModel> roleList = new  ArrayList<RoleModel>();
		for(int role:roles) {
			RoleModel roleModel = new RoleModel(roleMap.get(role).getId(),roleMap.get(role).getRoleName());
			roleList.add(roleModel);
		}
		fareDistributionTypeModel
				.setRole(roleList);
		return fareDistributionTypeModel;
	}

	@Override
	public String delete(String uuid) throws Exception {
		if (null == uuid || uuid.isEmpty())
			throw new NOT_FOUND("Please mention uuid");
		FareDistributionTypeDomain fareDistributionTypeDomain = fareDistributionTypeDAO.getFareDistributionType(uuid);
		if(fareDistributionTypeDomain != null) {
		List<FareDistributionDomain> fareDistributionDomainList = fareDistributionDAO.getFareDistributioneByFDTypeId(uuid);
		if(!fareDistributionDomainList.isEmpty())
			throw new FARE_DISTRBUTION_ALREADY_EXIST(fareDistributionTypeDomain.getType());
		}
		return fareDistributionTypeDAO.delete(uuid);
	}

	@Override
	public String activateFDType(String uuid, Map<String, String> reqParam) throws Exception {
		if (null == uuid || uuid.isEmpty())
			throw new NOT_FOUND("Please mention uuid");
		String franchiseId = null;
		fareDistributionTypeDAO.getFareDistributionType(uuid);
		if (reqParam.size() > 0) {
			if (reqParam.containsKey("franchiseId")) {
				franchiseId = reqParam.get("franchiseId");
			} else
				throw new NOT_FOUND("Please mention request parameter as 'franchiseId' ");
		}
		if (null != franchiseId)
			fareDistributionService.activateFD(uuid, franchiseId);
		return fareDistributionTypeDAO.updateIsActive(true, uuid);
	}

	@Override
	public String deActivateFDType(String uuid, Map<String, String> reqParam) throws Exception {
		if (null == uuid || uuid.isEmpty())
			throw new NOT_FOUND("Please mention uuid");
		String franchiseId = null;
		fareDistributionTypeDAO.getFareDistributionType(uuid);
		if (reqParam.size() > 0) {
			if (reqParam.containsKey("franchiseId")) {
				franchiseId = reqParam.get("franchiseId");
			} else
				throw new NOT_FOUND("Please mention request parameter as 'franchiseId' ");
		}
		if (null != franchiseId)
			fareDistributionService.deActivateFD(uuid, franchiseId);
		else
			fareDistributionService.deActivateFD(uuid, franchiseId);
		return fareDistributionTypeDAO.updateIsActive(false, uuid);
	}

}
