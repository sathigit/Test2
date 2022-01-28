package com.atpl.mmg.service.leadremarks;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.dao.leadremarks.LeadRemarksDAO;
import com.atpl.mmg.domain.lead.LeadDomain;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;
import com.atpl.mmg.mapper.lead.LeadMapper;
import com.atpl.mmg.model.lead.LeadModel;
import com.atpl.mmg.utils.CommonUtils;
import com.atpl.mmg.utils.ListDto;

@Service("leadRemarksService")
public class LeadRemarksServiceImpl implements LeadRemarksService {

	@Autowired
	LeadRemarksDAO leadRemarksDAO;

	@Autowired
	LeadMapper leadMapper;

	private static final Logger logger = LoggerFactory.getLogger(LeadRemarksServiceImpl.class);

	public LeadRemarksServiceImpl() {
		// constructor
	}

	@Override
	public String save(LeadModel leadModel) throws Exception {
		LeadDomain leadDomain = new LeadDomain();
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_REMARKS.name(), SeverityTypes.DEBUG.ordinal(),
				" save in LeadRemarksServiceImpl ") + JsonUtil.toJsonString(leadModel));

		BeanUtils.copyProperties(leadModel, leadDomain);
		leadDomain.setUuid(CommonUtils.generateRandomId());
		leadDomain.setStatus(true);
		return leadRemarksDAO.save(leadDomain);
	}

	@Override
	public String update(LeadModel leadModel) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_REMARKS.name(), SeverityTypes.DEBUG.ordinal(),
				" update in LeadRemarksServiceImpl ") + JsonUtil.toJsonString(leadModel));
		leadRemarksDAO.getLeadRemarks(leadModel.getUuid());
		LeadDomain leadDomain = new LeadDomain();
		BeanUtils.copyProperties(leadModel, leadDomain);
		leadDomain.setStatus(true);
		return leadRemarksDAO.update(leadDomain);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ListDto getLeadRemarks(Map<String,String> reqParam) throws Exception {
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
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_REMARKS.name(), SeverityTypes.DEBUG.ordinal(),
				"getLeadRemarks in LeadRemarksServiceImpl "));
		List<LeadDomain> leadDomain = leadRemarksDAO.getLeadRemarks(lowerBound, upperBound);
		List<LeadModel> goodstypeModel = leadMapper.entityList(leadDomain);
		ListDto listDto = new ListDto();
		listDto.setList(goodstypeModel);
		totalSize = leadRemarksDAO.getLeadRemarksCount().getTotal();
		listDto.setTotalSize(totalSize);
		return listDto;

	}

	@Override
	public String delete(String uuid) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_REMARKS.name(), SeverityTypes.DEBUG.ordinal(),
				"delete in LeadRemarksServiceImpl "));

		leadRemarksDAO.getLeadRemarks(uuid);
		return leadRemarksDAO.delete(uuid);
	}

}
