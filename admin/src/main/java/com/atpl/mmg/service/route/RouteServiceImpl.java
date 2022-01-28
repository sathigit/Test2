package com.atpl.mmg.service.route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.constant.Role;
import com.atpl.mmg.constant.TagLocation;
import com.atpl.mmg.dao.city.CityDAO;
import com.atpl.mmg.dao.country.CountryDAO;
import com.atpl.mmg.dao.route.RouteDao;
import com.atpl.mmg.dao.route.RouteTagDao;
import com.atpl.mmg.dao.state.StateDAO;
import com.atpl.mmg.domain.city.CityDomain;
import com.atpl.mmg.domain.country.CountryDomain;
import com.atpl.mmg.domain.route.RouteDomain;
import com.atpl.mmg.domain.route.RouteTagDomain;
import com.atpl.mmg.domain.state.StateDomain;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.ROUTE_CREATE_ACCESS_DENIED;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;
import com.atpl.mmg.mapper.route.RouteMapper;
import com.atpl.mmg.model.profile.AddressModel;
import com.atpl.mmg.model.profile.Profile;
import com.atpl.mmg.model.profile.ProfileModel;
import com.atpl.mmg.model.route.Address;
import com.atpl.mmg.model.route.AddressDetailsModel;
import com.atpl.mmg.model.route.RouteDashboard;
import com.atpl.mmg.model.route.RouteDetailsModel;
import com.atpl.mmg.model.route.RouteModel;
import com.atpl.mmg.model.route.RouteModelDTO;
import com.atpl.mmg.model.route.RouteTagDetailsModel;
import com.atpl.mmg.service.admin.FranchiseCommonService;
import com.atpl.mmg.service.auth.AdminAuthService;
import com.atpl.mmg.service.dashboard.DashboardService;
import com.atpl.mmg.service.faredist.BookingCommonService;
import com.atpl.mmg.utils.CommonUtils;
import com.atpl.mmg.utils.ListDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("RouteService")
public class RouteServiceImpl implements RouteService {

	@Autowired
	RouteDao routeDao;

	@Autowired
	RouteTagDao routeTagDao;

	@Autowired
	RouteMapper routeMapper;

	@Autowired
	CityDAO cityDAO;

	@Autowired
	StateDAO stateDAO;

	@Autowired
	CountryDAO countryDAO;

	@Autowired
	AdminAuthService authService;

	@Autowired
	FranchiseCommonService franchiseCommonService;

	@Autowired
	DashboardService dashboardService;

	@Autowired
	RouteTagServiceImpl routeTagServiceImpl;
	
	@Autowired
	BookingCommonService bookingCommonService;

	private RouteModel validateRoute(RouteDetailsModel routeModel, boolean isUpdate) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(), SeverityTypes.DEBUG.ordinal(),
				"Exception validateRoute in RouteServiceImpl " + JsonUtil.toJsonString(routeModel)));
		RouteModel route = new RouteModel();
		BeanUtils.copyProperties(routeModel, route);
		if (isUpdate) {
			if (CommonUtils.isNullCheck(routeModel.getUuid()))
				throw new NOT_FOUND("Please mention the routeId");
			else
				getRouteDetailsById(routeModel.getUuid());
		}
		if (CommonUtils.isNullCheck(routeModel.getCreatedBy()))
			throw new NOT_FOUND("Please mention the createdById");
		else {
			if (0 >= routeModel.getRoleId())
				throw new NOT_FOUND("Please mention the roleId");
			else {
				if (!Role.OWNER.getCode().equals(String.valueOf(routeModel.getRoleId()))
						&& !Role.TIEUPS.getCode().equals(String.valueOf(routeModel.getRoleId()))) {
					Role role = Role.getRole(String.valueOf(routeModel.getRoleId()));
					throw new ROUTE_CREATE_ACCESS_DENIED("Route creation access denied for this " + role + " role!!");
				}
				authService.getRoleInfo(routeModel.getRoleId());
				ProfileModel profile = authService.getProfileDetByProfileId(routeModel.getCreatedBy(),
						routeModel.getRoleId());
				if (null == profile)
					throw new NOT_FOUND("Please mention the proper profile details");
			}
		}
		if (null == routeModel.getSource())
			throw new NOT_FOUND("Please mention the source");
		else {
			AddressDetailsModel sourceAddress = routeModel.getSource();
			Address source = validateAddress(sourceAddress, Constants.SOURCE, false);
			route.setSource(source);
		}
		if (null == routeModel.getDestination())
			throw new NOT_FOUND("Please mention the destination");
		else {
			AddressDetailsModel destinationAddress = routeModel.getDestination();
			Address destination = validateAddress(destinationAddress, Constants.DESTINATION, false);
			route.setDestination(destination);
		}

		return route;
	}

	private void validateAddressExists(RouteModel routeModel, boolean googleApiAddress) throws Exception {
		RouteDomain route = new RouteDomain();
		if (googleApiAddress) {
//			route = changeLocationNameAsId(routeModel);
			route = routeDao.validateRouteDetails(route);
			Address source = route.getSourceAddress();
			Address destination = route.getDestinationAddress();
			String srclat = String.valueOf(source.getLatitude());
			String srclong = String.valueOf(source.getLongitude());
			String dstlat = String.valueOf(destination.getLatitude());
			String dstlong = String.valueOf(destination.getLongitude());
			route = routeDao.validateRouteDetailsOnLatLong(srclat, srclong, dstlat, dstlong);
		} else {
			Address source = routeModel.getSource();
			Address destination = routeModel.getDestination();
			String srcCity = String.valueOf(source.getCity());
			String dstCity = String.valueOf(destination.getCity());
			route = routeDao.validateRouteCityDetails(srcCity, dstCity);
		}
		if (null != route)
			throw new NOT_FOUND("Route already exists");
	}

	private RouteDomain changeLocationNameAsId(RouteModel routeModel) throws Exception {
		RouteDomain routeDomain = new RouteDomain();
		BeanUtils.copyProperties(routeModel, routeDomain);
		routeDomain.setSourceAddress(routeModel.getSource());
		routeDomain.setDestinationAddress(routeModel.getDestination());
		RouteDomain routeDetailsDomain = new RouteDomain();
		BeanUtils.copyProperties(routeDomain, routeDetailsDomain);
//		routeDetailsDomain.setSourceAddress(getAddressLocationId(routeDomain.getSourceAddress()));
//		routeDetailsDomain.setDestinationAddress(getAddressLocationId(routeDomain.getDestinationAddress()));
		ObjectMapper mapper = new ObjectMapper();
		routeDetailsDomain.setSource(mapper.writeValueAsString(routeDetailsDomain.getSourceAddress()));
		routeDetailsDomain.setDestination(mapper.writeValueAsString(routeDetailsDomain.getDestinationAddress()));
		return routeDetailsDomain;
	}

	private Address validateAddress(AddressDetailsModel address, String target, boolean googleApiAddress)
			throws Exception {
		Address addressModel = new Address();
		if (googleApiAddress) {
			// Address should have complete Address , names instead of id's in state
			// ,country and city
			/*
			 * if (CommonUtils.isNullCheck(address.getAddress1())) throw new
			 * NOT_FOUND("Please mention the " + target + " address1"); if
			 * (CommonUtils.isNullCheck(address.getAddress2())) throw new
			 * NOT_FOUND("Please mention the " + target + " address2"); if
			 * (CommonUtils.isNullCheck(address.getCity())) throw new
			 * NOT_FOUND("Please mention the " + target + " city"); if
			 * (CommonUtils.isNullCheck(address.getState())) throw new
			 * NOT_FOUND("Please mention the " + target + " state"); if
			 * (CommonUtils.isNullCheck(address.getCountry())) throw new
			 * NOT_FOUND("Please mention the " + target + " country"); if
			 * (CommonUtils.isNullCheck(address.getPinCode())) { CityDomain city =
			 * cityDAO.getCityId(address.getCity()); if
			 * (!CommonUtils.isNullCheck(city.getPinCode()))
			 * address.setPinCode(city.getPinCode()); else throw new
			 * NOT_FOUND("Please mention the " + target + " pincode"); } if (0.0 >=
			 * address.getLatitude()) throw new NOT_FOUND("Please mention the  " + target +
			 * " latitude"); if (0.0 >= address.getLongitude()) throw new
			 * NOT_FOUND("Please mention the " + target + " longitude");
			 */
		} else {
			if (CommonUtils.isNullCheck(address.getAddress1()))
				address.setAddress1("");
			if (CommonUtils.isNullCheck(address.getAddress2()))
				address.setAddress2("");
			if (0 >= address.getCity())
				throw new NOT_FOUND("Please mention the " + target + " city");
			else
				cityDAO.getCity(address.getCity());
			if (0 >= address.getState())
				throw new NOT_FOUND("Please mention the " + target + " state");
			if (0 >= address.getCountry())
				throw new NOT_FOUND("Please mention the " + target + " country");
			if (CommonUtils.isNullCheck(address.getPinCode()))
				address.setPinCode("");
			if (CommonUtils.isNullCheck(address.getLandmark()))
				address.setLandmark("");
			if (0.0 >= address.getLatitude())
				address.setLatitude(0.0);
			if (0.0 >= address.getLongitude())
				address.setLongitude(0.0);
			BeanUtils.copyProperties(address, addressModel);
			addressModel.setCity(String.valueOf(address.getCity()));
			addressModel.setState(String.valueOf(address.getState()));
			addressModel.setCountry(String.valueOf(address.getCountry()));
		}
		return addressModel;

	}

	private Address getAddressLocationId(Address address) throws Exception {
		StateDomain stateDomain = new StateDomain();
		CountryDomain countryDomain = new CountryDomain();
		CityDomain cityDomain = cityDAO.getCityId(address.getCity());
		if (!CommonUtils.isNullCheck(address.getState())) {
			stateDomain = stateDAO.getState(address.getState());
		}
		if (!CommonUtils.isNullCheck(address.getCountry())) {
			countryDomain = countryDAO.getCountry(address.getCountry());
		}
		Address addressDetailsModel = new Address();
		BeanUtils.copyProperties(address, addressDetailsModel);
		addressDetailsModel.setCity(String.valueOf(cityDomain.getId()));
		if (0 < stateDomain.getId())
			addressDetailsModel.setState(String.valueOf(stateDomain.getId()));
		if (0 < countryDomain.getId())
			addressDetailsModel.setCountry(String.valueOf(countryDomain.getId()));
		return addressDetailsModel;
	}

	private Address getAddressLocationName(Address addressDetailsModel) throws Exception {
		StateDomain stateDomain = new StateDomain();
		CountryDomain countryDomain = new CountryDomain();
		CityDomain cityDomain = cityDAO.getCity(Integer.valueOf(addressDetailsModel.getCity()));
		if (!CommonUtils.isNullCheck(addressDetailsModel.getState())) {
			stateDomain = stateDAO.getState(Integer.valueOf(addressDetailsModel.getState()));
		}
		if (!CommonUtils.isNullCheck(addressDetailsModel.getCountry())) {
			countryDomain = countryDAO.getCountry(Integer.valueOf(addressDetailsModel.getCountry()));
		}
		Address address = new Address();
		BeanUtils.copyProperties(addressDetailsModel, address);
		address.setCityId(cityDomain.getId());
		address.setCity(cityDomain.getName());
		address.setState(stateDomain.getName());
		address.setCountry(countryDomain.getName());
		return address;
	}

	private RouteModel mapAddressToRouteDetails(RouteModel routeModel, RouteDomain routeDomain) throws Exception {
		String jsonString = null;
		jsonString = routeDomain.getDestination();
		Address destinationRoute = getAddressDetails(jsonString);
		routeModel.setDestination(destinationRoute);
		jsonString = routeDomain.getSource();
		Address sourceRoute = getAddressDetails(jsonString);
		routeModel.setSource(sourceRoute);
		return routeModel;
	}

	private Address getAddressDetails(String address) throws Exception {
		Address route = JsonUtil.fromJson(address, Address.class);
		Address addressDetails = getAddressLocationName(route);
		Address addr = new Address(addressDetails.getCityId(), addressDetails.getCity(), addressDetails.getState(),
				addressDetails.getCountry());
		return addr;
	}

	private RouteModelDTO mapAddressToRouteModelDTO(RouteModelDTO routeModelDTO, RouteDomain routeDomain)
			throws Exception {
		String jsonString = null;
		jsonString = routeDomain.getDestination();
		Address destinationRoute = getAddressDetails(jsonString);
		routeModelDTO.setDestination(destinationRoute);
		jsonString = routeDomain.getSource();
		Address sourceRoute = getAddressDetails(jsonString);
		routeModelDTO.setSource(sourceRoute);
		return routeModelDTO;
	}

	@Override

	public RouteModel addRouteDetails(RouteDetailsModel routeModel) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(), SeverityTypes.DEBUG.ordinal(),
				"Exception addRoute in RouteServiceImpl " + JsonUtil.toJsonString(routeModel)));
		RouteModel routeDetailsModel = validateRoute(routeModel, false);
		RouteDomain routeDetailsDomain = new RouteDomain();
		BeanUtils.copyProperties(routeDetailsModel, routeDetailsDomain);
		routeDetailsDomain = changeLocationNameAsId(routeDetailsModel);
		validateAddressExists(routeDetailsModel, false);
		routeDetailsDomain.setUuid(CommonUtils.generateRandomId());
		routeDao.addRoute(routeDetailsDomain);
		RouteModel route = new RouteModel(routeDetailsDomain.getUuid());
		return route;
	}

	@Override
	public RouteModel getRouteDetailsById(String routeId) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(), SeverityTypes.DEBUG.ordinal(),
				" getRouteById in RouteServiceImpl ") + JsonUtil.toJsonString(routeId));
		RouteDomain routeDomain = routeDao.getRouteById(routeId);
		if (routeDomain == null)
			throw new NOT_FOUND("Route not found");
		RouteModel routeModel = new RouteModel();
		BeanUtils.copyProperties(routeDomain, routeModel);
		routeModel = mapAddressToRouteDetails(routeModel, routeDomain);
		return routeModel;
	}

	@Override
	public RouteModelDTO getRouteDetailsByRouteId(String uuid) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(), SeverityTypes.DEBUG.ordinal(),
				" getRouteDetailsByRouteId in RouteServiceImpl ") + JsonUtil.toJsonString(uuid));
		RouteDomain routeDomain = routeDao.getRouteById(uuid);
		if (routeDomain == null)
			throw new NOT_FOUND("Route not found");
		RouteModelDTO routeModelDTO = new RouteModelDTO();
		BeanUtils.copyProperties(routeDomain, routeModelDTO);
		routeModelDTO = mapAddressToRouteModelDTO(routeModelDTO, routeDomain);
		routeModelDTO = getRouteTag(routeModelDTO, uuid, TagLocation.SOURCE.name(), TagLocation.DESTINATION.name());
		RouteDashboard  routeDashboard = new RouteDashboard();
		Map<String,String> reqParam = new HashMap<String, String>();
		reqParam.put("routeId", uuid);
		routeDashboard.setTotalTrips(dashboardService.getRouteCount(reqParam).getTotal());
		routeDashboard.setTotalWeight(bookingCommonService.getMasterTripTotalWeight(uuid,null,null).getTotal());
		routeDashboard.setTotalEarnings(0.0);
		routeModelDTO.setRouteDashboard(routeDashboard);
//		List<VehicleDetailsModel> vehicleModel = franchiseCommonService.vehicleListOnRouteId(uuid);
//		routeModelDTO.setTaggedVehicles(vehicleModel);
		return routeModelDTO;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ListDto getAllRouteDetails(Map<String, String> reqParam) throws Exception {
		int lowerBound = 0, upperBound = 0, totalSize = 0;
		Boolean status = null;
		if (reqParam.size() > 0) {
			Map<String, Integer> paginationCount = CommonUtils.getPagination(reqParam);
			if (paginationCount.size() > 0) {
				lowerBound = paginationCount.get("lowerBound");
				upperBound = paginationCount.get("upperBound");
			}

			if (reqParam.containsKey("status")) {
				String isActive = reqParam.get("status");
				status = Boolean.parseBoolean(isActive);
			}
		}
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(), SeverityTypes.DEBUG.ordinal(),
				"getAllRoute in RouteServiceImpl"));
		List<RouteDomain> routeDomain = routeDao.getAllRoute(status, lowerBound, upperBound);
		totalSize = routeDao.getRouteCount(status).getTotal();
		List<RouteModel> routeModelDTO = new ArrayList<RouteModel>();
		for (RouteDomain route : routeDomain) {
			RouteModel routeDTO = new RouteModel();
			BeanUtils.copyProperties(route, routeDTO);
			routeDTO = mapAddressToRouteDetails(routeDTO, route);
			routeDTO.setTaggedVehiclesCount(routeTagDao.getAllRouteTagVehiclesVendors(route.getUuid(), true, 0).size());
			routeDTO.setTaggedVendorsCount(routeTagDao
					.getAllRouteTagVehiclesVendors(route.getUuid(), false, Integer.valueOf(Role.FRANCHISE.getCode()))
					.size());
			routeModelDTO.add(routeDTO);
		}
		ListDto listDto = new ListDto();
		listDto.setList(routeModelDTO);
		listDto.setTotalSize(totalSize);
		return listDto;
	}

	@Override
	public RouteModel editRoute(RouteDetailsModel routeModel) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(), SeverityTypes.DEBUG.ordinal(),
				"Exception editRoute in RouteServiceImpl " + JsonUtil.toJsonString(routeModel)));
		RouteModel routeDetailsModel = validateRoute(routeModel, false);
		RouteDomain routeDetailsDomain = new RouteDomain();
		BeanUtils.copyProperties(routeDetailsModel, routeDetailsDomain);
		routeDetailsDomain = changeLocationNameAsId(routeDetailsModel);
		validateAddressExists(routeDetailsModel, false);
//		RouteDomain routeDomain = new RouteDomain();
//		BeanUtils.copyProperties(routeDetailsModel, routeDomain);
//		routeDomain.setSourceAddress(routeDetailsModel.getSource());
//		routeDomain.setDestinationAddress(routeDetailsModel.getDestination());
//		routeDomain.setSourceAddress(getAddressLocationId(routeDomain.getSourceAddress()));
//		routeDomain.setDestinationAddress(getAddressLocationId(routeDomain.getDestinationAddress()));
//		ObjectMapper mapper = new ObjectMapper();
//		routeDomain.setSource(mapper.writeValueAsString(routeDomain.getSourceAddress()));
//		routeDomain.setDestination(mapper.writeValueAsString(routeDomain.getDestinationAddress()));
		routeDao.updateRoute(routeDetailsDomain);
		RouteModel route = new RouteModel(routeDetailsDomain.getUuid());
		return route;
	}

	@Override
	public String deleteRoute(String routeId) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(), SeverityTypes.DEBUG.ordinal(),
				"deleteRoute in RouteServiceImpl ") + JsonUtil.toJsonString(routeId));
		getRouteDetailsById(routeId);
		return routeDao.deleteRoute(routeId);
	}

	@Override
	public String updateRouteStatus(String uuid, Map<String, String> reqParam) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(), SeverityTypes.DEBUG.ordinal(),
				" activateRoute in RouteServiceImpl ") + JsonUtil.toJsonString(uuid));
		boolean status = false;
		if (reqParam.size() > 0) {
			if (reqParam.containsKey("status")) {
				String isActive = reqParam.get("status");
				status = Boolean.parseBoolean(isActive);
			}
		}
		getRouteDetailsById(uuid);
		if (status)
			return routeDao.updateRouteStatus(uuid, true);
		else
			return routeDao.updateRouteStatus(uuid, false);
	}

	private List<Profile> getProfileOnAddress(List<Profile> routeFranchise, String city) throws Exception {
		System.out.println(city);
		List<Profile> profileList = new ArrayList<Profile>();
		CityDomain cityDomain = new CityDomain();
		try {
			cityDomain = cityDAO.getCityId(city);
		} catch (Exception e) {
			cityDomain = null;
		}
		if (null != cityDomain) {
			for (Profile profile : routeFranchise) {
				List<AddressModel> address = new ArrayList<AddressModel>();
				address = profile.getAddress();
				for (AddressModel dstaadr : address) {
					if (0 < dstaadr.getCityId())
						if (cityDomain.getId() == dstaadr.getCityId()) {
							profileList.add(profile);
						}
				}
			}
		}

		return profileList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ListDto getAllRouteBySourceAndDestination(String[] sCities, Map<String, String> reqParam) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(), SeverityTypes.DEBUG.ordinal(),
				" getAllRouteBySourceAndDestination in RouteServiceImpl ") + JsonUtil.toJsonString(sCities));

		int lowerBound = 0, upperBound = 0, totalSize = 0;
		Boolean status = null;
		String destination = null, allCities = null;
		String[] destCities = null;

		if (reqParam.size() > 0) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(),
					SeverityTypes.DEBUG.ordinal(), " getAllRouteBySourceAndDestination in RouteServiceImpl ")
					+ JsonUtil.toJsonString(reqParam));

			Map<String, Integer> paginationCount = CommonUtils.getPagination(reqParam);
			if (paginationCount.size() > 0) {
				lowerBound = paginationCount.get("lowerBound");
				upperBound = paginationCount.get("upperBound");
			}
			if (reqParam.containsKey("status")) {
				String isActive = reqParam.get("status");
				status = Boolean.parseBoolean(isActive);
			}
			if (reqParam.containsKey("destination")) {
				destination = reqParam.get("destination");
				destCities = destination.split(",");
			}
		}
		List<RouteDomain> routeSrcDstDomain = null;
		List<RouteModelDTO> routeModelDTO = new ArrayList<RouteModelDTO>();

		if (null != destCities) {
			if (0 < destCities.length) {
				allCities = CommonUtils.convertStringArrayToString(sCities, destCities);
				routeSrcDstDomain = new ArrayList<RouteDomain>();
				for (String srcCity : sCities) {
					CityDomain cityDomain = cityDAO.getCityId(srcCity);
					srcCity = String.valueOf(cityDomain.getId());
					for (String dstcity : destCities) {
						CityDomain dstCityDomain = cityDAO.getCityId(dstcity);
						dstcity = String.valueOf(dstCityDomain.getId());
						List<RouteDomain> routeDstDomain = new ArrayList<RouteDomain>();
						routeDstDomain = routeDao.getRouteBySourceAndDestination(status, srcCity, dstcity, lowerBound,
								upperBound);
						totalSize += routeDao.getCountRouteBySourceAndDestination(status, srcCity, dstcity).getTotal();
						routeSrcDstDomain.addAll(routeDstDomain);
					}
				}
			}
		} else {
			routeSrcDstDomain = new ArrayList<RouteDomain>();
			for (String srcCity : sCities) {
				CityDomain sourceCityDomain = cityDAO.getCityId(srcCity);
				srcCity = String.valueOf(sourceCityDomain.getId());
				List<RouteDomain> routeSrcDomain = routeDao.getRouteBySourceAndDestination(status, srcCity, null,
						lowerBound, upperBound);
				totalSize += routeDao.getCountRouteBySourceAndDestination(status, srcCity, null).getTotal();
				routeSrcDstDomain.addAll(routeSrcDomain);
			}
		}
//		AuthModel authModel = authService.getProfileDetails(allCities);
//		List<Profile> routeFranchise = new ArrayList<Profile>();
//		List<Profile> routeCoordinator = new ArrayList<Profile>();
//		if (!authModel.getFranchiseList().isEmpty()) {
//			routeFranchise = authModel.getFranchiseList();
//		}
//		if (!authModel.getCoordinatorList().isEmpty()) {
//			routeCoordinator = authModel.getCoordinatorList();
//		}

		for (RouteDomain route : routeSrcDstDomain) {
			RouteModelDTO routeDTO = new RouteModelDTO();
			BeanUtils.copyProperties(route, routeDTO);
			routeDTO = mapAddressToRouteModelDTO(routeDTO, route);
//			int taggedVehicles = routeTagDao.getAllRouteTagVehiclesVendors(route.getUuid(), true, 0).size();
			routeDTO.setTaggedVehiclesCount(routeTagDao.getAllRouteTagVehiclesVendors(route.getUuid(), true, 0).size());
//			int taggedVendors = routeTagDao
//					.getAllRouteTagVehiclesVendors(route.getUuid(), false, Integer.valueOf(Role.FRANCHISE.getCode()))
//					.size();
			routeDTO.setTaggedVendorsCount(routeTagDao
					.getAllRouteTagVehiclesVendors(route.getUuid(), false, Integer.valueOf(Role.FRANCHISE.getCode()))
					.size());
			routeModelDTO.add(routeDTO);
		}
		Map<String, RouteModelDTO> routeMap = new HashMap();
		for (RouteModelDTO route : routeModelDTO) {
			routeMap.put(route.getUuid(), route);
		}
		List<RouteModelDTO> list = new ArrayList<RouteModelDTO>(routeMap.values());
//		List<RouteModelDTO> routeDetailsList = new ArrayList<RouteModelDTO>();
//		for (RouteModelDTO routeDetails : list) {
//			RouteModelDTO route = new RouteModelDTO();
//			BeanUtils.copyProperties(routeDetails, route);
//			route.setSourceFranchiseDetails(getProfileOnAddress(routeFranchise, routeDetails.getSource().getCity()));
//			route.setDestinationFranchiseDetails(
//					getProfileOnAddress(routeFranchise, routeDetails.getDestination().getCity()));
//			route.setSourceCoordinatorDetails(
//					getProfileOnAddress(routeCoordinator, routeDetails.getSource().getCity()));
//			route.setDestinationCoordinatorDetails(
//					getProfileOnAddress(routeCoordinator, routeDetails.getDestination().getCity()));
//			routeDetailsList.add(route);
//		}
//		ListDto listDto = new ListDto();
//		listDto.setList(routeDetailsList);
		ListDto listDto = new ListDto();
		listDto.setList(list);
		listDto.setTotalSize(totalSize);
		return listDto;
	}

	private RouteModelDTO getRouteTag(RouteModelDTO routeModelDTO, String routeId, String srctagLocation,
			String dsttagLocation) throws Exception {
		List<RouteTagDetailsModel> srcRouteTagDetailsModel = new ArrayList<RouteTagDetailsModel>();
		List<RouteTagDetailsModel> dstRouteTagDetailsModel = new ArrayList<RouteTagDetailsModel>();
		List<RouteTagDomain> srcrouteTagDomain = routeTagDao.getAllRouteTag(routeId, null, srctagLocation, null);
//		srcRouteTagDetailsModel = fetchRouteTagDetails(srcrouteTagDomain, false);
		srcRouteTagDetailsModel = routeTagServiceImpl.fetchRouteTagDetails(srcrouteTagDomain, false, false, false);
		routeModelDTO.setSourceTaggedVendors(srcRouteTagDetailsModel);
		List<RouteTagDomain> dstRouteTagDomain = routeTagDao.getAllRouteTag(routeId, null, dsttagLocation, null);
		dstRouteTagDetailsModel = routeTagServiceImpl.fetchRouteTagDetails(dstRouteTagDomain, false, false, false);
//		dstRouteTagDetailsModel = fetchRouteTagDetails(dstRouteTagDomain, false);
		routeModelDTO.setTaggedVehiclesCount(routeTagDao.getAllRouteTagVehiclesVendors(routeId, true, 0).size());
		routeModelDTO.setTaggedVendorsCount(routeTagDao
				.getAllRouteTagVehiclesVendors(routeId, false, Integer.valueOf(Role.FRANCHISE.getCode())).size());
		routeModelDTO.setDestinationTaggedVendors(dstRouteTagDetailsModel);
		RouteModelDTO modelDTO = new RouteModelDTO(routeModelDTO.getUuid(), routeModelDTO.getSource(),
				routeModelDTO.getDestination(), routeModelDTO.getCreatedBy(), routeModelDTO.getSourceTaggedVendors(),
				routeModelDTO.getDestinationTaggedVendors(), routeModelDTO.getStatus(),
				routeModelDTO.getTaggedVendorsCount(), routeModelDTO.getTaggedVehiclesCount(),
				routeModelDTO.getRouteDashboard());

		return modelDTO;
	}

	@Override
	public RouteModelDTO getRouteById(String uuid) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ROUTE.name(), SeverityTypes.DEBUG.ordinal(),
				" getRouteById in RouteServiceImpl ") + JsonUtil.toJsonString(uuid));
		RouteDomain routeDomain = routeDao.getRouteById(uuid);
		if (routeDomain == null)
			throw new NOT_FOUND("Route not found");
		RouteModelDTO routeModelDTO = new RouteModelDTO();
		BeanUtils.copyProperties(routeDomain, routeModelDTO);
		routeModelDTO = mapAddressToRouteModelDTO(routeModelDTO, routeDomain);
		return routeModelDTO;
	}


}
