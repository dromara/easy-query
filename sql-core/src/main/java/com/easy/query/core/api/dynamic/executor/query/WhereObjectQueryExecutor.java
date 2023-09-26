package com.easy.query.core.api.dynamic.executor.query;

import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;

/**
 * create time 2023/9/26 08:27
 * 文件说明
 *
 * @author xuejiaming
 */
public interface WhereObjectQueryExecutor {
    void whereObject(Object object,EntityQueryExpressionBuilder entityQueryExpressionBuilder);
}
