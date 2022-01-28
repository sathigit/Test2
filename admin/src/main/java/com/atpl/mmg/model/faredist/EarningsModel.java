package com.atpl.mmg.model.faredist;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.atpl.mmg.model.profile.ProfileModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class EarningsModel {

	private BigInteger bookingId;
	private Date bookingDate;
	private double totalAmount;
	private Double totalEarnings;
	private String role;
	private String source;
	private String destination;
	private List<EarningsModel> earnings;
	private List<EarningWithTypeModel> type;
	private double earning;
	private String name;
	private String driverId;
	private String franchiseId;
	private String bookingsDate;
	private ProfileModel profile;

	public EarningsModel() {

	}

	public EarningsModel(Double totalEarnings, String role, ProfileModel profile) {
		this.totalEarnings = totalEarnings;
		this.role = role;
		this.profile = profile;
	}

	public EarningsModel(BigInteger bookingId, Date bookingDate, double totalAmount, String source,
			String destination) {
		this.bookingId = bookingId;
		this.bookingDate = bookingDate;
		this.totalAmount = totalAmount;
		this.source = source;
		this.destination = destination;
	}

	public EarningsModel(BigInteger bookingId, String bookingsDate, double totalAmount, String source,
			String destination, double earning, String name, String driverId, String franchiseId) {
		this.bookingId = bookingId;
		this.setBookingsDate(bookingsDate);
		this.totalAmount = totalAmount;
		this.source = source;
		this.destination = destination;
		this.earning = earning;
		this.name = name;
		this.driverId = driverId;
		this.franchiseId = franchiseId;
	}

	public BigInteger getBookingId() {
		return bookingId;
	}

	public void setBookingId(BigInteger bookingId) {
		this.bookingId = bookingId;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public Double getTotalEarnings() {
		return totalEarnings;
	}

	public void setTotalEarnings(Double totalEarnings) {
		this.totalEarnings = totalEarnings;
	}

	public List<EarningsModel> getEarnings() {
		return earnings;
	}

	public void setEarnings(List<EarningsModel> earnings) {
		this.earnings = earnings;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<EarningWithTypeModel> getType() {
		return type;
	}

	public void setType(List<EarningWithTypeModel> type) {
		this.type = type;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public double getEarning() {
		return earning;
	}

	public void setEarning(double earning) {
		this.earning = earning;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	public String getFranchiseId() {
		return franchiseId;
	}

	public void setFranchiseId(String franchiseId) {
		this.franchiseId = franchiseId;
	}

	public String getBookingsDate() {
		return bookingsDate;
	}

	public void setBookingsDate(String bookingsDate) {
		this.bookingsDate = bookingsDate;
	}

	public ProfileModel getProfile() {
		return profile;
	}

	public void setProfile(ProfileModel profile) {
		this.profile = profile;
	}

}
