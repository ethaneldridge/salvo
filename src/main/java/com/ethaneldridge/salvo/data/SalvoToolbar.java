package com.ethaneldridge.salvo.data;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JToolBar;

public class SalvoToolbar {

	public SalvoToolbar () {
		this.id = SalvoToolbar.classId++;
	}

	public List<SalvoToolbarItem> getSalvoToolbarItems() {
		return salvoToolbarItems;
	}

	public void add(SalvoToolbarItem salvoToolbarMenuItem) {
		this.salvoToolbarItems.add(salvoToolbarMenuItem);
	}

	public long getId() {
		return this.id;
	}

	public SalvoMap getParentSalvoMap() {
		return parentSalvoMap;
	}

	public void setParentSalvoMap(SalvoMap parentSalvoMap) {
		this.parentSalvoMap = parentSalvoMap;
	}

	public JToolBar getAssociatedVassalJToolBar() {
		return associatedVassalJToolBar;
	}

	public void setAssociatedVassalJToolBar(JToolBar associatedVassalJToolBar) {
		this.associatedVassalJToolBar = associatedVassalJToolBar;
	}

	private List<SalvoToolbarItem> salvoToolbarItems = new ArrayList<>();
	private long id;
	private SalvoMap parentSalvoMap;
	private JToolBar associatedVassalJToolBar;
	private static long classId = 0;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((associatedVassalJToolBar == null) ? 0 : associatedVassalJToolBar.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((parentSalvoMap == null) ? 0 : parentSalvoMap.hashCode());
		result = prime * result + ((salvoToolbarItems == null) ? 0 : salvoToolbarItems.hashCode());
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
		SalvoToolbar other = (SalvoToolbar) obj;
		if (associatedVassalJToolBar == null) {
			if (other.associatedVassalJToolBar != null)
				return false;
		} else if (!associatedVassalJToolBar.equals(other.associatedVassalJToolBar))
			return false;
		if (id != other.id)
			return false;
		if (parentSalvoMap == null) {
			if (other.parentSalvoMap != null)
				return false;
		} else if (!parentSalvoMap.equals(other.parentSalvoMap))
			return false;
		if (salvoToolbarItems == null) {
			if (other.salvoToolbarItems != null)
				return false;
		} else if (!salvoToolbarItems.equals(other.salvoToolbarItems))
			return false;
		return true;
	}
}
