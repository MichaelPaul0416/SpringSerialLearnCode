package com.wq.spring.context.demo.bean;

import java.util.Map;

/**
 * @Author: wangqiang20995
 * @Date:2018/11/12
 * @Description:
 * @Resource:
 */
public class Framework {

	private String name;
	private Map<String,Object> options;

	public Framework(){}

	@Override
	public String toString() {
		return "Framework{" +
				"name='" + name + '\'' +
				", options=" + options +
				'}';
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Object> getOptions() {
		return options;
	}

	public void setOptions(Map<String, Object> options) {
		this.options = options;
	}
}
