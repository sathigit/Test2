package com.atpl.mmg.model.faredist;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class EarningWithTypeModel {
	private double earning;
	private String name;

	public EarningWithTypeModel() {

	}

	public EarningWithTypeModel(double earning, String name) {
		this.earning = earning;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getEarning() {
		return earning;
	}

	public void setEarning(double earning) {
		this.earning = earning;
	}

}
