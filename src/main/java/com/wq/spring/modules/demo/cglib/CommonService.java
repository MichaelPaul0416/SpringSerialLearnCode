package com.wq.spring.modules.demo.cglib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: wangqiang20995
 * @Date:2018/12/1
 * @Description:
 * @Resource:
 */
public class CommonService {

    private String serviceId;

    private Map<String,Object>  relation;

    private Map<Class<?>,Object> valueRecord;

    private AtomicInteger atomicInteger;

    private static final Integer DELTA = 1;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public CommonService(){
        atomicInteger = new AtomicInteger(0);
        valueRecord = new ConcurrentHashMap<>();
    }

    public void describe(Object param){
        if(relation == null){
            relation = new HashMap<>();
        }

        atomicInteger.addAndGet(DELTA);
        relation.put(param.getClass().getName(),param);
        if(valueRecord.containsKey(param.getClass())){
            AtomicInteger times = (AtomicInteger) valueRecord.get(param.getClass());
            times.addAndGet(DELTA);
        }else {
            AtomicInteger times = new AtomicInteger(1);
            valueRecord.putIfAbsent(param.getClass(),times);
        }
    }

    public void showTotal(){
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return "CommonService{" +
                "serviceId='" + serviceId + '\'' +
                ", relation=" + relation +
                ", valueRecord=" + valueRecord +
                ", atomicInteger=" + atomicInteger +
                '}';
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public Map<String, Object> getRelation() {
        return relation;
    }

    public void setRelation(Map<String, Object> relation) {
        this.relation = relation;
    }
}
