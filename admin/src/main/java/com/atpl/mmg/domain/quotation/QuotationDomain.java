package com.atpl.mmg.domain.quotation;

import java.math.BigInteger;
import java.util.List;

import com.atpl.mmg.domain.bookedItems.BookedItemsDomain;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class QuotationDomain {
private static final long serialVersionUID = -2196266153406082047L;
private String quotationId;
private String source;
private String sStreet;
private String sCity;
private String sState;
private String sCountry;
private String sLandMark;
private String destination;
private String dStreet;
private String dCity;
private String dState;
private String dCountry;
private String dLandMark;
private Double sourcelatitude;
private Double sourcelongitude;
private Double destinationlatitude;
private Double destinationlongitude;
private int kerbWeightId;
private int goodsTypeId;
private double totalAmount;
private String vehicleCategoryId;
private String status;
private String emailId;
private String customerId;
private String pickUpDate;
private String pickUpTime;
private String customerName;
private BigInteger contactNumber;
private String address;
private String goodsType;
private String goodsWeight;
private int total;


private String consignorName;
private BigInteger consignorNumber;
private String consigneeName;
private BigInteger consigneeNumber;
private String consignorGST;
private String consigneeGST;
private String consignorPAN;
private String consigneePAN;
private List<BookedItemsDomain> bookedItems;
private int bookedGoodsTypes;
private int sPincode;
private int dPincode;

public int getsPincode() {
	return sPincode;
}
public void setsPincode(int sPincode) {
	this.sPincode = sPincode;
}
public int getdPincode() {
	return dPincode;
}
public void setdPincode(int dPincode) {
	this.dPincode = dPincode;
}
public QuotationDomain() {

}
public QuotationDomain(String quotationId, String source, String destination, int kerbWeightId,
		int goodsTypeId, Double totalAmount, String vehicleCategoryId, String status, String emailId, String customerId,
		String pickUpDate, String customerName, BigInteger contactNumber) {
	this.quotationId = quotationId;
	this.source = source;
	this.destination = destination;
	this.kerbWeightId = kerbWeightId;
	this.goodsTypeId = goodsTypeId;
	this.totalAmount = totalAmount;
	this.vehicleCategoryId = vehicleCategoryId;
	this.status = status;
	this.emailId = emailId;
	this.customerId = customerId;
	this.pickUpDate = pickUpDate;
	this.customerName = customerName;
	this.contactNumber = contactNumber;
}



public String getConsignorName() {
	return consignorName;
}
public void setConsignorName(String consignorName) {
	this.consignorName = consignorName;
}
public BigInteger getConsignorNumber() {
	return consignorNumber;
}
public void setConsignorNumber(BigInteger consignorNumber) {
	this.consignorNumber = consignorNumber;
}
public String getConsigneeName() {
	return consigneeName;
}
public void setConsigneeName(String consigneeName) {
	this.consigneeName = consigneeName;
}
public BigInteger getConsigneeNumber() {
	return consigneeNumber;
}
public void setConsigneeNumber(BigInteger consigneeNumber) {
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
public List<BookedItemsDomain> getBookedItems() {
	return bookedItems;
}
public void setBookedItems(List<BookedItemsDomain> bookedItems) {
	this.bookedItems = bookedItems;
}
public int getBookedGoodsTypes() {
	return bookedGoodsTypes;
}
public void setBookedGoodsTypes(int bookedGoodsTypes) {
	this.bookedGoodsTypes = bookedGoodsTypes;
}
public static long getSerialversionuid() {
	return serialVersionUID;
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
public String getPickUpDate() {
	return pickUpDate;
}
public void setPickUpDate(String pickUpDate) {
	this.pickUpDate = pickUpDate;
}
public String getPickUpTime() {
	return pickUpTime;
}
public void setPickUpTime(String pickUpTime) {
	this.pickUpTime = pickUpTime;
}
public String getQuotationId() {
	return quotationId;
}
public void setQuotationId(String quotationId) {
	this.quotationId = quotationId;
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
public Double getSourcelatitude() {
	return sourcelatitude;
}
public void setSourcelatitude(Double sourcelatitude) {
	this.sourcelatitude = sourcelatitude;
}
public Double getSourcelongitude() {
	return sourcelongitude;
}
public void setSourcelongitude(Double sourcelongitude) {
	this.sourcelongitude = sourcelongitude;
}
public Double getDestinationlatitude() {
	return destinationlatitude;
}
public void setDestinationlatitude(Double destinationlatitude) {
	this.destinationlatitude = destinationlatitude;
}
public Double getDestinationlongitude() {
	return destinationlongitude;
}
public void setDestinationlongitude(Double destinationlongitude) {
	this.destinationlongitude = destinationlongitude;
}
public int getKerbWeightId() {
	return kerbWeightId;
}
public void setKerbWeightId(int kerbWeightId) {
	this.kerbWeightId = kerbWeightId;
}
public int getGoodsTypeId() {
	return goodsTypeId;
}
public void setGoodsTypeId(int goodsTypeId) {
	this.goodsTypeId = goodsTypeId;
}
public double getTotalAmount() {
	return totalAmount;
}
public void setTotalAmount(double totalAmount) {
	this.totalAmount = totalAmount;
}
public String getVehicleCategoryId() {
	return vehicleCategoryId;
}
public void setVehicleCategoryId(String vehicleCategoryId) {
	this.vehicleCategoryId = vehicleCategoryId;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getEmailId() {
	return emailId;
}
public void setEmailId(String emailId) {
	this.emailId = emailId;
}
public String getCustomerId() {
	return customerId;
}
public void setCustomerId(String customerId) {
	this.customerId = customerId;
}
public String getCustomerName() {
	return customerName;
}
public void setCustomerName(String customerName) {
	this.customerName = customerName;
}
public BigInteger getContactNumber() {
	return contactNumber;
}
public void setContactNumber(BigInteger contactNumber) {
	this.contactNumber = contactNumber;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getsStreet() {
	return sStreet;
}
public void setsStreet(String sStreet) {
	this.sStreet = sStreet;
}
public int getTotal() {
	return total;
}
public void setTotal(int total) {
	this.total = total;
}
}
