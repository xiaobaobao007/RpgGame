package netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import manager.GameLogManager;

import java.net.SocketAddress;

public class NettyServerHandler extends SimpleChannelInboundHandler {

	private NettyServer nettyServer;

	public NettyServerHandler(NettyServer nettyServer) {
		this.nettyServer = nettyServer;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object o) {
		if (o instanceof CSPacket) {
			CSPacket csPacket = (CSPacket) o;
			GameLogManager.logger.debug("收到client信息{}", csPacket.toString());
		} else {
			GameLogManager.logger.warn("收到异常信息{}", o);
		}
		System.out.println("client：" + o.toString());
//		String clientName = ctx.channel().remoteAddress().toString();
//		Channel channel = nettyServer.getChannel(clientName);
//		if (channel != null) {
//			channel.writeAndFlush("1" + System.getProperty("line.separator"));
//		}
//		ctx.writeAndFlush("2" + System.getProperty("line.separator"));
//		ctx.channel().writeAndFlush("3" + System.getProperty("line.separator"));
//		ctx.flush();
//		counter = 0;
	}


	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		String address = ctx.channel().remoteAddress().toString();
		GameLogManager.logger.debug("{} 建立连接", address);
		nettyServer.freeConnect(address, ctx.channel());
		super.channelActive(ctx);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		SocketAddress address = ctx.channel().remoteAddress();
		GameLogManager.logger.error("游离客户端断开,{}", address);
		super.handlerRemoved(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.channel().close();
		GameLogManager.logger.error("连接异常", cause);
		super.exceptionCaught(ctx, cause);
	}

}