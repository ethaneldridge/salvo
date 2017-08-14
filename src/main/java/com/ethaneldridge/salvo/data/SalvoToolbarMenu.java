package com.ethaneldridge.salvo.data;

import java.util.List;

public class SalvoToolbarMenu {

	public List<SalvoToolbarMenuItem> getToolbarMenuItems() {
		return toolbarMenuItems;
	}

	public void setToolbarMenuItems(List<SalvoToolbarMenuItem> toolbarMenuItems) {
		this.toolbarMenuItems = toolbarMenuItems;
	}
	List<SalvoToolbarMenuItem> toolbarMenuItems;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((toolbarMenuItems == null) ? 0 : toolbarMenuItems.hashCode());
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
		SalvoToolbarMenu other = (SalvoToolbarMenu) obj;
		if (toolbarMenuItems == null) {
			if (other.toolbarMenuItems != null)
				return false;
		} else if (!toolbarMenuItems.equals(other.toolbarMenuItems))
			return false;
		return true;
	}

}
