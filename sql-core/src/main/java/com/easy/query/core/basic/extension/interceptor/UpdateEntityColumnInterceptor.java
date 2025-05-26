package com.easy.query.core.basic.extension.interceptor;

import org.jetbrains.annotations.NotNull;
import com.easy.query.core.expression.parser.core.base.ColumnOnlySelector;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;

import java.util.Objects;

/**
 * @author xuejiaming
 */
public interface UpdateEntityColumnInterceptor extends Interceptor {
    void configure(@NotNull Class<?> entityClass,@NotNull EntityUpdateExpressionBuilder entityUpdateExpressionBuilder,@NotNull ColumnOnlySelector<Object> columnSelector,@NotNull Object entity);
}
