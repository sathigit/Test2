package com.atpl.mmg.service.admin;

import java.util.List;

import com.atpl.mmg.model.admin.AdminModel;

public interface AdminService {

	public List<AdminModel> getService() throws Exception;

	public List<AdminModel> getSeverity() throws Exception;

	public List<AdminModel> getCustomerIssue() throws Exception;

	public List<AdminModel> getFranchiseIssue() throws Exception;

	public List<AdminModel> getFleetIssue() throws Exception;

	public String addDriverIssue(AdminModel adminModel) throws Exception;

	public String addCustomerIssue(AdminModel adminModel) throws Exception;

	public String addFranchiseIssue(AdminModel adminModel) throws Exception;

	public String addFleetIssue(AdminModel adminModel) throws Exception;

	public String updateCustomerIssue(AdminModel adminModel) throws Exception;

	public String updateDriverIssue(AdminModel adminModel) throws Exception;

	public String updateFranchiseIssue(AdminModel adminModel) throws Exception;

	public String updateFleetIssue(AdminModel adminModel) throws Exception;

	public String deleteCustomerIssue(int id) throws Exception;

	public String deleteDriverIssue(int id) throws Exception;

	public String deleteFranchiseIssue(int id) throws Exception;

	public String deleteFleetIssue(int id) throws Exception;

	public String addSeverity(AdminModel adminModel) throws Exception;

	public String updateSeverity(AdminModel adminModel) throws Exception;

	public String deleteSeverity(int id) throws Exception;

	public AdminModel getCustomerIssue(int id) throws Exception;

	public AdminModel getFranchiseIssue(int id) throws Exception;

	public AdminModel getDriverIssue(int id) throws Exception;

	public AdminModel getFleetIssue(int id) throws Exception;

	public AdminModel getSeverity(int id) throws Exception;

	public List<AdminModel> getEmployeeList() throws Exception;
}
