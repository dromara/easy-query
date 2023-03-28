package com.easy.query.core.basic.plugin.interceptor;

import com.easy.query.core.expression.parser.abstraction.SqlPredicate;
import com.easy.query.core.expression.sql.SqlEntityExpression;
import com.easy.query.core.expression.sql.SqlLambdaEntityExpression;

/**
 * @FileName: GlobalQueryFilterInterceptor.java
 * @Description: 文件说明
 * @Date: 2023/3/7 22:25
 * @author xuejiaming
 */
public interface EasyPredicateFilterInterceptor extends EasyInterceptor {

    /**
     * 配置
     * @param entityClass
     * @param sqlPredicate
     */
    void configure(Class<?> entityClass, SqlLambdaEntityExpression sqlLambdaEntityExpression, SqlPredicate<Object> sqlPredicate);
}
