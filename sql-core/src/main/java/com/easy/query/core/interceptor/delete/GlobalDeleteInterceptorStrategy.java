package com.easy.query.core.interceptor.delete;

import com.easy.query.core.expression.parser.abstraction.SqlPredicate;
import com.easy.query.core.interceptor.GlobalInterceptorStrategy;
import com.easy.query.core.query.SqlEntityDeleteExpression;

/**
 * @FileName: GlobalUpdateEntityInterceptorStrategy.java
 * @Description: 文件说明
 * @Date: 2023/3/9 21:34
 * @Created by xuejiaming
 */
public interface GlobalDeleteInterceptorStrategy extends GlobalInterceptorStrategy {
    /**
     * 配置
     * @param entityClass
     * @param sqlEntityDeleteExpression
     * @param sqlPredicate
     */
    void configure(Class<?> entityClass, SqlEntityDeleteExpression sqlEntityDeleteExpression, SqlPredicate<?> sqlPredicate);
}
