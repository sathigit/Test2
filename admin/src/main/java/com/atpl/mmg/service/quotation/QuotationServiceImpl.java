package com.atpl.mmg.service.quotation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.atpl.mmg.common.ApiUtility;
import com.atpl.mmg.common.GenericHttpClient;
import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.common.MmgEnum;
import com.atpl.mmg.config.MMGProperties;
import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.dao.city.CityDAO;
import com.atpl.mmg.dao.goodstype.GoodstypeDAO;
import com.atpl.mmg.dao.quotation.QuotationDAO;
import com.atpl.mmg.dao.weight.WeightDAO;
import com.atpl.mmg.domain.city.CityDomain;
import com.atpl.mmg.domain.goodstype.GoodstypeDomain;
import com.atpl.mmg.domain.quotation.QuotationDomain;
import com.atpl.mmg.domain.weight.WeightDomain;
import com.atpl.mmg.exception.MmgRestException;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.GOOGLE_MAP_API_KEY_NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.mapper.quotation.QuotationMapper;
import com.atpl.mmg.model.bookedItems.BookedItemsModel;
import com.atpl.mmg.model.quotation.FareModel;
import com.atpl.mmg.model.quotation.ProfileModel;
import com.atpl.mmg.model.quotation.QuotationModel;
import com.atpl.mmg.service.bookedItems.BookedItemsService;
import com.atpl.mmg.utils.BookedGoodsTypes;
import com.atpl.mmg.utils.EmailValidator;
import com.atpl.mmg.utils.IDGeneration;

@Service("quotationService")
@SuppressWarnings("unused")
public class QuotationServiceImpl implements QuotationService, Constants {
	@Autowired
	QuotationDAO quotationDAO;

	@Autowired
	QuotationMapper quotationMapper;

	@Autowired
	WeightDAO weightDAO;

	@Autowired
	GoodstypeDAO goodstypeDAO;

	@Autowired
	CityDAO cityDAO;

	@Autowired
	IDGeneration idGeneration;

	@Autowired
	MMGProperties mmgProperties;

	@Autowired
	EmailValidator emailValidator;

	@Autowired
	BookedItemsService bookedItemsService;

	private static final Logger logger = LoggerFactory.getLogger(QuotationServiceImpl.class);

	public QuotationServiceImpl() {
		// constructor
	}

	/**
	 * Author:Vidya S K Created Date: 11-01-2020 Modified Date: 20/2/2020
	 * Description: Save Quotation
	 * 
	 */
	@Override
	public String saveQuotation(QuotationModel quotationModel) throws Exception {
		String response = null;
		validateQuotation(quotationModel);
		if (quotationModel.getBookedGoodsTypes() == BookedGoodsTypes.NEW_GOODS.getCode()) {
			validateBookedItems(quotationModel);
		}
		QuotationDomain quotationDomain = new QuotationDomain();
		BeanUtils.copyProperties(quotationModel, quotationDomain);
		String quotationId = idGeneration.generateQuotationNumber();
		quotationDomain.setQuotationId(quotationId);
		validateCustomerId(quotationModel.getCustomerId());
		response = quotationDAO.saveQuotation(quotationDomain);
		if (quotationDomain.getBookedGoodsTypes() == BookedGoodsTypes.NEW_GOODS.getCode()) {
			addBookedItemsList(quotationDomain);
		}
		String message = null;
//		bdoBdmMessageForOfflineBooking(quotationDomain);
		if(null != quotationModel.getCustomerName())
		message = "Dear "+ quotationModel.getCustomerName() +",\nYour Quotation is generated.BookingID:"+quotationId +".Call 901 902 903 6 for support";
		if(null != quotationModel.getContactNumber())
		emailValidator.sendSMSMessage(quotationModel.getContactNumber().toString(),
				message, null, true);
		return response;
	}

	private boolean validateQuotation(QuotationModel quotationModel) throws Exception {
		if (null == quotationModel.getSource())
			throw new NOT_FOUND("Please enter source address");
		if (null == quotationModel.getSourcelatitude())
			throw new NOT_FOUND("Please enter source latitude");
		if (null == quotationModel.getSourcelongitude())
			throw new NOT_FOUND("Please enter source longitude");
		if (null == quotationModel.getsCity() || quotationModel.getsCity().isEmpty())
			throw new NOT_FOUND("Please enter source city");
		if (null == quotationModel.getsState() || quotationModel.getsState().isEmpty())
			throw new NOT_FOUND("Please enter source state");
		if (null == quotationModel.getsCountry() || quotationModel.getsCountry().isEmpty())
			throw new NOT_FOUND("Please enter source country");
		if (null == quotationModel.getDestination())
			throw new NOT_FOUND("Please enter destination address");
		if (null == quotationModel.getDestinationlatitude())
			throw new NOT_FOUND("Please enter destination latitude");
		if (null == quotationModel.getDestinationlongitude())
			throw new NOT_FOUND("Please enter destination longitude");
		if (null == quotationModel.getdCity() || quotationModel.getdCity().isEmpty())
			throw new NOT_FOUND("Please enter destination city");
		if (null == quotationModel.getdState() || quotationModel.getdState().isEmpty())
			throw new NOT_FOUND("Please enter destination state");
		if (null == quotationModel.getdCountry() || quotationModel.getdCountry().isEmpty())
			throw new NOT_FOUND("Please enter destination country");
		if (0 == quotationModel.getKerbWeightId())
			throw new NOT_FOUND("Please select goods weight");
		if (0 == quotationModel.getGoodsTypeId())
			throw new NOT_FOUND("Please select goods type");
		if (null == quotationModel.getConsignorName())
			throw new NOT_FOUND("Please Enter ConsignorName()");
		if (null == quotationModel.getConsigneeName())
			throw new NOT_FOUND("Please Enter ConsigneeName()");
		if (null == quotationModel.getConsignorNumber())
			throw new NOT_FOUND("Please Enter ConsignorNumber()");
		if (null == quotationModel.getConsigneeNumber())
			throw new NOT_FOUND("Please Enter ConsigneeNumber()");
		quotationModel = validateSourceAddress(quotationModel);
		quotationModel = validateDestinationAddress(quotationModel);
		return true;

	}

	private QuotationModel validateDestinationAddress(QuotationModel quotationModel) throws Exception {
		int cityId = 0;
		CityDomain cityDomain = new CityDomain();
		FareModel fareModel = new FareModel();
		fareModel.setFromlatitude(quotationModel.getDestinationlatitude());
		fareModel.setFromlongitude(quotationModel.getDestinationlongitude());
		if (null != quotationModel.getdCity()) {
			try {
				cityDomain = cityDAO.getCityId(quotationModel.getdCity());
				cityId = cityDomain.getId();
			} catch (Exception e) {
				if (e instanceof MmgRestException.NOT_FOUND)
					cityId = 0;
			}
		}
		FareModel fareAddressModel = null;
		if (cityId == 0)
			fareAddressModel = getAddressLatAndLong(fareModel);
		if (null != fareAddressModel) {
			quotationModel.setdCity(fareAddressModel.getCity());
			quotationModel.setdState(fareAddressModel.getState());
			quotationModel.setdCountry(fareAddressModel.getCountry());
			quotationModel.setdPincode(fareAddressModel.getPincode());
		}
		try {
			cityDomain = cityDAO.getCityId(quotationModel.getdCity());
			cityId = cityDomain.getId();
		} catch (Exception e) {
			if (e instanceof MmgRestException.NOT_FOUND)
				cityId = 0;
		}
		if (cityId == 0)
			throw new NOT_FOUND("[" + quotationModel.getdCity() + "] is not a City Please specify nearest City");
		return quotationModel;
	}

	private QuotationModel validateSourceAddress(QuotationModel quotationModel) throws Exception {
		int cityId = 0;
		CityDomain cityDomain = new CityDomain();
		FareModel fareModel = new FareModel();
		fareModel.setFromlatitude(quotationModel.getSourcelatitude());
		fareModel.setFromlongitude(quotationModel.getSourcelongitude());
		if (null != quotationModel.getsCity()) {
			try {
				cityDomain = cityDAO.getCityId(quotationModel.getsCity());
				cityId = cityDomain.getId();
			} catch (Exception e) {
				if (e instanceof MmgRestException.NOT_FOUND)
					cityId = 0;
			}
		}
		FareModel fareAddressModel = null;
		if (cityId == 0)
			fareAddressModel = getAddressLatAndLong(fareModel);
		if (null != fareAddressModel) {
			quotationModel.setsCity(fareAddressModel.getCity());
			quotationModel.setsState(fareAddressModel.getState());
			quotationModel.setsCountry(fareAddressModel.getCountry());
			quotationModel.setsPincode(fareAddressModel.getPincode());
		}
		try {
			cityDomain = cityDAO.getCityId(quotationModel.getsCity());
			cityId = cityDomain.getId();
		} catch (Exception e) {
			if (e instanceof MmgRestException.NOT_FOUND)
				cityId = 0;
		}
		if (cityId == 0)
			throw new NOT_FOUND("[" + quotationModel.getsCity() + "] is not a City Please specify nearest City");
		return quotationModel;
	}

	private void validateBookedItems(QuotationModel quotationModel) {
		for (int i = 0; i < quotationModel.getBookedItems().size(); i++) {
			if (null == quotationModel.getBookedItems().get(i).getInvoiceNo()
					|| quotationModel.getBookedItems().get(i).getInvoiceNo().isEmpty())
				throw new NOT_FOUND("Please enter InvoiceNo");
			if (null == quotationModel.getBookedItems().get(i).getInvoiceDate()
					|| quotationModel.getBookedItems().get(i).getInvoiceDate().isEmpty())
				throw new NOT_FOUND("Please enter InvoiceDate");
			if (null == quotationModel.getBookedItems().get(i).getInvoiceAmount())
				throw new NOT_FOUND("Please enter InvoiceAmount");
			if (null == quotationModel.getBookedItems().get(i).getArticle()
					|| quotationModel.getBookedItems().get(i).getArticle().isEmpty())
				throw new NOT_FOUND("Please enter Article");
			if (null == quotationModel.getBookedItems().get(i).getPackagingId())
				throw new NOT_FOUND("Please enter Packaging");
			if (null == quotationModel.getBookedItems().get(i).getGoodsContained()
					|| quotationModel.getBookedItems().get(i).getGoodsContained().isEmpty())
				throw new NOT_FOUND("Please enter GoodsContained");
			if (null == quotationModel.getBookedItems().get(i).getWeight())
				throw new NOT_FOUND("Please enter Weight");
		}
	}

	private void addBookedItemsList(QuotationDomain quotationDomain) throws Exception {
		BookedItemsModel bookedItemsModel = new BookedItemsModel();
		BeanUtils.copyProperties(quotationDomain, bookedItemsModel);
		bookedItemsService.saveBookedItem(bookedItemsModel);
	}

	private FareModel getAddressLatAndLong(FareModel fareModel) throws Exception {
		/**
		 * Create Object for FareModel
		 */
		String reqBody = JsonUtil.toJsonString(fareModel);
		Map<String, Object> httpResponse = GenericHttpClient.doHttpPost(
				mmgProperties.getFareUrl() + "v1" + "/address/lat/long",
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()), reqBody,
				MmgEnum.FARE.name());

		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode == HttpStatus.OK.value())
				fareModel = JsonUtil.fromJson(jsonResponse.getJSONObject("data").toString(), FareModel.class);
			else
				throw new GOOGLE_MAP_API_KEY_NOT_FOUND(jsonResponse.getString("message"));

		} else {
			throw new BACKEND_SERVER_ERROR();
		}
		return fareModel;
	}

	/**
	 * Author:Vidya S K Created Date: 11-01-2020 Modified Date:24-02-2020
	 * Description: Get Quotation by QuotationId
	 * 
	 */
	@Override
	public QuotationModel getQuotations(String id) throws Exception {
		QuotationDomain quotationDomain = quotationDAO.getQuotations(id);
		QuotationModel quotationModel = new QuotationModel();
		if (quotationDomain == null)
			throw new NOT_FOUND("Quotation not found");
		BeanUtils.copyProperties(quotationDomain, quotationModel);
		return quotationModel;
	}

	/**
	 * Author:Vidya S K Created Date: 11-01-2020 Modified Date:24-02-2020
	 * Description: Get Quotation
	 * 
	 */
	@Override
	public List<QuotationModel> getQuotation() throws Exception {
		List<QuotationDomain> quotationDomain = quotationDAO.getQuotation();
		return quotationMapper.entityList(quotationDomain);
	}

	/**
	 * Author:Vidya S K Created Date: 11-01-2020 Modified Date: 20-02-2020
	 * Description: Update Quotation by QuotationId
	 * 
	 */
	@Override
	public String updateQuotation(QuotationModel quotationModel) throws Exception {
		validateCustomerId(quotationModel.getCustomerId());
		QuotationDomain quotationDomain = new QuotationDomain();
		BeanUtils.copyProperties(quotationModel, quotationDomain);
		validateCustomerId(quotationDomain.getCustomerId());
		return quotationDAO.updateQuotation(quotationDomain);
	}

	/**
	 * Author:Vidya S K Created Date: 11-01-2020 Modified Date: Description: Get
	 * Quotation by customerId
	 * 
	 */
	@Override
	public List<QuotationModel> getQuotationByCustomer(String customerId) throws Exception {
		validateCustomerId(customerId);
		List<QuotationDomain> quotationDomain = quotationDAO.getQuotationByCustomer(customerId);
		return quotationMapper.entityList(quotationDomain);
	}

	/**
	 * Author:Vidya S K Created Date: 11-01-2020 Modified
	 * Date:17-01-2020,20-01-2020,24-02-2020,25-02-2020 Description: 1.Validate
	 * Email Send Quotation by emailId(quotationId) and Default send Quotation to
	 * Customer
	 * 
	 */

	@Override
	public String sendQuotation(QuotationModel quotationModel) throws Exception {
		quotationModel = validateEmailQuotation(quotationModel);
		/**
		 * Send Email
		 */
		Map<String, Object> variables = new HashMap<>();
		variables.put("customerName", quotationModel.getCustomerName());
		variables.put("contactNumber", quotationModel.getContactNumber());
		variables.put("emailId", quotationModel.getEmailId());
		variables.put("source", quotationModel.getSource());
		variables.put("destination", quotationModel.getDestination());
		variables.put("pickupDate", quotationModel.getPickUpDate());
		variables.put("goodsType", quotationModel.getGoodsType());
		variables.put("goodsWeight", quotationModel.getGoodsWeight());
		variables.put("totalAmount", quotationModel.getTotalAmount());
		String pdf = emailValidator.generateMailHtml("quotationPdf", variables);
		String body = emailValidator.generateMailHtml("quotation", variables);
		emailValidator.sendEmail("Quotation", quotationModel.getEmailId(), body, pdf);
		return "Sent quotation successfully";

	}

	private QuotationModel validateEmailQuotation(QuotationModel quotationModel) throws Exception {

		if (null == quotationModel.getEmailId() || quotationModel.getEmailId().isEmpty()) {
			QuotationDomain quotationDomain = new QuotationDomain();
			quotationDomain = quotationDAO.getQuotations(quotationModel.getQuotationId());
			ProfileModel profileModel = customerPersonal(quotationModel.getCustomerId());
			quotationModel.setCustomerName(profileModel.getFirstName());
			quotationModel.setContactNumber(profileModel.getMobileNumber());
			quotationModel.setAddress(profileModel.getDoorNumber() + profileModel.getStreet());
			WeightDomain weightDomain = weightDAO.getWeight(quotationDomain.getKerbWeightId());
			quotationDomain.setGoodsWeight(weightDomain.getWeight());
			GoodstypeDomain goodstypeDomain = goodstypeDAO.getGoodsType(quotationDomain.getGoodsTypeId());
			quotationDomain.setGoodsType(goodstypeDomain.getName());
			BeanUtils.copyProperties(quotationModel, quotationDomain);
			return quotationModel;

		} else
			return quotationModel;
	}

	private ProfileModel customerPersonal(String customerId) throws JSONException, Exception {
		ProfileModel profile = null;
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getAuthUrl() + "v1" + "/profile/customer/" + customerId,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.AUTH.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode != HttpStatus.OK.value())
				throw new NOT_FOUND("Profile not found");
			else {
				profile = JsonUtil.fromJson(jsonResponse.getJSONObject("data").toString(), ProfileModel.class);
				return profile;
			}
		} else
			throw new BACKEND_SERVER_ERROR();
	}

	/**
	 * Author:Vidya S K Created Date: 21-01-2020 Modified Date:22-01-2020,09-03-2020
	 * Description: Filter the Quotation based on Status and City In Source Admin
	 * Api city/name/id
	 */
	@Override
	public List<QuotationModel> getQuotation(String status, int cityId) throws Exception {
		List<QuotationDomain> quotationDomain = new ArrayList<QuotationDomain>();
		List<QuotationDomain> allQuotation = new ArrayList<QuotationDomain>();
		CityDomain cityDomain = cityDAO.getCity(cityId);
		if (status.equals("ALL")) {
			quotationDomain = quotationDAO.getQuotation();
		} else {
			quotationDomain = quotationDAO.getQuotation(status, cityDomain.getName());
		}
		for (QuotationDomain allQuotationDomain : quotationDomain) {
			ProfileModel profile = customerPersonal(allQuotationDomain.getCustomerId());
			allQuotationDomain.setCustomerName(profile.getFirstName());
			allQuotationDomain.setContactNumber(profile.getMobileNumber());
			ArrayList<QuotationDomain> arraylist = new ArrayList<QuotationDomain>();
			arraylist.add(new QuotationDomain(allQuotationDomain.getQuotationId(), allQuotationDomain.getSource(),
					allQuotationDomain.getDestination(), allQuotationDomain.getKerbWeightId(),
					allQuotationDomain.getGoodsTypeId(), allQuotationDomain.getTotalAmount(),
					allQuotationDomain.getVehicleCategoryId(), allQuotationDomain.getStatus(),
					allQuotationDomain.getEmailId(), allQuotationDomain.getCustomerId(),
					allQuotationDomain.getPickUpDate(), allQuotationDomain.getCustomerName(),
					allQuotationDomain.getContactNumber()));
			allQuotation.addAll(arraylist);
		}
		return quotationMapper.entityList(allQuotation);
	}

	/** Customer Validation **/
	public boolean validateCustomerId(String customerId) throws Exception {
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getAuthUrl() + "v1" + "/profile/customer/" + customerId,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.AUTH.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode != HttpStatus.OK.value())
				throw new NOT_FOUND("Profile not found");
			else {
				return true;
			}
		} else
			throw new BACKEND_SERVER_ERROR();
	}

	public List<ProfileModel> bdmProfileList(int cityId) throws Exception {
		List<ProfileModel> profileModel = null;
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getAuthUrl() + "v1" + "/getbdmProfileList/" + cityId,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.AUTH.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode == HttpStatus.OK.value())
				profileModel = JsonUtil.parseJsonArray(jsonResponse.getJSONArray("data").toString(),
						ProfileModel.class);
		} else
			throw new BACKEND_SERVER_ERROR();
		return profileModel;
	}

	public List<ProfileModel> bdoProfileList(int cityId) throws Exception {
		List<ProfileModel> profileModel = null;
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getAuthUrl() + "v1" + "/profile/bdo/" + cityId,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.AUTH.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode == HttpStatus.OK.value())
				profileModel = JsonUtil.parseJsonArray(jsonResponse.getJSONArray("data").toString(),
						ProfileModel.class);
		} else
			throw new BACKEND_SERVER_ERROR();
		return profileModel;
	}

	public List<ProfileModel> operationalTeamProfileList(int cityId) throws Exception {
		List<ProfileModel> profileModel = null;
		Map<String, Object> httpResponse = GenericHttpClient.doHttpGet(
				mmgProperties.getAuthUrl() + "v1" + "/profile/operational/" + cityId,
				ApiUtility.getHeaders(mmgProperties.getHeaderApiKey(), mmgProperties.getAccessTokenKey()),
				MmgEnum.AUTH.name());
		int statusCode = (int) httpResponse.get("statusCode");
		JSONObject jsonResponse = (JSONObject) httpResponse.get("response");
		if (jsonResponse != null) {
			if (statusCode == HttpStatus.OK.value())
				profileModel = JsonUtil.parseJsonArray(jsonResponse.getJSONArray("data").toString(),
						ProfileModel.class);
		} else
			throw new BACKEND_SERVER_ERROR();
		return profileModel;
	}

	private boolean bdoBdmMessageForOfflineBooking(QuotationDomain quotationDomain) throws Exception {
		/* To Get CityId based on city Name */
		CityDomain cityDomain = cityDAO.getCityId(quotationDomain.getsCity());
		int cityId = cityDomain.getId();
		List<ProfileModel> bdoModel = bdoProfileList(cityId);
		if (!bdoModel.isEmpty()) {
			for (ProfileModel bookingModel : bdoModel) {
				emailValidator.sendSMSMessage(bookingModel.getMobileNumber().toString(),
						Constants.BDM_Or_BDM_QUOTATION_MESSAGE, quotationDomain, true);
			}
		} else {
			List<ProfileModel> bdoMasterModel = bdoProfileList(Constants.MASTER_FRANCHISE_CHITYID);
			if (!bdoMasterModel.isEmpty()) {
				for (ProfileModel bookingModel : bdoMasterModel) {
					emailValidator.sendSMSMessage(bookingModel.getMobileNumber().toString(),
							Constants.BDM_Or_BDM_QUOTATION_MESSAGE, quotationDomain, true);
				}
			}
		}
		List<ProfileModel> bdmModel = bdmProfileList(cityId);
		if (!bdmModel.isEmpty()) {
			for (ProfileModel bookingModel : bdmModel) {
				emailValidator.sendSMSMessage(bookingModel.getMobileNumber().toString(),
						Constants.BDM_Or_BDM_QUOTATION_MESSAGE, quotationDomain, true);
			}
		} else {
			List<ProfileModel> bdmMasterModel = bdmProfileList(Constants.MASTER_FRANCHISE_CHITYID);
			if (!bdmMasterModel.isEmpty()) {
				for (ProfileModel bookingModel : bdmMasterModel) {
					emailValidator.sendSMSMessage(bookingModel.getMobileNumber().toString(),
							Constants.BDM_Or_BDM_QUOTATION_MESSAGE, quotationDomain, true);
				}
			}
		}

		List<ProfileModel> operationalModel = operationalTeamProfileList(cityId);
		if (!operationalModel.isEmpty()) {
			for (ProfileModel bookingModel : operationalModel) {
				emailValidator.sendSMSMessage(bookingModel.getMobileNumber().toString(),
						Constants.BDM_Or_BDM_QUOTATION_MESSAGE, quotationDomain, true);
			}
		} else {
			List<ProfileModel> operationalMasterModel = operationalTeamProfileList(Constants.MASTER_FRANCHISE_CHITYID);
			if (!operationalMasterModel.isEmpty()) {
				for (ProfileModel bookingModel : operationalMasterModel) {
					emailValidator.sendSMSMessage(bookingModel.getMobileNumber().toString(),
							Constants.BDM_Or_BDM_QUOTATION_MESSAGE, quotationDomain, true);
				}
			}
		}
		return true;

	}

}
