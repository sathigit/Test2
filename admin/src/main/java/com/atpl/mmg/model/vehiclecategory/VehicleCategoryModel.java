package com.atpl.mmg.model.vehiclecategory;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleCategoryModel implements Serializable {

	private static final long serialVersionUID = -2196266153406082047L;

	public int vehicleCategoryId;
	public String category;
	private List<Integer> goodsTypeId;
	private int weightId;
	public double pricePerKm;
	public double pricePerMin;
	public double baseFare;
	public String commission;
	public double vehicleEngineCapacity;
	public int vehicleSpeed;
	public int fuelTank;
	public int grossVehicleWeight;
	public int weight;
	public int overallLength;
	public int loadBodyLength;
	public double waitingTimeCharge;
	public double minimumBookingKm;
	private double nightFee;
	
	private String name;
	private String path;
	private String imageId;

	public int getVehicleCategoryId() {
		return vehicleCategoryId;
	}

	public void setVehicleCategoryId(int vehicleCategoryId) {
		this.vehicleCategoryId = vehicleCategoryId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<Integer> getGoodsTypeId() {
		return goodsTypeId;
	}

	public void setGoodsTypeId(List<Integer> goodsTypeId) {
		this.goodsTypeId = goodsTypeId;
	}

	public int getWeightId() {
		return weightId;
	}

	public void setWeightId(int weightId) {
		this.weightId = weightId;
	}

	public double getPricePerKm() {
		return pricePerKm;
	}

	public void setPricePerKm(double pricePerKm) {
		this.pricePerKm = pricePerKm;
	}

	public double getPricePerMin() {
		return pricePerMin;
	}

	public void setPricePerMin(double pricePerMin) {
		this.pricePerMin = pricePerMin;
	}

	public double getBaseFare() {
		return baseFare;
	}

	public void setBaseFare(double baseFare) {
		this.baseFare = baseFare;
	}

	public String getCommission() {
		return commission;
	}

	public void setCommission(String commission) {
		this.commission = commission;
	}

	public double getVehicleEngineCapacity() {
		return vehicleEngineCapacity;
	}

	public void setVehicleEngineCapacity(double vehicleEngineCapacity) {
		this.vehicleEngineCapacity = vehicleEngineCapacity;
	}

	public int getVehicleSpeed() {
		return vehicleSpeed;
	}

	public void setVehicleSpeed(int vehicleSpeed) {
		this.vehicleSpeed = vehicleSpeed;
	}

	public int getFuelTank() {
		return fuelTank;
	}

	public void setFuelTank(int fuelTank) {
		this.fuelTank = fuelTank;
	}

	public int getGrossVehicleWeight() {
		return grossVehicleWeight;
	}

	public void setGrossVehicleWeight(int grossVehicleWeight) {
		this.grossVehicleWeight = grossVehicleWeight;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getOverallLength() {
		return overallLength;
	}

	public void setOverallLength(int overallLength) {
		this.overallLength = overallLength;
	}

	public int getLoadBodyLength() {
		return loadBodyLength;
	}

	public void setLoadBodyLength(int loadBodyLength) {
		this.loadBodyLength = loadBodyLength;
	}

	public double getWaitingTimeCharge() {
		return waitingTimeCharge;
	}

	public void setWaitingTimeCharge(double waitingTimeCharge) {
		this.waitingTimeCharge = waitingTimeCharge;
	}

	public double getMinimumBookingKm() {
		return minimumBookingKm;
	}

	public void setMinimumBookingKm(double minimumBookingKm) {
		this.minimumBookingKm = minimumBookingKm;
	}

	public double getNightFee() {
		return nightFee;
	}

	public void setNightFee(double nightFee) {
		this.nightFee = nightFee;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

}