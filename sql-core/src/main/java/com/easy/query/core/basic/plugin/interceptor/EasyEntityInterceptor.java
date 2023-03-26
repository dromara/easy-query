package com.easy.query.core.basic.plugin.interceptor;

import com.easy.query.core.expression.sql.SqlEntityInsertExpression;
import com.easy.query.core.expression.sql.SqlEntityUpdateExpression;

/**
 * @FileName: GlobalEntityInterceptor.java
 * @Description: 文件说明
 * @Date: 2023/3/15 13:01
 * @author xuejiaming
 */
public interface EasyEntityInterceptor extends EasyInterceptor {
    void configureInsert(Class<?> entityClass, SqlEntityInsertExpression sqlEntityUpdateExpression, Object entity);
    void configureUpdate(Class<?> entityClass, SqlEntityUpdateExpression sqlEntityUpdateExpression, Object entity);
}
