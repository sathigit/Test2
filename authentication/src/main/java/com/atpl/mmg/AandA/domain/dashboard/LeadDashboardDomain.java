package com.atpl.mmg.AandA.domain.dashboard;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class LeadDashboardDomain {

	private Integer leadStatus;
	private Integer leadRemarks;
	private Integer pending;
	private Integer inProgress;
	private Integer completed;
	private Integer cancelled;
	private Integer total;
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
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}

	
	
}
