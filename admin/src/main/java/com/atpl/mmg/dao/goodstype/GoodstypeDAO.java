package com.atpl.mmg.dao.goodstype;

import java.util.List;

import com.atpl.mmg.domain.dashboard.DashboardDomain;
import com.atpl.mmg.domain.goodstype.GoodstypeDomain;

@SuppressWarnings("rawtypes")
public interface GoodstypeDAO {
	
	public String saveGoodsType(GoodstypeDomain organizationDomain) throws Exception;
	
	public List<GoodstypeDomain> getGoodsType(int lowerBound,int upperBound) throws Exception;
	
	public GoodstypeDomain getGoodsType(int id) throws Exception;
	
	public DashboardDomain getGoodsTypeCount() throws Exception;
	
	public GoodstypeDomain getGoodsTypes(String name) throws Exception;
	
	public String updateGoodsType(GoodstypeDomain id) throws Exception;
	
	public String deleteGoodsType(int id)throws Exception;
	
}
