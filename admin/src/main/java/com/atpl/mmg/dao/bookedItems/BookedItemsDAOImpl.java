package com.atpl.mmg.dao.bookedItems;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.common.JsonUtil;
import com.atpl.mmg.domain.bookedItems.BookedItemsDomain;
import com.atpl.mmg.domain.dashboard.DashboardDomain;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.log.EventsLogUtil;
import com.atpl.mmg.log.Loggly;
import com.atpl.mmg.log.ServiceTypes;
import com.atpl.mmg.log.SeverityTypes;
import com.atpl.mmg.utils.DateUtility;

@Repository
public class BookedItemsDAOImpl implements BookedItemsDAO {

	private static final Logger logger = LoggerFactory.getLogger(BookedItemsDAOImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public String saveBookedItem(BookedItemsDomain bookedItemsDomain) throws Exception {
		try {
			String sql = "INSERT INTO quotationbookeditems (uuid,quotationId,custosmerId,invoiceNo,invoiceDate,invoiceAmount,ewayBillNo,ewayBillDate,article,packagingId,goodsContained,weight,status,creationDate,modificationDate) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { bookedItemsDomain.getUuid(), bookedItemsDomain.getQuotationId(),
							bookedItemsDomain.getCustomerId(), bookedItemsDomain.getInvoiceNo(),
							bookedItemsDomain.getInvoiceDate(), bookedItemsDomain.getInvoiceAmount(),
							bookedItemsDomain.getEwayBillNo(), bookedItemsDomain.getEwayBillDate(),
							bookedItemsDomain.getArticle(), bookedItemsDomain.getPackagingId(),
							bookedItemsDomain.getGoodsContained(), bookedItemsDomain.getWeight(),
							bookedItemsDomain.isStatus(), DateUtility.setTimeZone(new Date()),
							DateUtility.setTimeZone(new Date()) });
			if (res == 1) {
				return "BookedItem saved successfully";
			} else
				throw new SAVE_FAILED("BookedItem save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOOKED_ITEM.name(),
					SeverityTypes.ALERT.ordinal(), "Exception saveBookedItem in BookedItemsDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<BookedItemsDomain> getBookedItem(String quotationId,int lowerBound, int upperBound) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
		      sql.append("SELECT uuid,invoiceNo,invoiceDate,invoiceAmount,ewayBillNo,ewayBillDate,article,packagingId,goodsContained,weight from quotationbookeditems where quotationId=? order by creationDate Desc");
		  	if (upperBound > 0)
				sql.append(" limit " + lowerBound + "," + upperBound);
//			String sql = "SELECT uuid,invoiceNo,invoiceDate,invoiceAmount,ewayBillNo,ewayBillDate,article,packagingId,goodsContained,weight from quotationbookeditems where quotationId=? order by creationDate Desc";
			List<BookedItemsDomain> bookedItemsDomain = jdbcTemplate.query(sql.toString(), new Object[] { quotationId },
					new BeanPropertyRowMapper<BookedItemsDomain>(BookedItemsDomain.class));
			return bookedItemsDomain;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOOKED_ITEM.name(),
					SeverityTypes.ALERT.ordinal(), "Exception getBookedItem in BookedItemsDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateBookedItem(BookedItemsDomain bookedItemsDomain) throws Exception {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));

			String sql = "UPDATE quotationbookeditems SET invoiceNo=?,invoiceDate=?,invoiceAmount=?,ewayBillNo=?,ewayBillDate=?,article=?,packagingId=?,goodsContained=?,weight=?,modificationDate=? WHERE uuid=?";
			int res = jdbcTemplate.update(sql,
					new Object[] { bookedItemsDomain.getInvoiceNo(), bookedItemsDomain.getInvoiceDate(),
							bookedItemsDomain.getInvoiceAmount(), bookedItemsDomain.getEwayBillNo(),
							bookedItemsDomain.getEwayBillDate(), bookedItemsDomain.getArticle(),
							bookedItemsDomain.getPackagingId(), bookedItemsDomain.getGoodsContained(),
							bookedItemsDomain.getWeight(), simpleDateFormat.format(new Date()),
							bookedItemsDomain.getUuid() });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("UpdateBookedItem");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOOKED_ITEM.name(),
					SeverityTypes.ALERT.ordinal(), "Exception BookedItem in BookedItemsDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public DashboardDomain getBookedItemCount(String quotationId) throws Exception {
		try {
			String sql = "SELECT count(*) as total from quotationbookeditems where quotationId=? order by creationDate Desc";
			DashboardDomain dashboardDomain = jdbcTemplate.queryForObject(sql, new Object[] { quotationId },
					new BeanPropertyRowMapper<DashboardDomain>(DashboardDomain.class));
			return dashboardDomain;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BOOKED_ITEM.name(),
					SeverityTypes.ALERT.ordinal(), "Exception getBookedItemCount in BookedItemsDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

}
