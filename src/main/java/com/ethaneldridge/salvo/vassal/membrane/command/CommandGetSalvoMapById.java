package com.ethaneldridge.salvo.vassal.membrane.command;

import com.ethaneldridge.salvo.dal.SalvoMapDal;
import com.ethaneldridge.salvo.data.SalvoMap;
import com.ethaneldridge.salvo.vassal.membrane.VassalEngine;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommandGetSalvoMapById implements Command {

	public CommandGetSalvoMapById(VassalEngine vassalEngine, SalvoMapDal salvoMapDal) {
		this.vassalEngine = vassalEngine;
		this.salvoMapDal = salvoMapDal;
	}
	@Override
	public Object apply(String request) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();

		HttpGetRequest httpGetRequest = objectMapper.readValue(request, HttpGetRequest.class);
		// Some commands trigger a game activity.
		// This one doesn't.  Just need to tell the thread to continue
		synchronized (vassalEngine) {
			vassalEngine.resetClicks();
			vassalEngine.notify();
		}
		SalvoMap salvoMap = salvoMapDal.getById((String)httpGetRequest.getQueryParameters().get("id"));
		return salvoMap;
	}

	private VassalEngine vassalEngine;
	private SalvoMapDal salvoMapDal;
}
