package com.ethaneldridge.salvo.vassal.membrane;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
