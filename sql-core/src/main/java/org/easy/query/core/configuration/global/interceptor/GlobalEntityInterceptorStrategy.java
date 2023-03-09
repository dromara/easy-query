package org.easy.query.core.configuration.global.interceptor;

/**
 * @FileName: GlobalEntityInterceptorStrategy.java
 * @Description: 文件说明
 * @Date: 2023/3/9 21:37
 * @Created by xuejiaming
 */
public interface GlobalEntityInterceptorStrategy extends GlobalInterceptorStrategy{
    void configure(Class<?> entityClass, Object entity);
}
