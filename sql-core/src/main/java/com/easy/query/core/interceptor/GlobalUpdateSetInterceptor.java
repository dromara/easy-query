package com.easy.query.core.interceptor;

import com.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import com.easy.query.core.query.SqlEntityUpdateExpression;

/**
 * @FileName: GlobalUpdateInterceptor.java
 * @Description: 文件说明
 * @Date: 2023/3/9 21:34
 * @author xuejiaming
 */
public interface GlobalUpdateSetInterceptor extends GlobalInterceptor {
    void configure(Class<?> entityClass, SqlEntityUpdateExpression sqlEntityUpdateExpression, SqlColumnSetter<Object> columnSetter);
//    void configureWhere(Class<?> entityClass, SqlEntityUpdateExpression sqlEntityUpdateExpression, SqlPredicate<Object> sqlPredicate);
}
