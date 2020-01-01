package dao;

import constant.Constant;
import db.Account;
import framework.dao.BaseGSDao;
import framework.db.BaseObject;
import framework.db.NullBaseObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaobaobao
 * @date 2019/12/14，20:54
 */
public class AccountDAO extends BaseGSDao {

	public static AccountDAO dao = new AccountDAO();

	public Account getAccount(String accountId) {
		BaseObject baseObject = getCacheObject(Constant.CACHE_TYPE_ACCOUNT, Account.PREFIX + accountId);
		if (baseObject == null) {
			baseObject = (Account) queryForObjectFromDb("Account.getAccount", accountId);
			if (baseObject != null) {
				insertCache(baseObject);
			} else {
				insertCache(new NullBaseObject(Constant.CACHE_TYPE_ACCOUNT, Account.PREFIX + accountId));
				return null;
			}
		} else if (baseObject instanceof NullBaseObject) {
			return null;
		}
		return (Account) baseObject;
	}

	public Map getAccountByTest(Map map) {
		return (Map) queryForObjectFromDb("Account.getAccountByTest", map);
	}

	public void insertAccount(Account account) {
		super.insert("Account.insertAccount", account);
	}

	public void updateAccount(Account account) {
		super.update("Account.updateAccount", account);
	}
}
