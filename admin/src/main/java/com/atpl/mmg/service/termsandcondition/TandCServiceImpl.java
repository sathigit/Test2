package com.atpl.mmg.service.termsandcondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.dao.termsandcondition.TandCDAO;
import com.atpl.mmg.domain.packaging.PackagingDomain;
import com.atpl.mmg.domain.termsandcondition.TandCDomain;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.T_C_CONTENT_VALIDATE;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;
import com.atpl.mmg.mapper.tandc.TandCMapper;
import com.atpl.mmg.model.packaging.PackagingModel;
import com.atpl.mmg.model.profile.RoleModel;
import com.atpl.mmg.model.termsandcondition.TandCDto;
import com.atpl.mmg.model.termsandcondition.TandCModel;
import com.atpl.mmg.service.auth.AdminAuthService;
import com.atpl.mmg.utils.CommonUtils;
import com.atpl.mmg.utils.ListDto;

@Service("TandCService")
public class TandCServiceImpl implements TandCService {

	@Autowired
	TandCDAO tandCDAO;

	@Autowired
	TandCMapper tandCMapper;

	@Autowired
	AdminAuthService authService;

	@Autowired
	CommonUtils commonUtils;

	private void validateTc(TandCModel tandCModel) throws Exception {
		String roleName = " ";
		if (tandCModel.getRoleId() <= 0)
			throw new NOT_FOUND("Please mention the roleId!!");
		if (null == tandCModel.getName())
			throw new NOT_FOUND("Please mention the name for terms and conditions!!");
		if (null == tandCModel.getTermsAndConditions())
			throw new NOT_FOUND("Please mention the terms and conditions!!");
		roleName = authService.getRoleInfo(tandCModel.getRoleId());
		List<TandCDomain> termsAndCondition = tandCDAO.getAllTandCListByRole(tandCModel.getRoleId(), 0, 0);
		if (!termsAndCondition.isEmpty())
			for (TandCDomain domain : termsAndCondition) {
				if (domain.getTermsAndConditions().equals(tandCModel.getTermsAndConditions()))
					throw new T_C_CONTENT_VALIDATE(
							"Same terms and conditions content already exists for " + roleName + " !!");
			}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public TandCDto saveTandC(TandCModel tandCModel) throws Exception {
		TandCDto tandCDto = new TandCDto();
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.TERMS_CONDITIONS.name(),
				SeverityTypes.DEBUG.ordinal(), " validateTc in TandCServiceImpl ") + JsonUtil.toJsonString(tandCModel));
		validateTc(tandCModel);
		TandCDomain tandCDomain = new TandCDomain();
		BeanUtils.copyProperties(tandCModel, tandCDomain);
		tandCDomain.setUuid(CommonUtils.generateRandomId());
		Loggly.sendLogglyEvent(
				EventsLogUtil.prepareLogEvent(ServiceTypes.TERMS_CONDITIONS.name(), SeverityTypes.DEBUG.ordinal(),
						" getTandCByRole  in TandCServiceImpl ") + JsonUtil.toJsonString(tandCModel));
		TandCDomain tandCinDb = tandCDAO.getTandCByRole(tandCModel.getRoleId(), true);
		List<TandCDomain> tandCListinDb = tandCDAO.getAllTandCListByRole(tandCModel.getRoleId(), 0, 0);
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.TERMS_CONDITIONS.name(),
				SeverityTypes.DEBUG.ordinal(), " Get existing terms and conditions version  in TandCServiceImpl ")
				+ JsonUtil.toJsonString(tandCModel));
		if (!tandCListinDb.isEmpty()) {
			if (tandCModel.getIsActive()) {
				for (TandCDomain tAndCList : tandCListinDb) {
					Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.TERMS_CONDITIONS.name(),
							SeverityTypes.DEBUG.ordinal(),
							"Inactivate existing terms and conditions   in TandCServiceImpl ")
							+ JsonUtil.toJsonString(tandCModel));
					tandCDAO.updateTermsAndCondition(tAndCList.getUuid(), false);
				}
				tandCDto.setIsAlreadyActivated(true);
			}
			tandCDomain.setVersion(commonUtils.termsAndConditionVersion(tandCinDb.getVersion()));
			tandCDAO.saveTandC(tandCDomain);
		} else {
			tandCDomain.setVersion("v " + 0.1);
			tandCDomain.setIsActive(true);
			tandCDto.setIsAlreadyActivated(true);
			tandCDAO.saveTandC(tandCDomain);
		}
		tandCDto.setUuid(tandCDomain.getUuid());
		return tandCDto;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ListDto getTandCListByRoleAndStatus(int roleId, Map<String, String> reqParam) throws Exception {
		authService.getRoleInfo(roleId);
		Map<Integer, RoleModel> roleMap = authService.getRolesList();
		int lowerBound = 0;
		int upperBound = 0;
		int totalSize = 0;
		List<TandCDomain> tandCDomain = new ArrayList<TandCDomain>();
		boolean isActive = false;
		if (reqParam.size() > 0) {
			Map<String, Integer> paginationCount = CommonUtils.getPagination(reqParam);
			if (paginationCount.size() > 0) {
				lowerBound = paginationCount.get("lowerBound");
				upperBound = paginationCount.get("upperBound");
			}
			if (reqParam.containsKey("isActive")) {
				String active = reqParam.get("isActive");
				isActive = Boolean.parseBoolean(active);
			}
		}
		if (reqParam.size() == 0) {
			tandCDomain = tandCDAO.getAllTandCListByRole(roleId, lowerBound, upperBound);
			totalSize = tandCDAO.getAllTandCListByRoleCount(roleId).getTotal();
		} else {
			tandCDomain = tandCDAO.getTandCListByRoleAndStatus(roleId, isActive);
			totalSize = tandCDAO.getTandCListByRoleAndStatusCount(roleId, isActive).getTotal();
		}
		for (TandCDomain tandC : tandCDomain) {
			tandC.setRole(roleMap.get(tandC.getRoleId()).getRoleName());
		}
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.TERMS_CONDITIONS.name(),
				SeverityTypes.DEBUG.ordinal(), " getTandCListByRoleAndStatus in TandCServiceImpl "));
		List<TandCModel> tandCModel = tandCMapper.entityList(tandCDomain);
		ListDto listDto = new ListDto();
		listDto.setList(tandCModel);
		listDto.setTotalSize(totalSize);
		return listDto;
	}

	@Override
	public TandCModel getTandCById(String uuid) throws Exception {
		String roleName = null;
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.TERMS_CONDITIONS.name(),
				SeverityTypes.DEBUG.ordinal(), " getTandCByuuId in TandCServiceImpl ") + JsonUtil.toJsonString(uuid));
		TandCDomain tandCDomain = tandCDAO.getTandCById(uuid);
		if (null == tandCDomain)
			throw new NOT_FOUND("Terms and condition not found");
		else {
			roleName = authService.getRoleInfo(tandCDomain.getRoleId());
			if (null != roleName) {
				tandCDomain.setRole(roleName);
			}
		}
		TandCModel tandCModel = new TandCModel();
		BeanUtils.copyProperties(tandCDomain, tandCModel);
		return tandCModel;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ListDto getTandCList(boolean isActive, Map<String, String> reqParam) throws Exception {
		int lowerBound = 0;
		int upperBound = 0;
		Map<Integer, RoleModel> roleMap = authService.getRolesList();
		if (reqParam.size() > 0) {
			Map<String, Integer> paginationCount = CommonUtils.getPagination(reqParam);
			if (paginationCount.size() > 0) {
				lowerBound = paginationCount.get("lowerBound");
				upperBound = paginationCount.get("upperBound");
			}
		}
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.TERMS_CONDITIONS.name(),
				SeverityTypes.DEBUG.ordinal(), " getTandCList in TandCServiceImpl "));
		List<TandCDomain> tandCDomain = tandCDAO.getTandCList(isActive, lowerBound, upperBound);
		for (TandCDomain tandC : tandCDomain) {
			tandC.setRole(roleMap.get(tandC.getRoleId()).getRoleName());
		}

		List<TandCModel> tandCModel = tandCMapper.entityList(tandCDomain);
		ListDto listDto = new ListDto();
		listDto.setList(tandCModel);
		listDto.setTotalSize(tandCDAO.getTandCListCount(isActive).getTotal());
		return listDto;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public String activateTandC(String uuid) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.TERMS_CONDITIONS.name(),
				SeverityTypes.DEBUG.ordinal(), " activateTandC in TandCServiceImpl ") + JsonUtil.toJsonString(uuid));
		TandCDomain domain = tandCDAO.getTandCById(uuid);
		if (null == domain)
			throw new NOT_FOUND("Terms and conditions not found");
		List<TandCDomain> tandC = tandCDAO.getTandCListByRole(domain.getRoleId());
		if (!tandC.isEmpty()) {
			for (TandCDomain termsAndConditions : tandC) {
				tandCDAO.updateTermsAndCondition(termsAndConditions.getUuid(), false);
			}
		}
		return tandCDAO.updateTermsAndCondition(uuid, true);
	}

	@Override
	public String deActivateTandC(String uuid) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.TERMS_CONDITIONS.name(),
				SeverityTypes.DEBUG.ordinal(), " deActivateTandC in TandCServiceImpl ") + JsonUtil.toJsonString(uuid));
		TandCDomain domain = tandCDAO.getTandCById(uuid);
		if (null == domain)
			throw new NOT_FOUND("Terms and conditions not found");
		return tandCDAO.updateTermsAndCondition(uuid, false);
	}

}
