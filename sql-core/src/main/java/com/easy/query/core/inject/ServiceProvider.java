package com.easy.query.core.inject;

/**
 * create time 2023/5/5 17:29
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ServiceProvider {
     <T> T getService(Class<T> serviceType);
    Object getServiceObject(Class<?> serviceType);
}
