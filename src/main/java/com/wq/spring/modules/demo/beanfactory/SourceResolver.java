package com.wq.spring.modules.demo.beanfactory;

/**
 * @Author: wangqiang20995
 * @Date:2018/11/28
 * @Description:
 * @Resource:
 */
public interface SourceResolver {

	String doResolve(String protocol, String path);
}
