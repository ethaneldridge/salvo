package com.ethaneldridge.salvo.dal;

import com.ethaneldridge.salvo.data.SalvoToolbarItem;

import VASSAL.tools.ToolBarComponent;

public interface SalvoToolbarItemDal {

	public SalvoToolbarItem getFromVassalToolBarComponent(ToolBarComponent toolBarComponent);
}
