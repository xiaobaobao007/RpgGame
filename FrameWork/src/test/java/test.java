import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xiaobaobao
 * @date 2019/12/17，16:05
 */
public class test {
	private static AtomicInteger id = new AtomicInteger(0);

	@Test
	public void test1() {
		System.out.println(getReqId());
		System.out.println(getReqId());
		System.out.println(getReqId());
		System.out.println(getReqId());
		System.out.println(getReqId());
		System.out.println(getReqId());
		System.out.println(getReqId());
		System.out.println(getReqId());
		System.out.println(getReqId());
	}

	private static long getReqId() {
		return System.currentTimeMillis() << 32 | id.getAndIncrement() ;
	}

}
