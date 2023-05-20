package com.easy.query.core.basic.plugin.interceptor;

import com.easy.query.core.expression.parser.core.SQLWherePredicate;
import com.easy.query.core.expression.sql.builder.LambdaEntityExpressionBuilder;

/**
 * @FileName: GlobalQueryFilterInterceptor.java
 * @Description: 文件说明
 * @Date: 2023/3/7 22:25
 * @author xuejiaming
 */
public interface PredicateFilterInterceptor extends Interceptor {


    /**
     * 配置
     * @param entityClass
     * @param lambdaEntityExpressionBuilder
     * @param sqlWherePredicate
     */
    void configure(Class<?> entityClass, LambdaEntityExpressionBuilder lambdaEntityExpressionBuilder, SQLWherePredicate<Object> sqlWherePredicate);
}
