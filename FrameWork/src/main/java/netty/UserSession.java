package netty;

import java.nio.channels.Channel;

/**
 * 玩家session的管理
 *
 * @author xiaobaobao
 * @date 2019/12/17，18:47
 */
public class UserSession {

	private int userId;
	private String ip;
	private Channel channel;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}
}
