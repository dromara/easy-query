package com.easy.query.core.interceptor.update;

import com.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import com.easy.query.core.interceptor.GlobalInterceptorStrategy;
import com.easy.query.core.query.SqlEntityUpdateExpression;

/**
 * @FileName: GlobalUpdateSetInterceptorStrategy.java
 * @Description: 文件说明
 * @Date: 2023/3/9 21:36
 * @Created by xuejiaming
 */
public interface GlobalUpdateSetInterceptorStrategy extends GlobalInterceptorStrategy {
    void configure(Class<?> entityClass, SqlEntityUpdateExpression sqlEntityUpdateExpression, SqlColumnSetter<?> columnSetter);
}
