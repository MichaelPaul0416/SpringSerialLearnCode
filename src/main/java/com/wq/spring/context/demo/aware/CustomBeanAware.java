package com.wq.spring.context.demo.aware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;

/**
 * @Author: wangqiang20995
 * @Date:2018/11/25
 * @Description:
 * @Resource:
 */
public class CustomBeanAware implements BeanNameAware,BeanClassLoaderAware,BeanFactoryAware {

	private String registerName;

	private ClassLoader classLoader;

	private BeanFactory beanFactory;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void setBeanName(String name) {
		this.registerName = name;
		logger.info("setBeanName-->[" + name + "]");
	}

	public String getRegisterName() {
		return registerName;
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public ClassLoader parentLoader(){
		return classLoader.getParent();
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	public BeanFactory beanFactory(){
		return this.beanFactory;
	}
}
