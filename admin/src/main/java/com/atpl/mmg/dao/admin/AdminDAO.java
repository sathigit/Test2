package com.atpl.mmg.dao.admin;

import java.util.List;

import com.atpl.mmg.domain.admin.AdminDomain;

public interface AdminDAO {

	public List<AdminDomain> getService() throws Exception;

	public List<AdminDomain> getSeverity() throws Exception;

	public List<AdminDomain> getCustomerIssue() throws Exception;

	public List<AdminDomain> getFranchiseIssue() throws Exception;

	public List<AdminDomain> getFleetIssue() throws Exception;

	public String addDriverIssue(AdminDomain adminDomain) throws Exception;

	public String addCustomerIssue(AdminDomain adminDomain) throws Exception;

	public String addFranchiseIssue(AdminDomain adminDomain) throws Exception;

	public String addFleetIssue(AdminDomain adminDomain) throws Exception;

	public String updateCustomerIssue(AdminDomain adminDomain) throws Exception;

	public String updateDriverIssue(AdminDomain adminDomain) throws Exception;

	public String updateFranchiseIssue(AdminDomain adminDomain) throws Exception;

	public String updateFleetIssue(AdminDomain adminDomain) throws Exception;

	public String deleteCustomerIssue(int id) throws Exception;

	public String deleteDriverIssue(int id) throws Exception;

	public String deleteFranchiseIssue(int id) throws Exception;

	public String deleteFleetIssue(int id) throws Exception;

	public String addSeverity(AdminDomain adminDomain) throws Exception;

	public String updateSeverity(AdminDomain adminDomain) throws Exception;

	public String deleteSeverity(int id) throws Exception;

	public AdminDomain getCustomerIssue(int id) throws Exception;

	public AdminDomain getFranchiseIssue(int id) throws Exception;

	public AdminDomain getDriverIssue(int id) throws Exception;

	public AdminDomain getFleetIssue(int id) throws Exception;

	public AdminDomain getSeverity(int id) throws Exception;

	public List<AdminDomain> getEmployeeList() throws Exception;
}
