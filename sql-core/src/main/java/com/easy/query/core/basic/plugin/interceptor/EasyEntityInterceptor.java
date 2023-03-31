package com.easy.query.core.basic.plugin.interceptor;

import com.easy.query.core.expression.sql.EntityInsertExpression;
import com.easy.query.core.expression.sql.EntityUpdateExpression;

/**
 * @FileName: GlobalEntityInterceptor.java
 * @Description: 文件说明
 * @Date: 2023/3/15 13:01
 * @author xuejiaming
 */
public interface EasyEntityInterceptor extends EasyInterceptor {
    void configureInsert(Class<?> entityClass, EntityInsertExpression entityInsertExpression, Object entity);
    void configureUpdate(Class<?> entityClass, EntityUpdateExpression entityUpdateExpression, Object entity);
}
