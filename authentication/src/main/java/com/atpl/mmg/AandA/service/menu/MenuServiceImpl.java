package com.atpl.mmg.AandA.service.menu;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.AandA.dao.menu.MenuDao;
import com.atpl.mmg.AandA.domain.menu.MenuDomain;
import com.atpl.mmg.AandA.mapper.menu.MenuMapper;
import com.atpl.mmg.AandA.model.menu.MenuModel;
import com.atpl.mmg.AandA.utils.CommonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("menuService")
public class MenuServiceImpl implements MenuService {
	@Autowired
	MenuDao menuDao;

	@Autowired
	MenuMapper menuMapper;

	ObjectMapper mapper = new ObjectMapper();

	@Override
	public MenuModel getMenus(String roleId) throws Exception {
			MenuDomain menuDomain = menuDao.getMenus(roleId);
			MenuModel packagesModel = new MenuModel();
			if (menuDomain == null)
				return null;
			BeanUtils.copyProperties(menuDomain, packagesModel);
			MenuModel menuModel = mapper.readValue(CommonUtils.getBlobData(menuDomain.getMenus()), MenuModel.class);
			return menuModel;

	}

}
