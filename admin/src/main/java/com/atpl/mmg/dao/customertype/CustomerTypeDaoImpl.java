package com.atpl.mmg.dao.customertype;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.domain.customertype.CustomerTypeDomain;
import com.atpl.mmg.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.exception.MmgRestException.CUSTOMER_TYPE_NOT_FOUND;
import com.atpl.mmg.exception.MmgRestException.DELETE_FAILED;
import com.atpl.mmg.exception.MmgRestException.SAVE_FAILED;
import com.atpl.mmg.exception.MmgRestException.UPDATE_FAILED;
import com.atpl.mmg.utils.DateUtility;

@Repository
public class CustomerTypeDaoImpl implements CustomerTypeDao {

	private static final Logger logger = LoggerFactory.getLogger(CustomerTypeDaoImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public String save(CustomerTypeDomain CustomerTypeDomain) {
		try {
			String sql = "INSERT INTO customertype(id,typeName,isDefault,createdDate, modifiedDate) VALUES(?,?,?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] { CustomerTypeDomain.getId(), CustomerTypeDomain.getTypeName(),
					true, DateUtility.setTimeZone(new Date()), DateUtility.setTimeZone(new Date()) });
			if (res == 1) {
				return "Saved successfully";
			} else
				throw new SAVE_FAILED("customertype Save Failed");
		} catch (Exception e) {
			logger.error("Exception save in CustomerTypeDaoImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	public List<CustomerTypeDomain> getCustomerType(Boolean status) throws Exception {
		try {
			List<CustomerTypeDomain> CustomerType = new ArrayList<CustomerTypeDomain>();
			String sql = null;
			if (null != status) {
				sql = "SELECT * FROM customertype where status = ?  and  isDefault= true";
				CustomerType = jdbcTemplate.query(sql, new Object[] { status },
						new BeanPropertyRowMapper<CustomerTypeDomain>(CustomerTypeDomain.class));
			} else {
				sql = "SELECT * FROM customertype where isDefault= true";
				CustomerType = jdbcTemplate.query(sql, new Object[] {},
						new BeanPropertyRowMapper<CustomerTypeDomain>(CustomerTypeDomain.class));
			}
			return CustomerType;
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getCustomerType in CustomerTypeDaoImpl" + e.getMessage());
			return null;
		} catch (Exception e) {
			logger.error("Exception getCustomerType in CustomerTypeDaoImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}

	}

	@Override
	public String update(CustomerTypeDomain CustomerTypeDomain) throws Exception {
		try {

			String sql = "UPDATE customertype SET typeName=?, modifiedDate=?  WHERE id=?";
			int res = jdbcTemplate.update(sql,
					new Object[] { CustomerTypeDomain.getTypeName(), new Date(), CustomerTypeDomain.getId() });
			if (res == 1) {
				return "Updated Successfully";
			} else
				throw new UPDATE_FAILED("CustomerType Update Failed");
		} catch (Exception e) {
			logger.error("Exception update in CustomerTypeDaoImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public CustomerTypeDomain getCustomerTypeById(int id) throws Exception {
		try {
			String sql = "SELECT * FROM customertype where id=?";
			return (CustomerTypeDomain) jdbcTemplate.queryForObject(sql, new Object[] { id },
					new BeanPropertyRowMapper<CustomerTypeDomain>(CustomerTypeDomain.class));
		} catch (EmptyResultDataAccessException e) {
			throw new CUSTOMER_TYPE_NOT_FOUND();
		} catch (Exception e) {
			logger.error("Exception getCustomerTypeById in CustomerTypeDaoImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String delete(int id) throws Exception {

		try {
			String sql = "UPDATE customertype SET isDefault=? WHERE id=?";
			int res = jdbcTemplate.update(sql, new Object[] { false, id });
			if (res == 1) {
				return "Deleted successfully";
			} else
				throw new DELETE_FAILED("CustomerType Delete Failed");
		} catch (Exception e) {
			logger.error("Exception delete in CustomerTypeDaoImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public CustomerTypeDomain getCustomerTypeByName(String name) throws Exception {
		try {
			String sql = "SELECT * FROM customertype where typeName=?";
			return (CustomerTypeDomain) jdbcTemplate.queryForObject(sql, new Object[] { name },
					new BeanPropertyRowMapper<CustomerTypeDomain>(CustomerTypeDomain.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			logger.error("Exception getCustomerTypeByName in CustomerTypeDaoImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

	@Override
	public String updateStatus(int id, boolean status) throws Exception {
		try {
			String sql = "UPDATE customertype SET status=? WHERE id=?";
			int res = jdbcTemplate.update(sql, new Object[] { status, id });
			if (res == 1) {
				return "Updated successfully";
			} else
				throw new UPDATE_FAILED("update  Failed");
		} catch (Exception e) {
			logger.error("Exception updateStatus in CustomerTypeDaoImpl", e);
			throw new BACKEND_SERVER_ERROR();
		}
	}

}
