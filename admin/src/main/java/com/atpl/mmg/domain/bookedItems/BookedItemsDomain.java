package com.atpl.mmg.domain.bookedItems;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class BookedItemsDomain implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3620634927049769248L;
	/**
	 * 
	 */
	private String uuid;
	private String quotationId;
	private String customerId;
	private String invoiceNo;
	private String invoiceDate;
	private Double invoiceAmount;
	private String ewayBillNo;
	private String ewayBillDate;
	private String article;
	private BigInteger packagingId;
	private String goodsContained;
	private Double weight;
	private boolean status;
	private String creationDate;
	private String modificationDate;
	private List<BookedItemsDomain> bookedItems;
	private String packing;
	private String packagingName;

	
	
	public String getPackagingName() {
		return packagingName;
	}

	public void setPackagingName(String packagingName) {
		this.packagingName = packagingName;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getQuotationId() {
		return quotationId;
	}

	public void setQuotationId(String quotationId) {
		this.quotationId = quotationId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Double getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public String getEwayBillNo() {
		return ewayBillNo;
	}

	public void setEwayBillNo(String ewayBillNo) {
		this.ewayBillNo = ewayBillNo;
	}

	public String getEwayBillDate() {
		return ewayBillDate;
	}

	public void setEwayBillDate(String ewayBillDate) {
		this.ewayBillDate = ewayBillDate;
	}

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public BigInteger getPackagingId() {
		return packagingId;
	}

	public void setPackagingId(BigInteger packagingId) {
		this.packagingId = packagingId;
	}

	public String getGoodsContained() {
		return goodsContained;
	}

	public void setGoodsContained(String goodsContained) {
		this.goodsContained = goodsContained;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(String modificationDate) {
		this.modificationDate = modificationDate;
	}

	public List<BookedItemsDomain> getBookedItems() {
		return bookedItems;
	}

	public void setBookedItems(List<BookedItemsDomain> bookedItems) {
		this.bookedItems = bookedItems;
	}

	public String getPacking() {
		return packing;
	}

	public void setPacking(String packing) {
		this.packing = packing;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
