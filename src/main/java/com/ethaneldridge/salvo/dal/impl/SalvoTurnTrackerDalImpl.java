package com.ethaneldridge.salvo.dal.impl;

import java.util.List;

import com.ethaneldridge.salvo.dal.SalvoTurnTrackerDal;

import VASSAL.build.GameModule;
import VASSAL.build.module.turn.VassalSalvoTurnTracker;

public class SalvoTurnTrackerDalImpl implements SalvoTurnTrackerDal {

	@Override
	public VassalSalvoTurnTracker getSalvoTurnTracker() {
		List<VassalSalvoTurnTracker> components = GameModule.getGameModule().getComponentsOf(VassalSalvoTurnTracker.class);
		return components.stream().findFirst().get();
	}

}
