//package netty;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.atomic.AtomicInteger;
//
//import com.alibaba.fastjson.JSONObject;
//
//public class CSManager {
//
//	public static final CSManager instance = new CSManager();
//
//	private CSManager() {
//	}
//
//	private static int SYNC_MSG_TIMEOUT = 150; // 同步消息响应超时时间
//
//	Map<Long, SyncPacketFuture<CSPacket>> futureMap;
//	Map<Integer, UserSession> sessionMap;
//
//	public void init(String handlerPacketName) {
//		// center 地址 、端口
//		String ip = PropKit.use("conf/server.conf").get("center.ip");
//		if (StringUtil.isNull(ip)) {
//			return;
//		}
//
//		int port = PropKit.use("conf/server.conf").getInt("center.port");
//		futureMap = new ConcurrentHashMap<>();
//		CSHandlerManager.init(handlerPacketName);
//		BaseNioClientHandler baseNioClientHandler = new CSClientHandler();
//		nioClient = new BaseNioClient(ip, port, baseNioClientHandler);
//		nioClient.setClientName("CSManager");
//		nioClient.init();
//		nioClient.connect(this::connectSuccessCallback);
//
//		// 定时移除超时的同步消息
//		ScheduledThreadUtil.addTask(this::checkTimeoutReq, 1, 1);
//	}
//
//
//	public boolean isConnected() {
//		return nioClient != null && nioClient.isConnected();
//	}
//
//	public CSPacket syncMsg(String cmd, BaseMsg msg) {
//		if (nioClient == null || !nioClient.isConnected()) {
//			return null;
//		}
//		CSPacket req = new CSPacket(cmd, msg);
//		req.setRequestId(getReqId());
//		req.setType((byte) 1);
//		ByteBuf buffer = Unpooled.buffer();
//		buffer.writeBytes(KryoPoolSerializerUtil.serialize(req));
//		nioClient.getClientChannel().writeAndFlush(buffer);
//
//		if (GameLogFactory.getGameLog().isTraceEnabled()) {
//			GameLogFactory.getGameLog().trace("向Center发送消息:{}", JSONObject.toJSONString(req));
//		}
//
//		SyncPacketFuture<CSPacket> syncPacketFuture = new SyncPacketFuture<>();
//		futureMap.put(req.getRequestId(), syncPacketFuture);
//
//		try {
//			CSPacket resp = syncPacketFuture.get(SYNC_MSG_TIMEOUT, TimeUnit.MILLISECONDS);
//			futureMap.remove(req.getRequestId());
//			if (GameLogFactory.getGameLog().isTraceEnabled()) {
//				GameLogFactory.getGameLog().trace("Center返回消息:{}", JSONObject.toJSONString(resp));
//			}
//			return resp;
//		} catch (Exception e) {
//			GameLogFactory.getGameLog().error("同步请求center异常：", e);
//		}
//		return null;
//	}
//
//	public void asyncMsg(String cmd, BaseMsg msg) {
//		if (nioClient == null || !nioClient.isConnected()) {
//			return;
//		}
//		CSPacket req = new CSPacket(cmd, msg);
//		ByteBuf buffer = Unpooled.buffer();
//		buffer.writeBytes(KryoPoolSerializerUtil.serialize(req));
//		nioClient.getClientChannel().writeAndFlush(buffer);
//
//		if (GameLogFactory.getGameLog().isTraceEnabled()) {
//			GameLogFactory.getGameLog().trace("向Center发送消息:{}", JSONObject.toJSONString(req));
//		}
//	}
//
//	private void checkTimeoutReq() {
//		long cum = System.currentTimeMillis();
//		futureMap.values().removeIf(future -> cum > future.getBeginTime() + SYNC_MSG_TIMEOUT);
//	}
//
//	private static AtomicInteger id = new AtomicInteger(0);
//
//	private static long getReqId() {
//		return (System.currentTimeMillis() & 0xFFFFFFFFL) << 32 | id.addAndGet(1) & Integer.MAX_VALUE;
//	}
//}
