package com.wq.spring.modules.demo.beanfactory;

import com.wq.spring.modules.demo.bean.RemoteServiceImporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * @Author: wangqiang20995
 * @Date:2018/11/28
 * @Description:
 * @Resource:
 */
public class SimpleCustomPropertiesContainer {

	private static final DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();

	private static final PropertiesBeanDefinitionReader propertiesBeanDefinitionReader = new PropertiesBeanDefinitionReader(defaultListableBeanFactory);

	private static Logger logger = LoggerFactory.getLogger(SimpleCustomPropertiesContainer.class);

	public static void main(String args[]) throws IOException {
		Resource resource = new SimpleCustomLocalBeanRegister().resource("com/wq/spring/modules/demo/SimpleApplicationContext.properties");
		propertiesBeanDefinitionReader.loadBeanDefinitions(resource);

		logger.debug("获取构造器注入的bean");
		RemoteServiceImporter importer = (RemoteServiceImporter) defaultListableBeanFactory.getBean("serviceImporter");
		logger.info("importer[{}]",importer);


		logger.info("获取属性注入的bean");
		RemoteServiceImporter setterImporter = (RemoteServiceImporter) defaultListableBeanFactory.getBean("setterServiceImporter");
		logger.info("setter importer[{}]",setterImporter);
	}
}
