package com.ethaneldridge.salvo.dal.impl;

import javax.swing.JToolBar;

import com.ethaneldridge.salvo.dal.SalvoToolbarItemDal;
import com.ethaneldridge.salvo.data.SalvoMap;
import com.ethaneldridge.salvo.data.SalvoToolbar;
import com.ethaneldridge.salvo.data.SalvoToolbarItem;
import com.ethaneldridge.salvo.vassal.membrane.VassalRepository;

import VASSAL.tools.ToolBarComponent;

public class SalvoToolbarItemDalImpl implements SalvoToolbarItemDal {

	public SalvoToolbarItemDalImpl(VassalRepository vassalRepository) {
		this.vassalRepository = vassalRepository;
	}

	@Override
	public SalvoToolbarItem getFromVassalToolBarComponent(ToolBarComponent toolBarComponent) {
		SalvoToolbarItem salvoToolbarMenuItem = new SalvoToolbarItem();
		JToolBar jToolBar = toolBarComponent.getToolBar();
		salvoToolbarMenuItem.setButtonIcon("NA");
		salvoToolbarMenuItem.setButtonText("NA");
		salvoToolbarMenuItem.setDescription(jToolBar.getName());
		salvoToolbarMenuItem.setButtonHotkey("NA");
		salvoToolbarMenuItem.setTooltip(jToolBar.getToolTipText());
		salvoToolbarMenuItem.setMenuItems(new String[]{});
		
		return salvoToolbarMenuItem;
	}

	
	private VassalRepository vassalRepository;
}
