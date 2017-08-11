package com.ethaneldridge.salvo.vassal.membrane.command;

import javax.swing.JComponent;

import com.ethaneldridge.salvo.dal.SalvoMapDal;
import com.ethaneldridge.salvo.dal.VassalMapViewDal;
import com.ethaneldridge.salvo.data.SalvoGamePiece;
import com.ethaneldridge.salvo.data.SalvoMap;
import com.ethaneldridge.salvo.vassal.membrane.VassalEngine;
import com.ethaneldridge.salvo.vassal.membrane.command.io.Mouse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommandPostPiece implements Command {

	public CommandPostPiece (VassalEngine vassalEngine, SalvoMapDal salvoMapDal, VassalMapViewDal vassalMapViewDal, Mouse mouse) {
		this.vassalEngine = vassalEngine;
		this.salvoMapDal = salvoMapDal;
		this.vassalMapViewDal = vassalMapViewDal;
		this.mouse = mouse;
	}

	@Override
	public Object apply(String request) throws Exception {
		vassalEngine.setExpectedClicks(1);
		ObjectMapper objectMapper = new ObjectMapper();

		SalvoGamePiece gamePiece = objectMapper.readValue(request, SalvoGamePiece.class);
		JComponent mapViewOld = vassalMapViewDal.getViewByMapId(gamePiece.getLocationOld().getSalvoMapId());

		double xOldScreen = gamePiece.getLocationOld().getSalvoPoint().getX();// / map.getZoom();
		double yOldScreen = gamePiece.getLocationOld().getSalvoPoint().getY();// / map.getZoom();
		int xOld = (int)xOldScreen; // 1010;
		int yOld = (int)yOldScreen; // 120;

		mouse.pressMouse(mapViewOld, xOld, yOld);

		JComponent mapViewNew = vassalMapViewDal.getViewByMapId(gamePiece.getLocationNew().getSalvoMapId());

		double xNewScreen = gamePiece.getLocationNew().getSalvoPoint().getX();// / map.getZoom();
		double yNewScreen = gamePiece.getLocationNew().getSalvoPoint().getY();// / map.getZoom();
		int xNew = (int)xNewScreen;
		int yNew = (int)yNewScreen;

		mouse.releaseMouse (mapViewNew, xNew, yNew, 1);
		
		SalvoMap salvoMap = salvoMapDal.getMapById(gamePiece.getLocationNew().getSalvoMapId());
		return salvoMap;
	}

	private final VassalEngine vassalEngine;
	private final SalvoMapDal salvoMapDal;
	private final VassalMapViewDal vassalMapViewDal;
	private final Mouse mouse;
}
