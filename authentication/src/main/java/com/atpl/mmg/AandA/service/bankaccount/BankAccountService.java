package com.atpl.mmg.AandA.service.bankaccount;

import java.util.List;
import java.util.Map;

import com.atpl.mmg.AandA.model.bankaccount.BankAccountModel;
import com.atpl.mmg.AandA.model.bankaccount.BankAccountModelDTO;

public interface BankAccountService {
	
	public String save(BankAccountModel bankAccountModel) throws Exception;

	public List<BankAccountModel> getBankAccounts(String profileId,Map<String, String> reqParam) throws Exception;
	
	public BankAccountModel getBankAccountsByUuid(String profileId) throws Exception;
	
	public String updateBank(BankAccountModelDTO bankAccountModelDTO) throws Exception;
	
	public String activateDeactivateBank(String uuid,boolean status) throws Exception;

}
