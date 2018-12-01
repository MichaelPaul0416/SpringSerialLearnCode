package com.wq.spring.modules.demo;

import com.wq.spring.modules.demo.aware.CustomBeanAware;
import com.wq.spring.modules.demo.bean.Framework;
import com.wq.spring.modules.demo.factorybean.SimpleBean;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: wangqiang20995
 * @Date:2018/11/12
 * @Description:
 * @Resource:
 */
public class DemoApplicationContextTest {

	private ApplicationContext applicationContext ;

	public static final String APPLICATION_CONFIG_PATH = "com/wq/spring/modules/demo/ExampleApplicationContext.xml";

	@Before
	public void prepare(){
		applicationContext = new ClassPathXmlApplicationContext(APPLICATION_CONFIG_PATH);
	}

	@After
	public void finish(){

	}

	@Test
	public void simpleProxyFactoryBean(){
		SimpleBean simpleBean = (SimpleBean) applicationContext.getBean("simpleProxyBean");
		simpleBean.showSelf("hello");
	}

	@Test
	public void testGetBeanWithoutNoIdName(){
		//如果bean没有声明id或者name，那么默认spring替他成的beanName是类的全限定名#number
		Framework framework = applicationContext.getBean(Framework.class);
		for(String beanDefinition : applicationContext.getBeanDefinitionNames()) {
			System.out.println(beanDefinition);
		}
		//同一个类加载器加载
		Assert.assertEquals(framework.getClass(),Framework.class);
	}

	@Test
	public void testClassLoaderLoadResource() throws URISyntaxException {
		ClassLoader classLoader = getClass().getClassLoader();
		System.out.println(classLoader.getResource("").toURI().toString());
	}

	@Test
	public void testDependOnBean(){
		HoldingBean holdingBean = (HoldingBean) applicationContext.getBean("holdingBean");
	}


	@Test
	public void beanNameAwareTest(){
		CustomBeanAware customBean = applicationContext.getBean(CustomBeanAware.class);
		System.out.println("canonicalName-->" + customBean.getClass().getCanonicalName());
		System.out.println("simpleName-->" + customBean.getClass().getSimpleName());
		System.out.println("name-->" + customBean.getClass().getName());
		System.out.println("typeName-->" + customBean.getClass().getTypeName());
		Assert.assertEquals(customBean.getRegisterName(),CustomBeanAware.class.getCanonicalName()+"#0");
	}

	@Test
	public void classLoaderAwareTest(){
		CustomBeanAware customBeanAware = applicationContext.getBean(CustomBeanAware.class);
		ClassLoader parentClassLoader = customBeanAware.parentLoader();
		System.out.println(parentClassLoader.getClass().getCanonicalName());
	}

	@Test
	public void beanFactoryTest(){
		CustomBeanAware customBeanAware = applicationContext.getBean(CustomBeanAware.class);
		BeanFactory beanFactory = customBeanAware.beanFactory();
		showArray(beanFactory.getAliases("customBeanAware"));
	}

	private static void showArray(String[] array){
		List<String> list = Arrays.asList(array);
		System.out.println(list);
	}
	public static class DemoBean1 implements DisposableBean {

		private static boolean destroy = false;

		private static boolean prepare = false;

		public DemoBean1(){
			prepare = true;
		}


		@Override
		public void destroy() throws Exception {
			this.destroy = true;
		}
	}

	public static class DemoBean2 implements DisposableBean {
		public static boolean prepare = false;
		public static boolean destroy = false;

		@Override
		public void destroy() throws Exception {
			System.out.println("destroy bean and set argument destroy to true");
			this.destroy = true;
		}

		public DemoBean2(){
			this.prepare = true;
		}
	}

	public static class DependingBean implements InitializingBean,DisposableBean {

		private  static int destroyCount  = 0;
		public static boolean destroy = false;


		public DependingBean(){}

		public DependingBean(DemoBean1 demoBean1,DemoBean2 demoBean2){}

		public void setDemoBean1(DemoBean1 demoBean1){}

		public void setDemoBean2(DemoBean2 demoBean2){}

		@Override
		public void destroy() throws Exception {
			if(!(DemoBean1.destroy && DemoBean2.destroy)){
				throw new IllegalStateException("depend beans has not already destroy --> ["+DemoBean1.class+","+DemoBean2.class+"]");
			}

			destroyCount ++;
			destroy = true;
		}

		@Override
		public void afterPropertiesSet() throws Exception {
			if(!(DemoBean1.prepare && DemoBean2.prepare)){
				throw new IllegalStateException("depend beans has not already initialization --> ["+DemoBean1.class+","+DemoBean2.class+"]");
			}
		}
	}

	public static class HoldingBean implements DisposableBean {

		private static boolean destroy = false;

		private DependingBean dependingBean;

		public void setDependingBean(DependingBean dependingBean) {
			this.dependingBean = dependingBean;
		}

		@Override
		public void destroy() throws Exception {
			if(!DependingBean.destroy){
				throw new IllegalStateException("depend bean has not destroy --> ["+DependingBean.class+"]");
			}

			destroy = true;
		}
	}
}
