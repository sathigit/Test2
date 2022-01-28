package com.atpl.mmg.service.employee;

import java.math.BigInteger;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.atpl.mmg.common.ApiUtility;
import com.atpl.mmg.common.GenericHttpClient;
import com.atpl.mmg.common.MmgEnum;
import com.atpl.mmg.config.MMGProperties;
import com.atpl.mmg.dao.employee.CrmDao;
import com.atpl.mmg.domain.employee.CrmDomain;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.PROFILE_NOT_FOUND;
import com.atpl.mmg.mapper.employee.CrmMapper;
import com.atpl.mmg.model.employee.CrmModel;

@SuppressWarnings({ "unused" })
@Service("crmService")
public class CrmServiceImpl implements CrmService {

	private static final Logger logger = LoggerFactory.getLogger(CrmServiceImpl.class);

	@Autowired
	CrmDao crmDao;

	@Autowired
	CrmMapper crmMapper;

	@Autowired
	MMGProperties mmgProperties;

	@Override
	public String savePerformance(CrmModel crmModel) throws Exception {
		CrmDomain crmDomain = new CrmDomain();
		BeanUtils.copyProperties(crmModel, crmDomain);
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getAuthUrl()+"v2" + "/profile/" + crmModel.getProfileId(),
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.AUTH.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		JSONObject data = (JSONObject) jsonResponse.get("data");
		if (jsonResponse != null) {
			if (statusCode != HttpStatus.OK.value()) {
				throw new PROFILE_NOT_FOUND();
			} else {
				String firstName = data.getString("firstName");
				crmDomain.setName(firstName);
				String emailId = data.getString("emailId");
				crmDomain.setEmailId(emailId);
				String mobileNumber = data.getString("mobileNumber");
				BigInteger phNo = new BigInteger(mobileNumber);
				crmDomain.setMobileNumber(phNo);
			}
		} else {
			throw new BACKEND_SERVER_ERROR();
		}
		return crmDao.savePerformance(crmDomain);

	}

	@Override
	public CrmModel getPerformance(int profileId) throws Exception {
		CrmDomain crmDomain = crmDao.getPerformance(profileId);
		CrmModel crmModel = new CrmModel();
		if (crmDomain == null)
			throw new NOT_FOUND("Performance details not found");
		BeanUtils.copyProperties(crmDomain, crmModel);
		return crmModel;

	}

	@Override
	public String updatePerformance(CrmModel crmModel) throws Exception {
		CrmDomain crmDomain = new CrmDomain();
		BeanUtils.copyProperties(crmModel, crmDomain);
		return crmDao.updatePerformance(crmDomain);
	}

}
