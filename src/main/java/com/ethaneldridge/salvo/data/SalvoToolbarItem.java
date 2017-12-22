package com.ethaneldridge.salvo.data;

import java.util.Arrays;

public class SalvoToolbarItem {

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getButtonText() {
		return buttonText;
	}
	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}
	public String getTooltip() {
		return tooltip;
	}
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	public String getButtonIcon() {
		return buttonIcon;
	}
	public void setButtonIcon(String buttonIcon) {
		this.buttonIcon = buttonIcon;
	}
	public String getButtonHotkey() {
		return buttonHotkey;
	}
	public void setButtonHotkey(String hotkeyLabel) {
		this.buttonHotkey = hotkeyLabel;
	}
	public String[] getMenuItems() {
		return menuItems;
	}
	public void setMenuItems(String[] menuItems) {
		this.menuItems = menuItems;
	}

	public SalvoToolbar getParentSalvoToolbar() {
		return parentSalvoToolbar;
	}
	public void setParentToolbar(SalvoToolbar parentSalvoToolbar) {
		this.parentSalvoToolbar = parentSalvoToolbar;
	}

	private String description;
	private String buttonText;
	private String tooltip;
	private String buttonIcon;
	private String buttonHotkey;
	private String[] menuItems;
	private SalvoToolbar parentSalvoToolbar;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buttonHotkey == null) ? 0 : buttonHotkey.hashCode());
		result = prime * result + ((buttonIcon == null) ? 0 : buttonIcon.hashCode());
		result = prime * result + ((buttonText == null) ? 0 : buttonText.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + Arrays.hashCode(menuItems);
		result = prime * result + ((parentSalvoToolbar == null) ? 0 : parentSalvoToolbar.hashCode());
		result = prime * result + ((tooltip == null) ? 0 : tooltip.hashCode());
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
		SalvoToolbarItem other = (SalvoToolbarItem) obj;
		if (buttonHotkey == null) {
			if (other.buttonHotkey != null)
				return false;
		} else if (!buttonHotkey.equals(other.buttonHotkey))
			return false;
		if (buttonIcon == null) {
			if (other.buttonIcon != null)
				return false;
		} else if (!buttonIcon.equals(other.buttonIcon))
			return false;
		if (buttonText == null) {
			if (other.buttonText != null)
				return false;
		} else if (!buttonText.equals(other.buttonText))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (!Arrays.equals(menuItems, other.menuItems))
			return false;
		if (parentSalvoToolbar == null) {
			if (other.parentSalvoToolbar != null)
				return false;
		} else if (!parentSalvoToolbar.equals(other.parentSalvoToolbar))
			return false;
		if (tooltip == null) {
			if (other.tooltip != null)
				return false;
		} else if (!tooltip.equals(other.tooltip))
			return false;
		return true;
	}
}
