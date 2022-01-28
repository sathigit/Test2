package com.atpl.mmg.service.quotation;

import java.util.List;

import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.model.quotation.QuotationModel;

@SuppressWarnings("unused")
public interface QuotationService {
	
	public String saveQuotation(QuotationModel qsuotationModel) throws Exception;
	
	public QuotationModel getQuotations(String  id) throws Exception;
	
	public List<QuotationModel> getQuotation() throws Exception;
	
	public List<QuotationModel> getQuotationByCustomer(String customerId) throws Exception;

	public String updateQuotation(QuotationModel id) throws Exception;

	public String sendQuotation(QuotationModel quotationModel) throws Exception;
	
	public List<QuotationModel> getQuotation(String status,int cityId) throws Exception;
}
