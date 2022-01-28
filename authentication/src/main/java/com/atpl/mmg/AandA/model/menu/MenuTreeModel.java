package com.atpl.mmg.AandA.model.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MenuTreeModel implements Serializable {
	private static final long serialVersionUID = -8577237060904930432L;
	String name;
	String link;
	String icon;
	String menuId;
	Boolean selected;
	Boolean active;
	String desc;
	private List<SubMenuTreeModel> subMenus = new ArrayList<SubMenuTreeModel>();
	
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
	public List<SubMenuTreeModel> getSubMenus() {
		return subMenus;
	}
	public void setSubMenus(List<SubMenuTreeModel> subMenus) {
		this.subMenus = subMenus;
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
