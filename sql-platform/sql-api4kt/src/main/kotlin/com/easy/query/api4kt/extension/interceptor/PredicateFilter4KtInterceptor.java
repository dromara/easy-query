package com.easy.query.api4kt.extension.interceptor;

import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtWherePredicateImpl;
import com.easy.query.core.basic.extension.interceptor.PredicateFilterInterceptor;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.sql.builder.LambdaEntityExpressionBuilder;

/**
 * create time 2023/6/2 17:13
 * 文件说明
 *
 * @author xuejiaming
 */
public interface PredicateFilter4KtInterceptor extends PredicateFilterInterceptor {
    @Override
    default void configure(Class<?> entityClass, LambdaEntityExpressionBuilder lambdaEntityExpressionBuilder, WherePredicate<Object> wherePredicate) {
        configure(entityClass, lambdaEntityExpressionBuilder, new SQLKtWherePredicateImpl<>(wherePredicate));
    }

    void configure(Class<?> entityClass, LambdaEntityExpressionBuilder lambdaEntityExpressionBuilder, SQLKtWherePredicate<Object> sqlWherePredicate);
}
