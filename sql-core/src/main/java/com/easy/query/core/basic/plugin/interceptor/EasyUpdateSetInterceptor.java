package com.easy.query.core.basic.plugin.interceptor;

import com.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import com.easy.query.core.expression.sql.EntityUpdateExpression;

/**
 * @FileName: EasyUpdateSetInterceptor.java
 * @author xuejiaming
 */
public interface EasyUpdateSetInterceptor extends EasyInterceptor {
    void configure(Class<?> entityClass, EntityUpdateExpression sqlEntityUpdateExpression, SqlColumnSetter<Object> columnSetter);
//    void configureWhere(Class<?> entityClass, SqlEntityUpdateExpression sqlEntityUpdateExpression, SqlPredicate<Object> sqlPredicate);
}
