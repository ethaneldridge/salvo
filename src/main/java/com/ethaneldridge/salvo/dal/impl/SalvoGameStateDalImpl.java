package com.ethaneldridge.salvo.dal.impl;

import com.ethaneldridge.salvo.dal.SalvoGamePiecePaletteDal;
import com.ethaneldridge.salvo.dal.SalvoGameStateDal;
import com.ethaneldridge.salvo.dal.SalvoMapDal;
import com.ethaneldridge.salvo.dal.SalvoTurnTrackerDal;
import com.ethaneldridge.salvo.data.SalvoGameState;

public class SalvoGameStateDalImpl implements SalvoGameStateDal {

	public SalvoGameStateDalImpl(SalvoTurnTrackerDal salvoTurnTrackerDal,
			SalvoMapDal salvoMapDal,
			SalvoGamePiecePaletteDal salvoGamePiecePaletteDal) {
		this.salvoTurnTrackerDal = salvoTurnTrackerDal;
		this.salvoMapDal = salvoMapDal;
		this.salvoGamePiecePaletteDal = salvoGamePiecePaletteDal;
	}

	@Override
	public SalvoGameState getSalvoGameState() {
		SalvoGameState salvoGameState = new SalvoGameState(
				this.salvoTurnTrackerDal.getSalvoTurnTracker(),
				this.salvoMapDal.searchAll(),
				this.salvoGamePiecePaletteDal.searchAll());

		return salvoGameState;
	}

	private SalvoTurnTrackerDal salvoTurnTrackerDal;
	private SalvoMapDal salvoMapDal;
	private SalvoGamePiecePaletteDal salvoGamePiecePaletteDal;
}
