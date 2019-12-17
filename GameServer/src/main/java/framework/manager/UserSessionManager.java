package framework.manager;

import netty.UserSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 所有已登录玩家的会话管理
 *
 * @author xiaobaobao
 * @date 2019/12/17，18:53
 */
public class UserSessionManager {

	private Map<Integer, UserSession> userSessionMap;

	public void init() {
		userSessionMap = new ConcurrentHashMap<>();
	}

	public Map<Integer, UserSession> getUserSessionMap() {
		return userSessionMap;
	}

	public void setUserSessionMap(Map<Integer, UserSession> userSessionMap) {
		this.userSessionMap = userSessionMap;
	}
}
