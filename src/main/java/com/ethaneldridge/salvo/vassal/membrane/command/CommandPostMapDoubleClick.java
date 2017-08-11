package com.ethaneldridge.salvo.vassal.membrane.command;

import javax.swing.JComponent;

import com.ethaneldridge.salvo.dal.SalvoMapDal;
import com.ethaneldridge.salvo.dal.VassalMapViewDal;
import com.ethaneldridge.salvo.data.SalvoMap;
import com.ethaneldridge.salvo.vassal.membrane.VassalEngine;
import com.ethaneldridge.salvo.vassal.membrane.command.io.Mouse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommandPostMapDoubleClick implements Command {

	public CommandPostMapDoubleClick (VassalEngine vassalEngine, SalvoMapDal salvoMapDal, VassalMapViewDal vassalMapViewDal, Mouse mouse) {
		this.vassalEngine = vassalEngine;
		this.salvoMapDal = salvoMapDal;
		this.vassalMapViewDal = vassalMapViewDal;
		this.mouse = mouse;
	}
	@Override
	public Object apply(String request) throws Exception {
		vassalEngine.setExpectedClicks(2);
		ObjectMapper objectMapper = new ObjectMapper();

		SalvoDoubleClick salvoDoubleClick = objectMapper.readValue(request, SalvoDoubleClick.class);

		JComponent mapView = vassalMapViewDal.getViewByMapId(salvoDoubleClick.getSalvoMapId());

		double xNewScreen = salvoDoubleClick.getSalvoPoint().getX();// / map.getZoom();
		double yNewScreen = salvoDoubleClick.getSalvoPoint().getY();// / map.getZoom();
		int xNew = (int)xNewScreen;
		int yNew = (int)yNewScreen;

		mouse.doubleClick(mapView, xNew, yNew);

		SalvoMap salvoMap = salvoMapDal.getMapById(salvoDoubleClick.getSalvoMapId());
		return salvoMap;
	}

	private final VassalEngine vassalEngine;
	private final SalvoMapDal salvoMapDal;
	private final VassalMapViewDal vassalMapViewDal;
	private final Mouse mouse;
}
