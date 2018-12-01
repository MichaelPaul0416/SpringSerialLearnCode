package com.wq.spring.context.demo.tags;

/**
 * @Author: wangqiang20995
 * @Date:2018/11/30
 * @Description:
 * @Resource:
 */
public class ExpandService {

	private String keyboard;
	private String monitor;
	private String mouse;

	@Override
	public String toString() {
		return "ExpandService{" +
				"keyboard='" + keyboard + '\'' +
				", monitor='" + monitor + '\'' +
				", mouse='" + mouse + '\'' +
				'}';
	}

	public String getKeyboard() {
		return keyboard;
	}

	public void setKeyboard(String keyboard) {
		this.keyboard = keyboard;
	}

	public String getMonitor() {
		return monitor;
	}

	public void setMonitor(String monitor) {
		this.monitor = monitor;
	}

	public String getMouse() {
		return mouse;
	}

	public void setMouse(String mouse) {
		this.mouse = mouse;
	}
}
