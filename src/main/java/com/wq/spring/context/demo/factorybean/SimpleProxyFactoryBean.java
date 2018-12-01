package com.wq.spring.context.demo.factorybean;

import com.wq.spring.context.demo.SimpleBean;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: wangqiang20995
 * @Date:2018/11/27
 * @Description:
 * @Resource:
 */
public class SimpleProxyFactoryBean implements FactoryBean<SimpleBean>,InvocationHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HHmmss");
		Object singleParam = args[0];

		if(singleParam instanceof String){
			System.out.println("invoke method["+method.getName()+"] and param value["+singleParam+"|"+simpleDateFormat.format(new Date())+"]");
		}else {
			System.out.println("wrong argument value");
		}
		return null;
	}

	@Override
	public SimpleBean getObject() throws Exception {
		return proxyBean();
	}

	@Override
	public Class<?> getObjectType() {
		return SimpleBean.class;
	}

	private SimpleBean proxyBean(){
		SimpleBean proxy = (SimpleBean) Proxy.newProxyInstance(this.getClass().getClassLoader(),new Class[]{SimpleBean.class},this);
		System.out.println("proxy bean -->["+proxy.getClass().getName()+"]");

		return proxy;
	}
}
