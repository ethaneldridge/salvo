package com.ethaneldridge.salvo.vassal.membrane.command;

import com.ethaneldridge.salvo.dal.SalvoGameStateDal;
import com.ethaneldridge.salvo.data.SalvoGameState;
import com.ethaneldridge.salvo.vassal.membrane.VassalEngine;

public class CommandGetGamestate implements Command {

	public CommandGetGamestate (VassalEngine vassalEngine, SalvoGameStateDal salvoGameStateDal) {
		this.vassalEngine = vassalEngine;
		this.salvoGameStateDal = salvoGameStateDal;
	}
	@Override
	public Object apply(String request) throws Exception {
		// No-op...just need to tell the thread to continue
		synchronized (vassalEngine) {
			vassalEngine.resetClicks();
			vassalEngine.notify();
		}
		SalvoGameState salvoGameState = salvoGameStateDal.getSalvoGameState();
		return salvoGameState;
	}
	private final VassalEngine vassalEngine;
	private final SalvoGameStateDal salvoGameStateDal;
}
