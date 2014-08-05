package com.server.game;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

import com.game.netty.Server;
import com.game.netty.coder.Decoder;
import com.game.netty.coder.Encoder;
import com.manager.ManagerPool;

public class GameServer extends Server {
	private static GameServer instance = new GameServer();
	private GameServer() {}
	public static GameServer getInstance() {
		return instance;
	}
	
	@Override
	protected boolean init() {
		return true;
	}

	@Override
	public void stop() {
		ManagerPool.stop();
	}

	@Override
	public ChannelInitializer<SocketChannel> getChannelInitializer() {
		return new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new Encoder());
				ch.pipeline().addLast(new Decoder());
				ch.pipeline().addLast(new GameHandler());
			}
		};
	}
}
