package com.easy.query.core.interceptor.update;

import com.easy.query.core.interceptor.GlobalInterceptorStrategy;
import com.easy.query.core.query.SqlEntityUpdateExpression;

/**
 * @FileName: GlobalUpdateEntityInterceptorStrategy.java
 * @Description: 文件说明
 * @Date: 2023/3/9 21:34
 * @Created by xuejiaming
 */
public interface GlobalUpdateInterceptorStrategy extends GlobalInterceptorStrategy {
    void configure(Class<?> entityClass, SqlEntityUpdateExpression sqlEntityUpdateExpression, Object entity);
}
