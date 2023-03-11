package org.easy.query.core.interceptor.insert;


import org.easy.query.core.interceptor.GlobalInterceptorStrategy;
import org.easy.query.core.query.SqlEntityInsertExpression;

/**
 * @FileName: GlobalInsertInterceptorStrategy.java
 * @Description: 文件说明
 * @Date: 2023/3/9 21:14
 * @Created by xuejiaming
 */
public interface GlobalInsertInterceptorStrategy extends GlobalInterceptorStrategy {
    void configure(Class<?> entityClass, SqlEntityInsertExpression sqlEntityInsertExpression, Object entity);
}
