package com.easy.query.test.common;

import com.easy.query.core.basic.extension.interceptor.PredicateFilterInterceptor;
import com.easy.query.core.basic.jdbc.executor.EntityExpressionExecutor;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.sql.builder.LambdaEntityExpressionBuilder;
import com.easy.query.test.mysql8.entity.bank.SysBankCard;

/**
 * create time 2025/4/18 10:37
 * 文件说明
 *
 * @author xuejiaming
 */
public class M8Interceptor implements PredicateFilterInterceptor {
    @Override
    public void configure(Class<?> entityClass, LambdaEntityExpressionBuilder lambdaEntityExpressionBuilder, WherePredicate<Object> wherePredicate) {
        System.out.println(lambdaEntityExpressionBuilder);

    }

    @Override
    public String name() {
        return "aaaa";
    }

    @Override
    public boolean apply(Class<?> entityClass) {
        return SysBankCard.class.equals(entityClass);
    }
}
