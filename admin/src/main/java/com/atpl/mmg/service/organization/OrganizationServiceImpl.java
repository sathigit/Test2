package com.atpl.mmg.service.organization;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.dao.organization.OrganizationDAO;
import com.atpl.mmg.domain.lead.LeadDomain;
import com.atpl.mmg.domain.organization.OrganizationDomain;
import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;
import com.atpl.mmg.mapper.organization.OrganizationMapper;
import com.atpl.mmg.model.lead.LeadModel;
import com.atpl.mmg.model.organization.OrganizationModel;
import com.atpl.mmg.utils.CommonUtils;
import com.atpl.mmg.utils.ListDto;

@Service("organizationService")
@SuppressWarnings("unused")
public class OrganizationServiceImpl implements OrganizationService, Constants {

	@Autowired
	OrganizationDAO organizationDAO;

	@Autowired
	OrganizationMapper organizationMapper;

	private static final Logger logger = LoggerFactory.getLogger(OrganizationServiceImpl.class);

	public OrganizationServiceImpl() {
		// constructor
	}

	/**
	 * Author:Vidya S K Modified Date: 20/2/2020 Description: Save Organization
	 */
	public String saveOrganization(OrganizationModel organizationModel) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ORGANISATION.name(), SeverityTypes.DEBUG.ordinal(),
				"saveOrganization in OrganizationServiceImpl ")+ JsonUtil.toJsonString(organizationModel));
		OrganizationDomain organizationDomain = new OrganizationDomain();
		BeanUtils.copyProperties(organizationModel, organizationDomain);
		return organizationDAO.saveOrganization(organizationDomain);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ListDto getOrganization(Map<String, String> reqParam) throws Exception {
		int lowerBound = 0;
		int upperBound = 0;
		int totalSize = 0;
		if (reqParam.size() > 0) {
			Map<String, Integer> paginationCount = CommonUtils.getPagination(reqParam);
			if (paginationCount.size() > 0) {
				lowerBound = paginationCount.get("lowerBound");
				upperBound = paginationCount.get("upperBound");
			}
		}
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ORGANISATION.name(), SeverityTypes.DEBUG.ordinal(),
				"getOrganization in OrganizationServiceImpl "));
		List<OrganizationDomain> organizationDomain = organizationDAO.getOrganization(lowerBound, upperBound);
		List<OrganizationModel> organizationModel =organizationMapper.entityList(organizationDomain);
		ListDto listDto = new ListDto();
		listDto.setList(organizationModel);
		totalSize = organizationDAO.getOrganizationCount().getTotal();
		listDto.setTotalSize(totalSize);
		return listDto;
	}

	/**
	 * Author:Vidya S K Modified Date: 20/2/2020 Description: update Organization
	 */
	@Override
	public String updateOrganization(OrganizationModel id) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ORGANISATION.name(), SeverityTypes.DEBUG.ordinal(),
				"updateOrganization in OrganizationServiceImpl ")+ JsonUtil.toJsonString(id));
		OrganizationDomain message = new OrganizationDomain();
		BeanUtils.copyProperties(id, message);
		return organizationDAO.updateOrganization(message);
	}

	/**
	 * Author:Vidya S K Modified Date: 20/2/2020
	 */
	@Override
	public OrganizationModel getOrganization(int id) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ORGANISATION.name(), SeverityTypes.DEBUG.ordinal(),
				"getOrganizationbyId in OrganizationServiceImpl ")+ JsonUtil.toJsonString(id));

		OrganizationDomain organizationDomain = organizationDAO.getOrganization(id);
		OrganizationModel organizationModel = new OrganizationModel();
		if (organizationDomain == null)
			throw new NOT_FOUND("Organization not found");
		BeanUtils.copyProperties(organizationDomain, organizationModel);
		return organizationModel;
	}

	/**
	 * Author:Vidya S K Modified Date: 20/2/2020 Description: Delete Organization
	 */
	@Override
	public String deleteOrganization(int id) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ORGANISATION.name(), SeverityTypes.DEBUG.ordinal(),
				"deleteOrganization in OrganizationServiceImpl ")+ JsonUtil.toJsonString(id));


		return organizationDAO.deleteOrganization(id);
	}
}
