package com.easy.query.api4j.extension.interceptor;

import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.api4j.sql.impl.SQLWherePredicateImpl;
import com.easy.query.core.basic.extension.interceptor.PredicateFilterInterceptor;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.sql.builder.LambdaEntityExpressionBuilder;

/**
 * create time 2023/6/2 17:13
 * 文件说明
 *
 * @author xuejiaming
 */
public interface PredicateFilter4JInterceptor extends PredicateFilterInterceptor {
    @Override
    default void configure(Class<?> entityClass, LambdaEntityExpressionBuilder lambdaEntityExpressionBuilder, WherePredicate<Object> wherePredicate) {
        configure(entityClass, lambdaEntityExpressionBuilder, new SQLWherePredicateImpl<>(wherePredicate));
    }

    void configure(Class<?> entityClass, LambdaEntityExpressionBuilder lambdaEntityExpressionBuilder, SQLWherePredicate<Object> sqlWherePredicate);
}
