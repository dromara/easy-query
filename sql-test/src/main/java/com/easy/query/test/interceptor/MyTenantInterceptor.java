package com.easy.query.test.interceptor;

import com.easy.query.core.basic.extension.interceptor.EntityInterceptor;
import com.easy.query.core.basic.extension.interceptor.PredicateFilterInterceptor;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.LambdaEntityExpressionBuilder;
import com.easy.query.test.entity.TopicInterceptor;
import com.easy.query.test.logicdel.CurrentUserHelper;
import org.jetbrains.annotations.NotNull;

/**
 * create time 2023/4/3 22:11
 * 文件说明
 *
 * @author xuejiaming
 */
public class MyTenantInterceptor implements EntityInterceptor, PredicateFilterInterceptor {
    @Override
    public String name() {
        return "MyTenantInterceptor";
    }

    @Override
    public boolean apply(@NotNull Class<?> entityClass) {
        return TopicInterceptor.class.isAssignableFrom(entityClass);
    }

    @Override
    public void configure(Class<?> entityClass, LambdaEntityExpressionBuilder lambdaEntityExpressionBuilder, WherePredicate<Object> wherePredicate) {
        if (CurrentUserHelper.getUserId() != null) {
            //获取租户id的lambda表达式
            wherePredicate.eq("tenantId", CurrentUserHelper.getTenantId());
        }
    }

    @Override
    public void configureInsert(Class<?> entityClass, EntityInsertExpressionBuilder entityInsertExpressionBuilder, Object entity) {

        TopicInterceptor topicInterceptor = (TopicInterceptor) entity;
        if (topicInterceptor.getTenantId() == null) {
            topicInterceptor.setTenantId(CurrentUserHelper.getTenantId());
        }
    }

    @Override
    public void configureUpdate(Class<?> entityClass, EntityUpdateExpressionBuilder entityUpdateExpressionBuilder, Object entity) {

    }
}
