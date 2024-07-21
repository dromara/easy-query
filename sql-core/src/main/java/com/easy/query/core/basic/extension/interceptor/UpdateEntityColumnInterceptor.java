package com.easy.query.core.basic.extension.interceptor;

import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.expression.parser.core.base.ColumnOnlySelector;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;

import java.util.Objects;

/**
 * @author xuejiaming
 */
public interface UpdateEntityColumnInterceptor extends Interceptor {
    void configure(Class<?> entityClass, EntityUpdateExpressionBuilder entityUpdateExpressionBuilder, ColumnOnlySelector<Object> columnSelector,@NotNull Object entity);
}
