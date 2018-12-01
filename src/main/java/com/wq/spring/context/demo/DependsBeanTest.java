package com.wq.spring.context.demo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: wangqiang20995
 * @Date:2018/11/25
 * @Description:
 * @Resource:
 */
public class DependsBeanTest {

	private ApplicationContext applicationContext ;

	public static final String APPLICATION_CONFIG_PATH = "com/wq/spring/context/demo/ExampleApplicationContext.xml";

	@Before
	public void prepare(){
		applicationContext = new ClassPathXmlApplicationContext(APPLICATION_CONFIG_PATH);
	}

	@After
	public void finish(){

	}

	@Test
	public void circleDependBeanTest(){
		BeanA beanA = applicationContext.getBean(BeanA.class);
		System.out.println(beanA.info);
	}

	public static class Bean{
		protected String info;
	}

	public static class BeanA extends Bean{
		{
			super.info = "BeanA";
		}
	}

	public static class BeanB extends Bean{
		{
			super.info = "BeanB";
		}
	}

	public static class BeanC extends Bean{
		{
			super.info = "BeanC";
		}
	}
}
