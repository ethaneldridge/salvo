package com.ethaneldridge.salvo.vassal.membrane.command.io;

import java.awt.event.MouseEvent;

import javax.swing.JComponent;

public class MouseImpl implements Mouse {

	/* (non-Javadoc)
	 * @see com.ethaneldridge.salvo.vassal.membrane.command.io.Mouse#doubleClick(javax.swing.JComponent, int, int)
	 */
	@Override
	public void doubleClick(JComponent mapView, int x, int y) {

		// clickMouse is irrelevant.  Doesn't hurt, but isn't needed.
		pressMouse(mapView, x, y);
		releaseMouse(mapView, x, y, 1);

		pressMouse(mapView, x, y);
		releaseMouse(mapView, x, y, 2);
	}

	/* (non-Javadoc)
	 * @see com.ethaneldridge.salvo.vassal.membrane.command.io.Mouse#pressMouse(javax.swing.JComponent, int, int)
	 */
	@Override
	public void pressMouse(JComponent mapView, int xOld, int yOld) {
		final MouseEvent mousePressedEvent = new MouseEvent(
				mapView, //playerWindow
				MouseEvent.MOUSE_PRESSED,
				System.currentTimeMillis(),
				0,
				xOld,
				yOld,
				1,
				false,
				MouseEvent.BUTTON1);
		mapView.dispatchEvent(mousePressedEvent);
	}

	/* (non-Javadoc)
	 * @see com.ethaneldridge.salvo.vassal.membrane.command.io.Mouse#releaseMouse(javax.swing.JComponent, int, int, int)
	 */
	@Override
	public void releaseMouse(JComponent mapView, int x, int y, int clicks) {
		final MouseEvent mouseReleasedEvent = new MouseEvent(
				mapView, // playerWindow
				MouseEvent.MOUSE_RELEASED,
				System.currentTimeMillis(),
				0,
				x,
				y,
				clicks,
				false,
				MouseEvent.BUTTON1);
		mapView.dispatchEvent(mouseReleasedEvent);
	}

	public void clickMouse(JComponent mapView, int x, int y, int clicks) {
		final MouseEvent mouseClickedOnce = new MouseEvent(
				mapView, // playerWindow
				MouseEvent.MOUSE_CLICKED,
				System.currentTimeMillis(),
				0,
				x,
				y,
				clicks,
				false,
				MouseEvent.BUTTON1);
		mapView.dispatchEvent(mouseClickedOnce);
	}
}
