package com.atpl.mmg.AandA.service.menu;

import com.atpl.mmg.AandA.model.menu.MenuModel;

public interface MenuService {
	MenuModel getMenus(String roleId) throws Exception;
}
