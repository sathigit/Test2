package com.atpl.mmg.service.bookedItems;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.config.MMGProperties;
import com.atpl.mmg.dao.bookedItems.BookedItemsDAO;
import com.atpl.mmg.dao.packaging.PackagingDAO;
import com.atpl.mmg.domain.bookedItems.BookedItemsDomain;
import com.atpl.mmg.domain.packaging.PackagingDomain;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;
import com.atpl.mmg.mapper.bookedItems.BookedItemsMapper;
import com.atpl.mmg.model.bookedItems.BookedItemsModel;
import com.atpl.mmg.utils.CommonUtils;
import com.atpl.mmg.utils.ListDto;

@Service("bookedItemsService")
public class BookedItemsServiceImpl implements BookedItemsService {

	@Autowired
	BookedItemsDAO bookedItemsDAO;

	@Autowired
	BookedItemsMapper bookedItemsMapper;

	@Autowired
	MMGProperties mmgProperties;

	@Autowired
	PackagingDAO packagingDAO;

	@Override
	public String saveBookedItem(BookedItemsModel bookedItemsModel) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BLOOD.name(),
				SeverityTypes.DEBUG.ordinal(), "Exception saveBookedItem in BookedItemsServiceImpl "
						+ JsonUtil.toJsonString(bookedItemsModel)));
		BookedItemsDomain bookedItemsDomain = new BookedItemsDomain();
		BookedItemsDomain bookedItemslist = new BookedItemsDomain();
		BeanUtils.copyProperties(bookedItemsModel, bookedItemsDomain);
		for (int i = 0; i < bookedItemsDomain.getBookedItems().size(); i++) {
			bookedItemslist.setUuid(CommonUtils.generateRandomId());
			bookedItemslist.setQuotationId(bookedItemsDomain.getQuotationId());
			bookedItemslist.setCustomerId(bookedItemsDomain.getCustomerId());
			bookedItemslist.setInvoiceNo(bookedItemsDomain.getBookedItems().get(i).getInvoiceNo());
			bookedItemslist.setInvoiceDate(bookedItemsDomain.getBookedItems().get(i).getInvoiceDate());
			bookedItemslist.setInvoiceAmount(bookedItemsDomain.getBookedItems().get(i).getInvoiceAmount());
			bookedItemslist.setEwayBillNo(bookedItemsDomain.getBookedItems().get(i).getEwayBillNo());
			bookedItemslist.setEwayBillDate(bookedItemsDomain.getBookedItems().get(i).getEwayBillDate());
			bookedItemslist.setArticle(bookedItemsDomain.getBookedItems().get(i).getArticle());
			bookedItemslist.setPackagingId(bookedItemsDomain.getBookedItems().get(i).getPackagingId());
			bookedItemslist.setGoodsContained(bookedItemsDomain.getBookedItems().get(i).getGoodsContained());
			bookedItemslist.setWeight(bookedItemsDomain.getBookedItems().get(i).getWeight());
			bookedItemslist.setStatus(true);
			bookedItemsDAO.saveBookedItem(bookedItemslist);
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ListDto getBookedItem(String quotationId,Map<String, String> reqParam) throws Exception {
		int lowerBound = 0;
		int upperBound = 0;
		int totalSize = 0;
		if (reqParam.size() > 0) {
			Map<String, Integer> paginationCount = CommonUtils.getPagination(reqParam);
			if (paginationCount.size() > 0) {
				lowerBound = paginationCount.get("lowerBound");
				upperBound = paginationCount.get("upperBound");
			}
		}
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BLOOD.name(),
				SeverityTypes.DEBUG.ordinal(), "Exception getBookedItem in BookedItemsServiceImpl "
						+ JsonUtil.toJsonString(quotationId)+" " + JsonUtil.toJsonString(reqParam) ));

		List<BookedItemsDomain> bookedItemsDomain = bookedItemsDAO.getBookedItem(quotationId,lowerBound,upperBound);
		BookedItemsModel bookedItemsodel = new BookedItemsModel();
		for (BookedItemsDomain BookedPacking : bookedItemsDomain) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BLOOD.name(),
					SeverityTypes.DEBUG.ordinal(), "Exception getPackingName in BookedItemsServiceImpl "
							+ JsonUtil.toJsonString(BookedPacking.getPackagingId())));

			bookedItemsodel = getPackingName(BookedPacking.getPackagingId());
			BookedPacking.setPacking(bookedItemsodel.getPacking());
		}
		List<BookedItemsModel> bookedItemsModel = bookedItemsMapper.entityList(bookedItemsDomain);
		ListDto listDto = new ListDto();
		listDto.setList(bookedItemsModel);
		listDto.setTotalSize(bookedItemsDAO.getBookedItemCount(quotationId).getTotal());
		return listDto;
	}

	private BookedItemsModel getPackingName(BigInteger packagingId) throws Exception {
		BookedItemsModel packName = new BookedItemsModel();
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BLOOD.name(),
				SeverityTypes.DEBUG.ordinal(), "Exception getPackageDetails in BookedItemsServiceImpl "
						+ JsonUtil.toJsonString(packagingId)));
		PackagingDomain pacakedItem = packagingDAO.getPackageDetails(packagingId);
		packName.setPackagingName(pacakedItem.getPackagingName());
		return packName;
	}

	@Override
	public String updateBookedItem(BookedItemsModel bookedItemsModel) throws Exception {
		validateBookedItems(bookedItemsModel);
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BLOOD.name(),
				SeverityTypes.DEBUG.ordinal(), "Exception updateBookedItem in BookedItemsServiceImpl "
						+ JsonUtil.toJsonString(bookedItemsModel)));

		BookedItemsDomain bookedItemsDomain = new BookedItemsDomain();
		BeanUtils.copyProperties(bookedItemsModel, bookedItemsDomain);
		return bookedItemsDAO.updateBookedItem(bookedItemsDomain);
	}

	private void validateBookedItems(BookedItemsModel bookedItemsModel) {
		if (null == bookedItemsModel.getUuid() || bookedItemsModel.getUuid().isEmpty())
			throw new NOT_FOUND("Please pass uuid");
		if (null == bookedItemsModel.getInvoiceNo() || bookedItemsModel.getInvoiceNo().isEmpty())
			throw new NOT_FOUND("Please enter InvoiceNo");
		if (null == bookedItemsModel.getInvoiceDate() || bookedItemsModel.getInvoiceDate().isEmpty())
			throw new NOT_FOUND("Please enter InvoiceDate");
		if (null == bookedItemsModel.getInvoiceAmount())
			throw new NOT_FOUND("Please enter InvoiceAmount");
		if (null == bookedItemsModel.getArticle() || bookedItemsModel.getArticle().isEmpty())
			throw new NOT_FOUND("Please enter Article");
		if (null == bookedItemsModel.getPackagingId())
			throw new NOT_FOUND("Please enter Packaging");
		if (null == bookedItemsModel.getGoodsContained() || bookedItemsModel.getGoodsContained().isEmpty())
			throw new NOT_FOUND("Please enter GoodsContained");
		if (null == bookedItemsModel.getWeight())
			throw new NOT_FOUND("Please enter Weight");
	}

}
