package com.wq.spring.modules.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wangqiang20995
 * @Date:2018/11/29
 * @Description:
 * @Resource:
 */
public class ServiceImpl {

	private static Logger logger = LoggerFactory.getLogger(ServiceImpl.class);
	
	public Map<String, String> display(Object object) {
		Map<String, String> map = new HashMap<>(1);
		map.put(object.getClass().getSimpleName(), object.getClass().getName());

		return map;
	}

	public static class ProtoServiceImpl implements ProtoService {

		@Override
		public void service(String query) {
			logger.debug("do proto service");
		}
	}

	public static class AdditionalServiceImpl implements AdditionalService {

		@Override
		public void additional(String add) {
			logger.debug("do additional service");
		}
	}

	public static class ServiceAdvice {

		public void doBefore() {
			logger.debug("do advice before call target method --> doBefore");
		}

		public void afterReturning(JoinPoint joinPoint, Object result) throws Throwable {
			logger.debug("do advice after target method returning[" + joinPoint.getSignature().getName() + " return " + result.getClass().getName() + "]");
		}

		public void doAfter(JoinPoint joinPoint) {
			logger.debug("do advice after call target method --> [" + joinPoint.getSignature().getName() + "]");
		}


		public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {
			logger.debug("around before");
			Object object = joinPoint.proceed();
			logger.debug("around after");
			return object;
		}

		public void before(JoinPoint joinPoint) throws Throwable {
			logger.debug("do advice before call target method[" + joinPoint.getSignature().getName() + "]");
		}
	}

	private static ApplicationContext applicationContext
			= new ClassPathXmlApplicationContext("com/wq/spring/modules/demo/ExampleApplicationContext.xml");

	public static void main(String args[]) {
		ProtoService protoService = (ProtoService) applicationContext.getBean("protoService");
		protoService.service("proto query");

		AdditionalService additionalService = (AdditionalService) applicationContext.getBean("protoService");
		additionalService.additional("pro");

		logger.debug("aop各种增强通知");
		ServiceImpl service = (ServiceImpl) applicationContext.getBean("serviceBean");
		logger.debug("display->>{}",service.display(new ArrayList<>()));
	}
}
