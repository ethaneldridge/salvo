package com.ethaneldridge.salvo.vassal.membrane;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ethaneldridge.salvo.dal.SalvoGamePieceDal;
import com.ethaneldridge.salvo.dal.SalvoGamePiecePaletteDal;
import com.ethaneldridge.salvo.dal.SalvoGameStateDal;
import com.ethaneldridge.salvo.dal.SalvoMapDal;
import com.ethaneldridge.salvo.dal.SalvoTurnTrackerDal;
import com.ethaneldridge.salvo.dal.impl.SalvoGamePieceDalImpl;
import com.ethaneldridge.salvo.dal.impl.SalvoGamePiecePaletteDalImpl;
import com.ethaneldridge.salvo.dal.impl.SalvoGameStateDalImpl;
import com.ethaneldridge.salvo.dal.impl.SalvoMapDalImpl;
import com.ethaneldridge.salvo.dal.impl.SalvoTurnTrackerDalImpl;

import VASSAL.launch.Player;

public class GameWrapper extends Player {

	// FIXME: get port dynamically from param
	private int port = 3030;
	public static void main(String[] args) {
		logger.debug("GameWrapper");
		try {
			new GameWrapper(args);
		} catch (Exception e) {
			// FIXME: proper exception handling
			e.printStackTrace();
		}
	}

	private GameWrapper(String[] args) throws Exception {
		super(args);

		SalvoGamePieceDal salvoGamePieceDal = new SalvoGamePieceDalImpl();
		SalvoTurnTrackerDal salvoTurnTrackerDal = new SalvoTurnTrackerDalImpl();
		SalvoMapDal salvoMapDal = new SalvoMapDalImpl(salvoGamePieceDal);
		SalvoGamePiecePaletteDal salvoGamePiecePaletteDal = new SalvoGamePiecePaletteDalImpl(salvoGamePieceDal);
		SalvoGameStateDal salvoGameStateDal = new SalvoGameStateDalImpl(
				salvoTurnTrackerDal,
				salvoMapDal,
				salvoGamePiecePaletteDal);

		vassalEngine.setSalvoGameStateDal(salvoGameStateDal);
		vassalEngine.connect(port);
	}

	@Override
	protected void launch() throws IOException {
		super.launch();

		vassalEngine.launch();
	}

	private static final Logger logger = LoggerFactory.getLogger(GameWrapper.class);
	private VassalEngine vassalEngine = VassalEngine.theVassalEngine();

}
