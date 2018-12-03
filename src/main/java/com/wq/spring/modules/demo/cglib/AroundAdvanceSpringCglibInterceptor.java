package com.wq.spring.modules.demo.cglib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author: wangqiang20995
 * @Date:2018/12/1
 * @Description:
 * @Resource:
 */
public class AroundAdvanceSpringCglibInterceptor implements MethodInterceptor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        logger.info("cglib generate proxy object class[{}] and show it before target method call[{}]",o.getClass().getName(),method.getName());
        Object result = methodProxy.invokeSuper(o,objects);
        if(result == null){
            logger.info("cglib methodInterceptor show null result after target method call");
        }else {
            logger.info("cglib methodInterceptor show result [{}] and it's class [{}] after target method call[{}]", result, result.getClass().getName(),method.getName());
        }
        return result;
    }
}
