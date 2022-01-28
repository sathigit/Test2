package com.atpl.mmg.service.franchisevehicles;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.config.MMGProperties;
import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.dao.franchisevehicles.FranchiseVehicleDAO;
import com.atpl.mmg.domain.franchisevehicles.FranchiseVehicleDomain;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.mapper.franchisevehicles.FranchiseVehicleMapper;
import com.atpl.mmg.model.franchisevehicles.FranchiseVehicleModel;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("franchiseVehicleService")
public class FranchiseVehicleServiceImpl implements FranchiseVehicleService, Constants {

	@Autowired
	FranchiseVehicleDAO franchiseVehicleDAO;

	@Autowired
	FranchiseVehicleMapper franchiseVehicleMapper;

	@Autowired
	MMGProperties mmgProperties;

	ObjectMapper mapper = new ObjectMapper();

	private static final Logger logger = LoggerFactory.getLogger(FranchiseVehicleServiceImpl.class);

	public FranchiseVehicleServiceImpl() {
		// constructor
	}

	@Override
	public List<FranchiseVehicleModel> getAllVehicleImages(String goodsTypeId, String kerbWeightId,Map<String, String> reqParam) throws Exception {
		double numberOfTon = 0;
		if (reqParam.size() > 0) {
			if (reqParam.containsKey("ton")) {
				String noOfTon = reqParam.get("ton");
				numberOfTon = Double.parseDouble(noOfTon);
			}
		}
		List<FranchiseVehicleDomain> franchisevehicleimages = franchiseVehicleDAO.getAllVehicleImages(goodsTypeId,
				kerbWeightId,numberOfTon*Constants.CONVERT_TONS);
		for (FranchiseVehicleDomain franchiseVehicleDomain : franchisevehicleimages) {
			franchiseVehicleDomain.setGoodsTypeId(goodsTypeId);
		}
		return franchiseVehicleMapper.entityList(franchisevehicleimages);
	}

	/**
	 * Author:Vidya S K Created Date: 22/10/2019 Modified Date: 22/10/2019
	 * Description: Only one Vehicle based on goodsTypeId and kerbWeightId
	 * 
	 */
	@Override
	public List<FranchiseVehicleModel> getSingleVehicleImages(String goodsTypeId, String kerbWeightId,Map<String, String> reqParam)
			throws Exception {
		double numberOfTon = 0;
		if (reqParam.size() > 0) {
			if (reqParam.containsKey("ton")) {
				String noOfTon = reqParam.get("ton");
				numberOfTon = Double.parseDouble(noOfTon);
			}
		}
		List<FranchiseVehicleDomain> franchisevehicleimages = franchiseVehicleDAO.getSingleVehicleImages(goodsTypeId,
				kerbWeightId,numberOfTon*Constants.CONVERT_TONS);
		for (FranchiseVehicleDomain franchiseVehicleDomain : franchisevehicleimages) {
			franchiseVehicleDomain.setGoodsTypeId(goodsTypeId);
		}
		return franchiseVehicleMapper.entityList(franchisevehicleimages);
	}

}
