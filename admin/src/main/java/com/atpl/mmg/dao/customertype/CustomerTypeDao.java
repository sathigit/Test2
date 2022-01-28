package com.atpl.mmg.dao.customertype;

import java.util.List;

import com.atpl.mmg.domain.customertype.CustomerTypeDomain;

public interface CustomerTypeDao {

	public String save(CustomerTypeDomain customerTypeDomain) throws Exception;

	public List<CustomerTypeDomain> getCustomerType(Boolean status) throws Exception;

	public CustomerTypeDomain getCustomerTypeById(int id) throws Exception;
	
	public CustomerTypeDomain getCustomerTypeByName(String name) throws Exception;

	public String update(CustomerTypeDomain customerTypeDomain) throws Exception;
	
	public String updateStatus(int id,boolean status) throws Exception;

	public String delete(int id) throws Exception;
}
