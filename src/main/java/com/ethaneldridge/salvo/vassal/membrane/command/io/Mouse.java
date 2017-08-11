package com.ethaneldridge.salvo.vassal.membrane.command.io;

import javax.swing.JComponent;

public interface Mouse {

	void doubleClick(JComponent mapView, int x, int y);

	void pressMouse(JComponent mapView, int xOld, int yOld);

	void releaseMouse(JComponent mapView, int x, int y, int clicks);

}