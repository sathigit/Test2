package com.atpl.mmg.model.vehicle;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class VehicleModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2196266153406082047L;
	public Integer vehicleCategoryId;
	public String category;
	public String goodsTypeId;
	public String kerbWeightId;
	public Integer getVehicleCategoryId() {
		return vehicleCategoryId;
	}
	public void setVehicleCategoryId(Integer vehicleCategoryId) {
		this.vehicleCategoryId = vehicleCategoryId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
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
	
	
}