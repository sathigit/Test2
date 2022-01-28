package com.atpl.mmg.service.leadstatus;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.dao.leadstatus.LeadStatusDAO;
import com.atpl.mmg.domain.lead.LeadDomain;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;
import com.atpl.mmg.mapper.lead.LeadMapper;
import com.atpl.mmg.model.lead.LeadModel;
import com.atpl.mmg.utils.CommonUtils;
import com.atpl.mmg.utils.ListDto;

@Service("leadStatusService")
public class LeadStatusServiceImpl implements LeadStatusService{
	
	@Autowired
	LeadStatusDAO leadStatusDAO;

	@Autowired
	LeadMapper leadMapper;

	private static final Logger logger = LoggerFactory.getLogger(LeadStatusServiceImpl.class);

	public LeadStatusServiceImpl() {
		// constructor
	}

	@Override
	public String save(LeadModel leadModel) throws Exception {
		Loggly.sendLogglyEvent(
				EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_PROFESSION.name(), SeverityTypes.CRITICAL.ordinal(),
						"save LeadStatus in LeadStatusServiceImpl" + JsonUtil.toJsonString(leadModel)));
		LeadDomain leadDomain = new LeadDomain();
		BeanUtils.copyProperties(leadModel, leadDomain);
		leadDomain.setUuid(CommonUtils.generateRandomId());
		leadDomain.setStatus(true);
		return leadStatusDAO.save(leadDomain);
	}

	@Override
	public String update(LeadModel leadModel) throws Exception {
		Loggly.sendLogglyEvent(
				EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_PROFESSION.name(), SeverityTypes.CRITICAL.ordinal(),
						"update LeadStatus in LeadStatusServiceImpl" + JsonUtil.toJsonString(leadModel)));

        leadStatusDAO.getLeadStatus(leadModel.getUuid());
		LeadDomain leadDomain = new LeadDomain();
		BeanUtils.copyProperties(leadModel, leadDomain);
		leadDomain.setStatus(true);
		return leadStatusDAO.update(leadDomain);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ListDto getLeadStatus(Map<String,String> reqParam) throws Exception {
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
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_STATUS.name(), SeverityTypes.DEBUG.ordinal(),
				"getLeadStatus in LeadStatusServiceImpl "));
		List<LeadDomain> leadDomain = leadStatusDAO.getLeadStatus(lowerBound, upperBound);
		List<LeadModel> goodstypeModel = leadMapper.entityList(leadDomain);
		ListDto listDto = new ListDto();
		listDto.setList(goodstypeModel);
		totalSize = leadStatusDAO.getLeadStatusCount().getTotal();
		listDto.setTotalSize(totalSize);
		return listDto;
	}

	@Override
	public String delete(String uuid) throws Exception {
		Loggly.sendLogglyEvent(
				EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_PROFESSION.name(), SeverityTypes.CRITICAL.ordinal(),
						"deleteLeadStatus in LeadStatusServiceImpl" + JsonUtil.toJsonString(uuid)));

		 leadStatusDAO.getLeadStatus(uuid);
		return leadStatusDAO.delete(uuid);
	}

}
