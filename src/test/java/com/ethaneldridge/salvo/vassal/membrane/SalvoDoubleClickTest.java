package com.ethaneldridge.salvo.vassal.membrane;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.ethaneldridge.salvo.data.SalvoPoint;
import com.ethaneldridge.salvo.vassal.membrane.command.SalvoLeftDoubleClick;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SalvoDoubleClickTest {

	@Test
	public void test() throws IOException {
		SalvoPoint salvoPoint = new SalvoPoint(0, 0);

		SalvoLeftDoubleClick salvoDoubleClick = new SalvoLeftDoubleClick();
		salvoDoubleClick.setSalvoPoint(salvoPoint);

		ObjectMapper objectMapper = new ObjectMapper();

		String salvoDoubleClickJson = objectMapper.writeValueAsString(salvoDoubleClick);
		System.out.println(salvoDoubleClickJson);

		SalvoLeftDoubleClick bean = objectMapper.readValue(salvoDoubleClickJson, SalvoLeftDoubleClick.class);

		assertEquals(salvoDoubleClick, bean);
	}
}
