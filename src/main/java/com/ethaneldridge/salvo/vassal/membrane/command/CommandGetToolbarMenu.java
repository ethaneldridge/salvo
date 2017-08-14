package com.ethaneldridge.salvo.vassal.membrane.command;

import com.ethaneldridge.salvo.dal.SalvoToolbarMenuDal;
import com.ethaneldridge.salvo.vassal.membrane.VassalEngine;

public class CommandGetToolbarMenu implements Command {

	public CommandGetToolbarMenu(VassalEngine vassalEngine, SalvoToolbarMenuDal salvoToolbarMenuDal) {
		this.vassalEngine = vassalEngine;
		this.salvoToolbarMenuDal = salvoToolbarMenuDal;
	}

	@Override
	public Object apply(String request) throws Exception {
		synchronized (vassalEngine) {
			vassalEngine.resetClicks();
			vassalEngine.notify();
		}
		
		return salvoToolbarMenuDal.getSalvoToolbarMenu();
	}

	private final VassalEngine vassalEngine;
	private final SalvoToolbarMenuDal salvoToolbarMenuDal;
}
