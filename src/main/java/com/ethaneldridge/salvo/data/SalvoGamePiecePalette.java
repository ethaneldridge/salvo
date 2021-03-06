package com.ethaneldridge.salvo.data;

import java.util.List;

public class SalvoGamePiecePalette implements SalvoGameElement {

	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
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
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public List<SalvoGameElement> getContents() {
		return contents;
	}

	@Override
	public void setContents(List<SalvoGameElement> contents) {
		this.contents = contents;
	}

	public String getTooltip() {
		return tooltip;
	}
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	// You might think this would be better as part of the hierarchy, and you would be right.
	// I'm doing in this way so that it is easier to manage in JavaScript.
	// Or, easier for me because right now, I'm honestly not comfortable with OO design in javascript.
	public enum Type {
		PIECE_WINDOW,
		CANVAS,
		TAB_WIDGET,
		PANEL_WIDGET,
		BOX_WIDGET,
		LIST_WIDGET,
		PIECE_SLOT,
		SALVO_GAME_PIECE
	}
	private Type type;
	private String id = "";
	private String name;
	private String width;
	private String height;
	private String tooltip;
	private List<SalvoGameElement> contents; // Could be a SalvoGamePiecePalette or a SalvoGamePiece

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contents == null) ? 0 : contents.hashCode());
		result = prime * result + ((height == null) ? 0 : height.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((tooltip == null) ? 0 : tooltip.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((width == null) ? 0 : width.hashCode());
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
		SalvoGamePiecePalette other = (SalvoGamePiecePalette) obj;
		if (contents == null) {
			if (other.contents != null)
				return false;
		} else if (!contents.equals(other.contents))
			return false;
		if (height == null) {
			if (other.height != null)
				return false;
		} else if (!height.equals(other.height))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (tooltip == null) {
			if (other.tooltip != null)
				return false;
		} else if (!tooltip.equals(other.tooltip))
			return false;
		if (type != other.type)
			return false;
		if (width == null) {
			if (other.width != null)
				return false;
		} else if (!width.equals(other.width))
			return false;
		return true;
	}
}
