package com.atpl.mmg.AandA.domain.profile;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class CustomerDomain {
	private String customerId;
	private String profileId;
	private boolean isTermsAndCondition;
	private String termsAndConditionsId;
	private String referenceId;
	private int customerTypeId;
	private String gstNo;
	private Date createdDate;
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
	public boolean isTermsAndCondition() {
		return isTermsAndCondition;
	}
	public void setTermsAndCondition(boolean isTermsAndCondition) {
		this.isTermsAndCondition = isTermsAndCondition;
	}
	public int getCustomerTypeId() {
		return customerTypeId;
	}
	public void setCustomerTypeId(int customerTypeId) {
		this.customerTypeId = customerTypeId;
	}
	public String getGstNo() {
		return gstNo;
	}
	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}
	
}
