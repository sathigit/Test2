package com.atpl.mmg.dao.quotation;

import java.util.List;

import com.atpl.mmg.domain.quotation.QuotationDomain;
import com.atpl.mmg.exception.GenericRes;

@SuppressWarnings("unused")
public interface QuotationDAO {
	
	public String saveQuotation(QuotationDomain quotationDomain) throws Exception;
	
	public List<QuotationDomain> getQuotation() throws Exception;
	
	public QuotationDomain getQuotations(String id) throws Exception;
	
	public List<QuotationDomain> getQuotationByCustomer(String customerId) throws Exception;
	
	public String updateQuotation(QuotationDomain id) throws Exception;
	
	public List<QuotationDomain> getQuotation(String status,String city) throws Exception;
	
	public QuotationDomain getDashboard(String customerId) throws Exception;

}
