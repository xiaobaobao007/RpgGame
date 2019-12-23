package boot;

import dao.AccountDAO;
import db.Account;
import framework.boot.BaseBoot;
import framework.manager.CacheManager;
import framework.manager.GameLogManager;
import framework.manager.MybatisManager;
import handler.GameHandler;

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
		Account a = new Account();
		a.setAccount("1");
		AccountDAO.dao.insertAccount(a);
		MybatisManager.getSession().commit();
//		String ip = PropKit.use("conf/server.conf").get("center.ip");
	}
}
