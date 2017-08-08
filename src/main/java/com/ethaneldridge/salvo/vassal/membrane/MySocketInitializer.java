package com.ethaneldridge.salvo.vassal.membrane;

import com.ethaneldridge.salvo.dal.SalvoGamePieceDal;
import com.ethaneldridge.salvo.dal.SalvoGamePiecePaletteDal;
import com.ethaneldridge.salvo.dal.SalvoGameStateDal;
import com.ethaneldridge.salvo.dal.SalvoMapDal;
import com.ethaneldridge.salvo.dal.SalvoTurnTrackerDal;
import com.ethaneldridge.salvo.dal.VassalMapViewDal;
import com.ethaneldridge.salvo.dal.impl.SalvoGamePieceDalImpl;
import com.ethaneldridge.salvo.dal.impl.SalvoGamePiecePaletteDalImpl;
import com.ethaneldridge.salvo.dal.impl.SalvoGameStateDalImpl;
import com.ethaneldridge.salvo.dal.impl.SalvoMapDalImpl;
import com.ethaneldridge.salvo.dal.impl.SalvoTurnTrackerDalImpl;
import com.ethaneldridge.salvo.dal.impl.VassalMapViewDalImpl;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.json.JsonObjectDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;

public class MySocketInitializer extends ChannelInitializer<SocketChannel> {

	
	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		
		pipeline.addLast(LineBasedFrameDecoder.class.getName(),
				new LineBasedFrameDecoder(256));
		
		pipeline.addLast(StringDecoder.class.getName(),
				new StringDecoder(CharsetUtil.UTF_8));
		
		pipeline.addLast(JsonObjectDecoder.class.getName(),
				new JsonObjectDecoder());

		SalvoGamePieceDal salvoGamePieceDal = new SalvoGamePieceDalImpl();
    SalvoTurnTrackerDal salvoTurnTrackerDal = new SalvoTurnTrackerDalImpl();
    SalvoMapDal salvoMapDal = new SalvoMapDalImpl(salvoGamePieceDal);
    SalvoGamePiecePaletteDal salvoGamePiecePaletteDal = new SalvoGamePiecePaletteDalImpl(salvoGamePieceDal);
    SalvoGameStateDal salvoGameStateDal = new SalvoGameStateDalImpl(salvoTurnTrackerDal, salvoMapDal, salvoGamePiecePaletteDal);
    
    VassalMapViewDal vassalMapViewDal = new VassalMapViewDalImpl();
		pipeline.addLast(new MyServerHandler(salvoMapDal, vassalMapViewDal, salvoTurnTrackerDal, salvoGameStateDal));
	}
}
