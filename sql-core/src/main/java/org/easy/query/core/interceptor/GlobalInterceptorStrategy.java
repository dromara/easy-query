package org.easy.query.core.interceptor;

/**
 * @FileName: GlobalInterceptorStrategy.java
 * @Description: 文件说明
 * @Date: 2023/3/9 21:35
 * @Created by xuejiaming
 */
public interface GlobalInterceptorStrategy {
    String interceptorName();
    boolean apply(Class<?> entityClass);
}
