package com.ethaneldridge.salvo.dal;

import java.util.Map;

import javax.swing.JToolBar;

import com.ethaneldridge.salvo.data.SalvoMap;
import com.ethaneldridge.salvo.data.SalvoToolbar;

public interface SalvoToolbarDal {

	public Map<JToolBar, SalvoToolbar> searchAll();
	public SalvoToolbar getByAssociatedJToolBar(JToolBar jToolBar);
	public void save(SalvoToolbar salvoToolbar);
}
