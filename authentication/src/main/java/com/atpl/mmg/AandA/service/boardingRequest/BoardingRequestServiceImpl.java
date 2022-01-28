package com.atpl.mmg.AandA.service.boardingRequest;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.constant.Constants;
import com.atpl.mmg.AandA.constant.EnquiryRole;
import com.atpl.mmg.AandA.constant.EnquiryStatus;
import com.atpl.mmg.AandA.constant.Role;
import com.atpl.mmg.AandA.dao.boardingRequest.BoardingRequestDAO;
import com.atpl.mmg.AandA.dao.profile.ProfileDAOV2;
import com.atpl.mmg.AandA.dao.role.RoleDAO;
import com.atpl.mmg.AandA.domain.boardingRequest.BoardingRequestDomain;
import com.atpl.mmg.AandA.domain.boardingRequest.EnquiryReasonDomain;
import com.atpl.mmg.AandA.domain.dashboard.DashboardDomain;
import com.atpl.mmg.AandA.domain.profile.ProfileDomainV2;
import com.atpl.mmg.AandA.domain.role.RoleDomain;
import com.atpl.mmg.AandA.exception.MmgRestException;
import com.atpl.mmg.AandA.exception.MmgRestException.EMAILID_PATTERN_NOT_MATCH;
import com.atpl.mmg.AandA.exception.MmgRestException.ENQUIRY_NOT_FOUND;
import com.atpl.mmg.AandA.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.AandA.exception.MmgRestException.VALIDATE_BOARDING_ENQUIRY;
import com.atpl.mmg.AandA.log.EventsLogUtil;
import com.atpl.mmg.AandA.log.Loggly;
import com.atpl.mmg.AandA.log.ServiceTypes;
import com.atpl.mmg.AandA.log.SeverityTypes;
import com.atpl.mmg.AandA.mapper.boardingRequest.BoardingRequestMapper;
import com.atpl.mmg.AandA.mapper.boardingRequest.EnquiryReasonMapper;
import com.atpl.mmg.AandA.model.boardingRequest.BoardingRequestModel;
import com.atpl.mmg.AandA.model.boardingRequest.EnquiryDashboardModel;
import com.atpl.mmg.AandA.model.boardingRequest.EnquiryReasonModel;
import com.atpl.mmg.AandA.model.profile.ListDto;
import com.atpl.mmg.AandA.service.DBUpdateService;
import com.atpl.mmg.AandA.service.profile.AdminCommonService;
import com.atpl.mmg.AandA.service.profile.ProfileUtil;
import com.atpl.mmg.AandA.utils.CommonUtils;
import com.atpl.mmg.AandA.utils.DataValidation;
import com.atpl.mmg.AandA.utils.EmailValidator;
import com.atpl.mmg.AandA.utils.IDGeneration;

@Service("BoardingRequestService")
public class BoardingRequestServiceImpl implements BoardingRequestService {

	@Autowired
	BoardingRequestDAO boardingRequestDAO;

	@Autowired
	ProfileDAOV2 profileDAOV2;

	@Autowired
	IDGeneration idGeneration;

	@Autowired
	BoardingRequestMapper boardingRequestMapper;

	@Autowired
	EnquiryReasonMapper enquiryReasonMapper;

	@Autowired
	EmailValidator emailValidator;

	@Autowired
	DataValidation dataValidation;

	@Autowired
	RoleDAO roleDao;

	@Autowired
	AdminCommonService adminCommonService;

	@Autowired
	DBUpdateService dbUpdateService;

	@Autowired
	ProfileUtil profileUtil;

	public String validateRole(int roleId) throws Exception {
		RoleDomain role = roleDao.getRoleName(roleId);
		if (null != role)
			return role.getRoleName();
		else
			throw new VALIDATE_BOARDING_ENQUIRY("Please mention the proper role!!");
	}

	public void validateEmail(BoardingRequestDomain boardingRequestDomain) throws Exception {
		if (0 >= boardingRequestDomain.getRoleId())
			throw new VALIDATE_BOARDING_ENQUIRY("Please mention the role!!");
		else
			validateRole(boardingRequestDomain.getRoleId());
		if (null == boardingRequestDomain.getMobileNumber())
			throw new VALIDATE_BOARDING_ENQUIRY("Please mention the mobile Number");
		if (null == boardingRequestDomain.getRequestNumber() || boardingRequestDomain.getRequestNumber().isEmpty())
			throw new VALIDATE_BOARDING_ENQUIRY("Please mention the request Number");
		if (null == boardingRequestDomain.getFirstName() || boardingRequestDomain.getFirstName().isEmpty())
			throw new VALIDATE_BOARDING_ENQUIRY("Please mention the name");
		if (!dataValidation.isValidate(boardingRequestDomain.getFirstName(), DataValidation.FIRSTNAME_PATTERN))
			throw new VALIDATE_BOARDING_ENQUIRY("Please mention the valid name");
		if (!dataValidation.isValidate(boardingRequestDomain.getEmail(), DataValidation.EMAIL_PATTERN))
			throw new EMAILID_PATTERN_NOT_MATCH();
	}

	private void sendOpsTeamNotification(int stateId, String roleName, String requestNumber) throws Exception {
		List<ProfileDomainV2> operationalTeamDomain = profileDAOV2.getProfilesByOtState(stateId, true, 0, 0);
		String message = "Onboard " + roleName + " Request " + requestNumber
				+ " has been initiated. Please look into this";
		if (operationalTeamDomain.size() > 0)
			for (ProfileDomainV2 opsdomain : operationalTeamDomain) {
				emailValidator.sendSMSMessage(opsdomain.getMobileNumber(),null, message, false);
			}
	}

	private void validate(BoardingRequestModel boardingRequestModel, boolean status) throws Exception {
		if (status) {
			if (0 >= boardingRequestModel.getRoleId())
				throw new VALIDATE_BOARDING_ENQUIRY("Please mention the role");
		}
		if (0 >= boardingRequestModel.getCountryId())
			throw new VALIDATE_BOARDING_ENQUIRY("Please select the country!!");
		else
			adminCommonService.countryNameById(boardingRequestModel.getCountryId());
		if (0 >= boardingRequestModel.getStateId())
			throw new VALIDATE_BOARDING_ENQUIRY("Please select the state!!");
		else
			adminCommonService.stateNameById(boardingRequestModel.getStateId());
		if (0 >= boardingRequestModel.getCityId())
			throw new VALIDATE_BOARDING_ENQUIRY("Please select the city!!");
		else
			adminCommonService.cityNameById(boardingRequestModel.getCityId());
		if (null == boardingRequestModel.getMessage() || boardingRequestModel.getMessage().isEmpty() == true)
			throw new VALIDATE_BOARDING_ENQUIRY("Please mention the Enquiry Message!!");
		if (null == boardingRequestModel.getEmail() || boardingRequestModel.getEmail().isEmpty())
			throw new VALIDATE_BOARDING_ENQUIRY("Please enter the emailId!!");
		else if (!dataValidation.isValidate(boardingRequestModel.getEmail(), DataValidation.EMAIL_PATTERN))
			throw new EMAILID_PATTERN_NOT_MATCH();
		if (null == boardingRequestModel.getMobileNumber() || boardingRequestModel.getMobileNumber().isEmpty())
			throw new VALIDATE_BOARDING_ENQUIRY("Please enter the MobileNumber!!");
		if (null == boardingRequestModel.getFirstName() || boardingRequestModel.getFirstName().isEmpty())
			throw new VALIDATE_BOARDING_ENQUIRY("Please mention the first name");
		if (!dataValidation.isValidate(boardingRequestModel.getFirstName(), DataValidation.FIRSTNAME_PATTERN))
			throw new VALIDATE_BOARDING_ENQUIRY("Please mention the valid name");
		if (null == boardingRequestModel.getLastName() || boardingRequestModel.getLastName().isEmpty())
			throw new VALIDATE_BOARDING_ENQUIRY("Please mention the last name");
		if (!dataValidation.isValidate(boardingRequestModel.getLastName(), DataValidation.FIRSTNAME_PATTERN))
			throw new VALIDATE_BOARDING_ENQUIRY("Please mention the valid name");
	}

	@Override
	public String saveRequest(BoardingRequestModel boardingRequestModel) throws Exception {
		BoardingRequestDomain boardingRequest = new BoardingRequestDomain();
		BoardingRequestDomain boardingRequestDomain = new BoardingRequestDomain();
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"Validate Onboarding Request: " + JsonUtil.toJsonString(boardingRequestModel)));
		validate(boardingRequestModel, true);
		boardingRequestModel
				.setValidateEnquiry(boardingRequestModel.getMobileNumber() + "_" + boardingRequestModel.getEmail() + "_"
						+ boardingRequestModel.getCountryId() + "_" + boardingRequestModel.getStateId() + "_"
						+ boardingRequestModel.getCityId() + "_" + boardingRequestModel.getRoleId());
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"Validate Onboarding Request with validateEnquiryKey: " + JsonUtil.toJsonString(boardingRequestModel)));
		boardingRequest = boardingRequestDAO.getRequestByValidateEnquiry(boardingRequestModel.getValidateEnquiry());
		if (null != boardingRequest)
			throw new VALIDATE_BOARDING_ENQUIRY("You have already submitted the request!!!");
		String requestNumber = idGeneration.generateRequestNumber();
		boardingRequestModel.setRequestNumber(requestNumber);
		BeanUtils.copyProperties(boardingRequestModel, boardingRequestDomain);
		boardingRequestDomain.setUuid(CommonUtils.generateRandomId());
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"Saving Onboarding Request data into table: " + JsonUtil.toJsonString(boardingRequestModel)));

		boardingRequestDAO.saveRequest(boardingRequestDomain);
		String role = validateRole(boardingRequestModel.getRoleId());
		String message = null;
		String franchiseMsg = "";
		message = "Dear " + boardingRequestModel.getFirstName() + ",\r\nThanks for your interest in MoveMyGoods. "
				+ role + " SRN:" + boardingRequestDomain.getRequestNumber() + ". Team MMG will contact you shortly. ";

		if (boardingRequestModel.getRoleId() == Integer.parseInt(Role.FRANCHISE.getCode())) {
			franchiseMsg = "To know more about MMG Franchise click: https://youtu.be/86TgaeeF1XA";
			message = message + franchiseMsg;
		}
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "Sending Sms: " + JsonUtil.toJsonString(boardingRequestModel)));
		emailValidator.sendSMSMessage(boardingRequestModel.getMobileNumber(),null, message, false);

		Map<String, Object> variables = new HashMap<>();
		variables.put("firstName", boardingRequestDomain.getFirstName());
		variables.put("requestNumber", boardingRequestDomain.getRequestNumber());
		variables.put("roleName", role);
		String body = emailValidator.generateMailHtml("boardingrequest", variables);
		emailValidator.sendEmail("Move My Goods", boardingRequestDomain.getEmail(), body);
		// To send sms notification to operational team member
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"Notification to operation team: " + JsonUtil.toJsonString(boardingRequestModel)));
		sendOpsTeamNotification(boardingRequestModel.getStateId(), role, boardingRequestDomain.getRequestNumber());
		return null;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public String updateEnquiryStatus(EnquiryReasonModel enquiryReasonModel) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"updateEnquiryStatus: " + JsonUtil.toJsonString(enquiryReasonModel)));
		BoardingRequestDomain boardingRequestDomain = boardingRequestDAO
				.getEnquiryRequest(enquiryReasonModel.getUuid());
		EnquiryReasonDomain enquiryReasonDomain = new EnquiryReasonDomain();
		BeanUtils.copyProperties(enquiryReasonModel, enquiryReasonDomain);
		EnquiryStatus enquiry = EnquiryStatus.getEnquiryStatus(enquiryReasonModel.getStatus());
		if (null == enquiry)
			throw new VALIDATE_BOARDING_ENQUIRY("Please send the proper enquiry status as : '"
					+ EnquiryRole.PENDING.name() + "' or '" + EnquiryRole.COMPLETED.name() + "' or '"
					+ EnquiryRole.INPROCESS.name() + "' or '" + EnquiryRole.ONHOLD.name() + "' ");
		else {
			boardingRequestDAO.updateEnquiryStatus(enquiryReasonDomain.getUuid(), enquiryReasonModel.getStatus());
			enquiryReasonDomain.setUuid(CommonUtils.generateRandomId());
			enquiryReasonDomain.setPreviousStatus(boardingRequestDomain.getStatus());
			enquiryReasonDomain.setChangedStatus(enquiryReasonModel.getStatus());
			enquiryReasonDomain.setEnquiryId(enquiryReasonModel.getUuid());
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.INFORMATIONAL.ordinal(),
					"updateEnquiryStatus in boarding enquiry: " + JsonUtil.toJsonString(enquiryReasonDomain)));
			enquiryReasonDomain = boardingRequestDAO.saveEnquiryReason(enquiryReasonDomain);
			if (null != enquiryReasonDomain) {
				Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
						SeverityTypes.INFORMATIONAL.ordinal(),
						"sendEmail after updating the status: " + JsonUtil.toJsonString(enquiryReasonDomain)));

				if (!enquiryReasonDomain.getPreviousStatus().equalsIgnoreCase(enquiryReasonDomain.getChangedStatus()))
					sendEmail(enquiryReasonDomain, enquiryReasonDomain.getChangedStatus());
			}
		}
		return "Updated successfully";
	}

	private void sendEmail(EnquiryReasonDomain enquiryReasonDomain, String status) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"SendEmail when status changed :" + JsonUtil.toJsonString(enquiryReasonDomain)));

		BoardingRequestDomain boardingRequestDomain = boardingRequestDAO
				.getEnquiryRequest(enquiryReasonDomain.getEnquiryId());
		String role = validateRole(boardingRequestDomain.getRoleId());
		String body = null, message = null;
		Map<String, Object> variables = new HashMap<>();
		if (status.equals(EnquiryRole.PENDING.name())) {
			variables.put("firstName", boardingRequestDomain.getFirstName());
			variables.put("requestNumber", boardingRequestDomain.getRequestNumber());
			variables.put("message", enquiryReasonDomain.getReason());
			variables.put("roleName", role);
			message = String.format(Constants.ENQUIRY_PENDING, boardingRequestDomain.getFirstName(), role);
			body = emailValidator.generateMailHtml("enquiry", variables);
		}
		/*
		 * if (status.equals(EnquiryRole.INPROCESS.name())) { message =
		 * "Your  KYC documents for " + role +
		 * " onboarding Request have been successfully verified.Call 901 902 903 6 for support"
		 * ; emailValidator.sendSMSMessage(boardingRequestDomain.getMobileNumber(),
		 * message, false); variables.put("firstName",
		 * boardingRequestDomain.getFirstName()); variables.put("requestNumber",
		 * boardingRequestDomain.getRequestNumber()); message =
		 * String.format(Constants.ENQUIRY_INPROCESS, role); body =
		 * emailValidator.generateMailHtml("verifyemail", variables); }
		 */
		if (status.equals(EnquiryRole.ONHOLD.name())) {
			variables.put("firstName", boardingRequestDomain.getFirstName());
			variables.put("requestNumber", boardingRequestDomain.getRequestNumber());
			variables.put("roleName", role);
			variables.put("reason", enquiryReasonDomain.getReason());
			message = String.format(Constants.ENQUIRY_ONHOLD, role);
			body = emailValidator.generateMailHtml("onholdemail", variables);
		}
		if (status.equals(EnquiryRole.INPROCESS.name())) {
			variables.put("firstName", boardingRequestDomain.getFirstName());
			variables.put("requestNumber", boardingRequestDomain.getRequestNumber());
			variables.put("roleName", role);
			message = String.format(Constants.ENQUIRY_FOLLOWUP, role);
			if (boardingRequestDomain.getRoleId() == Integer.parseInt(Role.FRANCHISE.getCode()))
				body = emailValidator.generateMailHtml("followupemail", variables);
			else
				body = emailValidator.generateMailHtml("enquiry", variables);
		}
		if (status.equals(EnquiryRole.COMPLETED.name())) {
			variables.put("firstName", boardingRequestDomain.getFirstName());
			variables.put("requestNumber", boardingRequestDomain.getRequestNumber());
			body = emailValidator.generateMailHtml("verifyemail", variables);
			message = String.format(Constants.ENQUIRY_COMPLETED, role);
		}
		emailValidator.sendSMSMessage(boardingRequestDomain.getMobileNumber(),null, message, false);
		emailValidator.sendEmail("Move My Goods", boardingRequestDomain.getEmail(), body);
	}

	@SuppressWarnings("rawtypes")
	@Override
//-	public List<BoardingRequestModel> getEnquiresByRoleStateStatusCity(int roleId, Map<String, String> reqParam)
	public ListDto getEnquiresByRoleStateStatusCity(int roleId, boolean isDownload, Map<String, String> reqParam)
			throws Exception {
		int lowerBound = 0;
		int upperBound = 0;
		int totalSize = 0;
		validateRole(roleId);
		List<BoardingRequestModel> boardingRequestModel = new ArrayList<BoardingRequestModel>();
		List<BoardingRequestDomain> boardingRequestDomain = new ArrayList<BoardingRequestDomain>();
		DashboardDomain dashboardDomain = new DashboardDomain();
		ListDto listDto = new ListDto();
		dashboardDomain = boardingRequestDAO.getEnquiresCountOnRoleStatus(roleId, null);
		totalSize = dashboardDomain.getTotal();
		String status = null;
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(),
				"getEnquiresByRoleStateStatusCity for role:" + JsonUtil.toJsonString(roleId)));

		if (reqParam.size() > 0) {
			Map<String, Integer> paginationCount = profileUtil.getPagination(reqParam);
			if (paginationCount.size() > 0) {
				lowerBound = paginationCount.get("lowerBound");
				upperBound = paginationCount.get("upperBound");
			}
			boardingRequestDomain = boardingRequestDAO.getEnquires(roleId, null, lowerBound, upperBound);
			if (reqParam.containsKey("status")) {
				Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
						SeverityTypes.INFORMATIONAL.ordinal(), "getEnquiresByRoleStateStatusCity for status:"
								+ JsonUtil.toJsonString(reqParam.get("status"))));

				EnquiryStatus enquiry = EnquiryStatus.getEnquiryStatus(reqParam.get("status"));
				if (null == enquiry)
					throw new VALIDATE_BOARDING_ENQUIRY("Please send the proper enquiry status as : '"
							+ EnquiryRole.PENDING.name() + "' or '" + EnquiryRole.COMPLETED.name() + "' or '"
							+ EnquiryRole.INPROCESS.name() + "' or '" + EnquiryRole.ONHOLD.name() + "' ");
				else {
					status = reqParam.get("status");
					boardingRequestDomain = boardingRequestDAO.getEnquires(roleId, status, lowerBound, upperBound);
					dashboardDomain = boardingRequestDAO.getEnquiresCountOnRoleStatus(roleId, status);
					totalSize = dashboardDomain.getTotal();
				}
			}
			if (reqParam.containsKey("stateId") && reqParam.containsKey("cityId")) {
				Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
						SeverityTypes.INFORMATIONAL.ordinal(),
						"getEnquiresByRoleStateStatusCity for both state and city:"));
				int stateId = Integer.parseInt(reqParam.get("stateId"));
				int cityId = Integer.parseInt(reqParam.get("cityId"));
				if (cityId > 0 && stateId > 0) {
					if (null == status || status.isEmpty()) {
						boardingRequestDomain = boardingRequestDAO.getRequestListOnStateAndCity(null, roleId, stateId,
								cityId, lowerBound, upperBound);
						dashboardDomain = boardingRequestDAO.getCountRequestListOnStateAndCity(null, roleId, stateId,
								cityId);
						totalSize = dashboardDomain.getTotal();
					} else {
						boardingRequestDomain = boardingRequestDAO.getRequestListOnStateAndCity(status, roleId, stateId,
								cityId, lowerBound, upperBound);
						dashboardDomain = boardingRequestDAO.getCountRequestListOnStateAndCity(status, roleId, stateId,
								cityId);
						totalSize = dashboardDomain.getTotal();
					}
				}
			} else {
				if (reqParam.containsKey("stateId")) {
					int stateId = Integer.parseInt(reqParam.get("stateId"));
					Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
							SeverityTypes.INFORMATIONAL.ordinal(),
							"getEnquiresByRoleStateStatusCity for state:" + JsonUtil.toJsonString(stateId)));
					if (stateId > 0) {
						if (null == status || status.isEmpty()) {
							boardingRequestDomain = boardingRequestDAO.getRequestListByStateAndStatus(null, roleId,
									stateId, lowerBound, upperBound);
							dashboardDomain = boardingRequestDAO.getCountRequestListByStateAndStatus(null, roleId,
									stateId);
							totalSize = dashboardDomain.getTotal();
						} else {
							boardingRequestDomain = boardingRequestDAO.getRequestListByStateAndStatus(status, roleId,
									stateId, lowerBound, upperBound);
							dashboardDomain = boardingRequestDAO.getCountRequestListByStateAndStatus(status, roleId,
									stateId);
							totalSize = dashboardDomain.getTotal();
						}
					}
				}
				if (reqParam.containsKey("cityId")) {
					int cityId = Integer.parseInt(reqParam.get("cityId"));
					Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
							SeverityTypes.INFORMATIONAL.ordinal(),
							"getEnquiresByRoleStateStatusCity for cityId:" + JsonUtil.toJsonString(cityId)));

					if (cityId > 0) {
						if (null == status || status.isEmpty()) {
							boardingRequestDomain = boardingRequestDAO.getEnquiresByCityAndStatus(null, roleId, cityId,
									lowerBound, upperBound);
							dashboardDomain = boardingRequestDAO.getCountEnquiresByCityAndStatus(null, roleId, cityId);
							totalSize = dashboardDomain.getTotal();
						} else {
							boardingRequestDomain = boardingRequestDAO.getEnquiresByCityAndStatus(status, roleId,
									cityId, lowerBound, upperBound);
							dashboardDomain = boardingRequestDAO.getCountEnquiresByCityAndStatus(status, roleId,
									cityId);
							totalSize = dashboardDomain.getTotal();
						}
					}
				}
			}
		} else
			boardingRequestDomain = boardingRequestDAO.getEnquires(roleId, null, lowerBound, upperBound);
		for (BoardingRequestDomain boardingRequest : boardingRequestDomain) {
			try {
				boardingRequest.setCity(adminCommonService.cityNameById(boardingRequest.getCityId()));
			} catch (Exception e) {
				if (e instanceof MmgRestException.CITY_NOT_FOUND)
					boardingRequest.setCity(null);
			}
			if (isDownload) {
				try {
					boardingRequest.setState(adminCommonService.stateNameById(boardingRequest.getStateId()));
				} catch (Exception e) {
					if (e instanceof MmgRestException.STATE_NOT_FOUND)
						boardingRequest.setState(null);
				}
			}
			try {
				boardingRequest.setRoleName(validateRole(boardingRequest.getRoleId()));
			} catch (Exception e) {
				if (e instanceof MmgRestException.ROLE_NOT_FOUND)
					boardingRequest.setRoleName(null);
			}
		}

		boardingRequestModel = boardingRequestMapper.entityList(boardingRequestDomain);
//		return boardingRequestMapper.entityList(boardingRequestDomain);
//		ListDto listDto = new ListDto();
////		DashboardDomain dashboardDomain = new DashboardDomain();
//		dashboardDomain = boardingRequestDAO.getEnquiresCountOnRoleStatus(roleId, null);
//		totalSize = dashboardDomain.getTotal();
//
//		if (reqParam.containsKey("status")) {
//			status = reqParam.get("status");
//			dashboardDomain = boardingRequestDAO.getEnquiresCountOnRoleStatus(roleId, status);
//			totalSize = dashboardDomain.getTotal();
//		}
//		if (reqParam.containsKey("stateId") && reqParam.containsKey("cityId")) {
//			int stateId = Integer.parseInt(reqParam.get("stateId"));
//			int cityId = Integer.parseInt(reqParam.get("cityId"));
//			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
//					SeverityTypes.INFORMATIONAL.ordinal(),
//					"getEnquiresByRoleStateStatusCityCount for cityId and stateId:"));
//			if (cityId > 0 && stateId > 0) {
//				if (null == status || status.isEmpty())
//					dashboardDomain = boardingRequestDAO.getCountRequestListOnStateAndCity(null, roleId, stateId,
//							cityId);
//				else
//					dashboardDomain = boardingRequestDAO.getCountRequestListOnStateAndCity(status, roleId, stateId,
//							cityId);
//				totalSize = dashboardDomain.getTotal();
//			}
//		} else {
//			if (reqParam.containsKey("stateId")) {
//				int stateId = Integer.parseInt(reqParam.get("stateId"));
//				Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
//						SeverityTypes.INFORMATIONAL.ordinal(), "getEnquiresByRoleStateStatusCityCount for  stateId:")
//						+ JsonUtil.toJsonString(stateId));
//
//				if (stateId > 0) {
//					adminCommonService.stateNameById(stateId);
//					if (null == status || status.isEmpty())
//						dashboardDomain = boardingRequestDAO.getCountRequestListByStateAndStatus(null, roleId, stateId);
//					else
//						dashboardDomain = boardingRequestDAO.getCountRequestListByStateAndStatus(status, roleId,
//								stateId);
//					totalSize = dashboardDomain.getTotal();
//				}
//			}
//			if (reqParam.containsKey("cityId")) {
//				int cityId = Integer.parseInt(reqParam.get("cityId"));
//				Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
//						SeverityTypes.INFORMATIONAL.ordinal(), "getEnquiresByRoleStateStatusCityCount for  cityId:")
//						+ JsonUtil.toJsonString(cityId));
//				if (cityId > 0) {
//					adminCommonService.cityNameById(cityId);
//					if (null == status || status.isEmpty())
//						dashboardDomain = boardingRequestDAO.getCountEnquiresByCityAndStatus(null, roleId, cityId);
//					else
//						dashboardDomain = boardingRequestDAO.getCountEnquiresByCityAndStatus(status, roleId, cityId);
//					totalSize = dashboardDomain.getTotal();
//				}
//			}
//		}
		listDto = profileUtil.getBoardingRequestWithPaginationCount(boardingRequestModel, totalSize);
		return listDto;
	}

	@Override
	public BoardingRequestModel getRequest(String requestNumber) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "getRequest:" + JsonUtil.toJsonString(requestNumber)));
		BoardingRequestDomain dashboardDomain = boardingRequestDAO.getRequest(requestNumber);
		if (null == dashboardDomain)
			throw new ENQUIRY_NOT_FOUND();
		BoardingRequestModel boardingRequestModel = new BoardingRequestModel();
		BeanUtils.copyProperties(dashboardDomain, boardingRequestModel);
		return boardingRequestModel;
	}

	private int getCountByStateAndCity(int roleId, int stateId, int cityId) throws Exception {
		int count = 0;
		List<BoardingRequestDomain> boardingReqDomainList = new ArrayList<BoardingRequestDomain>();
		if (stateId > 0)
			boardingReqDomainList = boardingRequestDAO.getRequestListByStateAndStatus(EnquiryRole.PENDING.name(),
					roleId, stateId, 0, 0);
		if (cityId > 0)
			boardingReqDomainList = boardingRequestDAO.getEnquiresByCityAndStatus(EnquiryRole.PENDING.name(), roleId,
					cityId, 0, 0);
		if (boardingReqDomainList.size() > 0)
			count = boardingReqDomainList.size();
		return count;
	}

	private int getCount(int roleId) throws Exception {
		int count = 0;
		List<BoardingRequestDomain> boardingReqDomainList = boardingRequestDAO.getEnquires(roleId,
				EnquiryRole.PENDING.name(), 0, 0);
		if (boardingReqDomainList.size() > 0)
			count = boardingReqDomainList.size();
		return count;
	}

	@Override
	public EnquiryDashboardModel getTotalEnquiryCount(Map<String, String> reqParam) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "getTotalEnquiryCount:"));

		EnquiryDashboardModel enquiryDashboardModel = new EnquiryDashboardModel();
		if (reqParam.size() > 0) {
			if (reqParam.containsKey("stateId")) {
				int stateId = Integer.parseInt(reqParam.get("stateId"));
				adminCommonService.stateNameById(stateId);
				enquiryDashboardModel
						.setFranchise(getCountByStateAndCity(Integer.parseInt(Role.FRANCHISE.getCode()), stateId, 0));
				enquiryDashboardModel
						.setFleet(getCountByStateAndCity(Integer.parseInt(Role.FLEET_OPERATOR.getCode()), stateId, 0));
				enquiryDashboardModel
						.setWarehouse(getCountByStateAndCity(Integer.parseInt(Role.WAREHOUSE.getCode()), stateId, 0));
				enquiryDashboardModel
						.setEnterprise(getCountByStateAndCity(Integer.parseInt(Role.ENTERPRISE.getCode()), stateId, 0));
			}
			if (reqParam.containsKey("cityId")) {
				Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
						SeverityTypes.INFORMATIONAL.ordinal(), "getTotalEnquiryCount on city:"));

				int cityId = Integer.parseInt(reqParam.get("cityId"));
				adminCommonService.cityNameById(cityId);
				enquiryDashboardModel
						.setFranchise(getCountByStateAndCity(Integer.parseInt(Role.FRANCHISE.getCode()), 0, cityId));
				enquiryDashboardModel
						.setFleet(getCountByStateAndCity(Integer.parseInt(Role.FLEET_OPERATOR.getCode()), 0, cityId));
				enquiryDashboardModel
						.setWarehouse(getCountByStateAndCity(Integer.parseInt(Role.WAREHOUSE.getCode()), 0, cityId));
				enquiryDashboardModel
						.setEnterprise(getCountByStateAndCity(Integer.parseInt(Role.ENTERPRISE.getCode()), 0, cityId));
			}
		} else {
			enquiryDashboardModel.setFranchise(getCount(Integer.parseInt(Role.FRANCHISE.getCode())));
			enquiryDashboardModel.setFleet(getCount(Integer.parseInt(Role.FLEET_OPERATOR.getCode())));
			enquiryDashboardModel.setWarehouse(getCount(Integer.parseInt(Role.WAREHOUSE.getCode())));
			enquiryDashboardModel.setEnterprise(getCount(Integer.parseInt(Role.ENTERPRISE.getCode())));
		}
		return enquiryDashboardModel;
	}

	@Override
	public String updateEnquiryRequest(BoardingRequestModel boardingRequestModel) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "updateEnquiryRequest :")
				+ JsonUtil.toJsonString(boardingRequestModel));

		BoardingRequestDomain boardingRequestDomain = new BoardingRequestDomain();
		BoardingRequestDomain boardingRequest = boardingRequestDAO.getEnquiryRequest(boardingRequestModel.getUuid());
		validate(boardingRequestModel, false);
		EnquiryReasonDomain enquiryReasonDomain = new EnquiryReasonDomain();
		BeanUtils.copyProperties(boardingRequestModel, boardingRequestDomain);

		EnquiryStatus enquiry = EnquiryStatus.getEnquiryStatus(boardingRequestDomain.getStatus());
		if (null == enquiry)
			throw new VALIDATE_BOARDING_ENQUIRY("Please send the proper enquiry status as : '"
					+ EnquiryRole.PENDING.name() + "' or '" + EnquiryRole.COMPLETED.name() + "' or '"
					+ EnquiryRole.INPROCESS.name() + "' or '" + EnquiryRole.ONHOLD.name() + "' ");
		else {

			ProfileDomainV2 profileDomain = profileDAOV2.getProfileDetails(boardingRequestDomain.getUserId());
			if (null == profileDomain)
				throw new NOT_FOUND("UserId not found!!");
			enquiryReasonDomain.setUuid(CommonUtils.generateRandomId());
			enquiryReasonDomain.setPreviousStatus(boardingRequest.getStatus());
			enquiryReasonDomain.setChangedStatus(boardingRequestDomain.getStatus());
			enquiryReasonDomain.setEnquiryId(boardingRequestDomain.getUuid());
			enquiryReasonDomain.setReason(boardingRequestDomain.getReason());
			enquiryReasonDomain.setUserId(boardingRequestDomain.getUserId());
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.INFORMATIONAL.ordinal(), "saveEnquiryReason :")
					+ JsonUtil.toJsonString(enquiryReasonDomain));
			enquiryReasonDomain = boardingRequestDAO.saveEnquiryReason(enquiryReasonDomain);
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.INFORMATIONAL.ordinal(), "updateEnquiryRequest :")
					+ JsonUtil.toJsonString(boardingRequestDomain));
			boardingRequestDAO.updateEnquiryRequest(boardingRequestDomain);
			if (null != enquiryReasonDomain) {
				Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
						SeverityTypes.INFORMATIONAL.ordinal(), "sendEmail :")
						+ JsonUtil.toJsonString(enquiryReasonDomain));
			}
		}
		return "Updated successfully";
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ListDto getEnquiryReasonList(String enquiryId, Map<String, String> reqParam) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "getEnquiryReasonList for enquiry:")
				+ JsonUtil.toJsonString(enquiryId));

		List<EnquiryReasonModel> reasons = new ArrayList<EnquiryReasonModel>();
		boardingRequestDAO.getEnquiryRequest(enquiryId);
		int lowerBound = 0;
		int upperBound = 0;
		int totalSize = 0;
		if (reqParam.size() > 0) {
			Map<String, Integer> paginationCount = profileUtil.getPagination(reqParam);
			if (paginationCount.size() > 0) {
				lowerBound = paginationCount.get("lowerBound");
				upperBound = paginationCount.get("upperBound");
			}
		}
		List<EnquiryReasonDomain> enquiryReasonDomain = boardingRequestDAO.getEnquiryReasonList(enquiryId, lowerBound,
				upperBound);
		for (EnquiryReasonDomain reasonDomain : enquiryReasonDomain) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
					SeverityTypes.INFORMATIONAL.ordinal(), "getProfileDetails:")
					+ JsonUtil.toJsonString(reasonDomain.getUserId()));

			ProfileDomainV2 profileDomain = profileDAOV2.getProfileDetails(reasonDomain.getUserId());
			if (null != profileDomain)
				reasonDomain.setUserName(profileDomain.getFirstName());
		}
		reasons = enquiryReasonMapper.entityList(enquiryReasonDomain);

		ListDto listDto = new ListDto();
		DashboardDomain dashboardDomain = boardingRequestDAO.getEnquiryReasonListCount(enquiryId);
		totalSize = dashboardDomain.getTotal();
		listDto = profileUtil.getEnquiryReasonWithPaginationCount(reasons, totalSize);
		return listDto;
	}

	@Override
	public BoardingRequestModel getEnquiryReasonListByEnquiryId(String enquiryId) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "getEnquiryReasonListByEnquiryId:")
				+ JsonUtil.toJsonString(enquiryId));

		BoardingRequestModel boardingRequestModel = new BoardingRequestModel();
		BoardingRequestDomain boardingRequest = boardingRequestDAO.getEnquiryRequest(enquiryId);
		BeanUtils.copyProperties(boardingRequest, boardingRequestModel);
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "getEnquiryReasonList:") + JsonUtil.toJsonString(enquiryId));

		List<EnquiryReasonDomain> enquiryReasonDomain = boardingRequestDAO.getEnquiryReasonList(enquiryId, 0, 0);
		List<EnquiryReasonModel> model = enquiryReasonMapper.entityList(enquiryReasonDomain);
		List<EnquiryReasonModel> enquiryModel = new ArrayList<EnquiryReasonModel>();
		for (EnquiryReasonModel reasonModel : model) {
			ProfileDomainV2 profileDomain = profileDAOV2.getProfileDetails(reasonModel.getUserId());
			if (null != profileDomain)
				reasonModel.setUserName(profileDomain.getFirstName());
			enquiryModel.add(new EnquiryReasonModel(reasonModel.getUserId(), reasonModel.getReason(),
					reasonModel.getPreviousStatus(), reasonModel.getChangedStatus(), reasonModel.getModifiedDate(),
					reasonModel.getUserName()));
		}
		boardingRequestModel.setEnquiryReason(enquiryModel);
		return boardingRequestModel;
	}

	@Override
	public String sendApproveEnquiryEmail(BoardingRequestModel boardingRequestModel) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "sendApproveEnquiryEmail:")
				+ JsonUtil.toJsonString(boardingRequestModel));

		BoardingRequestDomain boardingRequestDomain = new BoardingRequestDomain();
		BeanUtils.copyProperties(boardingRequestModel, boardingRequestDomain);

		if (0 >= boardingRequestDomain.getRoleId())
			throw new VALIDATE_BOARDING_ENQUIRY("Please mention the role");
		if (null == boardingRequestDomain.getFirstName() || boardingRequestDomain.getFirstName().isEmpty())
			throw new VALIDATE_BOARDING_ENQUIRY("Please mention the firstName");
		if (!dataValidation.isValidate(boardingRequestDomain.getFirstName(), DataValidation.FIRSTNAME_PATTERN))
			throw new VALIDATE_BOARDING_ENQUIRY("Please mention the valid name");
		if (null == boardingRequestDomain.getMobileNumber())
			throw new VALIDATE_BOARDING_ENQUIRY("Please mention the mobile Number");
		if (!dataValidation.isValidate(boardingRequestDomain.getEmail(), DataValidation.EMAIL_PATTERN))
			throw new EMAILID_PATTERN_NOT_MATCH();
		String role = validateRole(boardingRequestDomain.getRoleId());
		String body, msg, text, message = null;
		Map<String, Object> variables = new HashMap<>();
		if (null == boardingRequestModel.getInactiveReason()
				|| boardingRequestModel.getInactiveReason().length() == 0) {
			message = "Your MMG Onboarding " + role
					+ " Enquiry Request  is approved and activated.For further queries,contact Move My Goods Help Center.Contact No: 901 902 903 6.";
			msg = "Welcome to MOVE MY GOODS.";
			text = "Your MMG " + role
					+ " request is approved and activated.For further queries,contact Move My Goods Help Center.Contact No: 901 902 903 6.";
			variables.put("firstName", boardingRequestDomain.getFirstName());
			variables.put("message", msg);
			variables.put("text", text);
		} else {
			message = "Your MMG" + role
					+ " Account is disabled.Please contact Move My Goods Help Center for assistance.Contact No: 901 902 903 6.";
			msg = " ";
			text = "Your MMG" + role + "  Account is disabled due to the reason:"
					+ boardingRequestModel.getInactiveReason() + ".";
			variables.put("firstName", boardingRequestDomain.getFirstName());
			variables.put("message", msg);
			variables.put("text", text);
		}
		emailValidator.sendSMSMessage(boardingRequestModel.getMobileNumber(),null, message, false);
		body = emailValidator.generateMailHtml("statusemail", variables);
		emailValidator.sendEmail("Move My Goods", boardingRequestDomain.getEmail(), body);
		return null;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public byte[] downloadEnquiryList(int roleId, Map<String, String> reqParam) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOARDING_ENQUIRY_SERVICE.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "downloadEnquiryList for role:")
				+ JsonUtil.toJsonString(roleId));

		List<BoardingRequestModel> boardingRequestModelList = null;
		List<BoardingRequestDomain> boardingRequestDomain = new ArrayList<BoardingRequestDomain>();
//		boardingRequestModelList = getEnquiresByRoleStateStatusCity(roleId, reqParam);
		if (reqParam.size() > 0) {
			ListDto list = getEnquiresByRoleStateStatusCity(roleId, true, reqParam);
			boardingRequestModelList = list.getList();
		} else {
			boardingRequestDomain = boardingRequestDAO.getEnquires(roleId, null, 0, 0);
			boardingRequestModelList = boardingRequestMapper.entityList(boardingRequestDomain);
		}
		RoleDomain role = roleDao.getRoleName(roleId);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		String[] columnshead = { "Request Number", "Enquirer Name", "MobileNumber", "Email", "Message", "State", "City",
				"Status" };
		String sheetName = role.getRoleName() + "_Enquiry_Requests";
		XSSFWorkbook workbook = CommonUtils.createExcelWorkbook(columnshead, sheetName);
		XSSFSheet sheet = workbook.getSheet(sheetName);
		Row row = null;
		Cell rowCell = null;
		int count = 1;
		for (BoardingRequestModel boardingRequestModel : boardingRequestModelList) {
			row = sheet.createRow(count);
			for (int j = 0; j < columnshead.length; j++) {
				rowCell = row.createCell(j);
				switch (j + 1) {
				case 1:
					rowCell.setCellValue(boardingRequestModel.getRequestNumber());
					break;
				case 2:
					rowCell.setCellValue(
							boardingRequestModel.getFirstName() + " " + boardingRequestModel.getLastName());
					break;
				case 3:
					rowCell.setCellValue(String.valueOf(boardingRequestModel.getMobileNumber()));
					break;
				case 4:
					rowCell.setCellValue(boardingRequestModel.getEmail());
					break;
				case 5:
					rowCell.setCellValue(boardingRequestModel.getMessage());
					break;
				case 6:
					rowCell.setCellValue(boardingRequestModel.getState());
					break;
				case 7:
					rowCell.setCellValue(boardingRequestModel.getCity());
					break;
				case 8:
					rowCell.setCellValue(boardingRequestModel.getStatus());
					break;
				}
			}
			count++;
		}
		workbook.write(out);
		workbook.close();
		return out.toByteArray();

	}

	@SuppressWarnings("rawtypes")
	@Override
	public ListDto boardingRequestSearch(Map<String, String> reqParam) throws Exception {
		int lowerBound = 0;
		int upperBound = 0;
		String searchText = null;
		int roleId = 0;
		String status = null;
		int cityId = 0;
		int stateId = 0;
		if (reqParam.size() > 0) {
			Map<String, Integer> paginationCount = profileUtil.getPagination(reqParam);
			if (paginationCount.size() > 0) {
				lowerBound = paginationCount.get("lowerBound");
				upperBound = paginationCount.get("upperBound");
			}
			if (reqParam.containsKey("searchText")) {
				searchText = reqParam.get("searchText");
			}
			if (reqParam.containsKey("roleId")) {
				String role = reqParam.get("roleId");
				roleId = Integer.parseInt(role);
			}
			if (reqParam.containsKey("status")) {
				status = reqParam.get("status");
			}
			if (reqParam.containsKey("cityId")) {
				String city = reqParam.get("cityId");
				cityId = Integer.parseInt(city);
			}
			if (reqParam.containsKey("stateId")) {
				String state = reqParam.get("stateId");
				stateId = Integer.parseInt(state);
			}
		}
		List<BoardingRequestDomain> boardingRequestDomain = boardingRequestDAO.boardingRequestSearchList(roleId, status,
				cityId, stateId, searchText, lowerBound, upperBound);
		for (BoardingRequestDomain boardingRequest : boardingRequestDomain) {
			try {
				boardingRequest.setCity(adminCommonService.cityNameById(boardingRequest.getCityId()));
			} catch (Exception e) {
				if (e instanceof MmgRestException.CITY_NOT_FOUND)
					boardingRequest.setCity(null);
			}
			try {
				boardingRequest.setRoleName(validateRole(boardingRequest.getRoleId()));
			} catch (Exception e) {
				if (e instanceof MmgRestException.ROLE_NOT_FOUND)
					boardingRequest.setRoleName(null);
			}
		}
		ListDto listDto = new ListDto();
		List<BoardingRequestModel> boardingRequestModel = boardingRequestMapper.entityList(boardingRequestDomain);
		listDto = profileUtil.getBoardingRequestWithPaginationCount(boardingRequestModel,
				boardingRequestDAO.getboardingRequestSearchCount(roleId, status, cityId, stateId, searchText));
		return listDto;
	}

}
