package com.ethaneldridge.salvo.dal;

import com.ethaneldridge.salvo.data.SalvoToolbarMenuItem;

import VASSAL.build.module.ToolbarMenu;

public interface SalvoToolbarMenuItemDal {

	public SalvoToolbarMenuItem getSalvoToolbarMenuItemFromVassalToolbarMenu(ToolbarMenu vassalToolbarMenu);
}
