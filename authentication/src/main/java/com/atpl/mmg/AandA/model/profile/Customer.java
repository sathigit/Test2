package com.atpl.mmg.AandA.model.profile;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {
	private String customerId;
	@JsonIgnore
	private String profileId;
	private boolean isTermsAndCondition;
	private String termsAndConditionsId;
	private int customerTypeId;
	private String customerTypeName;
	private String gstNo;
	private String referenceId;
	@JsonIgnore
	private Date createdDate;
	@JsonIgnore
	private Date modifiedDate;
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getProfileId() {
		return profileId;
	}
	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}
	
	public boolean isIsTermsAndCondition() {
		return isTermsAndCondition;
	}

	public void setIsTermsAndCondition(boolean isTermsAndCondition) {
		this.isTermsAndCondition = isTermsAndCondition;
	}

	public String getTermsAndConditionsId() {
		return termsAndConditionsId;
	}
	public void setTermsAndConditionsId(String termsAndConditionsId) {
		this.termsAndConditionsId = termsAndConditionsId;
	}
	public int getCustomerTypeId() {
		return customerTypeId;
	}
	public void setCustomerTypeId(int customerTypeId) {
		this.customerTypeId = customerTypeId;
	}
	public String getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getCustomerTypeName() {
		return customerTypeName;
	}
	public void setCustomerTypeName(String customerTypeName) {
		this.customerTypeName = customerTypeName;
	}
	public String getGstNo() {
		return gstNo;
	}
	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}
	
}
