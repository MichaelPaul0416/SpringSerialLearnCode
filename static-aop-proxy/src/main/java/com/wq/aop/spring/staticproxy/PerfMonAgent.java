package com.wq.aop.spring.staticproxy;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;

/**
 * @Author: wangqiang20995
 * @Date:2019/1/29
 * @Description:
 * @Resource:
 */
public class PerfMonAgent {

    public static void premain(String agentArgs,Instrumentation ins){
        System.out.println("PerfMonAgent.permain() was called");

        ClassFileTransformer transformer = new PerfMonXformer();
        System.out.println("Adding a PerfMonXformer instances to the JVM");
        ins.addTransformer(transformer);
    }
}
