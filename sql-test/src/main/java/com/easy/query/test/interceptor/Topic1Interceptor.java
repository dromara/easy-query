package com.easy.query.test.interceptor;

import com.easy.query.core.basic.extension.interceptor.EntityInterceptor;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;
import com.easy.query.test.entity.TopicAuto;

/**
 * create time 2023/6/10 23:10
 * 文件说明
 *
 * @author xuejiaming
 */
public class Topic1Interceptor implements EntityInterceptor {
    @Override
    public void configureInsert(Class<?> entityClass, EntityInsertExpressionBuilder entityInsertExpressionBuilder, Object entity) {
        TopicAuto entity1 = (TopicAuto) entity;
        entity1.setTitle(null);
    }

    @Override
    public void configureUpdate(Class<?> entityClass, EntityUpdateExpressionBuilder entityUpdateExpressionBuilder, Object entity) {

    }

    @Override
    public String name() {
        return "Topic1Interceptor";
    }

    @Override
    public boolean apply(Class<?> entityClass) {
        return TopicAuto.class.equals(entityClass);
    }

    @Override
    public boolean enable() {
        return false;
    }
}
