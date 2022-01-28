package com.atpl.mmg.model.admin;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.atpl.mmg.model.image.VehicleImageModel;
import com.atpl.mmg.model.route.RouteModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class VehicleDetailsModel {

	private static final long serialVersionUID = -2196266153406082047L;

	private String vehicleId;
	private String driverId;
	private String registrationNumber;
	private Date registrationDate;
	private String ownerName;
	private BigInteger modelId;
	private String insuranceNumber;
	private Date insuranceExpiryDate;
	private String emissionPuccNumber;
	private Date emissionExpiryDate;
	private String modelName;
	private String franchiseId;
	private String vehicleTypeId;
	private Integer vehicleCategoryId;
	private String id;
	private String category;
	private String vehicleName;
	private String type;
	private String status;
	private String emissionDate;
	private String bookingId;
	private String goodsTypeId;
	private String kerbWeightId;
	private String path;
	private String name;
	private String imageId;
	private String kerbWeight;
	private String goodsType;
	private String firstName;
	private String lastName;
	private String driverName;
	private String franchiseeName;
	private String franchiseeCity;
	private int countryId;
	private int stateId;
	private int cityId;
	private String stateName;
	private String cityName;
	private Date createdDate;
	private Date modifiedDate;
	private Integer count;
	private String action;
	private String refferenceCode;
	private String vendorType;
	private String requestedBy;

	private String userId;
	private int userRoleId;
	private String reason;
	private String vehicleImagePath;
	private List<VehicleImageModel> vehicleImageModel;
	private boolean isRegistered;
	private boolean isMigrate;

	private boolean isTag;
	private String routeId;
	private List<RouteModel> routeDetails;

	public VehicleDetailsModel(String vehicleId, String registrationNumber, String ownerName, String category) {
		super();
		this.vehicleId = vehicleId;
		this.registrationNumber = registrationNumber;
		this.ownerName = ownerName;
		this.category = category;
	}

	public VehicleDetailsModel(String vehicleId, String driverId, String registrationNumber, Date registrationDate,
			String ownerName, String insuranceNumber, Date insuranceExpiryDate, String emissionPuccNumber,
			Date emissionExpiryDate, String franchiseId, Integer vehicleCategoryId, String status, int countryId,
			int stateId, int cityId, Date createdDate, Date modifiedDate, String refferenceCode, String requestedBy,
			boolean isMigrate) {
		this.vehicleId = vehicleId;
		this.driverId = driverId;
		this.registrationNumber = registrationNumber;
		this.registrationDate = registrationDate;
		this.ownerName = ownerName;
		this.insuranceNumber = insuranceNumber;
		this.insuranceExpiryDate = insuranceExpiryDate;
		this.emissionPuccNumber = emissionPuccNumber;
		this.emissionExpiryDate = emissionExpiryDate;
		this.franchiseId = franchiseId;
		this.vehicleCategoryId = vehicleCategoryId;
		this.status = status;
		this.countryId = countryId;
		this.stateId = stateId;
		this.cityId = cityId;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.refferenceCode = refferenceCode;
		this.requestedBy = requestedBy;
		this.isMigrate = isMigrate;
	}

	public VehicleDetailsModel(String vehicleId, String driverId, String registrationNumber, Date registrationDate,
			String ownerName, String insuranceNumber, Date insuranceExpiryDate, String emissionPuccNumber,
			Date emissionExpiryDate, String franchiseId, Integer vehicleCategoryId, String status, int countryId,
			int stateId, int cityId, Date createdDate, Date modifiedDate, String refferenceCode, String requestedBy) {
		this.vehicleId = vehicleId;
		this.driverId = driverId;
		this.registrationNumber = registrationNumber;
		this.registrationDate = registrationDate;
		this.ownerName = ownerName;
		this.insuranceNumber = insuranceNumber;
		this.insuranceExpiryDate = insuranceExpiryDate;
		this.emissionPuccNumber = emissionPuccNumber;
		this.emissionExpiryDate = emissionExpiryDate;
		this.franchiseId = franchiseId;
		this.vehicleCategoryId = vehicleCategoryId;
		this.status = status;
		this.countryId = countryId;
		this.stateId = stateId;
		this.cityId = cityId;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.refferenceCode = refferenceCode;
		this.requestedBy = requestedBy;
	}

	public VehicleDetailsModel(String driverId, String firstName) {
		this.driverId = driverId;
		this.firstName = firstName;
	}

	public VehicleDetailsModel() {
	}

	public VehicleDetailsModel(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getFranchiseeName() {
		return franchiseeName;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public void setFranchiseeName(String franchiseeName) {
		this.franchiseeName = franchiseeName;
	}

	public String getFranchiseeCity() {
		return franchiseeCity;
	}

	public void setFranchiseeCity(String franchiseeCity) {
		this.franchiseeCity = franchiseeCity;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmissionDate() {
		return emissionDate;
	}

	public void setEmissionDate(String emissionDate) {
		this.emissionDate = emissionDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public String getVehicleTypeId() {
		return vehicleTypeId;
	}

	public void setVehicleTypeId(String vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}

	public Integer getVehicleCategoryId() {
		return vehicleCategoryId;
	}

	public void setVehicleCategoryId(Integer vehicleCategoryId) {
		this.vehicleCategoryId = vehicleCategoryId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFranchiseId() {
		return franchiseId;
	}

	public void setFranchiseId(String franchiseId) {
		this.franchiseId = franchiseId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public BigInteger getModelId() {
		return modelId;
	}

	public void setModelId(BigInteger modelId) {
		this.modelId = modelId;
	}

	public String getInsuranceNumber() {
		return insuranceNumber;
	}

	public void setInsuranceNumber(String insuranceNumber) {
		this.insuranceNumber = insuranceNumber;
	}

	public String getEmissionPuccNumber() {
		return emissionPuccNumber;
	}

	public void setEmissionPuccNumber(String emissionPuccNumber) {
		this.emissionPuccNumber = emissionPuccNumber;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public String getGoodsTypeId() {
		return goodsTypeId;
	}

	public void setGoodsTypeId(String goodsTypeId) {
		this.goodsTypeId = goodsTypeId;
	}

	public String getKerbWeightId() {
		return kerbWeightId;
	}

	public void setKerbWeightId(String kerbWeightId) {
		this.kerbWeightId = kerbWeightId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getKerbWeight() {
		return kerbWeight;
	}

	public void setKerbWeight(String kerbWeight) {
		this.kerbWeight = kerbWeight;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getRefferenceCode() {
		return refferenceCode;
	}

	public void setRefferenceCode(String refferenceCode) {
		this.refferenceCode = refferenceCode;
	}

	public String getVendorType() {
		return vendorType;
	}

	public void setVendorType(String vendorType) {
		this.vendorType = vendorType;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Date getInsuranceExpiryDate() {
		return insuranceExpiryDate;
	}

	public void setInsuranceExpiryDate(Date insuranceExpiryDate) {
		this.insuranceExpiryDate = insuranceExpiryDate;
	}

	public Date getEmissionExpiryDate() {
		return emissionExpiryDate;
	}

	public void setEmissionExpiryDate(Date emissionExpiryDate) {
		this.emissionExpiryDate = emissionExpiryDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(int userRoleId) {
		this.userRoleId = userRoleId;
	}

	public List<VehicleImageModel> getVehicleImageModel() {
		return vehicleImageModel;
	}

	public void setVehicleImageModel(List<VehicleImageModel> vehicleImageModel) {
		this.vehicleImageModel = vehicleImageModel;
	}

	public String getVehicleImagePath() {
		return vehicleImagePath;
	}

	public void setVehicleImagePath(String vehicleImagePath) {
		this.vehicleImagePath = vehicleImagePath;
	}

	public boolean isIsRegistered() {
		return isRegistered;
	}

	public void setIsRegistered(boolean isRegistered) {
		this.isRegistered = isRegistered;
	}

	public boolean isIsMigrate() {
		return isMigrate;
	}

	public void setIsMigrate(boolean isMigrate) {
		this.isMigrate = isMigrate;
	}

	public boolean isIsTag() {
		return isTag;
	}

	public void setIsTag(boolean isTag) {
		this.isTag = isTag;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public List<RouteModel> getRouteDetails() {
		return routeDetails;
	}

	public void setRouteDetails(List<RouteModel> routeDetails) {
		this.routeDetails = routeDetails;
	}

}
