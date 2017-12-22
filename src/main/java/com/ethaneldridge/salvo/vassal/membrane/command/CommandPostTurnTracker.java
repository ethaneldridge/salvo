package com.ethaneldridge.salvo.vassal.membrane.command;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import com.ethaneldridge.salvo.dal.SalvoTurnTrackerDal;
import com.ethaneldridge.salvo.vassal.membrane.VassalEngine;

import VASSAL.build.GameModule;
import VASSAL.build.module.turn.VassalSalvoTurnTracker;

public class CommandPostTurnTracker implements Command {

	public CommandPostTurnTracker (VassalEngine vassalEngine, SalvoTurnTrackerDal salvoTurnTrackerDal) {
		this.salvoTurnTrackerDal = salvoTurnTrackerDal;
	}
	@Override
	public Object apply(String request) throws Exception {
		JFrame playerWindow = GameModule.getGameModule().getFrame(); // This is the PlayerWindow

		final KeyEvent keyEvent = new KeyEvent(
				playerWindow,
				KeyEvent.KEY_PRESSED	,
				System.currentTimeMillis(),
				0,
				KeyEvent.VK_F6,
				KeyEvent.CHAR_UNDEFINED);

		//Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(keyEvent);
		playerWindow.dispatchEvent(keyEvent);
		VassalSalvoTurnTracker salvoTurnTracker = salvoTurnTrackerDal.getSalvoTurnTracker();
		return salvoTurnTracker;
	};
	private final SalvoTurnTrackerDal salvoTurnTrackerDal;
}
