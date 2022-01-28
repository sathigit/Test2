package com.atpl.mmg.AandA.dao.bankaccount;

import java.math.BigInteger;
import java.util.List;

import com.atpl.mmg.AandA.domain.bankaccount.BankAccountDomain;

public interface BankAccountDAO {

	public String save(BankAccountDomain bankAccountDomain) throws Exception;
	
	public List<BankAccountDomain> getAllBankAccountsByProfileId(String profileId,Boolean status) throws Exception;

	public List<BankAccountDomain> getBankAccounts(String profileId,boolean status) throws Exception;
	
	public BankAccountDomain checkAccountNumber(BigInteger accountNumber) throws Exception;
	
	public BankAccountDomain getBankAccountsByUuid(String uuid) throws Exception;
	
	public String updateBank(BankAccountDomain bankAccountDomain) throws Exception;
	
	public String updateBankStatus(String uuid,boolean status) throws Exception;
	
	public BankAccountDomain getBankAccountValidateByBankAccount(BankAccountDomain bankAccountDomain) throws Exception;
}
