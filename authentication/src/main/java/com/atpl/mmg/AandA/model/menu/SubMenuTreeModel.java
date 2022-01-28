package com.atpl.mmg.AandA.model.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SubMenuTreeModel implements Serializable {
	private static final long serialVersionUID = -8577237060904930432L;
	String name;
	String link;
	String icon;
	String menuId;
	Boolean selected;
	Boolean active;
	String desc;
	private List<SubSubMenuTreeModel> subSubMenus = new ArrayList<SubSubMenuTreeModel>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public List<SubSubMenuTreeModel> getSubSubMenus() {
		return subSubMenus;
	}
	public void setSubSubMenus(List<SubSubMenuTreeModel> subSubMenus) {
		this.subSubMenus = subSubMenus;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public Boolean getSelected() {
		return selected;
	}
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
