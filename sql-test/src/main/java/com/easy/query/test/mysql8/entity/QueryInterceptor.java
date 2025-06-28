package com.easy.query.test.mysql8.entity;

import com.easy.query.core.basic.extension.interceptor.PredicateFilterInterceptor;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.sql.builder.LambdaEntityExpressionBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * create time 2025/6/26 22:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryInterceptor implements PredicateFilterInterceptor {
    @Override
    public void configure(Class<?> entityClass, LambdaEntityExpressionBuilder lambdaEntityExpressionBuilder, WherePredicate<Object> wherePredicate) {
        wherePredicate.eq("name","123");
    }

    @Override
    public String name() {
        return "aaaaxx";
    }

    @Override
    public boolean enable() {
        return false;
    }

    @Override
    public boolean apply(@NotNull Class<?> entityClass) {
        return M8User.class.equals(entityClass);
    }
}
