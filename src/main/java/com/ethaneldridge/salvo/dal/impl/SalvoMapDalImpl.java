package com.ethaneldridge.salvo.dal.impl;

import java.util.List;

import com.ethaneldridge.salvo.dal.SalvoMapDal;
import com.ethaneldridge.salvo.data.SalvoMap;
import com.ethaneldridge.salvo.vassal.membrane.VassalRepository;

public class SalvoMapDalImpl implements SalvoMapDal {

	public SalvoMapDalImpl (VassalRepository vassalRepository) {
		this.vassalRepository = vassalRepository;
	}

	@Override
	public List<SalvoMap> searchAll() {
		return vassalRepository.searchAllSalvoMaps();
	}

	@Override
	public SalvoMap getById(String id) {
		return vassalRepository.getSalvoMapById(id);
	}

	@Override
	public SalvoMap getMain() {
		// TODO Auto-generated method stub
		return getById(MAIN_MAP_ID);
	}

	private VassalRepository vassalRepository;
	private static final String MAIN_MAP_ID = "Map0";
}
