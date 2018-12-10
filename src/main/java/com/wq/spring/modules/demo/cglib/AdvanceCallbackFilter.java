package com.wq.spring.modules.demo.cglib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.CallbackFilter;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: wangqiang20995
 * @Date:2018/12/1
 * @Description:
 * @Resource:
 */
public class AdvanceCallbackFilter implements CallbackFilter {

    private static Logger logger = LoggerFactory.getLogger(AdvanceCallbackFilter.class);

    private static Map<String,Integer> filterRegistry = new ConcurrentHashMap<>(4);

    private static final Integer DEFAULT_STRATEGY = 0;//什么都不做

    @Override
    public int accept(Method method) {
        String methodName = method.getName();
        if(!filterRegistry.containsKey(methodName)){
            return DEFAULT_STRATEGY;
        }

        logger.info("filter current method [{}] to strategy [{}]",methodName,filterRegistry.get(methodName));
        return filterRegistry.get(methodName);
    }


    public static void registerMethodStrategy(String method,Integer integer){//Integer对应的是注册setCallback的增强，具体数字代表callbacks中的下标
        if(StringUtils.isEmpty(method)){
            logger.warn("empty method name");
            return;
        }

        if(filterRegistry.containsKey(method)){
            Integer old = filterRegistry.get(method);
            filterRegistry.replace(method,old,integer);
            logger.info("method [{}] already exists and will replace old [{}] with new[{}]",
                    method,old,integer);
            return;
        }

        logger.info("method [{}] registry with value [{}]",method,integer);
        filterRegistry.putIfAbsent(method,integer);
    }
}
