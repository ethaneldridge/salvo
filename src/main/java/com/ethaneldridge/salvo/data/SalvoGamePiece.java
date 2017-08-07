package com.ethaneldridge.salvo.data;

import java.util.List;

public class SalvoGamePiece implements SalvoGameElement {

	@Override
	public void setContents(List<SalvoGameElement> contents) {
		// No-Op
	}

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

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public String getStackName() {
		return stackName;
	}

	public void setStackName(String stackName) {
		this.stackName = stackName;
	}

	public SalvoRectangle getDimension() {
		return dimension;
	}

	public void setDimension(SalvoRectangle dimension) {
		this.dimension = dimension;
	}

	public Location getLocationOld() {
		return locationOld;
	}

	public void setLocationOld(Location LocationOld) {
		this.locationOld = LocationOld;
	}

	public Location getLocationNew() {
		return locationNew;
	}

	public void setLocationNew(Location locationNew) {
		this.locationNew = locationNew;
	}

	private String id;
	private String name;
	private String imageName;
	private boolean isVisible = false;
	private String stackName = null;
	private SalvoRectangle dimension;
	private Location locationOld;

	private Location locationNew;

	public static class Location {

		public String getSalvoMapId() {
			return salvoMapId;
		}
		public void setSalvoMapId(String salvoMapId) {
			this.salvoMapId = salvoMapId;
		}
		public SalvoPoint getSalvoPoint() {
			return salvoPoint;
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
			Location other = (Location) obj;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dimension == null) ? 0 : dimension.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((imageName == null) ? 0 : imageName.hashCode());
		result = prime * result + (isVisible ? 1231 : 1237);
		result = prime * result + ((locationNew == null) ? 0 : locationNew.hashCode());
		result = prime * result + ((locationOld == null) ? 0 : locationOld.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((stackName == null) ? 0 : stackName.hashCode());
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
		SalvoGamePiece other = (SalvoGamePiece) obj;
		if (dimension == null) {
			if (other.dimension != null)
				return false;
		} else if (!dimension.equals(other.dimension))
			return false;
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
		if (isVisible != other.isVisible)
			return false;
		if (locationNew == null) {
			if (other.locationNew != null)
				return false;
		} else if (!locationNew.equals(other.locationNew))
			return false;
		if (locationOld == null) {
			if (other.locationOld != null)
				return false;
		} else if (!locationOld.equals(other.locationOld))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (stackName == null) {
			if (other.stackName != null)
				return false;
		} else if (!stackName.equals(other.stackName))
			return false;
		return true;
	}
}
