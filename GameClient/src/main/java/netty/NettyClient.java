package netty;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import netty.code.JDKDecoder;
import netty.code.JDKEncoder;

import java.util.concurrent.TimeUnit;

public class NettyClient {

	private String host;
	private int port;
	private Channel channel;
	private Bootstrap b = null;

	public NettyClient(String host, int port) {
		this.host = host;
		this.port = port;
		init();
	}

	private void init() {
		b = new Bootstrap();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		b.group(workerGroup).option(ChannelOption.SO_KEEPALIVE, true)
				.channel(NioSocketChannel.class)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel socketChannel) throws Exception {
						ChannelPipeline pipeline = socketChannel.pipeline();
						//字符串编码解码
						pipeline.addLast("decoder", new JDKDecoder());
						pipeline.addLast("encoder", new JDKEncoder());
						//心跳检测
						pipeline.addLast(new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS));
						//客户端的逻辑执行
						pipeline.addLast("handler", new NettyClientHandler(NettyClient.this));
					}
				});
	}

	public void start() {
		ChannelFuture f = b.connect(host, port);
		//监听消息发送结果，如果消息写入网络Socket成功,则调用此方法
		f.addListener((ChannelFutureListener) channelFuture -> {
			if (!channelFuture.isSuccess()) {
				final EventLoop loop = channelFuture.channel().eventLoop();
				loop.schedule(() -> {
					System.out.println("disconnect");
					start();
				}, 1L, TimeUnit.SECONDS);
			} else {
				channel = channelFuture.channel();
				System.out.println("connect successful");
			}
		});
	}

	public Channel getChannel() {
		return channel;
	}

	public static void main(String[] args) {
		NettyClient nettyClient = new NettyClient("127.0.0.1", 20803);
		nettyClient.start();
	}
}