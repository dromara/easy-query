package com.easy.query.core.basic.extension.interceptor;

import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.builder.LambdaEntityExpressionBuilder;

/**
 * @FileName: GlobalQueryFilterInterceptor.java
 * @Description: 文件说明
 * create time 2023/3/7 22:25
 * @author xuejiaming
 */
public interface PredicateFilterInterceptor extends Interceptor {


    /**
     * 配置
     *
     * @param entityClass 处理的对象
     * @param lambdaEntityExpressionBuilder 整个表达式
     * @param wherePredicate 本次要添加的添加写到这里
//     * @param originalPredicate 添加之前的原始条件
     */
    void configure(Class<?> entityClass, LambdaEntityExpressionBuilder lambdaEntityExpressionBuilder, WherePredicate<Object> wherePredicate);
}
