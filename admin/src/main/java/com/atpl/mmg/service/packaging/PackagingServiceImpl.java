package com.atpl.mmg.service.packaging;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.dao.packaging.PackagingDAO;
import com.atpl.mmg.domain.packaging.PackagingDomain;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;
import com.atpl.mmg.mapper.packaging.PackagingMapper;
import com.atpl.mmg.model.packaging.PackagingModel;
import com.atpl.mmg.utils.CommonUtils;
import com.atpl.mmg.utils.ListDto;

@Service("PackagingService")
public class PackagingServiceImpl implements PackagingService, Constants {

	@Autowired
	PackagingDAO packagingDAO;

	@Autowired
	PackagingMapper packagingMapper;

	@Override
	public String savePackaging(PackagingModel packagingModel) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PACKAGE.name(), SeverityTypes.DEBUG.ordinal(),
				"savePackaging in PackagingServiceImpl ") + JsonUtil.toJsonString(packagingModel));
		PackagingDomain packagingDomain = new PackagingDomain();
		BeanUtils.copyProperties(packagingModel, packagingDomain);
		return packagingDAO.savePackaging(packagingDomain);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ListDto getPackagingList(Map<String,String> reqParam) throws Exception {
		int lowerBound = 0;
		int upperBound = 0;
		if (reqParam.size() > 0) {
			Map<String, Integer> paginationCount = CommonUtils.getPagination(reqParam);
			if (paginationCount.size() > 0) {
				lowerBound = paginationCount.get("lowerBound");
				upperBound = paginationCount.get("upperBound");
			}
		}
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PACKAGE.name(), SeverityTypes.DEBUG.ordinal(),
				" getPackagingList in PackagingServiceImpl "));
		List<PackagingDomain> packagingDomain = packagingDAO.getPackagingList(lowerBound, upperBound);
		List<PackagingModel> packagingModel = packagingMapper.entityList(packagingDomain);
		ListDto listDto = new ListDto();
		listDto.setList(packagingModel);
		listDto.setTotalSize(packagingDAO.getPackageDetailsCount().getTotal());
		return listDto;
	}

	@Override
	public PackagingModel getPackageDetails(BigInteger packagingId) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PACKAGE.name(), SeverityTypes.DEBUG.ordinal(),
				" getPackageDetailsById in PackagingServiceImpl ") + JsonUtil.toJsonString(packagingId));

		PackagingDomain packagingDomain = packagingDAO.getPackageDetails(packagingId);
		PackagingModel packagingModel = new PackagingModel();
		if (packagingDomain == null)
			throw new NOT_FOUND("Quotation not found");
		BeanUtils.copyProperties(packagingDomain, packagingModel);
		return packagingModel;
	}

	@Override
	public String updatePackagingStatus(PackagingModel packagingModel) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PACKAGE.name(), SeverityTypes.DEBUG.ordinal(),
				" updatePackagingStatus in PackagingServiceImpl ") + JsonUtil.toJsonString(packagingModel));

		PackagingDomain packagingDomain = new PackagingDomain();
		BeanUtils.copyProperties(packagingModel, packagingDomain);
		return packagingDAO.updatePackagingStatus(packagingDomain);
	}

	@Override
	public String deletePackaging(BigInteger packagingId) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PACKAGE.name(), SeverityTypes.DEBUG.ordinal(),
				"deletePackaging in PackagingServiceImpl ") + JsonUtil.toJsonString(packagingId));

		return packagingDAO.deletePackaging(packagingId);
	}

	@Override
	public String updatePackagingName(PackagingModel packagingModel) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.PACKAGE.name(), SeverityTypes.DEBUG.ordinal(),
				"updatePackagingName in PackagingServiceImpl ") + JsonUtil.toJsonString(packagingModel));

		PackagingDomain packagingDomain = new PackagingDomain();
		BeanUtils.copyProperties(packagingModel, packagingDomain);
		return packagingDAO.updatePackagingName(packagingDomain);
	}

}
