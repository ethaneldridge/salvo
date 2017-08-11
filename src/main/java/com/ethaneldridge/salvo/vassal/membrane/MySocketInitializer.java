package com.ethaneldridge.salvo.vassal.membrane;

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

		pipeline.addLast(new MyServerHandler());
	}
}
