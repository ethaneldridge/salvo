package com.ethaneldridge.salvo.data;

import java.util.List;

public class SalvoMap {

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public double getZoom() {
		return zoom;
	}
	public void setZoom(double zoom) {
		this.zoom = zoom;
	}
	public List<SalvoGamePiece> getPieces() {
		return pieces;
	}
	public void setPieces(List<SalvoGamePiece> pieces) {
		this.pieces = pieces;
	}

	private static final String MAIN_MAP_ID = "Map0";

	private String id;
	private String name;
	private String imageName;
	private double zoom;
	private List<SalvoGamePiece> pieces;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((imageName == null) ? 0 : imageName.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pieces == null) ? 0 : pieces.hashCode());
		long temp;
		temp = Double.doubleToLongBits(zoom);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		SalvoMap other = (SalvoMap) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (imageName == null) {
			if (other.imageName != null)
				return false;
		} else if (!imageName.equals(other.imageName))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pieces == null) {
			if (other.pieces != null)
				return false;
		} else if (!pieces.equals(other.pieces))
			return false;
		if (Double.doubleToLongBits(zoom) != Double.doubleToLongBits(other.zoom))
			return false;
		return true;
	}
}
