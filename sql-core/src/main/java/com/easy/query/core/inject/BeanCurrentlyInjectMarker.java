package com.easy.query.core.inject;

import com.easy.query.core.exception.EasyQueryInjectCurrentlyInCreationException;
import com.easy.query.core.util.EasyClassUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * create time 2023/5/16 08:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class BeanCurrentlyInjectMarker {
    /**
     *  使用ThreadLocal主要是考虑到多线程getService创建服务所以要单独对当前线程进行检查
     */
    private final ThreadLocal<Map<Class<?>,Long>> THREAD_BEAN_CREATED_MARKER = ThreadLocal.withInitial(HashMap::new);

    /**
     * 如果已经标记创建了那么将会报错
     * @param serviceDescriptor
     */
    public void beanCreateMark(ServiceDescriptor serviceDescriptor){
        long currentThreadId = Thread.currentThread().getId();
        Map<Class<?>, Long> beanMarker = THREAD_BEAN_CREATED_MARKER.get();
        Long o = beanMarker.putIfAbsent(serviceDescriptor.getServiceType(), currentThreadId);
        if(o!=null){
            throw new EasyQueryInjectCurrentlyInCreationException("bean currently creation:"+ EasyClassUtil.getSimpleName(serviceDescriptor.getServiceType()));
        }
    }

    /**
     * bean创建完成后调用重置掉对应的标记
     * @param serviceDescriptor
     */
    public void beanCreated(ServiceDescriptor serviceDescriptor){
        Map<Class<?>, Long> beanMarker = THREAD_BEAN_CREATED_MARKER.get();
        beanMarker.remove(serviceDescriptor.getServiceType());
    }
}
