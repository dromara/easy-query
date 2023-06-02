package com.easy.query.api4j.extension.interceptor;

import com.easy.query.api4j.sql.SQLColumnSetter;
import com.easy.query.api4j.sql.impl.SQLColumnSetterImpl;
import com.easy.query.core.basic.extension.interceptor.UpdateSetInterceptor;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;

/**
 * create time 2023/6/2 17:16
 * 文件说明
 *
 * @author xuejiaming
 */
public interface UpdateSet4JInterceptor extends UpdateSetInterceptor {
    @Override
    default void configure(Class<?> entityClass, EntityUpdateExpressionBuilder entityUpdateExpressionBuilder, ColumnSetter<Object> columnSetter) {
        configure(entityClass, entityUpdateExpressionBuilder, new SQLColumnSetterImpl<>(columnSetter));
    }

    void configure(Class<?> entityClass, EntityUpdateExpressionBuilder entityUpdateExpressionBuilder, SQLColumnSetter<Object> sqlColumnSetter);
}
