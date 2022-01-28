package com.atpl.mmg.AandA.dao.bankaccount;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.domain.bankaccount.BankAccountDomain;
import com.atpl.mmg.AandA.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.AandA.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.AandA.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.AandA.log.EventsLogUtil;
import com.atpl.mmg.AandA.log.Loggly;
import com.atpl.mmg.AandA.log.ServiceTypes;
import com.atpl.mmg.AandA.log.SeverityTypes;
import com.atpl.mmg.AandA.utils.DateUtility;

@Repository
public class BankAccountDAOImpl implements BankAccountDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public String save(BankAccountDomain bankAccountDomain) throws Exception {
		try {
			String sql = "INSERT INTO bank_account (uuid,profileId,accountNumber,bankId,ifscCode,branchName,status,createdDate,modifiedDate) VALUES(?,?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { bankAccountDomain.getUuid(), bankAccountDomain.getProfileId(),
							bankAccountDomain.getAccountNumber(), bankAccountDomain.getBankId(),
							bankAccountDomain.getIfscCode(), bankAccountDomain.getBranchName(),
							bankAccountDomain.isStatus(), DateUtility.getDateFormat(new Date()),
							DateUtility.getDateFormat(new Date()) });
			if (res == 1) {
				return "Bank_Account details saved successfully";
			} else
				throw new SAVE_FAILED("Bank_Account details save failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BANK_ACCOUNT_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception saveBank_Account in BankAccountDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<BankAccountDomain> getBankAccounts(String profileId, boolean status) throws Exception {
		try {
			String sql = "select * from bank_account where profileId = ? and status =?";
			List<BankAccountDomain> profileRoleList = jdbcTemplate.query(sql.toString(),
					new Object[] { profileId, status },
					new BeanPropertyRowMapper<BankAccountDomain>(BankAccountDomain.class));
			return profileRoleList;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BANK_ACCOUNT_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getBankAccounts in BankAccountDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BANK_ACCOUNT_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getBankAccounts in BankAccountDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public List<BankAccountDomain> getAllBankAccountsByProfileId(String profileId, Boolean status) throws Exception {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select * from bank_account where profileId = ?  ");
			if (null != status) {
				sql.append(" and  status = " + status + " ");
			}
			List<BankAccountDomain> profileRoleList = jdbcTemplate.query(sql.toString(), new Object[] { profileId },
					new BeanPropertyRowMapper<BankAccountDomain>(BankAccountDomain.class));
			return profileRoleList;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BANK_ACCOUNT_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getBankAccounts in BankAccountDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BANK_ACCOUNT_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getBankAccounts in BankAccountDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public BankAccountDomain checkAccountNumber(BigInteger accountNumber) throws Exception {
		try {
			String sql = "select profileId from bank_account where accountNumber = ?";
			BankAccountDomain profileRoleList = jdbcTemplate.queryForObject(sql.toString(),
					new Object[] { accountNumber },
					new BeanPropertyRowMapper<BankAccountDomain>(BankAccountDomain.class));
			return profileRoleList;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BANK_ACCOUNT_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception checkAccountNumber in BankAccountDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BANK_ACCOUNT_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception checkAccountNumber in BankAccountDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateBank(BankAccountDomain bankAccountDomain) throws Exception {
		try {
			String sql = "UPDATE bank_account SET accountNumber=?,bankId=?,ifscCode=?,branchName =?,status=?,modifiedDate=? WHERE uuid=?";
			int res = jdbcTemplate.update(sql, new Object[] { bankAccountDomain.getAccountNumber(),
					bankAccountDomain.getBankId(), bankAccountDomain.getIfscCode(), bankAccountDomain.getBranchName(),
					bankAccountDomain.isStatus(), DateUtility.getDateFormat(new Date()), bankAccountDomain.getUuid() });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Bank update failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BANK_ACCOUNT_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception updateBank in BankAccountDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public BankAccountDomain getBankAccountsByUuid(String uuid) throws Exception {
		try {
			String sql = "select * from bank_account where uuid = ?";
			BankAccountDomain bankAccountDomain = jdbcTemplate.queryForObject(sql.toString(), new Object[] { uuid },
					new BeanPropertyRowMapper<BankAccountDomain>(BankAccountDomain.class));
			return bankAccountDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BANK_ACCOUNT_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getBankAccountsByUuid in BankAccountDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BANK_ACCOUNT_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getBankAccountsByUuid in BankAccountDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public BankAccountDomain getBankAccountValidateByBankAccount(BankAccountDomain bankAccountDomain) throws Exception {
		try {
			String sql = "select * from bank_account where profileId = ? and accountNumber = ? and bankId = ? and ifscCode = ? and branchName = ?";
			BankAccountDomain bankAcctDomain = jdbcTemplate.queryForObject(sql.toString(),
					new Object[] { bankAccountDomain.getProfileId(), bankAccountDomain.getAccountNumber(),
							bankAccountDomain.getBankId(), bankAccountDomain.getIfscCode(),
							bankAccountDomain.getBranchName() },
					new BeanPropertyRowMapper<BankAccountDomain>(BankAccountDomain.class));
			return bankAcctDomain;
		} catch (EmptyResultDataAccessException e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BANK_ACCOUNT_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getBankAccountValidateByBankAccount in BankAccountDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			return null;
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BANK_ACCOUNT_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception getBankAccountValidateByBankAccount in BankAccountDAOImpl "
							+ JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateBankStatus(String uuid, boolean status) throws Exception {
		try {
			String sql = "UPDATE bank_account SET status =?,modifiedDate=? WHERE uuid=?";
			int res = jdbcTemplate.update(sql, new Object[] { status, DateUtility.getDateFormat(new Date()), uuid });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("Bank update failed");
		} catch (Exception e) {
			Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.BANK_ACCOUNT_SERVICE.name(),
					SeverityTypes.CRITICAL.ordinal(),
					"Exception updateBank in BankAccountDAOImpl " + JsonUtil.toJsonString(e.getMessage())));
			throw new BACKEND_SERVER_ERROR();
		}
	}

}
