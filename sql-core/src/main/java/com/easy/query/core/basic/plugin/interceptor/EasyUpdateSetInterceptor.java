package com.easy.query.core.basic.plugin.interceptor;

import com.easy.query.core.expression.parser.core.SqlColumnSetter;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;

/**
 * @FileName: EasyUpdateSetInterceptor.java
 * @author xuejiaming
 */
public interface EasyUpdateSetInterceptor extends EasyInterceptor {
    void configure(Class<?> entityClass, EntityUpdateExpressionBuilder entityUpdateExpression, SqlColumnSetter<Object> columnSetter);
//    void configureWhere(Class<?> entityClass, SqlEntityUpdateExpression sqlEntityUpdateExpression, SqlPredicate<Object> sqlPredicate);
}
