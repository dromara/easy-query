package org.easy.query.core.interceptor.update;

import org.easy.query.core.interceptor.GlobalInterceptorStrategy;
import org.easy.query.core.query.SqlEntityUpdateExpression;

/**
 * @FileName: GlobalUpdateEntityInterceptorStrategy.java
 * @Description: 文件说明
 * @Date: 2023/3/9 21:34
 * @Created by xuejiaming
 */
public interface GlobalUpdateInterceptorStrategy extends GlobalInterceptorStrategy {
    void configure(Class<?> entityClass, SqlEntityUpdateExpression sqlEntityUpdateExpression, Object entity);
}
