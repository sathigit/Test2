package com.atpl.mmg.service.faredist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.constant.Role;
import com.atpl.mmg.dao.faredist.FareDistributionDAO;
import com.atpl.mmg.dao.faredisttype.FareDistributionTypeDAO;
import com.atpl.mmg.domain.dbupdate.DBUpdate;
import com.atpl.mmg.domain.faredist.FareDistributionDomain;
import com.atpl.mmg.domain.faredisttype.FareDistributionTypeDomain;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.mapper.faredist.FareDistributionMapper;
import com.atpl.mmg.model.faredist.FareDistributionDTo;
import com.atpl.mmg.model.faredist.FareDistributionModel;
import com.atpl.mmg.model.faredist.FareDistributionRolePercentage;
import com.atpl.mmg.model.faredist.FareDistributionRolePercentageDTo;
import com.atpl.mmg.model.faredist.RolePercentage;
import com.atpl.mmg.model.faredisttype.FareDistributionTypeModel;
import com.atpl.mmg.model.profile.RoleModel;
import com.atpl.mmg.service.auth.AdminAuthService;
import com.atpl.mmg.service.dbupdate.DBUpdateService;
import com.atpl.mmg.utils.CommonUtils;

@Service("fareDistributionService")
public class FareDistributionServiceImpl implements FareDistributionService {

	@Autowired
	FareDistributionUtil fareDistributionUtil;

	@Autowired
	FareDistributionDAO fareDistributionDAO;

	@Autowired
	FareDistributionMapper fareDistributionMapper;

	@Autowired
	AdminAuthService authService;

	@Autowired
	FareDistributionTypeDAO fareDistributionTypeDAO;

	@Autowired
	DBUpdateService dbUpdateService;

	@Override
	public String save(FareDistributionModel fareDistributionModel) throws Exception {
		String response = null;
		FareDistributionDTo fareDistributionDTo = new FareDistributionDTo();
		fareDistributionUtil.validateFareDistribution(fareDistributionModel, fareDistributionDTo, false);
		fareDistributionUtil.validateFdTypeAndRoleAndCheckPercentage(fareDistributionModel, fareDistributionDTo, false);
		FareDistributionDomain fareDistributionDomain = new FareDistributionDomain();
		List<Integer> fdTypeList = new ArrayList<Integer>();
		for (FareDistributionRolePercentage fareDistributionRolePercentage : fareDistributionModel
				.getFareDistribution()) {
			RolePercentage rolePercentage = fareDistributionUtil
					.rolesPercentageListByPost(fareDistributionRolePercentage.getRoleBasedDistribution(), fdTypeList);
			for (int i = 0; i <= rolePercentage.getRole().size() - 1; i++) {
				fareDistributionDomain.setRoleId(rolePercentage.getRole().get(i).intValue());
				fareDistributionDomain.setIsPercentage(fareDistributionRolePercentage.isIsPercentage());
				if (fareDistributionRolePercentage.isIsPercentage())
					fareDistributionDomain.setPercentage(rolePercentage.getPercentage().get(i));
				else {
					fareDistributionDomain.setPercentage(0.0);
					fareDistributionDomain.setFixedCost(rolePercentage.getPercentage().get(i));
				}
				fareDistributionDomain.setUuid(CommonUtils.generateRandomId());
				fareDistributionDomain
						.setFareDistributionTypeId(fareDistributionRolePercentage.getFareDistributionTypeId());
				fareDistributionDomain.setFranchiseId(fareDistributionModel.getFranchiseId());
				response = fareDistributionDAO.save(fareDistributionDomain);
			}
		}
		return response;
	}

	@Override
	public String update(FareDistributionDTo fareDistributionDto) throws Exception {
		String response = null;
		FareDistributionModel fareDistributionModel = new FareDistributionModel();
		fareDistributionUtil.validateFareDistribution(fareDistributionModel, fareDistributionDto, true);
		fareDistributionUtil.validateFdTypeAndRoleAndCheckPercentage(fareDistributionModel, fareDistributionDto, true);
		List<Integer> fdTypeList = new ArrayList<Integer>();
		FareDistributionDomain fareDistributionDomain = new FareDistributionDomain();
		for (FareDistributionRolePercentageDTo fareDistributionRolePercentage : fareDistributionDto
				.getFareDistribution()) {
			RolePercentage rolePercentage = fareDistributionUtil.rolesPercentageListByUpdate(
					fareDistributionRolePercentage.getRoleBasedDistribution(), fdTypeList,
					fareDistributionRolePercentage.getIsPercentage());
			for (int i = 0; i < fareDistributionRolePercentage.getRoleBasedDistribution().size(); i++) {
				fareDistributionDomain.setRoleId(rolePercentage.getRole().get(i));
				fareDistributionDomain.setUuid(rolePercentage.getUuid().get(i));
				fareDistributionDomain
						.setFareDistributionTypeId(fareDistributionRolePercentage.getFareDistributionTypeId());
				FareDistributionDomain fDDomain = fareDistributionDAO
						.getFareDistribution(rolePercentage.getUuid().get(i));
				if (null != fDDomain) {
					fareDistributionDomain.setFranchiseId(fareDistributionDto.getFranchiseId());

					if (fDDomain.isIsActive())
						fareDistributionDomain.setIsActive(true);
					if (fareDistributionRolePercentage.getIsPercentage()) {
						fareDistributionDomain.setPercentage(rolePercentage.getPercentage().get(i));
						if (fDDomain.getPercentage() != fareDistributionDomain.getPercentage()) {
							updateStatusAndIsPercentage(fareDistributionDomain.getUuid());
							if (fDDomain.isIsPercentage())
								fareDistributionDomain.setIsPercentage(true);
							fareDistributionDomain.setUuid(CommonUtils.generateRandomId());
							response = fareDistributionDAO.save(fareDistributionDomain);
						} else
							response = fareDistributionDAO.update(fareDistributionDomain);
					} else {
						fareDistributionDomain.setPercentage(0.0);
						fareDistributionDomain.setFixedCost(rolePercentage.getPercentage().get(i));
						if (fDDomain.getFixedCost() != fareDistributionDomain.getFixedCost()) {
							updateStatusAndIsPercentage(fareDistributionDomain.getUuid());
							fareDistributionDomain.setUuid(CommonUtils.generateRandomId());
							response = fareDistributionDAO.save(fareDistributionDomain);
						} else
							response = fareDistributionDAO.update(fareDistributionDomain);
					}
				}
			}
		}
		return response;
	}

	private String updateStatusAndIsPercentage(String uuid) throws Exception {
		// Update method
		DBUpdate dbUpdate = new DBUpdate();
		dbUpdate.setTableName("faredistribution");
		Map<String, Object> expression = new HashMap<String, Object>();
		expression.put("status", false);
		expression.put("isActive", false);
		dbUpdate.setExpression(expression);
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("uuid", uuid);
		dbUpdate.setConditions(conditions);
		return dbUpdateService.dbUpdate(dbUpdate);
	}

	@Override
	public List<FareDistributionDTo> getFareDistributiones(Map<String, String> reqParam) throws Exception {
		Boolean isActive = null;
		String franchiseId = null;
		if (reqParam.size() > 0) {
			if (reqParam.containsKey("isActive")) {
				String active = null;
				active = reqParam.get("isActive");
				isActive = Boolean.parseBoolean(active);
			} else
				throw new NOT_FOUND("Please mention request parameter as 'isActive' ");
			if (reqParam.containsKey("franchiseId")) {
				franchiseId = reqParam.get("franchiseId");
			} else
				throw new NOT_FOUND("Please mention request parameter as 'franchiseId' ");
		}
		List<FareDistributionDomain> fareDistributionDomainList = new ArrayList<FareDistributionDomain>();
		if (null != isActive && null != franchiseId)
			fareDistributionDomainList = fareDistributionDAO.getFareDistributiones(isActive, franchiseId);
		else
			fareDistributionDomainList = fareDistributionDAO.getFareDistributiones();
		List<FareDistributionDTo> fareDistributionModelList = fareDistributionUtil
				.getCommonFDDetails(fareDistributionDomainList);
		return fareDistributionModelList;
	}

	@Override
	public FareDistributionModel getFareDistribution(String uuid) throws Exception {
		FareDistributionDomain fareDistributionDomain = fareDistributionDAO.getFareDistribution(uuid);
		if (null == fareDistributionDomain)
			throw new NOT_FOUND("Fare Distribution not found");
		FareDistributionModel fareDistributionModel = new FareDistributionModel();
		BeanUtils.copyProperties(fareDistributionDomain, fareDistributionModel);

		FareDistributionTypeDomain fareDistributionTypeDomain = fareDistributionTypeDAO
				.getFareDistributionType(fareDistributionModel.getFareDistributionTypeId());
		FareDistributionTypeModel fareDistributionTypeModel = new FareDistributionTypeModel(
				fareDistributionTypeDomain.getName());
		fareDistributionModel.setFareDistributionType(fareDistributionTypeModel);
		Map<Integer, RoleModel> roleMap = authService.getRolesList();
		RoleModel roleModel = new RoleModel(roleMap.get(fareDistributionDomain.getRoleId()).getRoleName());
		fareDistributionModel.setRole(roleModel);

		return fareDistributionModel;
	}

	@Override
	public String activateFD(String fareDistributionTypeId, String franchiseId) throws Exception {
		String response = null;
		List<FareDistributionDomain> fareDistributionDomainList = fareDistributionDAO
				.getFareDistributioneByTypeAndFranchiseId(fareDistributionTypeId, franchiseId);
		if (!fareDistributionDomainList.isEmpty()) {
			for (FareDistributionDomain fareDistributionDomain : fareDistributionDomainList) {
				response = fareDistributionDAO.updateIsActiveByFDTypeId(true, fareDistributionDomain.getUuid());
			}
		}
		return response;
	}

	@Override
	public String deActivateFD(String fareDistributionTypeId, String franchiseId) throws Exception {
		String response = null;
		List<FareDistributionDomain> fareDistributionDomainList = fareDistributionDAO
				.getFareDistributioneByTypeAndFranchiseId(fareDistributionTypeId, franchiseId);
		if (!fareDistributionDomainList.isEmpty()) {
			for (FareDistributionDomain fareDistributionDomain : fareDistributionDomainList) {
				response = fareDistributionDAO.updateIsActiveByFDTypeId(false, fareDistributionDomain.getUuid());
			}
		}
		return response;
	}

	@Override
	public String delete(String franchiseId, List<String> fdTypeIdList) throws Exception {
		String response = null;
		authService.getDetails(Role.FRANCHISE.getCode(), franchiseId,"franchiseId");
		for (String fDType : fdTypeIdList) {
			String fD = fDType.replace("{", "");
			fD = fD.replace("}", "");
			FareDistributionTypeDomain fareDistributionTypeDomain = fareDistributionTypeDAO.getFareDistributionType(fD);
			if (fareDistributionTypeDomain != null) {
				List<FareDistributionDomain> fDTypeDomainList = fareDistributionDAO.getFareDistributiones(fD,
						franchiseId);
				if (!fDTypeDomainList.isEmpty()) {
					for (FareDistributionDomain fareDistributionDomain : fDTypeDomainList) {
						List<String> uuidList = CommonUtils
								.stringToListOfStringConversion(fareDistributionDomain.getUuids());
						for (String uuid : uuidList) {
							response = fareDistributionDAO.delete(uuid);
						}
					}
				}
			} else
				throw new NOT_FOUND("Please mention valide 'fareDistributionTypeId' ");
		}
		return response;
	}

	@Override
	public List<FareDistributionDTo> getFareDistributiones(String franchiseId, Map<String, String> reqParam)
			throws Exception {
		String fdTypeId = null;
		if (reqParam.size() > 0) {
			if (reqParam.containsKey("fareDistributionTypeId")) {
				fdTypeId = reqParam.get("fareDistributionTypeId");
			} else
				throw new NOT_FOUND("Please mention request parameter as 'fareDistributionTypeId' ");
		}
		authService.getDetails(Role.FRANCHISE.getCode(), franchiseId,"franchiseId");
		List<FareDistributionDomain> fareDistributionDomainList = new ArrayList<FareDistributionDomain>();
		if (null != fdTypeId)
			fareDistributionDomainList = fareDistributionDAO.getFareDistributiones(fdTypeId, franchiseId);
		else
			fareDistributionDomainList = fareDistributionDAO.getFareDistributiones(franchiseId);
		List<FareDistributionDTo> fareDistributionModelList = fareDistributionUtil
				.getCommonFDDetails(fareDistributionDomainList);
		return fareDistributionModelList;
	}

}
