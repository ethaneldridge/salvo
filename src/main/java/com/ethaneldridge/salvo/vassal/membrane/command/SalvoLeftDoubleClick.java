package com.ethaneldridge.salvo.vassal.membrane.command;

import com.ethaneldridge.salvo.data.SalvoPoint;

public class SalvoLeftDoubleClick {

	public enum TargetType {
		MAP,
		PALETTE;
	}
	public SalvoLeftDoubleClick() {
		// Required for Jackson instantiation
	}

	public SalvoLeftDoubleClick(String mapId, SalvoPoint salvoPoint) {
		this.salvoPoint = salvoPoint;
	}

	public TargetType getTargetType() {
		return targetType;
	}

	public void setTargetType(TargetType targetType) {
		this.targetType = targetType;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public SalvoPoint getSalvoPoint() {
		return this.salvoPoint;
	}

	public void setSalvoPoint(SalvoPoint salvoPoint) {
		this.salvoPoint = salvoPoint;
	}

	private TargetType targetType;
	private String targetId;
	private SalvoPoint salvoPoint;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((salvoPoint == null) ? 0 : salvoPoint.hashCode());
		result = prime * result + ((targetId == null) ? 0 : targetId.hashCode());
		result = prime * result + ((targetType == null) ? 0 : targetType.hashCode());
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
		SalvoLeftDoubleClick other = (SalvoLeftDoubleClick) obj;
		if (salvoPoint == null) {
			if (other.salvoPoint != null)
				return false;
		} else if (!salvoPoint.equals(other.salvoPoint))
			return false;
		if (targetId == null) {
			if (other.targetId != null)
				return false;
		} else if (!targetId.equals(other.targetId))
			return false;
		if (targetType != other.targetType)
			return false;
		return true;
	}
}