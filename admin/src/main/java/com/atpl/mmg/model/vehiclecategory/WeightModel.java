package com.atpl.mmg.model.vehiclecategory;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class WeightModel implements Serializable {

	public List<Integer> weightId;
	
	
	public List<Integer> getWeightId() {
		return weightId;
	}

	public void setWeightId(List<Integer> weightId) {
		this.weightId = weightId;
	}

	
}