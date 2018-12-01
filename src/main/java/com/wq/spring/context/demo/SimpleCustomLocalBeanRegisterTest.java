package com.wq.spring.context.demo;

import com.wq.spring.context.demo.bean.RemoteServiceImporter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.URL;

/**
 * @Author: wangqiang20995
 * @Date:2018/11/28
 * @Description:
 * @Resource:
 */
public class SimpleCustomLocalBeanRegisterTest {

	public static void main(String args[]){

		URL url =  SimpleCustomLocalBeanRegisterTest.class.getResource("/com/wq/spring/context/demo/CustomBeanFactory.properties");
		System.out.println(url.getPath());

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("com/wq/spring/context/demo/ExampleApplicationContext.xml");

		RemoteServiceImporter serviceImporter = (RemoteServiceImporter) applicationContext.getBean("AliPay");
		System.out.println(serviceImporter);
	}
}
