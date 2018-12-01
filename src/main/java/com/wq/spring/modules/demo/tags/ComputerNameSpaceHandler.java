package com.wq.spring.modules.demo.tags;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: wangqiang20995
 * @Date:2018/11/29
 * @Description:
 * @Resource:
 */
public class ComputerNameSpaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		registerBeanDefinitionParser("computer",new ComputerTagParser());
	}

	public static void main(String args[]){
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("com/wq/spring/modules/demo/expand.xml");
		Computer computer = (Computer) applicationContext.getBean("computer");
		System.out.println(computer.getClass().getName());
	}
}
