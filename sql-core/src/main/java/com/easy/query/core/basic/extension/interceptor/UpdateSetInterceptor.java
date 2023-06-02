package com.easy.query.core.basic.extension.interceptor;

import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;

/**
 * @FileName: EasyUpdateSetInterceptor.java
 * @author xuejiaming
 */
public interface UpdateSetInterceptor extends Interceptor {
    void configure(Class<?> entityClass, EntityUpdateExpressionBuilder entityUpdateExpressionBuilder, ColumnSetter<Object> columnSetter);
}
