package com.ethaneldridge.salvo.data;

import java.util.List;

import VASSAL.build.module.turn.VassalSalvoTurnTracker;

public class SalvoGameState {

	public SalvoGameState() {
		// Required for Jackson instantiation
	}

	public SalvoGameState(VassalSalvoTurnTracker salvoTurnTracker, List<SalvoMap> salvoMapAll, List<SalvoGameElement> salvoGamePiecePalettes) {
		this.salvoTurnTracker = salvoTurnTracker;
		this.salvoMaps = salvoMapAll;
		this.salvoGamePiecePalettes = salvoGamePiecePalettes;
	}

	public VassalSalvoTurnTracker getSalvoTurnTracker() {
		return salvoTurnTracker;
	}

	public void setSalvoTurnTracker(VassalSalvoTurnTracker salvoTurnTracker) {
		this.salvoTurnTracker = salvoTurnTracker;
	}

	public List<SalvoMap> getSalvoMaps() {
		return salvoMaps;
	}

	public void setSalvoMaps(List<SalvoMap> salvoMaps) {
		this.salvoMaps = salvoMaps;
	}

	public List<SalvoGameElement> getSalvoGamePiecePalettes() {
		return salvoGamePiecePalettes;
	}

	public void setSalvoGamePiecePalettes(List<SalvoGameElement> salvoGamePiecePalettes) {
		this.salvoGamePiecePalettes = salvoGamePiecePalettes;
	}

	private VassalSalvoTurnTracker salvoTurnTracker;
	private List<SalvoMap> salvoMaps;
	private List<SalvoGameElement> salvoGamePiecePalettes;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((salvoGamePiecePalettes == null) ? 0 : salvoGamePiecePalettes.hashCode());
		result = prime * result + ((salvoMaps == null) ? 0 : salvoMaps.hashCode());
		result = prime * result + ((salvoTurnTracker == null) ? 0 : salvoTurnTracker.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SalvoGameState other = (SalvoGameState) obj;
		if (salvoGamePiecePalettes == null) {
			if (other.salvoGamePiecePalettes != null)
				return false;
		} else if (!salvoGamePiecePalettes.equals(other.salvoGamePiecePalettes))
			return false;
		if (salvoMaps == null) {
			if (other.salvoMaps != null)
				return false;
		} else if (!salvoMaps.equals(other.salvoMaps))
			return false;
		if (salvoTurnTracker == null) {
			if (other.salvoTurnTracker != null)
				return false;
		} else if (!salvoTurnTracker.equals(other.salvoTurnTracker))
			return false;
		return true;
	}

}
