package com.atpl.mmg.service.admin;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.dao.admin.AdminDAO;
import com.atpl.mmg.domain.admin.AdminDomain;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
//import com.atpl.mmg.domain.vehicletype.VehicleTypeDomain;
import com.atpl.mmg.mapper.admin.AdminMapper;
import com.atpl.mmg.model.admin.AdminModel;

@SuppressWarnings("rawtypes")
@Service("adminService")
public class AdminServiceImpl implements AdminService, Constants {

	@Autowired
	AdminDAO adminDAO;

	@Autowired
	AdminMapper adminMapper;

	private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

	public AdminServiceImpl() {
		// constructor
	}

	@Override
	public List<AdminModel> getService() throws Exception {
		List<AdminDomain> adminDomain = adminDAO.getService();
		return adminMapper.entityList(adminDomain);

	}

	@Override
	public List<AdminModel> getSeverity() throws Exception {
		List<AdminDomain> adminDomain = adminDAO.getSeverity();
		return adminMapper.entityList(adminDomain);

	}

	@Override
	public List<AdminModel> getCustomerIssue() throws Exception {
		List<AdminDomain> adminDomain = adminDAO.getCustomerIssue();
		return adminMapper.entityList(adminDomain);

	}

	@Override
	public List<AdminModel> getFranchiseIssue() throws Exception {
		List<AdminDomain> adminDomain = adminDAO.getFranchiseIssue();
		return adminMapper.entityList(adminDomain);

	}

	@Override
	public List<AdminModel> getFleetIssue() throws Exception {
		List<AdminDomain> adminDomain = adminDAO.getFleetIssue();
		return adminMapper.entityList(adminDomain);
	}

	@Override
	public String addDriverIssue(AdminModel adminModel) throws Exception {
		AdminDomain adminDomain = new AdminDomain();
		BeanUtils.copyProperties(adminModel, adminDomain);
		return adminDAO.addDriverIssue(adminDomain);
	}

	@Override
	public String addCustomerIssue(AdminModel adminModel) throws Exception {
		AdminDomain adminDomain = new AdminDomain();
		BeanUtils.copyProperties(adminModel, adminDomain);
		return adminDAO.addCustomerIssue(adminDomain);
	}

	@Override
	public String addFranchiseIssue(AdminModel adminModel) throws Exception {
		AdminDomain adminDomain = new AdminDomain();
		BeanUtils.copyProperties(adminModel, adminDomain);
		return adminDAO.addFranchiseIssue(adminDomain);
	}

	@Override
	public String addFleetIssue(AdminModel adminModel) throws Exception {
		AdminDomain adminDomain = new AdminDomain();
		BeanUtils.copyProperties(adminModel, adminDomain);
		return adminDAO.addFleetIssue(adminDomain);
	}

	@Override
	public String updateCustomerIssue(AdminModel adminModel) throws Exception {
		AdminDomain adminDomain = new AdminDomain();
		BeanUtils.copyProperties(adminModel, adminDomain);
		return adminDAO.updateCustomerIssue(adminDomain);
	}

	@Override
	public String updateDriverIssue(AdminModel adminModel) throws Exception {

		AdminDomain adminDomain = new AdminDomain();
		BeanUtils.copyProperties(adminModel, adminDomain);
		return adminDAO.updateDriverIssue(adminDomain);
	}

	@Override
	public String updateFranchiseIssue(AdminModel adminModel) throws Exception {
		AdminDomain adminDomain = new AdminDomain();
		BeanUtils.copyProperties(adminModel, adminDomain);
		return adminDAO.updateFranchiseIssue(adminDomain);
	}

	@Override
	public String updateFleetIssue(AdminModel adminModel) throws Exception {
		AdminDomain adminDomain = new AdminDomain();
		BeanUtils.copyProperties(adminModel, adminDomain);
		return adminDAO.updateFleetIssue(adminDomain);
	}

	@Override
	public String deleteCustomerIssue(int id) throws Exception {
		return adminDAO.deleteCustomerIssue(id);
	}

	@Override
	public String deleteDriverIssue(int id) throws Exception {
		return adminDAO.deleteDriverIssue(id);
	}

	@Override
	public String deleteFranchiseIssue(int id) throws Exception {
		return adminDAO.deleteFranchiseIssue(id);
	}

	@Override
	public String deleteFleetIssue(int id) throws Exception {
		return adminDAO.deleteFleetIssue(id);
	}

	@Override
	public String addSeverity(AdminModel adminModel) throws Exception {
		AdminDomain adminDomain = new AdminDomain();
		BeanUtils.copyProperties(adminModel, adminDomain);
		return adminDAO.addSeverity(adminDomain);
	}

	@Override
	public String updateSeverity(AdminModel adminModel) throws Exception {
		AdminDomain adminDomain = new AdminDomain();
		BeanUtils.copyProperties(adminModel, adminDomain);
		return adminDAO.updateSeverity(adminDomain);
	}

	@Override
	public String deleteSeverity(int id) throws Exception {
		return adminDAO.deleteSeverity(id);
	}

	@Override
	public AdminModel getCustomerIssue(int id) throws Exception {
		AdminDomain adminDomain = adminDAO.getCustomerIssue(id);
		AdminModel adminModel = new AdminModel();
		if (null == adminDomain)
			throw new NOT_FOUND("CustomerIssue not found");
		BeanUtils.copyProperties(adminDomain, adminModel);
		return adminModel;
	}

	@Override
	public AdminModel getFranchiseIssue(int id) throws Exception {
		AdminDomain adminDomain = adminDAO.getFranchiseIssue(id);
		AdminModel adminModel = new AdminModel();
		if (null == adminDomain)
			throw new NOT_FOUND("FranchiseIssue not found");
		BeanUtils.copyProperties(adminDomain, adminModel);
		return adminModel;

	}

	@Override
	public AdminModel getDriverIssue(int id) throws Exception {
		AdminDomain adminDomain = adminDAO.getDriverIssue(id);
		AdminModel adminModel = new AdminModel();
		if (null == adminDomain)
			throw new NOT_FOUND("DriverIssue not found");
		BeanUtils.copyProperties(adminDomain, adminModel);
		return adminModel;

	}

	@Override
	public AdminModel getFleetIssue(int id) throws Exception {
		AdminDomain adminDomain = adminDAO.getFleetIssue(id);
		AdminModel adminModel = new AdminModel();
		if (null == adminDomain)
			throw new NOT_FOUND("FleetIssue not found");
		BeanUtils.copyProperties(adminDomain, adminModel);
		return adminModel;

	}

	@Override
	public AdminModel getSeverity(int id) throws Exception {
		AdminDomain adminDomain = adminDAO.getSeverity(id);
		AdminModel adminModel = new AdminModel();
		if (null == adminDomain)
			throw new NOT_FOUND("Severity not found");
		BeanUtils.copyProperties(adminDomain, adminModel);
		return adminModel;
	}

	@Override
	public List<AdminModel> getEmployeeList() throws Exception {
		List<AdminDomain> adminDomain = adminDAO.getEmployeeList();
		return adminMapper.entityList(adminDomain);
	}
}
