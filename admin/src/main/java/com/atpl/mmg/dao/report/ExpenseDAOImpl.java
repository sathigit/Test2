package com.atpl.mmg.dao.report;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.domain.report.ExpenseDomain;
import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.DELETE_FAILED;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.exception.MmgRestException.UPDATE_FAILED;

@Repository
@SuppressWarnings("rawtypes")
public class ExpenseDAOImpl implements ExpenseDAO, Constants {
	private static final Logger logger = LoggerFactory.getLogger(ExpenseDAOImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public String addExpense(ExpenseDomain expenseDomain) throws Exception {
		try {
			String sql = "INSERT INTO expenses (expenseSourceId,expensesAmount,expensesYear) VALUES(?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] {expenseDomain.getExpenseSourceId(),expenseDomain.getExpensesAmount(),expenseDomain.getExpensesYear()});
			if (res == 1) {
				return "Save successfully";
			} else
				throw new SAVE_FAILED("Expense save failed");
		} catch (Exception e) {
			logger.error("Exception addExpense in ExpenseDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String addExpenseSource(ExpenseDomain expenseDomain) throws Exception {
		try {
			String sql = "INSERT INTO expensesource (source) VALUES(?)";
			int res = jdbcTemplate.update(sql, new Object[] {expenseDomain.getSource()});
			if (res == 1) {
				return "Save successfully";
			} else
				throw new SAVE_FAILED("ExpenseSource save failed");
		} catch (Exception e) {
			logger.error("Exception addExpenseSource in ExpenseDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public ExpenseDomain getExpenseSource(int id) throws Exception {
		try {
			String sql = "SELECT * FROM expensesource where id=?";
			return (ExpenseDomain) jdbcTemplate.queryForObject(sql, new Object[] { id },
					new BeanPropertyRowMapper<ExpenseDomain>(ExpenseDomain.class));
		} catch (EmptyResultDataAccessException e) {
			throw new NOT_FOUND("ExpenseSource not found");
		} catch (Exception e) {
			logger.error("Exception getExpenseSource in ExpenseDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}
	
	@Override
	public String updateExpenseSource(ExpenseDomain expenseDomain) throws Exception {
		try {
			String sql =" UPDATE admin.expensesource SET  source=? WHERE id=?";
			int res = jdbcTemplate.update(sql,
					new Object[] {expenseDomain.getSource(),expenseDomain.getId()});
			if (res == 1) {
				return "Update successfully";
			} else
				throw new UPDATE_FAILED("ExpenseSource update failed");
		} catch (Exception e) {
			logger.error("Exception updateExpenseSource in ExpenseDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}
	@Override
	public String deleteExpenseSource(int id)throws Exception {
		try {
			String sql = "DELETE FROM expensesource WHERE id=?";
			int res = jdbcTemplate.update(sql,new Object[] { id });
			if (res == 1) {
				return "Delete successfully";
			} else
				throw new DELETE_FAILED("ExpenseSource delete failed");
		} catch (Exception e) {
			logger.error("Exception deleteExpenseSource in ExpenseDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<ExpenseDomain> getExpenseSource() throws Exception {
		try {
			String sql = "SELECT id,source FROM expensesource";
			List<ExpenseDomain> expenseDomain = jdbcTemplate.query(sql, new Object[] {},
					new BeanPropertyRowMapper<ExpenseDomain>(ExpenseDomain.class));
			return expenseDomain;
		} catch (Exception e) {
			logger.error("Exception getExpenseSource in ExpenseDAOImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

}
