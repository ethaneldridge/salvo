package com.ethaneldridge.salvo.dal.impl;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;

import com.ethaneldridge.salvo.dal.VassalMapViewDal;

import VASSAL.build.module.Map;

public class VassalMapViewDalImpl implements VassalMapViewDal {

	@Override
	public JComponent getViewByMapId(String mapId) {
		List<VASSAL.build.module.Map> maps = VASSAL.build.module.Map.getMapList();
		
		VASSAL.build.module.Map vassalMap = maps.stream()
				.filter(map -> mapId.equals(map.getId()))
				.findFirst()
				.orElse(null);
		
		JComponent mapView = vassalMap.getView();

		return mapView;
	}

	private static Map.View getMapView(JFrame frame) {
		//String title = getLocalizedMapName().length() > 0 ? getLocalizedMapName() : Resources.getString("Map.window_title", GameModule.getGameModule().getLocalizedGameName()); //$NON-NLS-1$
		//Component mapComponent = map.getView();
		List<Component> components = getAllComponents(frame.getRootPane());
		Map.View result = null;
		for (Component c : components) {
			if (c instanceof VASSAL.build.module.Map.View) {
				Map.View mapView = (Map.View)c;
				Map map = mapView.getMap();
				String mapName = map.getAttributeValueString("mapName");
				if ("Main Map".equals(mapName)) {
					result = mapView;
					break;
				}
			}
		}
		return result;
	}
	
	private static List<Component> getAllComponents(final Container c) {
		Component[] comps = c.getComponents();
		List<Component> compList = new ArrayList<Component>();
		for (Component comp : comps) {
			compList.add(comp);
			if (comp instanceof Container)
				compList.addAll(getAllComponents((Container) comp));
		}
		return compList;
	}

}
