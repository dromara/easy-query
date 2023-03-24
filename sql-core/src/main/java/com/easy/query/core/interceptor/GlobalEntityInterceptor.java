package com.easy.query.core.interceptor;

import com.easy.query.core.query.SqlEntityInsertExpression;
import com.easy.query.core.query.SqlEntityUpdateExpression;

/**
 * @FileName: GlobalEntityInterceptor.java
 * @Description: 文件说明
 * @Date: 2023/3/15 13:01
 * @author xuejiaming
 */
public interface GlobalEntityInterceptor extends GlobalInterceptor {
    void configureInsert(Class<?> entityClass, SqlEntityInsertExpression sqlEntityUpdateExpression, Object entity);
    void configureUpdate(Class<?> entityClass, SqlEntityUpdateExpression sqlEntityUpdateExpression, Object entity);
}
