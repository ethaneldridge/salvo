package com.ethaneldridge.salvo.dal;

import java.util.List;

import com.ethaneldridge.salvo.data.SalvoMap;

public interface SalvoMapDal {

	SalvoMap getMapById(String id);
	List<SalvoMap> searchAll();
}
