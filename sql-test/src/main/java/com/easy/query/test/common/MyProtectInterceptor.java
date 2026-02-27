package com.easy.query.test.common;

import com.easy.query.core.basic.extension.interceptor.PredicateFilterInterceptor;
import com.easy.query.core.basic.extension.interceptor.ProtectedInterceptor;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.sql.builder.LambdaEntityExpressionBuilder;
import com.easy.query.test.mysql8.entity.M8UserProtect;
import org.jetbrains.annotations.NotNull;

/**
 * create time 2026/2/27 10:19
 * 文件说明
 *
 * @author xuejiaming
 */
public class MyProtectInterceptor implements PredicateFilterInterceptor, ProtectedInterceptor {
    @Override
    public void configure(Class<?> entityClass, LambdaEntityExpressionBuilder lambdaEntityExpressionBuilder, WherePredicate<Object> wherePredicate) {

        wherePredicate.sqlNativeSegment("1=1");
    }

    @Override
    public String name() {
        return "MyProtectInterceptor";
    }

    @Override
    public boolean apply(@NotNull Class<?> entityClass) {
        return M8UserProtect.class.equals(entityClass);
    }
}
