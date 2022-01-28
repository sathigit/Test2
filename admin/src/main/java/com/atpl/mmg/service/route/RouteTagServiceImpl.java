package com.atpl.mmg.service.route;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.constant.Role;
import com.atpl.mmg.constant.TagLocation;
import com.atpl.mmg.dao.route.RouteTagDao;
import com.atpl.mmg.domain.route.RouteTagDomain;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;
import com.atpl.mmg.mapper.route.RouteMapper;
import com.atpl.mmg.model.profile.ProfileModel;
import com.atpl.mmg.model.route.RouteModel;
import com.atpl.mmg.model.route.RouteTagDetailsModel;
import com.atpl.mmg.model.route.RouteTagModel;
import com.atpl.mmg.model.route.VendorDetails;
import com.atpl.mmg.service.admin.FranchiseCommonService;
import com.atpl.mmg.service.auth.AdminAuthService;
import com.atpl.mmg.utils.CommonUtils;

@Service("RouteTagService")
public class RouteTagServiceImpl implements RouteTagService {

	@Autowired
	RouteService routeService;

	@Autowired
	RouteTagDao routeTagDao;

	@Autowired
	RouteMapper routeMapper;

	@Autowired
	AdminAuthService authService;

	@Autowired
	FranchiseCommonService franchiseCommonService;

	private void validateRouteTag(RouteTagModel routeTagModel, boolean isUpdate) throws Exception {
		if (isUpdate) {
			if (CommonUtils.isNullCheck(routeTagModel.getUuid()))
				throw new NOT_FOUND("Please mention the uuid!!");
			else
				routeTagDao.getRouteTagById(routeTagModel.getUuid());
		}

		if (!CommonUtils.isNullCheck(routeTagModel.getVendorId())) {
			if (0 >= routeTagModel.getRoleId())
				throw new NOT_FOUND("Please mention the role Id!!");
			else {
				Role role = Role.getRole(String.valueOf(routeTagModel.getTagByRoleId()));
				if (null == role)
					throw new NOT_FOUND(role + " not found!!");
				if (routeTagModel.getRoleId() != Integer.valueOf(Role.FRANCHISE.getCode())) {
					throw new NOT_FOUND("Could not able to tag role " + role + "for the route");
				}
				authService.getDetails(role.toString(), routeTagModel.getVendorId(), "franchiseId");
			}

			if (CommonUtils.isNullCheck(routeTagModel.getTagLocation()))
				throw new NOT_FOUND("Please mention the tag location!!");
			else {
				TagLocation loc = TagLocation.getTagLocation(routeTagModel.getTagLocation());
				if (loc == null)
					throw new NOT_FOUND(routeTagModel.getTagLocation() + " not found  in TagLocation");
			}
		} else {
			if (!CommonUtils.isNullCheck(routeTagModel.getVehicleId())) {
				franchiseCommonService.getVehicleDetails(routeTagModel.getVehicleId());
			} else
				throw new NOT_FOUND("Please mention the vendorId !!!");
		}
//		if (CommonUtils.isNullCheck(routeTagModel.getVendorId()))
//			throw new NOT_FOUND("Please mention the vendorId!!");
//		if (0 >= routeTagModel.getRoleId())
//			throw new NOT_FOUND("Please mention the role Id!!");
//		else {
//			Role role = Role.getRole(String.valueOf(routeTagModel.getTagByRoleId()));
//			if (null == role)
//				throw new NOT_FOUND(role + " not found!!");
//			if (routeTagModel.getRoleId() != Integer.valueOf(Role.FRANCHISE.getCode())) {
//				throw new NOT_FOUND("Could not able to tag role " + role + "for the route");
//			}
//			authService.getDetails(role.toString(), routeTagModel.getVendorId(), "franchiseId");
//		}
//
//		if (CommonUtils.isNullCheck(routeTagModel.getTagLocation()))
//			throw new NOT_FOUND("Please mention the tag location!!");
//		else {
//			TagLocation loc = TagLocation.getTagLocation(routeTagModel.getTagLocation());
//			if (loc == null)
//				throw new NOT_FOUND(routeTagModel.getTagLocation() + " not found  in TagLocation");
//		}

		if (0 >= routeTagModel.getTagByRoleId())
			throw new NOT_FOUND("Please mention the tagBy role Id!!");
		else {
			Role role = Role.getRole(String.valueOf(routeTagModel.getTagByRoleId()));
			if (null == role)
				throw new NOT_FOUND(routeTagModel.getTagByRoleId() + " not found!!");
		}
		if (CommonUtils.isNullCheck(routeTagModel.getTagBy()))
			throw new NOT_FOUND("Please mention the tagBy!!");
		else
			authService.getProfileDetByProfileId(routeTagModel.getTagBy(), routeTagModel.getTagByRoleId());
		if (!CommonUtils.isNullCheck(routeTagModel.getVehicleId())) {
			franchiseCommonService.getVehicleDetails(routeTagModel.getVehicleId());
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public String addRouteTag(RouteTagModel routeTagModel) throws Exception {
		RouteTagDomain routeTagDomain = new RouteTagDomain();
		validateRouteTag(routeTagModel, false);
		BeanUtils.copyProperties(routeTagModel, routeTagDomain);
		if (!CommonUtils.isNullCheck(routeTagModel.getVehicleId())) {
			RouteTagDomain routeTag = routeTagDao.validateRouteTag( routeTagModel.getRouteId(), null,0,routeTagModel.getVehicleId());
			if (null == routeTag) {
				if (franchiseCommonService.updateVehicleIsTag(routeTagModel.getVehicleId(), true)) {
					routeTagDomain.setTagLocation(null);
					routeTagDomain.setVendorId(null);
					routeTagDomain.setRoleId(null);
					routeTagDomain.setUuid(CommonUtils.generateRandomId());
					routeTagDao.addRouteTag(routeTagDomain);
				}
			}
		}
		if (!CommonUtils.isNullCheck(routeTagModel.getVendorId()) && routeTagModel.getRoleId() > 0) {
			RouteTagDomain routeTag = routeTagDao.validateRouteTag( routeTagModel.getRouteId(), routeTagModel.getVendorId(),routeTagModel.getRoleId(),null);
			if (null == routeTag) {
				BeanUtils.copyProperties(routeTagModel, routeTagDomain);
				if (authService.tagVendor(routeTagModel)) {
					routeTagDomain.setVehicleId(null);
					routeTagDomain.setUuid(CommonUtils.generateRandomId());
						routeTagDao.addRouteTag(routeTagDomain);
				}
			}
		}
		return "Tagged successfully";
	}

	public List<RouteTagDetailsModel> fetchRouteTagDetails(List<RouteTagDomain> routeTagDomain, boolean fetchAll,
			boolean taggedVehicle, boolean taggedVendors) throws Exception {
		List<RouteTagDetailsModel> routeTagModel = new ArrayList<RouteTagDetailsModel>();
		if (!routeTagDomain.isEmpty()) {
			for (RouteTagDomain domain : routeTagDomain) {
				RouteTagDetailsModel model = new RouteTagDetailsModel();
				BeanUtils.copyProperties(domain, model);
				RouteModel routeDomain = routeService.getRouteDetailsById(domain.getRouteId());
				RouteTagDetailsModel routeTagDetailsModel = new RouteTagDetailsModel();
				RouteModel routeModel = new RouteModel(routeDomain.getSource(), routeDomain.getDestination());
				model.setRouteDetails(routeModel);

				if (null != domain.getRoleId() && !CommonUtils.isNullCheck(domain.getVendorId())) {
					Role role = Role.getRole(String.valueOf(domain.getRoleId()));
					ProfileModel profile = authService.getDetails(role.toString(), domain.getVendorId(), "franchiseId");
					VendorDetails vendor = new VendorDetails();
					vendor.setVendorId(domain.getVendorId());
					vendor.setVendorName(profile.getFranchise().getCompanyName());
					vendor.setVendorRole(role.toString());
					model.setVendorDetails(vendor);
				}
				routeTagDetailsModel = model;
				if (!fetchAll) {
					routeTagDetailsModel = new RouteTagDetailsModel(domain.getVehicleId(), model.getVendorDetails(),
							domain.getTagLocation(), domain.isStatus());
				}
				routeTagModel.add(routeTagDetailsModel);
				if (taggedVehicle) {
					if (CommonUtils.isNullCheck(domain.getVendorId()))
						routeTagModel.add(routeTagDetailsModel);
				}
				if (taggedVendors) {
					if (CommonUtils.isNullCheck(domain.getVehicleId()))
						routeTagModel.add(routeTagDetailsModel);
				}
			}
		}
		return routeTagModel;
	}

	@Override
	public List<RouteTagDetailsModel> getRouteTag(Map<String, String> reqParam) throws Exception {
		String vendorId = null, routeId = null, tagLocation = null, vehicleId = null;
		boolean taggedVehicle = false, taggedVendors = false;
		List<RouteTagDomain> routeTagDomain = new ArrayList<RouteTagDomain>();
		if (reqParam.size() > 0) {
			if (reqParam.containsKey("routeId"))
				routeId = reqParam.get("routeId");
			if (reqParam.containsKey("vendorId")) {
				vendorId = reqParam.get("vendorId");
			}
			if (reqParam.containsKey("tagLocation")) {
				tagLocation = reqParam.get("tagLocation");
			}
			if (reqParam.containsKey("vehicleId")) {
				vehicleId = reqParam.get("vehicleId");
			}
			if (reqParam.containsKey("taggedVehicle")) {
				taggedVehicle = Boolean.parseBoolean(reqParam.get("taggedVehicle"));
			}
			if (reqParam.containsKey("taggedVendors")) {
				taggedVendors = Boolean.parseBoolean(reqParam.get("taggedVendors"));
			}
			routeTagDomain = routeTagDao.getAllRouteTag(routeId, vendorId, tagLocation, vehicleId);
		} else
			routeTagDomain = routeTagDao.getAllRouteTag();
		List<RouteTagDetailsModel> routeTagModel = new ArrayList<RouteTagDetailsModel>();
		if (!routeTagDomain.isEmpty())
			routeTagModel = fetchRouteTagDetails(routeTagDomain, true, taggedVehicle, taggedVendors);
		return routeTagModel;
	}

	@Override
	public String updateRouteTag(RouteTagModel routeTagModel) throws Exception {
		RouteTagDomain routeTagDomain = new RouteTagDomain();
		validateRouteTag(routeTagModel, true);
		BeanUtils.copyProperties(routeTagModel, routeTagDomain);
		routeTagDao.updateRouteTag(routeTagDomain);
		return "Updated Successfully";
	}

	@Override
	public String updateRouteTagStatus(String uuid, boolean status) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(), SeverityTypes.DEBUG.ordinal(),
				" updateRouteTagStatus in RouteTagServiceImpl ") + JsonUtil.toJsonString(uuid));
		routeTagDao.getRouteTagById(uuid);
		return routeTagDao.updateRouteTagStatus(uuid, status);
	}

	@Override
	public String deleteRouteTag(String uuid) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(), SeverityTypes.DEBUG.ordinal(),
				"deleteRouteTag in RouteTagServiceImpl ") + JsonUtil.toJsonString(uuid));
		routeTagDao.getRouteTagById(uuid);
		return routeTagDao.deleteRouteTag(uuid);
	}
}
