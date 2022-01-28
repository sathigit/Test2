package com.atpl.mmg.service.admin;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.dao.admin.CatalogTransactionFeeDao;
import com.atpl.mmg.domain.admin.AdminDomain;
import com.atpl.mmg.domain.admin.CatalogTransactionFeeDomain;
import com.atpl.mmg.domain.bank.BankDomain;
import com.atpl.mmg.mapper.admin.CatalogTransactionFeeMapper;
import com.atpl.mmg.model.admin.CatalogTransactionFeeModel;
import com.atpl.mmg.utils.CommonUtils;

@Service("modifyService")
public class CatalogTransactionFeeServiceImpl implements CatalogTransactionFeeService {

	@Autowired
	CatalogTransactionFeeDao catalogTransactionFeeDao;

	@Autowired
	CatalogTransactionFeeMapper catalogTransactionFeeMapper;

	private static final Logger logger = LoggerFactory.getLogger(CatalogTransactionFeeServiceImpl.class);

	@Override
	public List<CatalogTransactionFeeModel> getModificationFee(CatalogTransactionFeeModel catalogTransactionFeeModel) throws Exception {
		CatalogTransactionFeeDomain catalogTransactionFeeDomain = new CatalogTransactionFeeDomain();
		BeanUtils.copyProperties(catalogTransactionFeeModel, catalogTransactionFeeDomain);
		List<CatalogTransactionFeeDomain> catalogTransactionFee = catalogTransactionFeeDao.getModificationFee(catalogTransactionFeeDomain);
		return catalogTransactionFeeMapper.entityList(catalogTransactionFee);
	}

	@Override
	public String saveModificationFee(CatalogTransactionFeeModel catalogTransactionFeeModel) throws Exception {
		CatalogTransactionFeeDomain catalogTransactionFeeDomain = new CatalogTransactionFeeDomain();
		BeanUtils.copyProperties(catalogTransactionFeeModel, catalogTransactionFeeDomain);
		catalogTransactionFeeDomain.setUuid(CommonUtils.generateRandomId());
		System.out.println(catalogTransactionFeeDomain.getUuid());
	    return catalogTransactionFeeDao.saveModificationFee(catalogTransactionFeeDomain);
	}
	
}
