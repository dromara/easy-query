package com.easy.query.core.basic.extension.interceptor;

import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;

/**
 * @FileName: GlobalEntityInterceptor.java
 * @Description: 文件说明
 * create time 2023/3/15 13:01
 * @author xuejiaming
 */
public interface EntityInterceptor extends Interceptor {
    void configureInsert(Class<?> entityClass, EntityInsertExpressionBuilder entityInsertExpressionBuilder, Object entity);
    void configureUpdate(Class<?> entityClass, EntityUpdateExpressionBuilder entityUpdateExpressionBuilder, Object entity);
}
