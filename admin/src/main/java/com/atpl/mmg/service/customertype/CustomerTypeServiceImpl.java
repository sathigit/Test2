package com.atpl.mmg.service.customertype;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.dao.customertype.CustomerTypeDao;
import com.atpl.mmg.domain.customertype.CustomerTypeDomain;
import com.atpl.mmg.exception.MmgRestException.CUSTOMER_TYPE_AlREADY_EXISTS;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.mapper.customertype.CustomerTypeMapper;
import com.atpl.mmg.model.customertype.CustomerTypeModel;
import com.atpl.mmg.utils.CommonUtils;

@Service("CustomerTypeService")
public class CustomerTypeServiceImpl implements CustomerTypeService {

	@Autowired
	CustomerTypeDao customerTypeDao;

	@Autowired
	CustomerTypeMapper customerTypeMapper;

	public CustomerTypeServiceImpl() {
		// constructor
	}

	public String save(CustomerTypeModel CustomerTypeModel) throws Exception {
		validatePackageTypeName(CustomerTypeModel.getTypeName().toUpperCase());
		CustomerTypeDomain CustomerTypeDomain = new CustomerTypeDomain();
		BeanUtils.copyProperties(CustomerTypeModel, CustomerTypeDomain);
		return customerTypeDao.save(CustomerTypeDomain);
	}

	@Override
	public List<CustomerTypeModel> getCustomerType(Boolean status) throws Exception {
		List<CustomerTypeDomain> CustomerTypeDomain = customerTypeDao.getCustomerType(status);
		return customerTypeMapper.entityList(CustomerTypeDomain);
	}

	@Override
	public String update(CustomerTypeModel CustomerTypeModel) throws Exception {
		validateId(CustomerTypeModel);
		validatePackageTypeName(CustomerTypeModel.getTypeName());
		CustomerTypeDomain CustomerTypeDomain = new CustomerTypeDomain();
		BeanUtils.copyProperties(CustomerTypeModel, CustomerTypeDomain);
		return customerTypeDao.update(CustomerTypeDomain);

	}

	@Override
	public CustomerTypeModel getCustomerTypeById(int id) throws Exception {
		CustomerTypeDomain customerTypeDomain = customerTypeDao.getCustomerTypeById(id);
		CustomerTypeModel customerTypeModel = new CustomerTypeModel();
		if (null == customerTypeDomain)
			throw new NOT_FOUND("CustomerType not found");
		BeanUtils.copyProperties(customerTypeDomain, customerTypeModel);
		return customerTypeModel;
	}

	@Override
	public String delete(int id) throws Exception {
		if (0 >= id) {
			throw new NOT_FOUND("Please mention the id");
		} else
			getCustomerTypeById(id);
		return customerTypeDao.delete(id);
	}

	private void validatePackageTypeName(String name) throws Exception {
		if (CommonUtils.isNullCheck(name)) {
			throw new NOT_FOUND("Please mention the customerType");
		} else {
			CustomerTypeDomain customerType = customerTypeDao.getCustomerTypeByName(name);
			if (null != customerType)
				throw new CUSTOMER_TYPE_AlREADY_EXISTS("Customer Type already exists");
		}
	}

	private void validateId(CustomerTypeModel CustomerTypeModel) throws Exception {
		if (0 >= CustomerTypeModel.getId()) {
			throw new NOT_FOUND("Please mention the id");
		} else
			getCustomerTypeById(CustomerTypeModel.getId());
	}

	@Override
	public String updateStatus(int id, boolean status) throws Exception {
		customerTypeDao.getCustomerTypeById(id);
		return customerTypeDao.updateStatus(id, status);
	}

	@Override
	public CustomerTypeModel getCustomerTypeByName(String typeName) throws Exception {
		CustomerTypeModel customerTypeModel = new CustomerTypeModel();
		CustomerTypeDomain customerTypeDomain = customerTypeDao.getCustomerTypeByName(typeName);
		BeanUtils.copyProperties(customerTypeDomain, customerTypeModel);
		return customerTypeModel;
	}
}
