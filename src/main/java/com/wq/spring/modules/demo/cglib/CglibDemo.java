package com.wq.spring.modules.demo.cglib;

import org.springframework.cglib.proxy.*;

/**
 * @Author: wangqiang20995
 * @Date:2018/12/1
 * @Description:
 * @Resource:
 */
public class CglibDemo {

    private static MethodInterceptor aroundAdvanceInterceptor = new AroundAdvanceSpringCglibInterceptor();

    private static Enhancer enhancer = new Enhancer();

    public static void main(String args[]){

//        commonMethodInterceptor();

        Callback[] callback = new Callback[3];
        callback[0] = NoOp.INSTANCE;
        callback[1] = aroundAdvanceInterceptor;
        callback[2] = new CglibDemo.FixedValueInterceptor();

        AdvanceCallbackFilter.registerMethodStrategy("toString",0);
        AdvanceCallbackFilter.registerMethodStrategy("showTotal",1);
        AdvanceCallbackFilter.registerMethodStrategy("describe",1);

        enhancer.setSuperclass(CommonService.class);
        enhancer.setCallbacks(callback);
        enhancer.setCallbackFilter(new AdvanceCallbackFilter());

        CommonService commonService = (CommonService) enhancer.create();
        commonService.describe("Well");
        System.out.println("------------------------------------------------------------");
        commonService.showTotal();
    }

    private static void commonMethodInterceptor() {
        enhancer.setSuperclass(CommonService.class);
        enhancer.setCallback(aroundAdvanceInterceptor);

        CommonService commonService = (CommonService) enhancer.create();
        commonService.describe("Hello");
        System.out.println("------------------------------------------------------------");
        commonService.showTotal();
    }

    public static class FixedValueInterceptor implements FixedValue{

        @Override
        public Object loadObject() throws Exception {
            return "Hello World";
        }
    }
}
