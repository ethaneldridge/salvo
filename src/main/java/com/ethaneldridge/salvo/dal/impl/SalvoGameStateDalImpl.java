package com.ethaneldridge.salvo.dal.impl;

import com.ethaneldridge.salvo.dal.SalvoGamePiecePaletteDal;
import com.ethaneldridge.salvo.dal.SalvoGameStateDal;
import com.ethaneldridge.salvo.dal.SalvoMapDal;
import com.ethaneldridge.salvo.dal.SalvoModuleMetaDataDal;
import com.ethaneldridge.salvo.dal.SalvoTurnTrackerDal;
import com.ethaneldridge.salvo.data.SalvoGameState;
import com.ethaneldridge.salvo.data.SalvoPlayer;
import com.ethaneldridge.salvo.vassal.membrane.SalvoBasicModule;

import VASSAL.build.GameModule;

public class SalvoGameStateDalImpl implements SalvoGameStateDal {

	public SalvoGameStateDalImpl(SalvoModuleMetaDataDal salvoModuleMetaDataDal,
			SalvoTurnTrackerDal salvoTurnTrackerDal,
			SalvoMapDal salvoMapDal,
			SalvoGamePiecePaletteDal salvoGamePiecePaletteDal) {
		this.salvoModuleMetaDataDal = salvoModuleMetaDataDal;
		this.salvoTurnTrackerDal = salvoTurnTrackerDal;
		this.salvoMapDal = salvoMapDal;
		this.salvoGamePiecePaletteDal = salvoGamePiecePaletteDal;
	}

	@Override
	public SalvoGameState getSalvoGameStateByPlayer(SalvoPlayer salvoPlayer) {
		SalvoBasicModule salvoGameModule = (SalvoBasicModule)GameModule.getGameModule();
		salvoGameModule.changeIdentityPreferences(salvoPlayer);
		SalvoGameState salvoGameState = new SalvoGameState(
				this.salvoModuleMetaDataDal.get(),
				this.salvoTurnTrackerDal.getSalvoTurnTracker(),
				this.salvoMapDal.searchAll(),
				this.salvoGamePiecePaletteDal.searchAll());

		return salvoGameState;
	}

	private SalvoModuleMetaDataDal salvoModuleMetaDataDal;
	private SalvoTurnTrackerDal salvoTurnTrackerDal;
	private SalvoMapDal salvoMapDal;
	private SalvoGamePiecePaletteDal salvoGamePiecePaletteDal;
}
