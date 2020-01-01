package udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;
import vo.Msg;

import java.net.InetSocketAddress;

/**
 * @author xiaobaobao
 * @date 2020/1/1，17:11
 */
public class UdpClient {

	private static class CLientHandler extends SimpleChannelInboundHandler<Msg> {

		@Override
		protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
			System.out.println(msg.getMsg());
		}
	}

	public static void main(String[] args) {

		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group)
					.channel(NioDatagramChannel.class)
					.handler(new CLientHandler());

			Channel ch = b.bind(0).sync().channel();

			ch.writeAndFlush(new DatagramPacket(
					Unpooled.copiedBuffer("来自客户端:南无本师释迦牟尼佛", CharsetUtil.UTF_8),
					new InetSocketAddress("127.0.0.1", 2555))).sync();

			ch.closeFuture().await();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
	}
}
