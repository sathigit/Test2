package com.atpl.mmg.model.termsandcondition;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TandCDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4884247489894866502L;
	private String uuid;
    private boolean isAlreadyActivated;
    

	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public boolean getIsAlreadyActivated() {
		return isAlreadyActivated;
	}
	public void setIsAlreadyActivated(boolean isAlreadyActivated) {
		this.isAlreadyActivated = isAlreadyActivated;
	}


    


}
