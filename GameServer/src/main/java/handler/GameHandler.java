package handler;

import aop.GameFacade;
import aop.GameFacadeProxy;
import dao.AccountDAO;
import db.Account;

import java.util.Calendar;

/**
 * @author xiaobaobao
 * @date 2019/12/15，0:16
 */
public class GameHandler implements GameFacade {

	public static GameFacade facade;
	public static GameFacade me;

	public static void init() {
		GameHandler gameHandler = new GameHandler();
		GameFacadeProxy proxy = new GameFacadeProxy();
		facade = (GameFacade) proxy.bind(gameHandler);
		me = new GameHandler();
	}

	@Override
	public void addAccount(String user) {
		Account account = new Account();
		account.setAccount(user);
		account.setCreateTime(Calendar.getInstance().getTime());
		AccountDAO.dao.insertAccount(account);
	}
}
