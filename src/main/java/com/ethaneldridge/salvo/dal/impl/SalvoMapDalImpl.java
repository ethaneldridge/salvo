package com.ethaneldridge.salvo.dal.impl;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.ethaneldridge.salvo.dal.SalvoGamePieceDal;
import com.ethaneldridge.salvo.dal.SalvoMapDal;
import com.ethaneldridge.salvo.data.SalvoGamePiece;
import com.ethaneldridge.salvo.data.SalvoMap;

import VASSAL.build.module.Map;
import VASSAL.build.module.map.BoardPicker;
import VASSAL.build.module.map.boardPicker.Board;
import VASSAL.counters.GamePiece;
import VASSAL.counters.Stack;

public class SalvoMapDalImpl implements SalvoMapDal {

	public SalvoMapDalImpl (SalvoGamePieceDal salvoGamePieceDal) {
		this.salvoGamePieceDal = salvoGamePieceDal;
	}

	@Override
	public SalvoMap getMapById(String id) {
		List<VASSAL.build.module.Map> maps = VASSAL.build.module.Map.getMapList();
		VASSAL.build.module.Map vassalMap = maps.stream()
								.filter(map -> id.equals(map.getId()))
								.findFirst()
								.orElse(null);

		SalvoMap salvoMap = buildSalvoMapFromVassalMap(vassalMap);
		return salvoMap;
	}

	@Override
	public List<SalvoMap> searchAll() {
		List<SalvoMap> salvoMaps = new ArrayList<>();
		List<VASSAL.build.module.Map> maps = VASSAL.build.module.Map.getMapList();
		for (VASSAL.build.module.Map vassalMap : maps) {
			SalvoMap salvoMap = buildSalvoMapFromVassalMap(vassalMap);
			salvoMaps.add(salvoMap);
		}
		return salvoMaps;
	}

	private SalvoMap buildSalvoMapFromVassalMap(Map vassalMap) {
		SalvoMap salvoMap = new SalvoMap();
		salvoMap.setId(vassalMap.getId());
		salvoMap.setName(vassalMap.getMapName());
		salvoMap.setImageName(getMainMapImageName(vassalMap));
		salvoMap.setZoom(vassalMap.getZoom());

		List<SalvoGamePiece> salvoGamePieces = new ArrayList<>();
		GamePiece[] gamePieces = vassalMap.getAllPieces();
		for (GamePiece gp : gamePieces) {
			if (gp instanceof Stack) {
				Point offset = new Point(0,0);
				Iterator<GamePiece> iterator = ((Stack) gp).getPiecesIterator();
				while(iterator.hasNext()) {
					GamePiece gamePiece = iterator.next();
					salvoGamePieces.add(salvoGamePieceDal.getSalvoGamePieceFromVassalGamePiece(gamePiece, offset));
					offset.x += STACK_X_OFFSET;
					offset.y += STACK_Y_OFFSET;
				}
			} else {
				salvoGamePieces.add(salvoGamePieceDal.getSalvoGamePieceFromVassalGamePiece(gp, POINT_NO_OFFSET));
			}
		}
		
		salvoMap.setPieces(salvoGamePieces);

		return salvoMap;
	}

	private String getMainMapImageName(Map mainMap) {
		Board board = getMainMapBoard(mainMap);
		String image = board.fileName();
		return image;
	}

	private Board getMainMapBoard(Map mainMap) {
		BoardPicker boardPicker = mainMap.getBoardPicker();
		Collection<Board> boards = boardPicker.getSelectedBoards();
		Board board = boards.iterator().next();
		return board;
	}

	private SalvoGamePieceDal salvoGamePieceDal;

	private static final Point POINT_NO_OFFSET = new Point(0, 0);
	private static final int STACK_X_OFFSET = 4;
	private static final int STACK_Y_OFFSET = -4;
}
