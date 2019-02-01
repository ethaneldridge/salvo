package com.ethaneldridge.salvo.vassal.membrane.repository.impl;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JToolBar;

import com.ethaneldridge.salvo.data.SalvoGamePiece;
import com.ethaneldridge.salvo.data.SalvoGamePiece.Location;
import com.ethaneldridge.salvo.vassal.membrane.VassalRepository;
import com.ethaneldridge.salvo.data.SalvoMap;
import com.ethaneldridge.salvo.data.SalvoPoint;
import com.ethaneldridge.salvo.data.SalvoRectangle;
import com.ethaneldridge.salvo.data.SalvoToolbar;
import com.ethaneldridge.salvo.data.SalvoToolbarItem;

import VASSAL.build.GameModule;
import VASSAL.build.module.VassalSalvoToolbarMenu;
import VASSAL.build.module.map.BoardPicker;
import VASSAL.build.module.map.boardPicker.Board;
import VASSAL.counters.BasicPiece;
import VASSAL.counters.Decorator;
import VASSAL.counters.GamePiece;
import VASSAL.counters.Stack;
import VASSAL.tools.SequenceEncoder;

public class VassalRepositoryImpl implements VassalRepository {

	@Override
	public Map<JToolBar, SalvoToolbar> searchAllSalvoToolbars() {
		return salvoToolbars;
	}

	@Override
	public List<SalvoMap> searchAllSalvoMaps() {
		List<SalvoMap> salvoMaps = new ArrayList<>();
		List<VASSAL.build.module.Map> maps = VASSAL.build.module.Map.getMapList();
		for (VASSAL.build.module.Map vassalMap : maps) {
			SalvoMap salvoMap = buildSalvoMapFromVassalMap(vassalMap);
			salvoMaps.add(salvoMap);
		}
		return salvoMaps;
	}

	@Override
	public SalvoGamePiece getSalvoGamePieceFromVassalGamePiece(GamePiece gamePiece, Point offset) {
		SalvoGamePiece salvoGamePiece = new SalvoGamePiece();
		if (gamePiece instanceof Decorator) {
			while (gamePiece instanceof Decorator) {
				gamePiece = ((Decorator)gamePiece).getInner();
			}
		}

		if (gamePiece instanceof BasicPiece) {
			
			buildSalvoGamePiece(salvoGamePiece, gamePiece, offset);
		}
		return salvoGamePiece;
	}

	@Override
	public SalvoMap getSalvoMapById(String id) {
		List<VASSAL.build.module.Map> maps = VASSAL.build.module.Map.getMapList();
		VASSAL.build.module.Map vassalMap = maps.stream()
								.filter(map -> id.equals(map.getId()))
								.findFirst()
								.orElse(null);

		SalvoMap salvoMap = buildSalvoMapFromVassalMap(vassalMap);
		return salvoMap;
	}

	@Override
	public SalvoToolbar getSalvoToolbarByJToolBar(JToolBar jToolBar) {
		SalvoToolbar salvoToolbar = salvoToolbars.get(jToolBar);
		if (salvoToolbar == null) {
			salvoToolbar = new SalvoToolbar();
			salvoToolbar.setAssociatedVassalJToolBar(jToolBar);
			save(salvoToolbar);
		}
		return salvoToolbar;
	}

	@Override
	public void save(SalvoToolbar salvoToolbar) {
		JToolBar jToolBar = salvoToolbar.getAssociatedVassalJToolBar();
		if (jToolBar == null) {
			throw new RuntimeException("Invalid associated Vassal JToolBar (null) for salvoToolbar ");
		}
		this.salvoToolbars.put(jToolBar, salvoToolbar);
	}

	@Override
	public void loadCache() {
		List<VassalSalvoToolbarMenu> vassalSalvoToolbarMenus = GameModule.getGameModule().getComponentsOf(VassalSalvoToolbarMenu.class);
		for (VassalSalvoToolbarMenu vassalSalvoToolbarMenu : vassalSalvoToolbarMenus) {
			SalvoToolbarItem salvoToolbarItem = getFromVassalToolbarMenu(vassalSalvoToolbarMenu);
			SalvoToolbar salvoToolbar = getSalvoToolbarByJToolBar(vassalSalvoToolbarMenu.getJToolBar());
			salvoToolbar.add(salvoToolbarItem);
			save(salvoToolbar);
		}
	}

	@Override
	public void validate() throws Exception {
		// FIXME - use a filter
		for (Entry<JToolBar, SalvoToolbar> mapEntry : salvoToolbars.entrySet()) {
			if (!(mapEntry.getKey() instanceof JToolBar)) {
				throw new Exception (String.format("Found SalvoToolbarMenu that doesn't belong to a JToolBar: [%d]", mapEntry.getValue().getId()));
			}
		}
		
	}

	private SalvoToolbarItem getFromVassalToolbarMenu(VassalSalvoToolbarMenu vassalSalvoToolbarMenu) {
		SalvoToolbarItem salvoToolbarItem = new SalvoToolbarItem();
		salvoToolbarItem.setButtonIcon(vassalSalvoToolbarMenu.getAttributeValueString(VassalSalvoToolbarMenu.BUTTON_ICON));
		salvoToolbarItem.setButtonText(vassalSalvoToolbarMenu.getAttributeValueString(VassalSalvoToolbarMenu.BUTTON_TEXT));
		salvoToolbarItem.setDescription(vassalSalvoToolbarMenu.getAttributeValueString(VassalSalvoToolbarMenu.DESCRIPTION));
		salvoToolbarItem.setButtonHotkey(vassalSalvoToolbarMenu.getAttributeValueString(VassalSalvoToolbarMenu.BUTTON_HOTKEY));
		salvoToolbarItem.setTooltip(vassalSalvoToolbarMenu.getAttributeValueString(VassalSalvoToolbarMenu.TOOLTIP));
		salvoToolbarItem.setMenuItems((vassalSalvoToolbarMenu.getAttributeValueString(VassalSalvoToolbarMenu.MENU_ITEMS)).split(","));

		return salvoToolbarItem;
	}

	private SalvoMap buildSalvoMapFromVassalMap(VASSAL.build.module.Map vassalMap) {
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
					offset.x += STACK_X_OFFSET;
					offset.y += STACK_Y_OFFSET;
					salvoGamePieces.add(getSalvoGamePieceFromVassalGamePiece(gamePiece, offset));
				}
			} else {
				salvoGamePieces.add(getSalvoGamePieceFromVassalGamePiece(gp, POINT_NO_OFFSET));
			}
		}
		
		salvoMap.setPieces(salvoGamePieces);

		// Find the toolbar that belongs to this map
		// FIXME: This is a filter operation
		java.util.Map<JToolBar, SalvoToolbar> salvoToolbarMenus = searchAllSalvoToolbars();
		for (java.util.Map.Entry<JToolBar, SalvoToolbar> mapEntry : salvoToolbarMenus.entrySet()) {
			JToolBar jToolBar = mapEntry.getKey();
			if (jToolBar.equals(vassalMap.getToolBar())) {
				SalvoToolbar salvoToolbar = mapEntry.getValue();
				// FIXME: not comfortable with setting this link here.  It seems a call to one should auto-link the other
				salvoMap.setSalvoToolbar(salvoToolbar);
				salvoToolbar.setParentSalvoMap(salvoMap);
				
				break;
			}
		}
		return salvoMap;
	}

	private String getMainMapImageName(VASSAL.build.module.Map mainMap) {
		Board board = getMainMapBoard(mainMap);
		String image = board.fileName();
		return image;
	}

	private Board getMainMapBoard(VASSAL.build.module.Map mainMap) {
		BoardPicker boardPicker = mainMap.getBoardPicker();
		Collection<Board> boards = boardPicker.getSelectedBoards();
		Board board = boards.iterator().next();
		return board;
	}

	private void buildSalvoGamePiece(SalvoGamePiece salvoGamePiece, GamePiece gamePiece, Point offset) {

		salvoGamePiece.setId(gamePiece.getId());
		salvoGamePiece.setName(gamePiece.getName());
		
		salvoGamePiece.setImageName(getGamePieceImageName(gamePiece));
		salvoGamePiece.setVisible(determineGamePieceVisibility(gamePiece));
		salvoGamePiece.setStackName(determineGamePieceStackName(gamePiece));

		SalvoRectangle dimension = new SalvoRectangle();
		dimension.setHeight(gamePiece.boundingBox().height);
		dimension.setWidth(gamePiece.boundingBox().width);
		salvoGamePiece.setDimension(dimension);
		SalvoGamePiece.Location locationOld = buildLocation(gamePiece, BasicPiece.OLD_MAP, BasicPiece.OLD_X, BasicPiece.OLD_Y);
		salvoGamePiece.setLocationOld(locationOld);

		SalvoGamePiece.Location locationNew = buildLocation(gamePiece, BasicPiece.CURRENT_MAP, BasicPiece.CURRENT_X, BasicPiece.CURRENT_Y);
		salvoGamePiece.setLocationNew(locationNew);
	}

	private Location buildLocation(GamePiece gamePiece, String PropertyMap, String PropertyX, String PropertyY) {
		SalvoGamePiece.Location location = new SalvoGamePiece.Location();

		String x = (String)gamePiece.getProperty(PropertyX);
		String y = (String)gamePiece.getProperty(PropertyY);
		
		if (x != null & y != null) {
			int xInt = Integer.valueOf(x);
			int yInt = Integer.valueOf(y);
			// Point oldP = mainMap.mapCoordinates(new Point(oldXint, oldYint));
			// oldP.x -= oldXint;
			// oldP.y -= oldYint;
			VASSAL.build.module.Map vassalMap = gamePiece.getMap();
			location.setSalvoMapId(vassalMap == null? "" : vassalMap.getId());
			location.setSalvoPoint(new SalvoPoint(xInt, yInt));
		}
		return location;
	}
	private String determineGamePieceStackName(GamePiece gamePiece) {
		String stackName = null;
		Stack stack = gamePiece.getParent();
		if (stack != null) {
			stackName = stack.getId();
		}
		
		return stackName;
	}

	private boolean determineGamePieceVisibility(GamePiece gamePiece) {
		String imageName = getGamePieceImageName(gamePiece);
		return (imageName.endsWith(".png") || imageName.endsWith("gif"));
	}

	private String getGamePieceImageName(GamePiece p) {
		String gamePieceType = p.getType();
		String gamePieceImage = decodeGamePieceType(gamePieceType);
		return gamePieceImage;
	}

	private String decodeGamePieceType(String gamePieceType) {
		final SequenceEncoder.Decoder st = new SequenceEncoder.Decoder(gamePieceType, ';');
		st.nextToken();
		char cloneKey = st.nextChar('\0');
		char deleteKey = st.nextChar('\0');
		String imageName = st.nextToken();
		String commonName = st.nextToken();

		return imageName;
	}

	private static final Point POINT_NO_OFFSET = new Point(0, 0);
	private static final int STACK_X_OFFSET = 4;
	private static final int STACK_Y_OFFSET = -4;

	private Map<JToolBar, SalvoToolbar> salvoToolbars = new HashMap<>();

}
