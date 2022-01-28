package com.atpl.mmg.model.route;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Address implements Serializable {

	private static final long serialVersionUID = -2196266153406082047L;

	private String address1;
	private String address2;
	private String landmark;
	private int cityId;
	private String city;
	private String state;
	private String country;
	private String pinCode;
	private Double latitude;
	private Double longitude;

	@Override
	public String toString() {
		return "Address [address1=" + address1 + ", address2=" + address2 + ", landmark=" + landmark + ", city=" + city
				+ ", state=" + state + ", country=" + country + ", pinCode=" + pinCode + ", latitude=" + latitude
				+ ", longitude=" + longitude + "]";
	}

	public Address() {
		super();
	}

	public Address(String address1, String address2, String landmark, String city, String state, String country,
			String pinCode, Double latitude, Double longitude) {
		super();
		this.address1 = address1;
		this.address2 = address2;
		this.landmark = landmark;
		this.city = city;
		this.state = state;
		this.country = country;
		this.pinCode = pinCode;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Address(int cityId,String city, String state, String country) {
		super();
		this.cityId = cityId;
		this.city = city;
		this.state = state;
		this.country = country;
	}
	
	public Address(String city, String state, String country) {
		super();
		this.city = city;
		this.state = state;
		this.country = country;
	}

	public Address(String city) {
		super();
		this.city = city;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	
	

}
