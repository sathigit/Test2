package com.atpl.mmg.AandA.domain.profile;

import java.util.Date;

public class DriverDomain {
	private String driverId;
	private String profileId;
	private String franchiseId;
	private int bloodId;
	private int driverTypeId;
	private double weight;
	private double height;
	private String identificationMark;
	private String companyName;
	private String companyAddress;
	private String dlNumber;
	private Date dlIssueDate;
	private Date dlExpiryDate;
	private String badgeNumber;
	private Date badgeExpiryDate;
	private int issueRto;
	private int licenceCategory;
	private String requestedBy;
	private double locationlatitude;
	private double locationlongitude;
	private boolean isRegistered;
	private boolean isAssignedToFranchise;
	private boolean updateStatus;
	private String refferenceCode;
	
	private Date createdDate;
	private Date modifiedDate;
	private String status;
	private String imagePath;
	private Boolean isParcelTrip;
	
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getDriverId() {
		return driverId;
	}
	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}
	public String getProfileId() {
		return profileId;
	}
	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}
	public String getFranchiseId() {
		return franchiseId;
	}
	public void setFranchiseId(String franchiseId) {
		this.franchiseId = franchiseId;
	}
	public int getBloodId() {
		return bloodId;
	}
	public void setBloodId(int bloodId) {
		this.bloodId = bloodId;
	}
	public int getDriverTypeId() {
		return driverTypeId;
	}
	public void setDriverTypeId(int driverTypeId) {
		this.driverTypeId = driverTypeId;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public String getIdentificationMark() {
		return identificationMark;
	}
	public void setIdentificationMark(String identificationMark) {
		this.identificationMark = identificationMark;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getDlNumber() {
		return dlNumber;
	}
	public void setDlNumber(String dlNumber) {
		this.dlNumber = dlNumber;
	}
	public Date getDlIssueDate() {
		return dlIssueDate;
	}
	public void setDlIssueDate(Date dlIssueDate) {
		this.dlIssueDate = dlIssueDate;
	}
	public Date getDlExpiryDate() {
		return dlExpiryDate;
	}
	public void setDlExpiryDate(Date dlExpiryDate) {
		this.dlExpiryDate = dlExpiryDate;
	}
	public String getBadgeNumber() {
		return badgeNumber;
	}
	public void setBadgeNumber(String badgeNumber) {
		if(null != badgeNumber &&  badgeNumber.trim().length() == 0)
			 badgeNumber = null;
		this.badgeNumber = badgeNumber;
	}
	public Date getBadgeExpiryDate() {
		return badgeExpiryDate;
	}
	public void setBadgeExpiryDate(Date badgeExpiryDate) {
		this.badgeExpiryDate = badgeExpiryDate;
	}
	public int getIssueRto() {
		return issueRto;
	}
	public void setIssueRto(int issueRto) {
		this.issueRto = issueRto;
	}
	public int getLicenceCategory() {
		return licenceCategory;
	}
	public void setLicenceCategory(int licenceCategory) {
		this.licenceCategory = licenceCategory;
	}

	public String getRequestedBy() {
		return requestedBy;
	}
	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}
	public double getLocationlatitude() {
		return locationlatitude;
	}
	public void setLocationlatitude(double locationlatitude) {
		this.locationlatitude = locationlatitude;
	}
	public double getLocationlongitude() {
		return locationlongitude;
	}
	public void setLocationlongitude(double locationlongitude) {
		this.locationlongitude = locationlongitude;
	}
	public boolean isRegistered() {
		return isRegistered;
	}
	public void setRegistered(boolean isRegistered) {
		this.isRegistered = isRegistered;
	}
	public boolean isAssignedToFranchise() {
		return isAssignedToFranchise;
	}
	public void setAssignedToFranchise(boolean isAssignedToFranchise) {
		this.isAssignedToFranchise = isAssignedToFranchise;
	}
	public boolean isUpdateStatus() {
		return updateStatus;
	}
	public void setUpdateStatus(boolean updateStatus) {
		this.updateStatus = updateStatus;
	}
	public String getRefferenceCode() {
		return refferenceCode;
	}
	public void setRefferenceCode(String refferenceCode) {
		this.refferenceCode = refferenceCode;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Boolean getIsParcelTrip() {
		return isParcelTrip;
	}
	public void setIsParcelTrip(Boolean isParcelTrip) {
		this.isParcelTrip = isParcelTrip;
	}
	

}
