package com.ethaneldridge.salvo.dal.impl;

import com.ethaneldridge.salvo.dal.SalvoToolbarMenuItemDal;
import com.ethaneldridge.salvo.data.SalvoToolbarMenuItem;

import VASSAL.build.module.ToolbarMenu;

public class SalvoToolbarMenuItemDalImpl implements SalvoToolbarMenuItemDal {

	@Override
	public SalvoToolbarMenuItem getSalvoToolbarMenuItemFromVassalToolbarMenu(ToolbarMenu vassalToolbarMenu) {
		SalvoToolbarMenuItem salvoToolbarMenuItem = new SalvoToolbarMenuItem();
		salvoToolbarMenuItem.setButtonIcon(vassalToolbarMenu.getAttributeValueString(ToolbarMenu.BUTTON_ICON));
		salvoToolbarMenuItem.setButtonText(vassalToolbarMenu.getAttributeValueString(ToolbarMenu.BUTTON_TEXT));
		salvoToolbarMenuItem.setDescription(vassalToolbarMenu.getAttributeValueString(ToolbarMenu.DESCRIPTION));
		salvoToolbarMenuItem.setButtonHotkey(vassalToolbarMenu.getAttributeValueString(ToolbarMenu.BUTTON_HOTKEY));
		salvoToolbarMenuItem.setTooltip(vassalToolbarMenu.getAttributeValueString(ToolbarMenu.TOOLTIP));
		salvoToolbarMenuItem.setMenuItems((vassalToolbarMenu.getAttributeValueString(ToolbarMenu.MENU_ITEMS)).split(","));
		
		return salvoToolbarMenuItem;
	}

}
