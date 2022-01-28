package com.atpl.mmg.AandA.dao;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.constant.Constants;
import com.atpl.mmg.AandA.domain.DBUpdate;
import com.atpl.mmg.AandA.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.AandA.log.EventsLogUtil;
import com.atpl.mmg.AandA.log.Loggly;
import com.atpl.mmg.AandA.log.ServiceTypes;
import com.atpl.mmg.AandA.log.SeverityTypes;


@Repository
public class DBUpdateDAOImpl implements DBUpdateDAO, Constants {

	protected static Logger logger = LoggerFactory.getLogger(DBUpdateDAOImpl.class);

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public String dbUpdate(DBUpdate dbUpdate) throws Exception {
		String result ="";
		try {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.DB_UPDATE_SERVICE.name(),
					SeverityTypes.INFORMATIONAL.ordinal(),
					"dbUpdate in DBUpdateDAOImpl " 
							+ JsonUtil.toJsonString(dbUpdate)));

			StringBuilder sql = new StringBuilder();
			Map<String, Object> params = new LinkedHashMap<String, Object>();
			sql.append("UPDATE " + dbUpdate.getTableName() + " SET ");

			StringBuilder expressionKeys = new StringBuilder();
			String prefix = "";
			for (Map.Entry<String, Object> entry : dbUpdate.getExpression().entrySet()) {
				expressionKeys.append(prefix);
				if (TextUtils.isEmpty(prefix))
					prefix = ",";
				expressionKeys.append(entry.getKey() + "=:" + entry.getKey());
			}
			sql.append(expressionKeys);
			prefix = "";
			StringBuilder conditionKeys = new StringBuilder();
			for (Map.Entry<String, Object> entry : dbUpdate.getConditions().entrySet()) {
				conditionKeys.append(prefix);
				if (TextUtils.isEmpty(prefix))
					prefix = " and ";
				conditionKeys.append(entry.getKey() + "=:" + entry.getKey());
			}
			sql.append(" WHERE ");
			sql.append(conditionKeys);
			params.putAll(dbUpdate.getExpression());
			params.putAll(dbUpdate.getConditions());
			int res = namedParameterJdbcTemplate.update(sql.toString(), params);

			if (res > 0) {
				result= "Success";
			} else {
				throw new UPDATE_FAILED("DbUpdate Failed !!!");
			}
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.DB_UPDATE_SERVICE.name(),
					SeverityTypes.INFORMATIONAL.ordinal(),
					"Exception in update  data " 
							+ JsonUtil.toJsonString(e.getMessage())));
		throw new UPDATE_FAILED("DbUpdate Failed !!!");
		}
		return result;
	}
}
