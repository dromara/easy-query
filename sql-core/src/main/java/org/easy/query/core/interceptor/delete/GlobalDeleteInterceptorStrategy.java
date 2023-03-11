package org.easy.query.core.interceptor.delete;

import org.easy.query.core.expression.parser.abstraction.SqlPredicate;
import org.easy.query.core.interceptor.GlobalInterceptorStrategy;
import org.easy.query.core.query.SqlEntityDeleteExpression;

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
