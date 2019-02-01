package com.ethaneldridge.salvo.dal.impl;

import java.util.List;
import java.util.Map;

import javax.swing.JToolBar;

import com.ethaneldridge.salvo.dal.SalvoToolbarDal;
import com.ethaneldridge.salvo.data.SalvoMap;
import com.ethaneldridge.salvo.data.SalvoToolbar;
import com.ethaneldridge.salvo.data.SalvoToolbarItem;
import com.ethaneldridge.salvo.vassal.membrane.VassalRepository;

import VASSAL.build.GameModule;
import VASSAL.build.module.VassalSalvoToolbarMenu;

public class SalvoToolbarDalImpl implements SalvoToolbarDal {

	public SalvoToolbarDalImpl (VassalRepository vassalRepository) {
		this.vassalRepository = vassalRepository;
	}

	@Override
	public Map<JToolBar, SalvoToolbar> searchAll() {
		// FIXME: need to return cloned SalvoToolbarMenu, not the actual one
//		List<VassalSalvoToolbarMenu> vassalSalvoToolbarMenus = GameModule.getGameModule().getComponentsOf(VassalSalvoToolbarMenu.class);
//		for (VassalSalvoToolbarMenu vassalSalvoToolbarMenu : vassalSalvoToolbarMenus) {
//			SalvoToolbarItem salvoToolbarItem = getFromVassalToolbarMenu(vassalSalvoToolbarMenu);
//			SalvoToolbar salvoToolbar = vassalRepository.getSalvoToolbarByJToolBar(vassalSalvoToolbarMenu.getJToolBar());
//			salvoToolbar.add(salvoToolbarItem);
//			save(salvoToolbarMenu);
//		}

		return vassalRepository.searchAllSalvoToolbars();
	}

	@Override
	public SalvoToolbar getByAssociatedJToolBar(JToolBar jToolBar) {
		// TODO Auto-generated method stub
		return vassalRepository.getSalvoToolbarByJToolBar(jToolBar);
	}

	@Override
	public void save(SalvoToolbar salvoToolbar) {
		vassalRepository.save(salvoToolbar);
	}

	private VassalRepository vassalRepository;

}
