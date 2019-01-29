package com.wq.spring.modules.demo.aop.staticproxy;

/**
 * @Author: wangqiang20995
 * @Date:2019/1/29
 * @Description:
 * @Resource:
 */
public class SpringStaticAopProxyDemo {

    public static void main(String args[]){
        new SpringStaticAopProxyDemo().costTime();
    }

    public void costTime(){
        System.out.println("测试花费时间的输出日志");
    }
}
