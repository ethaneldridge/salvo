package com.ethaneldridge.salvo.dal.impl;

import java.awt.Point;

import com.ethaneldridge.salvo.dal.SalvoGamePieceDal;
import com.ethaneldridge.salvo.data.SalvoGamePiece;
import com.ethaneldridge.salvo.data.SalvoGamePiece.Location;
import com.ethaneldridge.salvo.data.SalvoPoint;
import com.ethaneldridge.salvo.data.SalvoRectangle;

import VASSAL.counters.BasicPiece;
import VASSAL.counters.Decorator;
import VASSAL.counters.GamePiece;
import VASSAL.counters.Stack;
import VASSAL.tools.SequenceEncoder;

public class SalvoGamePieceDalImpl implements SalvoGamePieceDal {

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

}
