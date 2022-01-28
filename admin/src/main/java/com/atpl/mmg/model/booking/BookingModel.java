package com.atpl.mmg.model.booking;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.atpl.mmg.domain.bookedItems.BookedItemsDomain;

public class BookingModel {
	private BigInteger id;
	private String bookingType;
	private String referenceId;
	private String vendorType;
	private BigInteger frequentEnterpriseId;
	private String profileId;
	private double sourcelatitude;
	private double sourcelongitude;
	private String sStreet;
	private String sCity;
	private String sState;
	private String sCountry;
	private String sLandMark;
	private int sPincode;
	private double destinationlatitude;
	private double destinationlongitude;
	private String dStreet;
	private String dCity;
	private String dState;
	private String dCountry;
	private String dLandMark;
	private int dPincode;
	private double distance;
	private int goodsWeightId;
	private int goodsTypeId;
	private String vehicleCategoryId;
	private double perKm;
	private double baseFare;
	private String vehicleId;
	private String driverId;
	private int numberofLabours;
	private double goodsvalue;
	private Date pickUpDateTime;
	private String consignorName;
	private String consignorNumber;
	private String consigneeName;
	private String consigneeNumber;
	private String consignorGST;
	private String consigneeGST;
	private String consignorPAN;
	private String consigneePAN;
	private int bookedGoodsTypes;
	private double bookingAmount;
	private double bookingFee;
	private Double labourCharges;
	private double waitingcharge;
	private double CGST;
	private double SGST;
	private double totalAmount;
	private int advCompType;
	private double advancePayment;
	private boolean paid;
	private int paymentMode;
	private String paymentSource;
	private String bookedSource;
	private int bookingAmtOverride;
	private String amtReceivedByDriver;
	private Date amtReceivedPaiddate;
	private int readBooking;
	private Date creationDate;
	private Date modifiedDate;
	private String status;
	private boolean isActive;
	private int cancelledByCustomer;
	private int cancelledByDriver;
	private String bookedBy;
	private String workOrderId;
	private String fieldOfficerId;

	private String franchiseContactNumber;
	private String driverContactNumber;
	private String customerName;
	private String contactNumber;
	private String emailId;
	private String address;
	private String goodsType;
	private String goodsWeight;

	private List<BookedItemsDomain> bookedItems;

	private String source;
	private String destination;

	public BookingModel() {

	}

	public BookingModel(String source, String destination) {
		this.source = source;
		this.destination = destination;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getBookingType() {
		return bookingType;
	}

	public void setBookingType(String bookingType) {
		this.bookingType = bookingType;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getVendorType() {
		return vendorType;
	}

	public void setVendorType(String vendorType) {
		this.vendorType = vendorType;
	}

	public BigInteger getFrequentEnterpriseId() {
		return frequentEnterpriseId;
	}

	public void setFrequentEnterpriseId(BigInteger frequentEnterpriseId) {
		this.frequentEnterpriseId = frequentEnterpriseId;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public double getSourcelatitude() {
		return sourcelatitude;
	}

	public void setSourcelatitude(double sourcelatitude) {
		this.sourcelatitude = sourcelatitude;
	}

	public double getSourcelongitude() {
		return sourcelongitude;
	}

	public void setSourcelongitude(double sourcelongitude) {
		this.sourcelongitude = sourcelongitude;
	}

	public String getsStreet() {
		return sStreet;
	}

	public void setsStreet(String sStreet) {
		this.sStreet = sStreet;
	}

	public String getsCity() {
		return sCity;
	}

	public void setsCity(String sCity) {
		this.sCity = sCity;
	}

	public String getsState() {
		return sState;
	}

	public void setsState(String sState) {
		this.sState = sState;
	}

	public String getsCountry() {
		return sCountry;
	}

	public void setsCountry(String sCountry) {
		this.sCountry = sCountry;
	}

	public String getsLandMark() {
		return sLandMark;
	}

	public void setsLandMark(String sLandMark) {
		this.sLandMark = sLandMark;
	}

	public int getsPincode() {
		return sPincode;
	}

	public void setsPincode(int sPincode) {
		this.sPincode = sPincode;
	}

	public double getDestinationlatitude() {
		return destinationlatitude;
	}

	public void setDestinationlatitude(double destinationlatitude) {
		this.destinationlatitude = destinationlatitude;
	}

	public double getDestinationlongitude() {
		return destinationlongitude;
	}

	public void setDestinationlongitude(double destinationlongitude) {
		this.destinationlongitude = destinationlongitude;
	}

	public String getdStreet() {
		return dStreet;
	}

	public void setdStreet(String dStreet) {
		this.dStreet = dStreet;
	}

	public String getdCity() {
		return dCity;
	}

	public void setdCity(String dCity) {
		this.dCity = dCity;
	}

	public String getdState() {
		return dState;
	}

	public void setdState(String dState) {
		this.dState = dState;
	}

	public String getdCountry() {
		return dCountry;
	}

	public void setdCountry(String dCountry) {
		this.dCountry = dCountry;
	}

	public String getdLandMark() {
		return dLandMark;
	}

	public void setdLandMark(String dLandMark) {
		this.dLandMark = dLandMark;
	}

	public int getdPincode() {
		return dPincode;
	}

	public void setdPincode(int dPincode) {
		this.dPincode = dPincode;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getGoodsWeightId() {
		return goodsWeightId;
	}

	public void setGoodsWeightId(int goodsWeightId) {
		this.goodsWeightId = goodsWeightId;
	}

	public int getGoodsTypeId() {
		return goodsTypeId;
	}

	public void setGoodsTypeId(int goodsTypeId) {
		this.goodsTypeId = goodsTypeId;
	}

	public String getVehicleCategoryId() {
		return vehicleCategoryId;
	}

	public void setVehicleCategoryId(String vehicleCategoryId) {
		this.vehicleCategoryId = vehicleCategoryId;
	}

	public double getPerKm() {
		return perKm;
	}

	public void setPerKm(double perKm) {
		this.perKm = perKm;
	}

	public double getBaseFare() {
		return baseFare;
	}

	public void setBaseFare(double baseFare) {
		this.baseFare = baseFare;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	public int getNumberofLabours() {
		return numberofLabours;
	}

	public void setNumberofLabours(int numberofLabours) {
		this.numberofLabours = numberofLabours;
	}

	public double getGoodsvalue() {
		return goodsvalue;
	}

	public void setGoodsvalue(double goodsvalue) {
		this.goodsvalue = goodsvalue;
	}

	public Date getPickUpDateTime() {
		return pickUpDateTime;
	}

	public void setPickUpDateTime(Date pickUpDateTime) {
		this.pickUpDateTime = pickUpDateTime;
	}

	public String getConsignorName() {
		return consignorName;
	}

	public void setConsignorName(String consignorName) {
		this.consignorName = consignorName;
	}

	public String getConsignorNumber() {
		return consignorNumber;
	}

	public void setConsignorNumber(String consignorNumber) {
		this.consignorNumber = consignorNumber;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneeNumber() {
		return consigneeNumber;
	}

	public void setConsigneeNumber(String consigneeNumber) {
		this.consigneeNumber = consigneeNumber;
	}

	public String getConsignorGST() {
		return consignorGST;
	}

	public void setConsignorGST(String consignorGST) {
		this.consignorGST = consignorGST;
	}

	public String getConsigneeGST() {
		return consigneeGST;
	}

	public void setConsigneeGST(String consigneeGST) {
		this.consigneeGST = consigneeGST;
	}

	public String getConsignorPAN() {
		return consignorPAN;
	}

	public void setConsignorPAN(String consignorPAN) {
		this.consignorPAN = consignorPAN;
	}

	public String getConsigneePAN() {
		return consigneePAN;
	}

	public void setConsigneePAN(String consigneePAN) {
		this.consigneePAN = consigneePAN;
	}

	public int getBookedGoodsTypes() {
		return bookedGoodsTypes;
	}

	public void setBookedGoodsTypes(int bookedGoodsTypes) {
		this.bookedGoodsTypes = bookedGoodsTypes;
	}

	public double getBookingAmount() {
		return bookingAmount;
	}

	public void setBookingAmount(double bookingAmount) {
		this.bookingAmount = bookingAmount;
	}

	public double getBookingFee() {
		return bookingFee;
	}

	public void setBookingFee(double bookingFee) {
		this.bookingFee = bookingFee;
	}

	public Double getLabourCharges() {
		return labourCharges;
	}

	public void setLabourCharges(Double labourCharges) {
		this.labourCharges = labourCharges;
	}

	public double getWaitingcharge() {
		return waitingcharge;
	}

	public void setWaitingcharge(double waitingcharge) {
		this.waitingcharge = waitingcharge;
	}

	public double getCGST() {
		return CGST;
	}

	public void setCGST(double cGST) {
		CGST = cGST;
	}

	public double getSGST() {
		return SGST;
	}

	public void setSGST(double sGST) {
		SGST = sGST;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getAdvCompType() {
		return advCompType;
	}

	public void setAdvCompType(int advCompType) {
		this.advCompType = advCompType;
	}

	public double getAdvancePayment() {
		return advancePayment;
	}

	public void setAdvancePayment(double advancePayment) {
		this.advancePayment = advancePayment;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public int getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(int paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getPaymentSource() {
		return paymentSource;
	}

	public void setPaymentSource(String paymentSource) {
		this.paymentSource = paymentSource;
	}

	public String getBookedSource() {
		return bookedSource;
	}

	public void setBookedSource(String bookedSource) {
		this.bookedSource = bookedSource;
	}

	public int getBookingAmtOverride() {
		return bookingAmtOverride;
	}

	public void setBookingAmtOverride(int bookingAmtOverride) {
		this.bookingAmtOverride = bookingAmtOverride;
	}

	public String getAmtReceivedByDriver() {
		return amtReceivedByDriver;
	}

	public void setAmtReceivedByDriver(String amtReceivedByDriver) {
		this.amtReceivedByDriver = amtReceivedByDriver;
	}

	public Date getAmtReceivedPaiddate() {
		return amtReceivedPaiddate;
	}

	public void setAmtReceivedPaiddate(Date amtReceivedPaiddate) {
		this.amtReceivedPaiddate = amtReceivedPaiddate;
	}

	public int getReadBooking() {
		return readBooking;
	}

	public void setReadBooking(int readBooking) {
		this.readBooking = readBooking;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public int getCancelledByCustomer() {
		return cancelledByCustomer;
	}

	public void setCancelledByCustomer(int cancelledByCustomer) {
		this.cancelledByCustomer = cancelledByCustomer;
	}

	public int getCancelledByDriver() {
		return cancelledByDriver;
	}

	public void setCancelledByDriver(int cancelledByDriver) {
		this.cancelledByDriver = cancelledByDriver;
	}

	public String getBookedBy() {
		return bookedBy;
	}

	public void setBookedBy(String bookedBy) {
		this.bookedBy = bookedBy;
	}

	public String getWorkOrderId() {
		return workOrderId;
	}

	public void setWorkOrderId(String workOrderId) {
		this.workOrderId = workOrderId;
	}

	public String getFieldOfficerId() {
		return fieldOfficerId;
	}

	public void setFieldOfficerId(String fieldOfficerId) {
		this.fieldOfficerId = fieldOfficerId;
	}

	public String getFranchiseContactNumber() {
		return franchiseContactNumber;
	}

	public void setFranchiseContactNumber(String franchiseContactNumber) {
		this.franchiseContactNumber = franchiseContactNumber;
	}

	public String getDriverContactNumber() {
		return driverContactNumber;
	}

	public void setDriverContactNumber(String driverContactNumber) {
		this.driverContactNumber = driverContactNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getGoodsWeight() {
		return goodsWeight;
	}

	public void setGoodsWeight(String goodsWeight) {
		this.goodsWeight = goodsWeight;
	}

	public List<BookedItemsDomain> getBookedItems() {
		return bookedItems;
	}

	public void setBookedItems(List<BookedItemsDomain> bookedItems) {
		this.bookedItems = bookedItems;
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

}
