package com.atpl.mmg.service.leadprofession;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.dao.leadprofession.LeadProfessionDAO;
import com.atpl.mmg.domain.lead.LeadDomain;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;
import com.atpl.mmg.mapper.lead.LeadMapper;
import com.atpl.mmg.model.lead.LeadModel;
import com.atpl.mmg.utils.CommonUtils;
import com.atpl.mmg.utils.ListDto;

@Service("leadProfessionService")
public class LeadProfessionServiceImpl implements LeadProfessionService {

	@Autowired
	LeadProfessionDAO leadProfessionDAO;

	@Autowired
	LeadMapper leadMapper;

	private static final Logger logger = LoggerFactory.getLogger(LeadProfessionServiceImpl.class);

	public LeadProfessionServiceImpl() {
		// constructor
	}

	@Override
	public String save(LeadModel leadModel) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_REMARKS.name(), SeverityTypes.DEBUG.ordinal(),
				" save in LeadProfessionServiceImpl ") + JsonUtil.toJsonString(leadModel));

		LeadDomain leadDomain = new LeadDomain();
		BeanUtils.copyProperties(leadModel, leadDomain);
		leadDomain.setUuid(CommonUtils.generateRandomId());
		leadDomain.setStatus(true);
		return leadProfessionDAO.save(leadDomain);
	}

	@Override
	public String update(LeadModel leadModel) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_REMARKS.name(), SeverityTypes.DEBUG.ordinal(),
				" update in LeadProfessionServiceImpl ") + JsonUtil.toJsonString(leadModel));


		leadProfessionDAO.getLeadProfession(leadModel.getUuid());
		LeadDomain leadDomain = new LeadDomain();
		BeanUtils.copyProperties(leadModel, leadDomain);
		leadDomain.setStatus(true);
		return leadProfessionDAO.update(leadDomain);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ListDto getLeadProfession(@RequestParam Map<String,String> reqParam) throws Exception {
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
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_PROFESSION.name(), SeverityTypes.DEBUG.ordinal(),
				" getLeadProfession in LeadProfessionServiceImpl "));
		List<LeadDomain> leadDomain = leadProfessionDAO.getLeadProfession(lowerBound, upperBound);
		List<LeadModel> goodstypeModel = leadMapper.entityList(leadDomain);
		ListDto listDto = new ListDto();
		listDto.setList(goodstypeModel);
		totalSize = leadProfessionDAO.getLeadProfessionCount().getTotal();
		listDto.setTotalSize(totalSize);
		return listDto;

	}

	@Override
	public String delete(String uuid) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.LEAD_REMARKS.name(), SeverityTypes.DEBUG.ordinal(),
				" delete in LeadProfessionServiceImpl ") + JsonUtil.toJsonString(uuid));

		leadProfessionDAO.getLeadProfession(uuid);
		return leadProfessionDAO.delete(uuid);
	}

}
