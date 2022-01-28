package com.atpl.mmg.AandA.dao.boardingRequest;

import java.util.List;

import com.atpl.mmg.AandA.domain.boardingRequest.BoardingRequestDomain;
import com.atpl.mmg.AandA.domain.boardingRequest.EnquiryReasonDomain;
import com.atpl.mmg.AandA.domain.dashboard.DashboardDomain;

public interface BoardingRequestDAO {

	public String saveRequest(BoardingRequestDomain boardingRequestDomain) throws Exception;

	public String updateEnquiryStatus(String uuid, String status) throws Exception;

	public String updateOnHoldReason(BoardingRequestDomain boardingRequestDomain) throws Exception;

	public List<BoardingRequestDomain> getEnquires(int roleId, String status, int lowerBound, int upperBound)
			throws Exception;

	public BoardingRequestDomain getEnquiryRequest(String uuid) throws Exception;

	public BoardingRequestDomain getRequestByValidateEnquiry(String validateEnquiry) throws Exception;

	public BoardingRequestDomain getRequest(String requestNumber) throws Exception;

	public List<BoardingRequestDomain> getEnquiresByCityAndStatus(String status, int roleId, int cityId, int lowerBound,
			int upperBound) throws Exception;

	public List<BoardingRequestDomain> getRequestListByStateAndStatus(String status, int roleId, int stateId,
			int lowerBound, int upperBound) throws Exception;

	public List<BoardingRequestDomain> getRequestListOnStateAndCity(String status, int roleId, int stateId, int cityId,
			int lowerBound, int upperBound) throws Exception;

	public String updateEnquiryRequest(BoardingRequestDomain boardingRequestDomain) throws Exception;

	// Enquiry Reason
	public EnquiryReasonDomain saveEnquiryReason(EnquiryReasonDomain enquiryReasonDomain) throws Exception;

	public List<EnquiryReasonDomain> getEnquiryReasonList(String enquiryId,int lowerBound,int upperBound) throws Exception;

	public BoardingRequestDomain getRequest(int cityId, String mobileNumber, String emailId, int roleId)
			throws Exception;

	// count
	
	public DashboardDomain getEnquiresCountOnRoleStatus(int roleId, String status) throws Exception;

	public DashboardDomain getCountRequestListOnStateAndCity(String status, int roleId, int stateId, int cityId)
			throws Exception;

	public DashboardDomain getCountRequestListByStateAndStatus(String status, int roleId, int stateId) throws Exception;

	public DashboardDomain getCountEnquiresByCityAndStatus(String status, int roleId, int cityId) throws Exception;
	
	public DashboardDomain getEnquiryReasonListCount (String enquiryId) throws Exception;
	
	public List<BoardingRequestDomain> boardingRequestSearchList(int roleId,String status,int cityId,int stateId,String searchText,int lowerBound,int upperBound) throws Exception;
	
	public int getboardingRequestSearchCount(int roleId,String status,int cityId,int stateId,String searchText) throws Exception;

}
