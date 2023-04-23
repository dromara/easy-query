package com.easy.query.core.basic.plugin.interceptor;

import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;

/**
 * @FileName: GlobalEntityInterceptor.java
 * @Description: 文件说明
 * @Date: 2023/3/15 13:01
 * @author xuejiaming
 */
public interface EasyEntityInterceptor extends EasyInterceptor {
    void configureInsert(Class<?> entityClass, EntityInsertExpressionBuilder entityInsertExpression, Object entity);
    void configureUpdate(Class<?> entityClass, EntityUpdateExpressionBuilder entityUpdateExpression, Object entity);
}
