package com.atpl.mmg.model.ValidateModel;

public class ValidateModel {

	private String id;
	private int profileId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getProfileId() {
		return profileId;
	}

	public void setProfileId(int profileId) {
		this.profileId = profileId;
	}

	public ValidateModel(String id, int profileId) {
		this.id = id;
		this.profileId = profileId;
	}

}
