package com.atpl.mmg.model.dashboard;

import java.util.Date;

public class ReportDashboard {

	private boolean weekly;
	private boolean monthly;
	private boolean yearly;
	
	private Date startDate;
	private Date endDate;

	public boolean isWeekly() {
		return weekly;
	}

	public void setWeekly(boolean weekly) {
		this.weekly = weekly;
	}

	public boolean isMonthly() {
		return monthly;
	}

	public void setMonthly(boolean monthly) {
		this.monthly = monthly;
	}

	public boolean isYearly() {
		return yearly;
	}

	public void setYearly(boolean yearly) {
		this.yearly = yearly;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
