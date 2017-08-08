package com.ethaneldridge.salvo.dal.impl;

import java.util.List;

import javax.swing.JComponent;

import com.ethaneldridge.salvo.dal.VassalMapViewDal;

public class VassalMapViewDalImpl implements VassalMapViewDal {

	@Override
	public JComponent getViewByMapId(String mapId) {
		List<VASSAL.build.module.Map> maps = VASSAL.build.module.Map.getMapList();
		
		VASSAL.build.module.Map vassalMap = maps.stream()
				.filter(map -> mapId.equals(map.getId()))
				.findFirst()
				.orElse(null);

		// FIXME: Need to take appropriate action if vassalMap is null
		JComponent mapView = vassalMap.getView();

		return mapView;
	}
}
