package com.ethaneldridge.salvo.data;

public class SalvoDoubleClick {

	public SalvoDoubleClick() {
		// Required for Jackson instantiation
	}

	public SalvoDoubleClick(String mapId, SalvoPoint salvoPoint) {
		this.salvoPoint = salvoPoint;
	}

	public String getMapId() {
		return mapId;
	}

	public void setMapId(String mapId) {
		this.mapId = mapId;
	}

	public SalvoPoint getSalvoPoint() {
		return this.salvoPoint;
	}

	public void setSalvoPoint(SalvoPoint salvoPoint) {
		this.salvoPoint = salvoPoint;
	}

	private String mapId;
	private SalvoPoint salvoPoint;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mapId == null) ? 0 : mapId.hashCode());
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
		if (mapId == null) {
			if (other.mapId != null)
				return false;
		} else if (!mapId.equals(other.mapId))
			return false;
		if (salvoPoint == null) {
			if (other.salvoPoint != null)
				return false;
		} else if (!salvoPoint.equals(other.salvoPoint))
			return false;
		return true;
	}
}