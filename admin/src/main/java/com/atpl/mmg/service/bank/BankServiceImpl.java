package com.atpl.mmg.service.bank;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.dao.bank.BankDAO;
import com.atpl.mmg.domain.bank.BankDomain;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;
import com.atpl.mmg.mapper.bank.BankMapper;
import com.atpl.mmg.model.bank.BankModel;
import com.atpl.mmg.utils.CommonUtils;
import com.atpl.mmg.utils.ListDto;

@Service("bankService")
@SuppressWarnings("rawtypes")
public class BankServiceImpl implements BankService, Constants {

	@Autowired
	BankDAO bankDAO;

	@Autowired
	BankMapper bankMapper;

	private static final Logger logger = LoggerFactory.getLogger(BankServiceImpl.class);

	public BankServiceImpl() {
		// constructor
	}

	/**
	 * Author:Vidya S K,Sindhu Modified Date: 19/2/2020,29/2/2020 Description: Save
	 * Bank
	 * 
	 */
	public String saveBank(BankModel bankModel) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BANK.name(), SeverityTypes.DEBUG.ordinal(),
				"Exception saveBank in BankServiceImpl " + JsonUtil.toJsonString(bankModel)));
		BankDomain bankDomain = new BankDomain();
		BeanUtils.copyProperties(bankModel, bankDomain);
		return bankDAO.saveBank(bankDomain);
	}

	@Override
	public ListDto getBankList(Map<String, String> reqParam) throws Exception {
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
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BANK.name(), SeverityTypes.DEBUG.ordinal(),
				"Exception getBankList in BankServiceImpl "));

		ListDto<BankDomain> listDto = new ListDto<BankDomain>();
		List<BankDomain> bank = bankDAO.getBank(lowerBound, upperBound);
		totalSize = bankDAO.getBankCount().getTotal();
		listDto.setList(bank);
		listDto.setTotalSize(totalSize);
		return listDto;
	}

	/**
	 * Author:Vidya S K Modified Date: 19/2/2020 Description: Update Bank
	 * 
	 */
	@Override
	public String updateBank(BankModel id) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BANK.name(), SeverityTypes.DEBUG.ordinal(),
				"Exception updateBank in BankServiceImpl ") + JsonUtil.toJsonString(id));
		BankDomain message = new BankDomain();
		BeanUtils.copyProperties(id, message);
		return bankDAO.updateBank(message);

	}

	/**
	 * Author:Vidya S K Modified Date: 20/2/2020 Description: Bank
	 * 
	 */
	@Override
	public BankModel getBank(int id) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BANK.name(), SeverityTypes.DEBUG.ordinal(),
				"Exception getBankById in BankServiceImpl ") + JsonUtil.toJsonString(id));
		BankDomain bankDomain = bankDAO.getBank(id);
		BankModel bankModel = new BankModel();
		if (bankDomain == null)
			throw new NOT_FOUND("Bank not found");
		BeanUtils.copyProperties(bankDomain, bankModel);
		return bankModel;
	}

	/**
	 * Author:Vidya S K Modified Date: 19/2/2020 Description: Delete Bank
	 * 
	 */
	@Override
	public String deleteBank(int id) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BANK.name(), SeverityTypes.DEBUG.ordinal(),
				"Exception deleteBank in BankServiceImpl ") + JsonUtil.toJsonString(id));
		return bankDAO.deleteBank(id);
	}

}
