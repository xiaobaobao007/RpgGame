import boot.GSBoot;
import framework.manager.GameLogManager;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * 主方法
 *
 * @author xiaobaobao
 * @date 2019/12/14，18:01
 */
public class GameServer {

	public static GSBoot boot;

	public static void main(String[] args) {

		GameServer.boot = new GSBoot();
		try {
//			Properties property = new Properties();
//			InputStream is = Thread.currentThread().getClass().getResourceAsStream("/config/server.conf");
//			property.load(is);
//			System.out.println(property.getProperty("server.port"));
			boot.init();
		} catch (Exception e) {
			GameLogManager.logger.error("服务器启动异常", e);
		}
	}

}
