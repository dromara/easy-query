package com.easy.query.test.cache;

import com.easy.query.core.basic.extension.interceptor.PredicateFilterInterceptor;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.sql.builder.LambdaEntityExpressionBuilder;
import com.easy.query.test.entity.BlogEntity;
import org.jetbrains.annotations.NotNull;

/**
 * create time 2025/6/3 08:39
 * 文件说明
 *
 * @author xuejiaming
 */
public class BlogPredicateInterceptor implements PredicateFilterInterceptor {
    @Override
    public void configure(Class<?> entityClass, LambdaEntityExpressionBuilder lambdaEntityExpressionBuilder, WherePredicate<Object> wherePredicate) {
        wherePredicate.eq("content", "123");
    }

    @Override
    public String name() {
        return "blog-cache";
    }

    @Override
    public boolean apply(@NotNull Class<?> entityClass) {
        return BlogEntity.class.equals(entityClass);
    }

    @Override
    public boolean enable() {
        return false;
    }
}
