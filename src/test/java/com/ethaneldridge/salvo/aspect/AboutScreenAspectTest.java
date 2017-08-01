package com.ethaneldridge.salvo.aspect;

import org.junit.Test;

import VASSAL.build.module.documentation.AboutScreen;

public class AboutScreenAspectTest {

	@Test(expected=NullPointerException.class)
	public void testSetAttributeAround() {
		AboutScreen aboutScreen = new AboutScreen();
		aboutScreen.setAttribute("fileName", "test");
	}

}
