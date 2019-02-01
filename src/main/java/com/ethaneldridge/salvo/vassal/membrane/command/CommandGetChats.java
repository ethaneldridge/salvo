package com.ethaneldridge.salvo.vassal.membrane.command;

import com.ethaneldridge.salvo.dal.SalvoChatDal;
import com.ethaneldridge.salvo.vassal.membrane.VassalEngine;

public class CommandGetChats implements Command {

	public CommandGetChats(VassalEngine vassalEngine, SalvoChatDal salvoChatDal) {
		this.vassalEngine = vassalEngine;
		this.salvoChatDal = salvoChatDal;
	}

	@Override
	public Object apply(String request) throws Exception {
		synchronized (vassalEngine) {
			vassalEngine.resetClicks();
			vassalEngine.notify();
		}
		return salvoChatDal.searchAll();
	}

	private final VassalEngine vassalEngine;
	private SalvoChatDal salvoChatDal;
}
