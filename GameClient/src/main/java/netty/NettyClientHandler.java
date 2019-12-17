package netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import vo.Msg;

import java.util.concurrent.TimeUnit;

public class NettyClientHandler extends SimpleChannelInboundHandler {

	private NettyClient nettyClient;
	private int attempts = 0;

	public NettyClientHandler(NettyClient nettyClient) {
		this.nettyClient = nettyClient;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) {
		if (o instanceof Msg) {
			Msg msg = (Msg) o;
			System.out.println("service：" + msg.getMsg() + ":=" + (System.currentTimeMillis() - msg.getTime()));
		} else {
			System.out.println(o.toString());
		}
	}

	/**
	 * 新客户端发生连接
	 *
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("output connected!");
		attempts = 0;
	}

	/**
	 * 失去连接
	 *
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("offline");
		//使用过程中断线重连
		final EventLoop eventLoop = ctx.channel().eventLoop();
		if (attempts < 12) {
			attempts++;
		}
		int timeout = 2 << attempts;
		eventLoop.schedule(() -> nettyClient.start(), timeout, TimeUnit.SECONDS);
		ctx.fireChannelInactive();
	}

	/**
	 * 自定义实现心跳
	 * @param ctx
	 * @param evt
	 * @throws Exception
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//		if (evt instanceof IdleStateEvent) {
//			IdleStateEvent event = (IdleStateEvent) evt;
//			if (event.state().equals(IdleState.READER_IDLE)) {
//				System.out.println("READER_IDLE");
//			} else if (event.state().equals(IdleState.WRITER_IDLE)) {
//				System.out.println("WRITER_IDLE");
//				//发送心跳，保持长连接
//				ctx.channel().writeAndFlush("Heart");  //发送心跳成功
//			} else if (event.state().equals(IdleState.ALL_IDLE)) {
//				System.out.println("ALL_IDLE");
//			}
//		}
//		super.userEventTriggered(ctx, evt);
	}
}