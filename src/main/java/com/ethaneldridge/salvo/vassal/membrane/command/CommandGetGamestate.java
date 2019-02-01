package com.ethaneldridge.salvo.vassal.membrane.command;

import com.ethaneldridge.salvo.dal.SalvoGameStateDal;
import com.ethaneldridge.salvo.data.SalvoGamePiece;
import com.ethaneldridge.salvo.data.SalvoGameState;
import com.ethaneldridge.salvo.data.SalvoPlayer;
import com.ethaneldridge.salvo.vassal.membrane.VassalEngine;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommandGetGamestate implements Command {

	public CommandGetGamestate (VassalEngine vassalEngine, SalvoGameStateDal salvoGameStateDal) {
		this.vassalEngine = vassalEngine;
		this.salvoGameStateDal = salvoGameStateDal;
	}
	@Override
	public Object apply(String request) throws Exception {
		// Some commands trigger a game activity.
		// This one doesn't.  Just need to tell the thread to continue
		synchronized (vassalEngine) {
			vassalEngine.resetClicks();
			vassalEngine.notify();
		}
		
		ObjectMapper objectMapper = new ObjectMapper();

		SalvoPlayer player = objectMapper.readValue(request, SalvoPlayer.class);
		
		SalvoGameState salvoGameState = salvoGameStateDal.getSalvoGameStateByPlayer(player);
		return salvoGameState;
	}
	private final VassalEngine vassalEngine;
	private final SalvoGameStateDal salvoGameStateDal;
}
