package com.ethaneldridge.salvo.vassal.membrane.command;

import com.ethaneldridge.salvo.data.SalvoPoint;

public class SalvoDoubleClick {

	public SalvoDoubleClick() {
		// Required for Jackson instantiation
	}

	public SalvoDoubleClick(String mapId, SalvoPoint salvoPoint) {
		this.salvoPoint = salvoPoint;
	}

	public String getSalvoMapId() {
		return salvoMapId;
	}

	public void setSalvoMapId(String mapId) {
		this.salvoMapId = mapId;
	}

	public SalvoPoint getSalvoPoint() {
		return this.salvoPoint;
	}

	public void setSalvoPoint(SalvoPoint salvoPoint) {
		this.salvoPoint = salvoPoint;
	}

	private String salvoMapId;
	private SalvoPoint salvoPoint;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((salvoMapId == null) ? 0 : salvoMapId.hashCode());
		result = prime * result + ((salvoPoint == null) ? 0 : salvoPoint.hashCode());
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
		SalvoDoubleClick other = (SalvoDoubleClick) obj;
		if (salvoMapId == null) {
			if (other.salvoMapId != null)
				return false;
		} else if (!salvoMapId.equals(other.salvoMapId))
			return false;
		if (salvoPoint == null) {
			if (other.salvoPoint != null)
				return false;
		} else if (!salvoPoint.equals(other.salvoPoint))
			return false;
		return true;
	}
}