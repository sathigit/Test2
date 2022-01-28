package com.atpl.mmg.service.goodstype;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.dao.goodstype.GoodstypeDAO;
import com.atpl.mmg.domain.city.CityDomain;
import com.atpl.mmg.domain.goodstype.GoodstypeDomain;
import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;
import com.atpl.mmg.mapper.goodstype.GoodstypeMapper;
import com.atpl.mmg.model.city.CityModel;
import com.atpl.mmg.model.goodstype.GoodstypeModel;
import com.atpl.mmg.utils.CommonUtils;
import com.atpl.mmg.utils.ListDto;

@Service("goodstypeService")
@SuppressWarnings("unused")
public class GoodstypeServiceImpl implements GoodstypeService, Constants {

	@Autowired
	GoodstypeDAO organizationDAO;

	@Autowired
	GoodstypeMapper organizationMapper;

	private static final Logger logger = LoggerFactory.getLogger(GoodstypeServiceImpl.class);

	public GoodstypeServiceImpl() {
		// constructor
	}

	/**
	 * Author:Vidya S K Modified Date: 20/2/2020 Description: Save GoodsType
	 * 
	 */
	public String saveGoodsType(GoodstypeModel organizationModel) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.GOODS_TYPE.name(), SeverityTypes.DEBUG.ordinal(),
				" saveGoodsType in GoodstypeServiceImpl ")+ JsonUtil.toJsonString(organizationModel));
		GoodstypeDomain organizationDomain = new GoodstypeDomain();
		BeanUtils.copyProperties(organizationModel, organizationDomain);
		return organizationDAO.saveGoodsType(organizationDomain);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ListDto getGoodsType(Map<String,String> reqParam) throws Exception {
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
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.GOODS_TYPE.name(), SeverityTypes.DEBUG.ordinal(),
				" getGoodsType in GoodstypeServiceImpl "));
		
		List<GoodstypeDomain> Organization = organizationDAO.getGoodsType(lowerBound, upperBound);
		List<GoodstypeModel> goodstypeModel = organizationMapper.entityList(Organization);
		ListDto listDto = new ListDto();
		listDto.setList(goodstypeModel);
		listDto.setTotalSize(organizationDAO.getGoodsTypeCount().getTotal());
		return listDto;
		}

	@Override
	public String updateGoodsType(GoodstypeModel id) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.GOODS_TYPE.name(), SeverityTypes.DEBUG.ordinal(),
				" updateGoodsType in GoodstypeServiceImpl "));

		GoodstypeDomain message = new GoodstypeDomain();
		BeanUtils.copyProperties(id, message);
		return organizationDAO.updateGoodsType(message);
	}

	@Override
	public GoodstypeModel getGoodsType(int id) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.GOODS_TYPE.name(), SeverityTypes.DEBUG.ordinal(),
				" getGoodsTypeById in GoodstypeServiceImpl ") + JsonUtil.toJsonString(id));
		GoodstypeDomain organizationDomain = organizationDAO.getGoodsType(id);
		GoodstypeModel organizationModel = new GoodstypeModel();
		if (organizationDomain == null)
			throw new NOT_FOUND("GoodsType not found");
		BeanUtils.copyProperties(organizationDomain, organizationModel);
		return organizationModel;
	}

	@Override
	public String deleteGoodsType(int id) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.GOODS_TYPE.name(), SeverityTypes.DEBUG.ordinal(),
				" deleteGoodsType in GoodstypeServiceImpl ") + JsonUtil.toJsonString(id));
		return organizationDAO.deleteGoodsType(id);
	}

	@Override
	public GoodstypeModel getGoodsTypes(String name) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.GOODS_TYPE.name(), SeverityTypes.DEBUG.ordinal(),
				"Exception getGoodsTypesByName in GoodstypeServiceImpl ") + JsonUtil.toJsonString(name));

		GoodstypeDomain organizationDomain = organizationDAO.getGoodsTypes(name);
		GoodstypeModel organizationModel = new GoodstypeModel();
		if (organizationDomain == null)
			throw new NOT_FOUND("GoodsType not found");
		BeanUtils.copyProperties(organizationDomain, organizationModel);
		return organizationModel;
	}

}
