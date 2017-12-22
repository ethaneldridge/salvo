package com.ethaneldridge.salvo.dal;

import java.util.List;

import com.ethaneldridge.salvo.data.SalvoMap;

public interface SalvoMapDal {

	public SalvoMap getById(String id);
	public SalvoMap getMain();
	public List<SalvoMap> searchAll();
}
