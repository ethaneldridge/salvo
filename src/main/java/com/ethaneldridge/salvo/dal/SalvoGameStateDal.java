package com.ethaneldridge.salvo.dal;

import com.ethaneldridge.salvo.data.SalvoGameState;
import com.ethaneldridge.salvo.data.SalvoPlayer;

public interface SalvoGameStateDal {

	public SalvoGameState getSalvoGameStateByPlayer(SalvoPlayer salvoPlayer);
}
