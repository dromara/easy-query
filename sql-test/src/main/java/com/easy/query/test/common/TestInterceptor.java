package com.easy.query.test.common;

import com.easy.query.core.basic.extension.interceptor.PredicateFilterInterceptor;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.sql.builder.LambdaEntityExpressionBuilder;
import com.easy.query.test.mysql8.entity.Interceptor2Entity;
import com.easy.query.test.mysql8.entity.InterceptorEntity;
import org.jetbrains.annotations.NotNull;

/**
 * create time 2026/4/11 13:44
 * 文件说明
 *
 * @author xuejiaming
 */
public class TestInterceptor implements PredicateFilterInterceptor {
    @Override
    public void configure(Class<?> entityClass, LambdaEntityExpressionBuilder lambdaEntityExpressionBuilder, WherePredicate<Object> wherePredicate) {
        if(InterceptorEntity.class.equals(entityClass)){
            wherePredicate.sqlNativeSegment("1=1");
        }else{
            wherePredicate.sqlNativeSegment("2=2");
        }
    }

    @Override
    public String name() {
        return "TestInterceptor";
    }

    @Override
    public boolean apply(@NotNull Class<?> entityClass) {
        return InterceptorEntity.class.equals(entityClass) || Interceptor2Entity.class.equals(entityClass);
    }
}
