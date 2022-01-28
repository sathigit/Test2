package com.atpl.mmg.AandA.mapper.menu;

import org.springframework.stereotype.Component;

import com.atpl.mmg.AandA.domain.menu.MenuDomain;
import com.atpl.mmg.AandA.mapper.AbstractModelMapper;
import com.atpl.mmg.AandA.model.menu.MenuModel;
@Component
public class MenuMapper extends AbstractModelMapper<MenuModel, MenuDomain> {

	@Override
	public Class<MenuModel> entityType() {
		// TODO Auto-generated method stub
		return MenuModel.class;
	}

	@Override
	public Class<MenuDomain> modelType() {
		// TODO Auto-generated method stub
		return MenuDomain.class;
	} 
	

}
