package com.wq.aop.spring.staticproxy;

import javassist.*;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @Author: wangqiang20995
 * @Date:2019/1/29
 * @Description:
 * @Resource:
 */
public class PerfMonXformer implements ClassFileTransformer {

    private static final String AOP_CLASS = "com/wq/spring/modules/demo/aop/staticproxy/SpringStaticAopProxyDemo";

    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

        if(!AOP_CLASS.equals(className)){
            return null;
        }

        byte[] transformed = null;

        System.out.println("Transforming : " + className);

        ClassPool pool = ClassPool.getDefault();

        pool.importPackage("com.wq.spring.modules.demo.aop.staticproxy");
        CtClass ctClass = null;

        try{
            ctClass = pool.makeClass(new ByteArrayInputStream(classfileBuffer));

            if(ctClass.isInterface() == false){
                CtMethod ctMethod = ctClass.getDeclaredMethod("costTime");
                ctMethod.insertAfter("System.out.println(\"---------------------------------------\");");
                ctMethod.insertBefore("long start = System.currentTimeMillis();");

                ctMethod.insertAfter("System.out.println(\"cost for method [\"+ctMethod.getName()+\"] --> \" + System.currentTimeMillis() - start);");
//                CtBehavior[] methods = ctClass.getDeclaredBehaviors();
//                for(int i = 0;i<methods.length;i++){
//                    if(methods[i].isEmpty() == false){
//                        doMethod(methods[i]);
//                    }
//                }

                transformed = ctClass.toBytecode();
            }
        }catch (Exception e){
            System.err.println("Could not instrument " + className + " , exception:" + e.getMessage());
        }finally {
            if(ctClass != null){
                ctClass.detach();
            }
        }

        return transformed;
    }

    private void doMethod(CtBehavior behavior) throws NotFoundException, CannotCompileException{
        behavior.insertBefore("long time = System.nanoTime();");

        behavior.insertAfter("System.out.println(\"leave    " + behavior.getName() + "  and times:\" + (System.nanoTime() - time));");
    }
}
