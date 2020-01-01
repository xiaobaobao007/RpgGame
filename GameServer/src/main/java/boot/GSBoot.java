package boot;

import dao.AccountDAO;
import framework.boot.BaseBoot;
import framework.manager.CacheManager;
import framework.manager.MybatisManager;
import handler.GameHandler;
import manager.GameLogManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaobaobao
 * @date 2019/12/14，18:00
 */
public class GSBoot extends BaseBoot {

	private CacheManager cacheManager;

	@Override
	public void init() throws Exception {
		GameLogManager.logger.info("服务器正在启动中");
		cacheManager = new CacheManager();
		MybatisManager.init();
		GameHandler.init();
//		Account a = new Account();
//		a.setAccount("1");
//		AccountDAO.dao.insertAccount(a);
//		MybatisManager.getSession().commit();
		Map<String, Integer> map = new HashMap<>();
		map.put("userId", 0);
		Map map1 = AccountDAO.dao.getAccountByTest(map);

		map.clear();
		map.put("userId", 1);
		Map map2 = AccountDAO.dao.getAccountByTest(map);
//		String ip = PropKit.use("conf/server.conf").get("center.ip");
	}
}
