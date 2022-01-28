package com.atpl.mmg.AandA.domain.dashboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DashboardDomain {
	private Integer totalCustomer;
	private Integer total;
	private Integer bdoCount;
	private Integer leadStatus;
	private Integer leadRemarks;
	private Integer pending;
	private Integer inProgress;
	private Integer completed;
	private Integer cancelled;
	private String roleName;
	private Integer fieldOfficer;
	private Integer coordinator;
	private Integer roleId;
	
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getFieldOfficer() {
		return fieldOfficer;
	}
	public void setFieldOfficer(Integer fieldOfficer) {
		this.fieldOfficer = fieldOfficer;
	}
	public Integer getCoordinator() {
		return coordinator;
	}
	public void setCoordinator(Integer coordinator) {
		this.coordinator = coordinator;
	}
	public Integer getTotalCustomer() {
		return totalCustomer;
	}
	public void setTotalCustomer(Integer totalCustomer) {
		this.totalCustomer = totalCustomer;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getBdoCount() {
		return bdoCount;
	}
	public void setBdoCount(Integer bdoCount) {
		this.bdoCount = bdoCount;
	}
	public Integer getLeadStatus() {
		return leadStatus;
	}
	public void setLeadStatus(Integer leadStatus) {
		this.leadStatus = leadStatus;
	}
	public Integer getLeadRemarks() {
		return leadRemarks;
	}
	public void setLeadRemarks(Integer leadRemarks) {
		this.leadRemarks = leadRemarks;
	}
	public Integer getPending() {
		return pending;
	}
	public void setPending(Integer pending) {
		this.pending = pending;
	}
	public Integer getInProgress() {
		return inProgress;
	}
	public void setInProgress(Integer inProgress) {
		this.inProgress = inProgress;
	}
	public Integer getCompleted() {
		return completed;
	}
	public void setCompleted(Integer completed) {
		this.completed = completed;
	}
	public Integer getCancelled() {
		return cancelled;
	}
	public void setCancelled(Integer cancelled) {
		this.cancelled = cancelled;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
