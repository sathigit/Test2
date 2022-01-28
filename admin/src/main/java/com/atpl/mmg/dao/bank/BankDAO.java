package com.atpl.mmg.dao.bank;

import java.util.List;

import com.atpl.mmg.domain.bank.BankDomain;
import com.atpl.mmg.domain.dashboard.DashboardDomain;


@SuppressWarnings("rawtypes")
public interface BankDAO {
	
	public String saveBank(BankDomain bankDomain) throws Exception;
	
	public List<BankDomain> getBank(int lowerBound, int upperBound) throws Exception;
	
	public DashboardDomain getBankCount() throws Exception;
	
	public BankDomain getBank(int id) throws Exception;
	
	public String updateBank(BankDomain id) throws Exception;
	
	public String deleteBank(int id)throws Exception;
	
}
