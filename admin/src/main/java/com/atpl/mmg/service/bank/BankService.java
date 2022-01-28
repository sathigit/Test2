package com.atpl.mmg.service.bank;

import java.util.Map;

import com.atpl.mmg.model.bank.BankModel;
import com.atpl.mmg.utils.ListDto;

@SuppressWarnings("rawtypes")
public interface BankService {
	
	public String saveBank(BankModel bankModel) throws Exception;
	
	public  BankModel getBank(int  id) throws Exception;
	
//	public List<BankModel> getBank() throws Exception;
	
	public ListDto getBankList(Map<String, String> reqParam) throws Exception;

	public String updateBank(BankModel id) throws Exception;

	public String deleteBank(int id)throws Exception;
}
