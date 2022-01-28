package com.atpl.mmg.AandA.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.constant.Constants;
import com.atpl.mmg.AandA.dao.DBUpdateDAO;
import com.atpl.mmg.AandA.domain.DBUpdate;
import com.atpl.mmg.AandA.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.AandA.log.EventsLogUtil;
import com.atpl.mmg.AandA.log.Loggly;
import com.atpl.mmg.AandA.log.ServiceTypes;
import com.atpl.mmg.AandA.log.SeverityTypes;

@Service("DBUpdateService")
public class DBUpdateServiceImpl implements DBUpdateService, Constants {

	@Autowired
	DBUpdateDAO dbUpdateDAO;

	private static final Logger logger = LoggerFactory.getLogger(DBUpdateServiceImpl.class);

	public DBUpdateServiceImpl() {
		// TODO
	}

	public String dbUpdate(DBUpdate dbUpdate) throws Exception {
		try {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.DB_UPDATE_SERVICE.name(),
					SeverityTypes.INFORMATIONAL.ordinal(),
					" dbUpdate in DBUpdateServiceImpl" + JsonUtil.toJsonString(dbUpdate)));

			/**
			 * DBUpdate dbUpdate = new DBUpdate(); dbUpdate.setTableName("customer");
			 * Map<String, Object> expression = new HashMap<String, Object>();
			 * expression.put("status", CustomerStatus.ACTIVE.getName());
			 * expression.put("modifiedDate", new Date());
			 * dbUpdate.setExpression(expression); Map<String, Object> conditions = new
			 * HashMap<String, Object>(); conditions.put("mobileNumber",
			 * customer.getMobileNumber()); dbUpdate.setConditions(conditions);
			 * dbUpdateDAO.dbUpdate(dbUpdate);
			 */
			if (dbUpdate.getTableName() != null && dbUpdate.getTableName().length() > 0
					&& !dbUpdate.getConditions().isEmpty() && !dbUpdate.getExpression().isEmpty()) {
				return dbUpdateDAO.dbUpdate(dbUpdate);
			} else {
			}
		} catch (Exception ex) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.DB_UPDATE_SERVICE.name(),
					SeverityTypes.INFORMATIONAL.ordinal(),
					"Exception in dbUpdate" + JsonUtil.toJsonString(ex.getMessage())));
			throw new UPDATE_FAILED("DbUpdate Failed !!!");
		}
		return "Success";
	}

}
