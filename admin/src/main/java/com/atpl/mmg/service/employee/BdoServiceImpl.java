package com.atpl.mmg.service.employee;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
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
import com.atpl.mmg.dao.employee.BdoDao;
import com.atpl.mmg.dao.employee.EmployeeDAO;
import com.atpl.mmg.domain.employee.BdoDomain;
import com.atpl.mmg.domain.employee.EmployeeDomain;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.FRANCHISE_NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.PROFILE_NOT_FOUND;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;
import com.atpl.mmg.mapper.employee.BdoMapper;
import com.atpl.mmg.model.employee.BdoModel;
import com.atpl.mmg.utils.CommonUtils;
import com.atpl.mmg.utils.IDGeneration;
import com.atpl.mmg.utils.ListDto;

/* Author:Sindhu
 * creationDate:17-11-2019
 * Description:Bdo Mapping to Bdm*/

@SuppressWarnings({ "unused" })
@Service("bdoService")
public class BdoServiceImpl implements BdoService {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	BdoDao bdoDao;

	@Autowired
	BdoMapper bdoMapper;

	@Autowired
	MMGProperties mmgProperties;

	@Autowired
	IDGeneration idGeneration;

	@Autowired
	EmployeeDAO employeeDAO;

	@Override
	public String saveBdo(BdoModel bdoModel) throws Exception {
		BdoDomain bdoDomain = new BdoDomain();
		BeanUtils.copyProperties(bdoModel, bdoDomain);
		if (bdoDomain.getBdmId() == null || bdoDomain.getBdmId().isEmpty())
			throw new NOT_FOUND("Please select the bdm");
		if (null == bdoDomain.getFranchiseId() || bdoDomain.getFranchiseId().isEmpty())
			throw new NOT_FOUND("Please select the franchise");
		EmployeeDomain employeeDomain = employeeDAO.getEmployeeDetail(Integer.parseInt(bdoDomain.getBdoId()));
		bdoDomain.setBdoId(Integer.toString(employeeDomain.getProfileId()));
		return bdoDao.saveBdo(bdoDomain);
	}

	private BdoDomain getDetails(String profileId, String franchiseId) throws Exception {
		BdoDomain bdoDomain = new BdoDomain();
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getAuthUrl() +"v2"+ "/profile/" + profileId,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.AUTH.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		JSONObject data = (JSONObject) jsonResponse.get("data");
		if (jsonResponse != null) {
			if (statusCode != HttpStatus.OK.value()) {
				throw new PROFILE_NOT_FOUND();
			} else {
				String id= data.getString("id");
				bdoDomain.setProfileId(id);
				String firstName = data.getString("firstName");
				bdoDomain.setFirstName(firstName);
				String emailId = data.getString("emailId");
				bdoDomain.setEmailId(emailId);
				String mobileNumber = data.getString("mobileNumber");
				BigInteger phno = new BigInteger(mobileNumber);
				bdoDomain.setMobileNumber(phno);
			}
		} else {
			throw new BACKEND_SERVER_ERROR();
		}
		/**tieups micro service**/
		Map<String, Object> franchiseResponse = GenericHttpClient.doHttpGet(
				null+"v1" + "/franchiseDetail/" + franchiseId,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.TIEUPS.name());
		int franchisestatusCode = (int) franchiseResponse.get("statusCode");
		JSONObject franchisejsonResponse = (JSONObject) franchiseResponse.get("response");
		JSONObject franchisedata = (JSONObject) franchisejsonResponse.get("data");
		if (franchisejsonResponse != null) {
			if (franchisestatusCode != HttpStatus.OK.value()) {
				throw new FRANCHISE_NOT_FOUND(franchiseId);
			} else {
				bdoDomain.setFranchiseId(franchiseId);
				String franchiseName = franchisedata.getString("companyName");
				bdoDomain.setFranchiseName(franchiseName);
			}
		} else {
			throw new BACKEND_SERVER_ERROR();
		}
		return bdoDomain;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ListDto getBdoList(int bdmId,Map<String,String> reqParam) throws Exception {
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
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BDO.name(), SeverityTypes.DEBUG.ordinal(),
				"To getBdoList in BdoServiceImpl "));
		List<BdoDomain> bdoDomains = bdoDao.getbdoList(bdmId,lowerBound,upperBound);
		BdoDomain bdo = new BdoDomain();
		List<BdoDomain> list = new ArrayList<BdoDomain>();
		for (BdoDomain bdoDomain : bdoDomains) {
			List<BdoDomain> bdolist = new ArrayList<BdoDomain>();
			bdo = getDetails(bdoDomain.getBdoId(), bdoDomain.getFranchiseId());
			bdolist.add(new BdoDomain(bdo.getFranchiseId(), bdo.getFirstName(), bdo.getEmailId(), bdo.getMobileNumber(),
					bdo.getFranchiseName(), bdo.getProfileId()));
			list.addAll(bdolist);
		}
		List<BdoModel> bdoModel =  bdoMapper.entityList(list);
		ListDto listDto = new ListDto();
		listDto.setList(bdoModel);
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BDO.name(), SeverityTypes.DEBUG.ordinal(),
				"To getBdoListCountOnBdm in BdoServiceImpl "));
		totalSize = bdoDao.getbdoCount(bdmId).getBdoCount();
		listDto.setTotalSize(totalSize);
		return listDto;
	}

	@Override
	public BdoModel getbdoCount(int bdmId) throws Exception {
		BdoDomain bdoDomain = bdoDao.getbdoCount(bdmId);
		BdoModel bdoModel = new BdoModel();
		if (bdoDomain == null)
			return null;
		BeanUtils.copyProperties(bdoDomain, bdoModel);
		return bdoModel;
	}

	@Override
	public BdoModel getbdoFranchise(int bdoId) throws Exception {
		BdoDomain bdoDomain = bdoDao.getbdoFranchise(bdoId);
		bdoDomain = getDetails(bdoDomain.getBdmId(), bdoDomain.getFranchiseId());
		BdoModel bdoModel = new BdoModel();
		BeanUtils.copyProperties(bdoDomain, bdoModel);
		return bdoModel;
	}

	@Override
	public BdoModel getbdo(String franchiseId) throws Exception {
		BdoDomain bdoDomain = bdoDao.getbdo(franchiseId);
		BdoModel bdoModel = new BdoModel();
		if (bdoDomain == null)
			return null;
		BeanUtils.copyProperties(bdoDomain, bdoModel);
		return bdoModel;
	}

}
