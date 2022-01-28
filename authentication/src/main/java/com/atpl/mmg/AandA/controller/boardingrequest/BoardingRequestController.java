package com.atpl.mmg.AandA.controller.boardingrequest;

import static com.atpl.mmg.AandA.exception.HttpResponseUtils.prepareResponse;
import static com.atpl.mmg.AandA.exception.HttpResponseUtils.prepareSuccessResponse;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atpl.mmg.AandA.constant.Constants;
import com.atpl.mmg.AandA.exception.GenericRes;
import com.atpl.mmg.AandA.model.boardingRequest.BoardingRequestModel;
import com.atpl.mmg.AandA.model.boardingRequest.EnquiryReasonModel;
import com.atpl.mmg.AandA.service.boardingRequest.BoardingRequestService;
import com.atpl.mmg.AandA.utils.CommonUtils;

@RestController
@RequestMapping("/v2")
@SuppressWarnings({ "rawtypes", "unused" })
public class BoardingRequestController implements Constants {

	private static final Logger logger = LoggerFactory.getLogger(BoardingRequestController.class);

	@Autowired
	BoardingRequestService boardingRequestService;

	@RequestMapping(value = "/boarding/request", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> saveRequest(@RequestBody @Valid BoardingRequestModel boardingRequestModel)
			throws Exception {
		return prepareSuccessResponse(boardingRequestService.saveRequest(boardingRequestModel));
	}


	@RequestMapping(value = "/boarding/request/role/{roleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getEnquiresByRoleStateStatusCity(@PathVariable("roleId") int roleId,
			@RequestParam Map<String, String> reqParam, HttpServletRequest req) throws Exception {
		return prepareSuccessResponse(
				boardingRequestService.getEnquiresByRoleStateStatusCity(roleId,false,reqParam));
	}
	
	@RequestMapping(value = "/activateDeactivateEmail", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> sendApproveEnquiryEmail(@RequestBody BoardingRequestModel boardingRequestModel)
			throws Exception {
		return prepareSuccessResponse(boardingRequestService.sendApproveEnquiryEmail(boardingRequestModel));
	}

	@RequestMapping(value = "/boardingrequest/count", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getTotalEnquiryCount(@RequestParam Map<String, String> reqParam, HttpServletRequest req) throws Exception {
		return prepareSuccessResponse(boardingRequestService.getTotalEnquiryCount(reqParam));
	}

	@RequestMapping(value = "/boardingrequest/{requestNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getRequest(@PathVariable("requestNumber") String requestNumber) throws Exception {
		return prepareSuccessResponse(boardingRequestService.getRequest(requestNumber));
	}
	
	@RequestMapping(value = "/boarding/request", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateEnquiryRequest(@RequestBody BoardingRequestModel boardingRequestModel)
			throws Exception {
		return prepareSuccessResponse(boardingRequestService.updateEnquiryRequest(boardingRequestModel));
	}

	@RequestMapping(value = "/enquiry/reason", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> updateEnquiryStatus(@RequestBody EnquiryReasonModel enquiryReasonModel)
			throws Exception {
		return prepareSuccessResponse(boardingRequestService.updateEnquiryStatus(enquiryReasonModel));
	}
	
	@RequestMapping(value = "/enquiry/reason/{enquiryId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getEnquiryReason(@PathVariable("enquiryId") String enquiryId,@RequestParam Map<String, String> reqParam)
			throws Exception {
		return prepareSuccessResponse(boardingRequestService.getEnquiryReasonList(enquiryId,reqParam));
	}
	
	@RequestMapping(value = "/boarding/enquiry/reasons/{enquiryId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> getEnquiryReasonListByEnquiryId(@PathVariable("enquiryId") String enquiryId)
			throws Exception {
		return prepareSuccessResponse(boardingRequestService.getEnquiryReasonListByEnquiryId(enquiryId));
	}
	
	@RequestMapping(value = "/enquiry/download/role/{roleId}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> downloadEnquiryList(@PathVariable("roleId") int roleId,@RequestParam Map<String,String> reqParam, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return prepareResponse(boardingRequestService.downloadEnquiryList(roleId,reqParam),
				CommonUtils.getFileHeaders("Enquiry_List.xlsx"));
	}

	@RequestMapping(value = "/boardingrequest/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<GenericRes> boardingRequestSearch(@RequestParam Map<String, String> reqParam, HttpServletRequest req) throws Exception {
		return prepareSuccessResponse(boardingRequestService.boardingRequestSearch(reqParam));
	}

}
