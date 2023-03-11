package org.easy.query.core.interceptor.select;

import org.easy.query.core.expression.parser.abstraction.SqlPredicate;
import org.easy.query.core.interceptor.GlobalInterceptorStrategy;
import org.easy.query.core.query.SqlEntityQueryExpression;

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
