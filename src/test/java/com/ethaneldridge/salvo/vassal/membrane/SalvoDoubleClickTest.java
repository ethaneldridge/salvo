package com.ethaneldridge.salvo.vassal.membrane;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.ethaneldridge.salvo.data.SalvoDoubleClick;
import com.ethaneldridge.salvo.data.SalvoPoint;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SalvoDoubleClickTest {

	@Test
	public void test() throws IOException {
		SalvoPoint salvoPoint = new SalvoPoint(0, 0);

		SalvoDoubleClick salvoDoubleClick = new SalvoDoubleClick();
		salvoDoubleClick.setSalvoPoint(salvoPoint);

		ObjectMapper objectMapper = new ObjectMapper();

		String salvoDoubleClickJson = objectMapper.writeValueAsString(salvoDoubleClick);
		System.out.println(salvoDoubleClickJson);

		SalvoDoubleClick bean = objectMapper.readValue(salvoDoubleClickJson, SalvoDoubleClick.class);

		assertEquals(salvoDoubleClick, bean);
	}
}
