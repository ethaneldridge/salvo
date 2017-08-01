package io.netty.example.discard;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		// Discard the received data silently
		// ((ByteBuf) msg).release();
//		ByteBuf in = (ByteBuf) msg;
//		try {
//			while (in.isReadable()) {
//				System.out.print((char) in.readByte());
//				System.out.flush();
//			}
//		} finally {
//			ReferenceCountUtil.release(msg);
//		}
		ctx.write(msg);
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
