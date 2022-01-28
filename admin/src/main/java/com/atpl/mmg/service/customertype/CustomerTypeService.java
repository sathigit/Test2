package com.atpl.mmg.service.customertype;

import java.util.List;

import com.atpl.mmg.model.customertype.CustomerTypeModel;

public interface CustomerTypeService {

	public String save(CustomerTypeModel customerTypeModel) throws Exception;

	public CustomerTypeModel getCustomerTypeById(int id) throws Exception;
	
	public CustomerTypeModel  getCustomerTypeByName(String typeName) throws Exception;

	public List<CustomerTypeModel> getCustomerType(Boolean status) throws Exception;

	public String update(CustomerTypeModel customerTypeModel) throws Exception;
	
	public String updateStatus(int id,boolean status) throws Exception;

	public String delete(int id) throws Exception;

}
