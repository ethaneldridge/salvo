package com.ethaneldridge.salvo.dal.impl;

import java.util.List;

import com.ethaneldridge.salvo.dal.SalvoTurnTrackerDal;

import VASSAL.build.GameModule;
import VASSAL.build.module.turn.SalvoTurnTracker;

public class SalvoTurnTrackerDalImpl implements SalvoTurnTrackerDal {

	@Override
	public SalvoTurnTracker getSalvoTurnTracker() {
		List<SalvoTurnTracker> components = GameModule.getGameModule().getComponentsOf(SalvoTurnTracker.class);
		return components.stream().findFirst().get();
	}

}
