package framework.manager;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志记录
 *
 * @author xiaobaobao
 * @date 2019/12/14，20:32
 */
public class GameLogManager {

	public static Logger logger = LoggerFactory.getLogger(GameLogManager.class);

	public static void main(String[] args) {
		logger.debug(" This is debug!!!");
		logger.info(" This is info!!!");
		logger.warn(" This is warn!!!");
		logger.error(" This is error!!!");
	}

}
