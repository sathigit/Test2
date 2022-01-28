package com.atpl.mmg.AandA.service.boardingRequest;

import java.util.Map;

import com.atpl.mmg.AandA.model.boardingRequest.BoardingRequestModel;
import com.atpl.mmg.AandA.model.boardingRequest.EnquiryDashboardModel;
import com.atpl.mmg.AandA.model.boardingRequest.EnquiryReasonModel;
import com.atpl.mmg.AandA.model.profile.ListDto;

@SuppressWarnings("rawtypes")
public interface BoardingRequestService {

	public String saveRequest(BoardingRequestModel boardingRequestModel) throws Exception;

	public String updateEnquiryStatus(EnquiryReasonModel enquiryReasonModel) throws Exception;

	public EnquiryDashboardModel getTotalEnquiryCount(Map<String, String> reqParam) throws Exception;

	public String sendApproveEnquiryEmail(BoardingRequestModel boardingRequestModel) throws Exception;

	public BoardingRequestModel getRequest(String requestNumber) throws Exception;

	public ListDto getEnquiresByRoleStateStatusCity(int roleId,boolean isDownload, Map<String, String> reqParam) throws Exception;

	public String updateEnquiryRequest(BoardingRequestModel boardingRequestModel) throws Exception;

	public ListDto getEnquiryReasonList(String enquiryId,Map<String,String> reqParam) throws Exception;

	public BoardingRequestModel getEnquiryReasonListByEnquiryId(String enquiryId) throws Exception;

	public byte[] downloadEnquiryList(int roleId, Map<String, String> reqParam) throws Exception;

	public ListDto boardingRequestSearch(Map<String, String> reqParam) throws Exception;
}
