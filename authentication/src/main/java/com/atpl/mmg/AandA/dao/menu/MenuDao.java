package com.atpl.mmg.AandA.dao.menu;

import com.atpl.mmg.AandA.domain.menu.MenuDomain;

public interface MenuDao {
	MenuDomain getMenus(String roleId) throws Exception;
}
