package com.easy.query.interceptor;

import com.easy.query.core.basic.plugin.interceptor.EasyEntityInterceptor;
import com.easy.query.core.basic.plugin.interceptor.EasyPredicateFilterInterceptor;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.abstraction.SqlPredicate;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.LambdaEntityExpressionBuilder;
import com.easy.query.core.util.EasyUtil;
import com.easy.query.entity.TopicInterceptor;
import com.easy.query.logicdel.CurrentUserHelper;

/**
 * create time 2023/4/3 22:11
 * 文件说明
 *
 * @author xuejiaming
 */
public class MyTenantInterceptor implements EasyEntityInterceptor,EasyPredicateFilterInterceptor {
    @Override
    public String name() {
        return "MyTenantInterceptor";
    }

    @Override
    public boolean apply(Class<?> entityClass) {
        return TopicInterceptor.class.isAssignableFrom(entityClass);
    }

    @Override
    public void configure(Class<?> entityClass, LambdaEntityExpressionBuilder lambdaEntityExpression, SqlPredicate<Object> sqlPredicate) {
        if(CurrentUserHelper.getUserId()!=null){
            //获取租户id的lambda表达式
            Property<Object, ?> tenantId = EasyUtil.getPropertyGetterLambda(entityClass, "tenantId", String.class);
            sqlPredicate.eq(tenantId, CurrentUserHelper.getTenantId());
        }
    }

    @Override
    public void configureInsert(Class<?> entityClass, EntityInsertExpressionBuilder entityInsertExpression, Object entity) {

        TopicInterceptor topicInterceptor = (TopicInterceptor) entity;
        if (topicInterceptor.getTenantId() == null) {
            topicInterceptor.setTenantId(CurrentUserHelper.getTenantId());
        }
    }

    @Override
    public void configureUpdate(Class<?> entityClass, EntityUpdateExpressionBuilder entityUpdateExpression, Object entity) {

    }
}
