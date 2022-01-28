package com.atpl.mmg.AandA.model.datamigration;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileImageTemplateModel {

	private String uuid;
	private String role;
	private String name;
	private String type;
	private double size;
	private String path;
	private String category;
	private Date createdDate;
	private String franchiseId;

	public ProfileImageTemplateModel() {
	}

	public ProfileImageTemplateModel(String uuid, String name, String type, double size, String path, String category,
			Date createdDate, String franchiseId) {
		this.uuid = uuid;
		this.name = name;
		this.type = type;
		this.size = size;
		this.path = path;
		this.category = category;
		this.createdDate = createdDate;
		this.franchiseId = franchiseId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getFranchiseId() {
		return franchiseId;
	}

	public void setFranchiseId(String franchiseId) {
		this.franchiseId = franchiseId;
	}

}
