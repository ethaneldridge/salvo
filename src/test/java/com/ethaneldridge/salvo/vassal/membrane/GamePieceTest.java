package com.ethaneldridge.salvo.vassal.membrane;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;

import com.ethaneldridge.salvo.data.SalvoGamePiece;
import com.ethaneldridge.salvo.data.SalvoPoint;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GamePieceTest {

	@Test
	public void test() throws IOException, URISyntaxException {
		
		SalvoGamePiece.Location locationOld = new SalvoGamePiece.Location();
		locationOld.setSalvoMapId("map0");
		locationOld.setSalvoPoint(new SalvoPoint(0, 0));
		
		SalvoGamePiece.Location locationNew = new SalvoGamePiece.Location();
		locationNew.setSalvoMapId("map1");
		locationNew.setSalvoPoint(new SalvoPoint(1, 1));
		
		SalvoGamePiece gamePiece = new SalvoGamePiece();
		gamePiece.setId("0");
		gamePiece.setName("testPiece");
		gamePiece.setImageName("http://ethaneldridge.com/image.png");
		gamePiece.setLocationOld(locationOld);
		gamePiece.setLocationNew(locationNew);

		ObjectMapper objectMapper = new ObjectMapper();
		

		String gamePieceJson = objectMapper.writeValueAsString(gamePiece);
		
		System.out.println(gamePieceJson);

		String source = "{\"a\":[1,2,{\"b\":true},3],\"c\":3}";
//		Object ob = JSON.std.anyFrom(source); // will be `Map`
//		Map<String,Object> map = JSON.std.mapFrom(source);
//		
//		GamePiece bean = JSON.std.beanFrom(GamePiece.class, gamePieceJson);
//		

		SalvoGamePiece bean = objectMapper.readValue(gamePieceJson, SalvoGamePiece.class);

		assertEquals(gamePiece, bean);
	}

}
