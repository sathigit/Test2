package com.atpl.mmg.domain.weight;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class WeightDomain implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2796685222522488868L;

	public Integer goodsId;
	public String weight;
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}

	
}
