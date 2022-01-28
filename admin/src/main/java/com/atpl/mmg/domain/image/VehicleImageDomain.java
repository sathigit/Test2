package com.atpl.mmg.domain.image;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class VehicleImageDomain implements Serializable {

	private static final long serialVersionUID = -5414810538248433964L;

	private String imageId;
	private String vehicleId;
	private int vehicleCategoryId;
	private Integer vehicleTypeId;
	private String name;
	private String type;
	private long size;
	private String path;
	private String category;
	public Double pricePerKm;
	public Double pricePerMin;
	public Double baseFare;
	public String commission;
	public String capacity;
	public Integer vehicleSpeed;
	public String fuelTank;
	public String grossVehicleWeight;
	public String kerbWeight;
	public String overallLength;
	public String loadBodyLength;
	
	public String goodsTypeId;
	public String kerbWeightId;
	public int id;
	public int cityId;
	
	private String franchiseId;
	

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getFranchiseId() {
		return franchiseId;
	}

	public void setFranchiseId(String franchiseId) {
		this.franchiseId = franchiseId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Double getPricePerKm() {
		return pricePerKm;
	}

	public void setPricePerKm(Double pricePerKm) {
		this.pricePerKm = pricePerKm;
	}

	public Double getPricePerMin() {
		return pricePerMin;
	}

	public void setPricePerMin(Double pricePerMin) {
		this.pricePerMin = pricePerMin;
	}

	public Double getBaseFare() {
		return baseFare;
	}

	public void setBaseFare(Double baseFare) {
		this.baseFare = baseFare;
	}

	public String getCommission() {
		return commission;
	}

	public void setCommission(String commission) {
		this.commission = commission;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public Integer getVehicleSpeed() {
		return vehicleSpeed;
	}

	public void setVehicleSpeed(Integer vehicleSpeed) {
		this.vehicleSpeed = vehicleSpeed;
	}

	public String getFuelTank() {
		return fuelTank;
	}

	public void setFuelTank(String fuelTank) {
		this.fuelTank = fuelTank;
	}

	public String getGrossVehicleWeight() {
		return grossVehicleWeight;
	}

	public void setGrossVehicleWeight(String grossVehicleWeight) {
		this.grossVehicleWeight = grossVehicleWeight;
	}

	public String getKerbWeight() {
		return kerbWeight;
	}

	public void setKerbWeight(String kerbWeight) {
		this.kerbWeight = kerbWeight;
	}

	public String getOverallLength() {
		return overallLength;
	}

	public void setOverallLength(String overallLength) {
		this.overallLength = overallLength;
	}

	public String getLoadBodyLength() {
		return loadBodyLength;
	}

	public void setLoadBodyLength(String loadBodyLength) {
		this.loadBodyLength = loadBodyLength;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public int getVehicleCategoryId() {
		return vehicleCategoryId;
	}

	public void setVehicleCategoryId(int vehicleCategoryId) {
		this.vehicleCategoryId = vehicleCategoryId;
	}

	public Integer getVehicleTypeId() {
		return vehicleTypeId;
	}

	public void setVehicleTypeId(Integer vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
