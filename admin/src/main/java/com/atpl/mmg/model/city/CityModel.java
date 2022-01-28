package com.atpl.mmg.model.city;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class CityModel {

	private String name;
	private int id;
	private int state_id;
	private String shortName;
	private List<String> alias;
	private String pinCode;
	private String isGovtPreferred;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getState_id() {
		return state_id;
	}

	public void setState_id(int state_id) {
		this.state_id = state_id;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public List<String> getAlias() {
		return alias;
	}

	public void setAlias(List<String> alias) {
		this.alias = alias;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPincode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getIsGovtPreferred() {
		return isGovtPreferred;
	}

	public void setIsGovtPreferred(String isGovtPreferred) {
		this.isGovtPreferred = isGovtPreferred;
	}

}
