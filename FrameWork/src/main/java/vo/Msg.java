package vo;

import java.io.Serializable;

/**
 * @author xiaobaobao
 * @date 2019/12/16，21:39
 */
public class Msg implements Serializable {
	private static final long serialVersionUID = 1L;

	private String msg;
	private long time;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
}
