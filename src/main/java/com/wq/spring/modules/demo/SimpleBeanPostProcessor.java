package com.wq.spring.modules.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.StringUtils;

/**
 * @Author: wangqiang20995
 * @Date:2018/11/28
 * @Description:
 * @Resource:
 */
public class SimpleBeanPostProcessor implements BeanPostProcessor, InitializingBean {

	private Validator validator;

	private boolean afterInitialization = false;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void afterPropertiesSet() throws Exception {
		if (this.validator == null) {
			this.validator = new Validator();
		}
	}


	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {//传入的bean其实就是spring通过Constructor生成的实例对象
		if (!afterInitialization) {
			logger.debug("valid bean name before initialization -->[" + beanName + "]");
		}
		this.afterInitialization = true;
		return null;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (afterInitialization) {
			if (validator.valid(beanName)) {
				logger.debug("valid bean name ok [" + beanName + "]");
			} else {
				logger.debug("valid bean name failed [" + beanName + "]");
			}
		}
		this.afterInitialization = false;
		return null;
	}

	private class Validator {

		private static final String PREFIX_LOCAL_BEAN = "local";

		public boolean valid(String beanName) {
			if (StringUtils.isEmpty(beanName)) {
				return false;
			}

			return beanName.startsWith(PREFIX_LOCAL_BEAN);
		}
	}
}
