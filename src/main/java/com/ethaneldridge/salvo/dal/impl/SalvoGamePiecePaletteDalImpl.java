package com.ethaneldridge.salvo.dal.impl;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
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
		SalvoGamePiecePalette salvoGamePiecePalette = new SalvoGamePiecePalette();
		salvoGamePiecePalette.setName(widget.getConfigureName());
		if (widget instanceof PieceSlot) {
			PieceSlot pieceSlot = (PieceSlot)widget;
			salvoGamePiecePalette.setType(SalvoGamePiecePalette.Type.PIECE_SLOT);
			salvoGamePiecePalette.setId(pieceSlot.getGpId());

			GamePiece gamePiece = PieceCloner.getInstance().clonePiece(pieceSlot.getPiece());
			Point offset = new Point(0,0);
			SalvoGamePiece salvoGamePiece = salvoGamePieceDal.getSalvoGamePieceFromVassalGamePiece(gamePiece, offset);
			String salvoGamePieceMapId = String.format("%s%s",SalvoGamePiecePalette.Type.PIECE_SLOT, pieceSlot.getGpId());
			salvoGamePiece.getLocationNew().setSalvoMapId(salvoGamePieceMapId);

			salvoGamePiecePalette.setContents(Arrays.asList(new SalvoGamePiece[] {salvoGamePiece}));
			salvoGamePiecePalette.setWidth(Integer.toString(salvoGamePiece.getDimension().getWidth()));
			salvoGamePiecePalette.setHeight(Integer.toString(salvoGamePiece.getDimension().getHeight()));
		} else {
			String width = widget.getAttributeValueString(Widget.WIDTH);
			String height = widget.getAttributeValueString(Widget.HEIGHT);
			salvoGamePiecePalette.setWidth(width);
			salvoGamePiecePalette.setHeight(height);

			if (widget instanceof TabWidget){
				salvoGamePiecePalette.setType(SalvoGamePiecePalette.Type.TAB_WIDGET);
			} else if (widget instanceof PanelWidget) {
				salvoGamePiecePalette.setType(SalvoGamePiecePalette.Type.PANEL_WIDGET);
			} else if (widget instanceof BoxWidget) {
				salvoGamePiecePalette.setType(SalvoGamePiecePalette.Type.BOX_WIDGET);
			} else if (widget instanceof ListWidget) {
				salvoGamePiecePalette.setType(SalvoGamePiecePalette.Type.LIST_WIDGET);
			} else if (widget instanceof PieceWindow) {
				salvoGamePiecePalette.setType(SalvoGamePiecePalette.Type.PIECE_WINDOW);
			} else {
				logger.warn("Unknown type of widget");
			}
			List<Buildable> buildables = widget.getBuildables();
			List<SalvoGameElement> salvoGameElements = getSalvoGameElementsFromBuildables (buildables);
			salvoGamePiecePalette.setContents(salvoGameElements);
		}
		return salvoGamePiecePalette;
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
