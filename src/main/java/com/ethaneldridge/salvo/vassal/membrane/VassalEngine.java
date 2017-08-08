package com.ethaneldridge.salvo.vassal.membrane;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ethaneldridge.salvo.dal.SalvoGameStateDal;
import com.ethaneldridge.salvo.data.SalvoGameState;

import VASSAL.build.GameModule;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class VassalEngine {

	private VassalEngine() {
		// Only access is via singleton
	}

	public void setSalvoGameStateDal(SalvoGameStateDal salvoGameStateDal) {
		this.salvoGameStateDal = salvoGameStateDal;
	}

	public SalvoGameState getSalvoGameState() {
		return salvoGameStateDal.getSalvoGameState();
	}

	public static VassalEngine theVassalEngine() {
		return theVassalEngine;
	}

	void launch() {
		// Don't launch the TCP/IP server until the UI is ready
		// FIXME: Defend against NPE
		GameModule.getGameModule().getFrame().addWindowListener(new MyWindowAdapter());
	}

	void connect(int port) throws Exception {
		// wait for the notice that the game is ready...
		// "Always invoke wait inside a loop that tests for the condition being waited for. Don't assume that the interrupt was for the particular condition you were waiting for, or that the condition is still true."
		synchronized (lock) {
			while (!isVassalReady) {
				try {
					lock.wait();
				} catch (InterruptedException e1) {
					// FIXME proper handling of exception
					e1.printStackTrace();
					Thread.currentThread().interrupt();
				}
			}
		}
		try {
			startConnectionThread(port);
		} catch (Exception e) {
			// FIXME proper handling of exception
			e.printStackTrace();
		}
	}

	public synchronized boolean isStateReady() {
		return expectedClicks == receivedClicks;
	}

	public synchronized VassalEngine setExpectedClicks(int expectedClicks) {
		this.expectedClicks = expectedClicks;
		return this;
	}

	public synchronized VassalEngine setReceivedClicks(int receivedClicks) {
		this.receivedClicks = receivedClicks;
		return this;
	}

	public synchronized VassalEngine resetClicks() {
		this.expectedClicks = 0;
		this.receivedClicks = 0;
		return this;
	}

	private void startConnectionThread(int port) {
		EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
		EventLoopGroup workerGroup = new NioEventLoopGroup(2);// FIXME
		try {
			ServerBootstrap b = new ServerBootstrap(); // (2)
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class) // (3)
					.childHandler(new MySocketInitializer())
					.option(ChannelOption.SO_BACKLOG, 128) // (5)
					.childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

			// Bind and start to accept incoming connections.
			ChannelFuture f;
			f = b.bind(port).sync(); // (7)

			// Wait until the server socket is closed.
			// In this example, this does not happen, but you can do that to
			// gracefully
			// shut down your server.
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace(); // FIXME: Better exception handling
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

	private class MyWindowAdapter extends WindowAdapter {
		@Override
		public void windowActivated(WindowEvent e) {
			synchronized(lock) {
				isVassalReady = true;
				lock.notifyAll();
			}
		}
	}

	private SalvoGameStateDal salvoGameStateDal;
	private static VassalEngine theVassalEngine = new VassalEngine();
	private final Object lock = new Object();
	private boolean isVassalReady = false;
	private int expectedClicks = 0;
	private int receivedClicks = 0;
}
