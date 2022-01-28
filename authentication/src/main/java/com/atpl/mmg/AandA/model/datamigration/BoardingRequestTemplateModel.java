package com.atpl.mmg.AandA.model.datamigration;

public class BoardingRequestTemplateModel {

	private String mobileNumber;
	private String name;
	private String email;
	private String message;
	private int roleId;
	private Integer countryId;
	private Integer stateId;
	private Integer cityId;
	private String cityName;
	private String categoryName;
	private Integer status;
	private String requestNumber;
	private Integer total;
	private int otStateId;
	private int profileId;
	private String creationDate;
	private String modificationDate;
	private String onHoldReason;
	private String inactiveReason;
	private String refferenceCode;
	private String roleName;
	private String state;
	private String city;

	public BoardingRequestTemplateModel() {

	}

	public BoardingRequestTemplateModel(String mobileNumber, String name, String email, String message, int roleId,
			Integer countryId, Integer stateId, Integer cityId, Integer status, String requestNumber,
			String creationDate, String onHoldReason, String refferenceCode) {
		this.mobileNumber = mobileNumber;
		this.name = name;
		this.email = email;
		this.message = message;
		this.roleId = roleId;
		this.countryId = countryId;
		this.stateId = stateId;
		this.cityId = cityId;
		this.status = status;
		this.requestNumber = requestNumber;
		this.creationDate = creationDate;
		this.onHoldReason = onHoldReason;
		this.refferenceCode = refferenceCode;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public int getOtStateId() {
		return otStateId;
	}

	public void setOtStateId(int otStateId) {
		this.otStateId = otStateId;
	}

	public int getProfileId() {
		return profileId;
	}

	public void setProfileId(int profileId) {
		this.profileId = profileId;
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

	public String getOnHoldReason() {
		return onHoldReason;
	}

	public void setOnHoldReason(String onHoldReason) {
		this.onHoldReason = onHoldReason;
	}

	public String getInactiveReason() {
		return inactiveReason;
	}

	public void setInactiveReason(String inactiveReason) {
		this.inactiveReason = inactiveReason;
	}

	public String getRefferenceCode() {
		return refferenceCode;
	}

	public void setRefferenceCode(String refferenceCode) {
		this.refferenceCode = refferenceCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
