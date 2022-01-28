package com.atpl.mmg.AandA.dao.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atpl.mmg.AandA.domain.menu.MenuDomain;
import com.atpl.mmg.AandA.exception.MmgRestException.BACKEND_SERVER_ERROR;
import com.atpl.mmg.AandA.exception.MmgRestException.NOT_FOUND;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class MenuDaoImpl implements MenuDao {
	private static final Logger logger = LoggerFactory.getLogger(MenuDaoImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	ObjectMapper mapper = new ObjectMapper();

	@Override
	public MenuDomain getMenus(String roleId) throws Exception {
		try {
			String sql = "SELECT * FROM menus where roleId=?";
			return (MenuDomain) jdbcTemplate.queryForObject(sql, new Object[] { roleId },
					new BeanPropertyRowMapper<MenuDomain>(MenuDomain.class));
		} catch (EmptyResultDataAccessException e) {
			logger.error("EmptyResultDataAccessException getMenus in MenuDaoImpl" + e.getMessage());
			throw new NOT_FOUND("Menus not found");
		} catch (Exception e) {
			logger.error("Exception getMenus in MenuDaoImpl" + e.getMessage());
			throw new BACKEND_SERVER_ERROR();
		}
	}

}
