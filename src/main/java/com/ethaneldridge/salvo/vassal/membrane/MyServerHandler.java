package com.ethaneldridge.salvo.vassal.membrane;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JFrame;

import com.ethaneldridge.salvo.dal.SalvoGameStateDal;
import com.ethaneldridge.salvo.dal.SalvoMapDal;
import com.ethaneldridge.salvo.dal.SalvoTurnTrackerDal;
import com.ethaneldridge.salvo.dal.VassalMapViewDal;
import com.ethaneldridge.salvo.data.SalvoDoubleClick;
import com.ethaneldridge.salvo.data.SalvoGamePiece;
import com.ethaneldridge.salvo.data.SalvoGameState;
import com.ethaneldridge.salvo.data.SalvoMap;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import VASSAL.build.GameModule;
import VASSAL.build.module.turn.SalvoTurnTracker;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

public class MyServerHandler extends ChannelInboundHandlerAdapter {

	public MyServerHandler(SalvoMapDal salvoMapDal, VassalMapViewDal vassalMapViewDal, SalvoTurnTrackerDal salvoTurnTrackerDal, SalvoGameStateDal salvoGameStateDal) {
		this.salvoMapDal = salvoMapDal;
		this.vassalMapViewDal = vassalMapViewDal;
		this.salvoTurnTrackerDal = salvoTurnTrackerDal;
		this.salvoGameStateDal = salvoGameStateDal;
		
		commandMap.put("POST_PIECE", postPiece);
		commandMap.put("GET_GAMESTATE", getGamestate);
		commandMap.put("POST_MAP_DOUBLECLICK", postMapDoubleClick);
		commandMap.put("POST_TURNTRACKER", postTurnTracker);
	}
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		String request = (String) msg;
		ReferenceCountUtil.release(msg);

		ObjectMapper objectMapper = new ObjectMapper();
		java.util.Map<String,Object> commandRequest = objectMapper.readValue(request, new TypeReference<java.util.Map<String, Object>>(){});

		java.util.Map.Entry<String, Object> entryMap = commandRequest.entrySet().iterator().next();
		String key = entryMap.getKey();

		Object value = entryMap.getValue();

		@SuppressWarnings("unchecked")
		java.util.Map <String, Object> mapValue = (java.util.Map <String, Object>) value;

		String jsonValue = objectMapper.writeValueAsString(mapValue);
		Object response = commandMap.get(key).apply(jsonValue);

		// Wait for the game to update
		synchronized (vassalEngine) {
			while (!vassalEngine.isStateReady()) {
				try {
					vassalEngine.wait();
				} catch (InterruptedException e1) {
					// FIXME proper handling of exception
					e1.printStackTrace();
					Thread.currentThread().interrupt();
				}
			}
			// Get and send current game state;
			//vassalEngine.resetClicks();
			String mainMapMediaTypeJson = objectMapper.writeValueAsString(response);
			ByteBuf buffer = Unpooled.copiedBuffer(mainMapMediaTypeJson, CharsetUtil.UTF_8);
			ctx.writeAndFlush(buffer).addListener(ChannelFutureListener.CLOSE);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	private SalvoMapDal salvoMapDal;
	private VassalMapViewDal vassalMapViewDal;
	private SalvoTurnTrackerDal salvoTurnTrackerDal;
	private SalvoGameStateDal salvoGameStateDal;
	private static VassalEngine vassalEngine = VassalEngine.theVassalEngine();

	private interface Command<T> {
		public Object apply(T t) throws Exception;
	}

	private Command<String> postPiece = request -> {

		vassalEngine.setExpectedClicks(1);
		ObjectMapper objectMapper = new ObjectMapper();

		SalvoGamePiece gamePiece = objectMapper.readValue(request, SalvoGamePiece.class);
		JComponent mapViewOld = vassalMapViewDal.getViewByMapId(gamePiece.getLocationOld().getSalvoMapId());

		double xOldScreen = gamePiece.getLocationOld().getSalvoPoint().getX();// / map.getZoom();
		double yOldScreen = gamePiece.getLocationOld().getSalvoPoint().getY();// / map.getZoom();
		int xOld = (int)xOldScreen; // 1010;
		int yOld = (int)yOldScreen; // 120;

		pressMouse(mapViewOld, xOld, yOld);

		JComponent mapViewNew = vassalMapViewDal.getViewByMapId(gamePiece.getLocationNew().getSalvoMapId());

		double xNewScreen = gamePiece.getLocationNew().getSalvoPoint().getX();// / map.getZoom();
		double yNewScreen = gamePiece.getLocationNew().getSalvoPoint().getY();// / map.getZoom();
		int xNew = (int)xNewScreen;
		int yNew = (int)yNewScreen;

		releaseMouse (mapViewNew, xNew, yNew, 1);
		
		SalvoMap salvoMap = salvoMapDal.getMapById(gamePiece.getLocationNew().getSalvoMapId());
		return salvoMap;
	};

	private Command<String> getGamestate = request -> {
		// No-op...just need to tell the thread to continue
		synchronized (vassalEngine) {
			vassalEngine.resetClicks();
			vassalEngine.notify();
		}
		SalvoGameState salvoGameState = salvoGameStateDal.getSalvoGameState();
		return salvoGameState;
	};
	
	private Command<String> postMapDoubleClick = request -> {

		vassalEngine.setExpectedClicks(2);
		ObjectMapper objectMapper = new ObjectMapper();

		SalvoDoubleClick salvoDoubleClick = objectMapper.readValue(request, SalvoDoubleClick.class);

		JComponent mapView = vassalMapViewDal.getViewByMapId(salvoDoubleClick.getSalvoMapId());

		double xNewScreen = salvoDoubleClick.getSalvoPoint().getX();// / map.getZoom();
		double yNewScreen = salvoDoubleClick.getSalvoPoint().getY();// / map.getZoom();
		int xNew = (int)xNewScreen;
		int yNew = (int)yNewScreen;

		doubleClick(mapView, xNew, yNew);
		
		SalvoMap salvoMap = salvoMapDal.getMapById(salvoDoubleClick.getSalvoMapId());
    return salvoMap;
	};

	private Command<String> postTurnTracker = request -> {

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
		SalvoTurnTracker salvoTurnTracker = salvoTurnTrackerDal.getSalvoTurnTracker();
		return salvoTurnTracker;
	};

	private java.util.Map<String, Command<String> > commandMap = new HashMap<>();

	private void doubleClick(JComponent mapView, int x, int y) {

		// clickMouse is irrelevant.  Doesn't hurt, but isn't needed.
		pressMouse(mapView, x, y);
		releaseMouse(mapView, x, y, 1);

		pressMouse(mapView, x, y);
		releaseMouse(mapView, x, y, 2);
	}

	private void pressMouse(JComponent mapView, int xOld, int yOld) {
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

	private void releaseMouse(JComponent mapView, int x, int y, int clicks) {
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

	private void clickMouse(JComponent mapView, int x, int y, int clicks) {
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
