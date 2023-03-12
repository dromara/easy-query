package com.easy.query.core.interceptor.select;

import com.easy.query.core.expression.parser.abstraction.SqlPredicate;
import com.easy.query.core.interceptor.GlobalInterceptorStrategy;
import com.easy.query.core.query.SqlEntityQueryExpression;

/**
 * @FileName: EntityQueryFilterConfiguration.java
 * @Description: 文件说明
 * @Date: 2023/3/7 22:25
 * @Created by xuejiaming
 */
public interface GlobalSelectInterceptorStrategy extends GlobalInterceptorStrategy {

    /**
     * 配置
     * @param entityClass
     * @param sqlEntityQueryExpression
     * @param sqlPredicate
     */
    void configure(Class<?> entityClass, SqlEntityQueryExpression sqlEntityQueryExpression, SqlPredicate<?> sqlPredicate);
}
