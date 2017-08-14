package com.ethaneldridge.salvo.dal.impl;

import java.util.ArrayList;
import java.util.List;

import com.ethaneldridge.salvo.dal.SalvoToolbarMenuDal;
import com.ethaneldridge.salvo.dal.SalvoToolbarMenuItemDal;
import com.ethaneldridge.salvo.data.SalvoToolbarMenu;
import com.ethaneldridge.salvo.data.SalvoToolbarMenuItem;

import VASSAL.build.GameModule;
import VASSAL.build.module.ToolbarMenu;

public class SalvoToolbarMenuDalImpl implements SalvoToolbarMenuDal {

	public SalvoToolbarMenuDalImpl (SalvoToolbarMenuItemDal salvoToolbarMenuItemDal) {
		this.salvoToolbarMenuItemDal = salvoToolbarMenuItemDal;
	}
	@Override
	public SalvoToolbarMenu getSalvoToolbarMenu() {
		SalvoToolbarMenu salvoToolbarMenu = new SalvoToolbarMenu();
		GameModule gameModule = GameModule.getGameModule();
		List<ToolbarMenu> toolbarMenus = gameModule.getComponentsOf(ToolbarMenu.class);
		
		List<SalvoToolbarMenuItem> toolbarMenuItems = new ArrayList<>();
		for (ToolbarMenu t : toolbarMenus) {
			SalvoToolbarMenuItem salvoToolbarMenuItem = salvoToolbarMenuItemDal.getSalvoToolbarMenuItemFromVassalToolbarMenu(t);
			toolbarMenuItems.add(salvoToolbarMenuItem);
		}
		
		salvoToolbarMenu.setToolbarMenuItems(toolbarMenuItems);
		return salvoToolbarMenu;
	}

	private SalvoToolbarMenuItemDal salvoToolbarMenuItemDal;
}
