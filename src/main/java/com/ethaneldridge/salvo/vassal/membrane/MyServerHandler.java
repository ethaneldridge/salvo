package com.ethaneldridge.salvo.vassal.membrane;
import com.ethaneldridge.salvo.dal.SalvoChatDal;
import com.ethaneldridge.salvo.dal.SalvoGamePiecePaletteDal;
import com.ethaneldridge.salvo.dal.SalvoGameStateDal;
import com.ethaneldridge.salvo.dal.SalvoMapDal;
import com.ethaneldridge.salvo.dal.SalvoModuleMetaDataDal;
import com.ethaneldridge.salvo.dal.SalvoToolbarDal;
import com.ethaneldridge.salvo.dal.SalvoTurnTrackerDal;
import com.ethaneldridge.salvo.dal.VassalMapViewDal;
import com.ethaneldridge.salvo.dal.impl.SalvoChatDalImpl;
import com.ethaneldridge.salvo.dal.impl.SalvoGamePiecePaletteDalImpl;
import com.ethaneldridge.salvo.dal.impl.SalvoGameStateDalImpl;
import com.ethaneldridge.salvo.dal.impl.SalvoMapDalImpl;
import com.ethaneldridge.salvo.dal.impl.SalvoModuleMetaDataDalImpl;
import com.ethaneldridge.salvo.dal.impl.SalvoToolbarDalImpl;
import com.ethaneldridge.salvo.dal.impl.SalvoTurnTrackerDalImpl;
import com.ethaneldridge.salvo.dal.impl.VassalMapViewDalImpl;
import com.ethaneldridge.salvo.data.SalvoChat;
import com.ethaneldridge.salvo.data.SalvoToolbar;
import com.ethaneldridge.salvo.vassal.membrane.command.Command;
import com.ethaneldridge.salvo.vassal.membrane.command.CommandGetChats;
import com.ethaneldridge.salvo.vassal.membrane.command.CommandGetGamestate;
import com.ethaneldridge.salvo.vassal.membrane.command.CommandGetMainToolbar;
import com.ethaneldridge.salvo.vassal.membrane.command.CommandGetSalvoMapById;
import com.ethaneldridge.salvo.vassal.membrane.command.CommandPostLeftDoubleClick;
import com.ethaneldridge.salvo.vassal.membrane.command.CommandPostPiece;
import com.ethaneldridge.salvo.vassal.membrane.command.CommandPostTurnTracker;
import com.ethaneldridge.salvo.vassal.membrane.command.io.Mouse;
import com.ethaneldridge.salvo.vassal.membrane.command.io.MouseImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

public class MyServerHandler extends ChannelInboundHandlerAdapter {

	static {
		// Sequence here is important.  Need the vassalEngine before calling methods on it.
		MyServerHandler.vassalEngine = VassalEngine.theVassalEngine();
		VassalRepository vassalRepository = MyServerHandler.vassalEngine.getVassalRepository();
		SalvoGamePiecePaletteDal salvoGamePiecePaletteDal = new SalvoGamePiecePaletteDalImpl(vassalRepository);
		SalvoModuleMetaDataDal salvoModuleMetaDataDal = new SalvoModuleMetaDataDalImpl();

		MyServerHandler.salvoToolbarMenuDal = new SalvoToolbarDalImpl(vassalRepository);
		MyServerHandler.salvoMapDal = new SalvoMapDalImpl(vassalRepository);
		MyServerHandler.vassalMapViewDal = new VassalMapViewDalImpl();
		MyServerHandler.salvoTurnTrackerDal = new SalvoTurnTrackerDalImpl();
		MyServerHandler.salvoGameStateDal = new SalvoGameStateDalImpl(salvoModuleMetaDataDal, MyServerHandler.salvoTurnTrackerDal, MyServerHandler.salvoMapDal, salvoGamePiecePaletteDal);
		MyServerHandler.salvoChatDal = new SalvoChatDalImpl();
	}
	public enum Actions {
		POST_PIECE ("POST_PIECE", new CommandPostPiece(vassalEngine, salvoMapDal, vassalMapViewDal, mouse)),
		GET_GAMESTATE ("GET_GAMESTATE", new CommandGetGamestate(vassalEngine, salvoGameStateDal)),
		GET_SALVOMAP_BY_ID ("GET_SALVOMAP_BY_ID", new CommandGetSalvoMapById(vassalEngine, salvoMapDal)),
		POST_LEFT_DOUBLE_CLICK ("POST_LEFT_DOUBLE_CLICK", new CommandPostLeftDoubleClick(vassalEngine, salvoMapDal, vassalMapViewDal, mouse)),
		POST_TURNTRACKER ("POST_TURNTRACKER", new CommandPostTurnTracker(vassalEngine, salvoTurnTrackerDal)),
		GET_TOOLBARS ("GET_TOOLBARS", new CommandGetMainToolbar(vassalEngine)),
		GET_CHATS ("GET_CHATS", new CommandGetChats(vassalEngine, salvoChatDal));

		Actions (String value, Command command) {
			this.command = command;
		}

		Object apply(String request) throws Exception{
			return this.command.apply(request);
		}
		private final Command command;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		String request = (String) msg;
		ReferenceCountUtil.release(msg);

		ObjectMapper objectMapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addSerializer(SalvoToolbar.class, new SalvoToolbarSerializer());
		module.addSerializer(SalvoChat.class, new SalvoChatSerializer());
		objectMapper.registerModule(module);

		java.util.Map<String,Object> commandRequest = objectMapper.readValue(request, new TypeReference<java.util.Map<String, Object>>(){});

		java.util.Map.Entry<String, Object> entryMap = commandRequest.entrySet().iterator().next();
		String key = entryMap.getKey();
		Actions action = Actions.valueOf(key);

		Object value = entryMap.getValue();

		@SuppressWarnings("unchecked")
		java.util.Map <String, Object> mapValue = (java.util.Map <String, Object>) value;

		String jsonValue = objectMapper.writeValueAsString(mapValue);
		Object response = action.apply(jsonValue);

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

	private static VassalEngine vassalEngine;
	private static Mouse mouse = new MouseImpl();
	private static SalvoMapDal salvoMapDal;
	private static VassalMapViewDal vassalMapViewDal;
	private static SalvoTurnTrackerDal salvoTurnTrackerDal;
	private static SalvoGameStateDal salvoGameStateDal;
	private static SalvoToolbarDal salvoToolbarMenuDal;
	private static SalvoChatDal salvoChatDal;

}
