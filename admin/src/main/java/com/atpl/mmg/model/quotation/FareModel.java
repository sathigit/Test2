package com.atpl.mmg.model.quotation;

public class FareModel {
	private double fromlatitude;
	private double fromlongitude;
	private String village;
	private String city;
	private String state;
	private String country;
	private int pincode;
	public double getFromlatitude() {
		return fromlatitude;
	}
	public void setFromlatitude(double fromlatitude) {
		this.fromlatitude = fromlatitude;
	}
	public double getFromlongitude() {
		return fromlongitude;
	}
	public void setFromlongitude(double fromlongitude) {
		this.fromlongitude = fromlongitude;
	}
	public String getVillage() {
		return village;
	}
	public void setVillage(String village) {
		this.village = village;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
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
	public int getPincode() {
		return pincode;
	}
	public void setPincode(int pincode) {
		this.pincode = pincode;
	}
	
}
