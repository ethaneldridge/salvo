package com.ethaneldridge.salvo.vassal.membrane.command;

import com.ethaneldridge.salvo.dal.SalvoMapDal;
import com.ethaneldridge.salvo.vassal.membrane.VassalEngine;

public class CommandGetMainToolbar implements Command {

	public CommandGetMainToolbar(VassalEngine vassalEngine, SalvoMapDal salvoMapDal) {
		this.vassalEngine = vassalEngine;
		this.salvoMapDal = salvoMapDal;
	}

	@Override
	public Object apply(String request) throws Exception {
		// Some commands trigger a game activity.
		// This one doesn't.  Just need to tell the thread to continue
		synchronized (vassalEngine) {
			vassalEngine.resetClicks();
			vassalEngine.notify();
		}
		
//		return salvoMapDal.getMain().getSalvoToolbar();
		return vassalEngine.getVassalRepository().searchAllSalvoToolbars();
	}

	private final VassalEngine vassalEngine;
	private final SalvoMapDal salvoMapDal;
}
