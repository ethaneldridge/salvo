package com.ethaneldridge.salvo.dal.impl;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ethaneldridge.salvo.dal.SalvoGamePieceDal;
import com.ethaneldridge.salvo.dal.SalvoGamePiecePaletteDal;
import com.ethaneldridge.salvo.data.SalvoGameElement;
import com.ethaneldridge.salvo.data.SalvoGamePiece;
import com.ethaneldridge.salvo.data.SalvoGamePiecePalette;

import VASSAL.build.Buildable;
import VASSAL.build.GameModule;
import VASSAL.build.Widget;
import VASSAL.build.module.PieceWindow;
import VASSAL.build.widget.BoxWidget;
import VASSAL.build.widget.ListWidget;
import VASSAL.build.widget.PanelWidget;
import VASSAL.build.widget.PieceSlot;
import VASSAL.build.widget.TabWidget;
import VASSAL.counters.GamePiece;
import VASSAL.counters.PieceCloner;

public class SalvoGamePiecePaletteDalImpl implements SalvoGamePiecePaletteDal {

	public SalvoGamePiecePaletteDalImpl(SalvoGamePieceDal salvoGamePieceDal) {
		this.salvoGamePieceDal = salvoGamePieceDal;
	}
	@Override
	public List<SalvoGameElement> searchAll() {
		List<SalvoGameElement> salvoGameElements = new ArrayList<>();
		
		GameModule gameModule = GameModule.getGameModule();
		List<PieceWindow> pieceWindows = gameModule.getComponentsOf(PieceWindow.class);
		
		for (PieceWindow pieceWindow: pieceWindows) {
			SalvoGameElement salvoGameElement = buildSalvoGameElementFromWidget(pieceWindow);
			salvoGameElements.add(salvoGameElement);
		}
		return salvoGameElements;
	}

	private SalvoGameElement buildSalvoGameElementFromWidget(Widget widget) {
		SalvoGameElement salvoGameElement = null;
		if (widget instanceof PieceSlot) {
			PieceSlot pieceSlot = (PieceSlot)widget;
			GamePiece gamePiece = PieceCloner.getInstance().clonePiece(pieceSlot.getPiece());
			Point offset = new Point(0,0);
			SalvoGamePiece salvoGamePiece = salvoGamePieceDal.getSalvoGamePieceFromVassalGamePiece(gamePiece, offset);
			salvoGameElement = salvoGamePiece;
		} else {
			SalvoGamePiecePalette salvoGamePiecePalette = new SalvoGamePiecePalette();
			salvoGameElement = salvoGamePiecePalette;

			salvoGamePiecePalette.setName(widget.getConfigureName());
			salvoGamePiecePalette.setWidth(widget.getAttributeValueString(Widget.WIDTH));
			salvoGamePiecePalette.setHeight(widget.getAttributeValueString(Widget.HEIGHT));
			if (widget instanceof TabWidget){
				salvoGamePiecePalette.setType(SalvoGamePiecePalette.Type.TabWidget);
			} else if (widget instanceof PanelWidget) {
				salvoGamePiecePalette.setType(SalvoGamePiecePalette.Type.PanelWidget);
			} else if (widget instanceof BoxWidget) {
				salvoGamePiecePalette.setType(SalvoGamePiecePalette.Type.BoxWidget);
			} else if (widget instanceof ListWidget) {
				salvoGamePiecePalette.setType(SalvoGamePiecePalette.Type.ListWidget);
			} else if (widget instanceof PieceWindow) {
				salvoGamePiecePalette.setType(SalvoGamePiecePalette.Type.PieceWindow);
			} else {
				logger.warn("Unknown type of widget");
			}
			
			List<Buildable> buildables = widget.getBuildables();
			List<SalvoGameElement> salvoGameElements = getSalvoGameElementsFromBuildables (buildables);
				
			salvoGameElement.setContents(salvoGameElements);


		}
		return salvoGameElement;
	}

	private List<SalvoGameElement> getSalvoGameElementsFromBuildables(List<Buildable> buildables) {
		
		List<SalvoGameElement> salvoGameElements = new ArrayList<>();
		
		for (Buildable buildable : buildables) {
			if (buildable instanceof Widget) {
				Widget widget = (Widget)buildable;
		
				SalvoGameElement salvoGameElement = buildSalvoGameElementFromWidget(widget);
				salvoGameElements.add(salvoGameElement);
			}
		}
		
		return salvoGameElements;
	}

	private SalvoGamePieceDal salvoGamePieceDal;
	private static final Logger logger = LoggerFactory.getLogger(SalvoGamePiecePaletteDalImpl.class);
	
}
