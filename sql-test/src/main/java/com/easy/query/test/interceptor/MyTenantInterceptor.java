package com.easy.query.test.interceptor;

import com.easy.query.core.basic.plugin.interceptor.EntityInterceptor;
import com.easy.query.core.basic.plugin.interceptor.PredicateFilterInterceptor;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.SQLWherePredicate;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.LambdaEntityExpressionBuilder;
import com.easy.query.core.util.EasyBeanUtil;
import com.easy.query.test.entity.TopicInterceptor;
import com.easy.query.test.logicdel.CurrentUserHelper;

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
    public boolean apply(Class<?> entityClass) {
        return TopicInterceptor.class.isAssignableFrom(entityClass);
    }

    @Override
    public void configure(Class<?> entityClass, LambdaEntityExpressionBuilder lambdaEntityExpressionBuilder, SQLWherePredicate<Object> sqlWherePredicate) {
        if(CurrentUserHelper.getUserId()!=null){
            //获取租户id的lambda表达式
            Property<Object, ?> tenantId = EasyBeanUtil.getPropertyGetterLambda(entityClass, "tenantId", String.class);
            sqlWherePredicate.eq(tenantId, CurrentUserHelper.getTenantId());
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
