package com.atpl.mmg.service.goodstype;

import java.util.Map;

import com.atpl.mmg.model.goodstype.GoodstypeModel;
import com.atpl.mmg.utils.ListDto;

@SuppressWarnings("rawtypes")
public interface GoodstypeService {

	public String saveGoodsType(GoodstypeModel organizationModel) throws Exception;

	public GoodstypeModel getGoodsType(int id) throws Exception;

	public GoodstypeModel getGoodsTypes(String name) throws Exception;

	public ListDto getGoodsType(Map<String,String> reqParam) throws Exception;

	public String updateGoodsType(GoodstypeModel id) throws Exception;

	public String deleteGoodsType(int id) throws Exception;
}
