package com.easy.query.test.mytest;

import com.easy.query.core.basic.extension.interceptor.PredicateFilterInterceptor;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.sql.builder.LambdaEntityExpressionBuilder;
import org.jetbrains.annotations.NotNull;
import org.junit.Ignore;

/**
 * @FileName: NameQueryFilter.java
 * @Description: 文件说明
 * create time 2023/3/8 10:24
 * @author xuejiaming
 */
public class NameQueryFilter implements PredicateFilterInterceptor {
    @Override
    public void configure(Class<?> entityClass, LambdaEntityExpressionBuilder lambdaEntityExpressionBuilder, WherePredicate<Object> wherePredicate) {
        wherePredicate.isNull("namex");
    }

    @Override
    public String name() {
        return "nameQueryFilter";
    }

    @Override
    public boolean apply(@NotNull Class<?> entityClass) {
        return TestUserMysql.class.equals(entityClass);
    }
}
