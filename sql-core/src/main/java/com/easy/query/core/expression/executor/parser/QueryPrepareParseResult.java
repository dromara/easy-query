package com.easy.query.core.expression.executor.parser;

import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;

/**
 * create time 2023/4/24 23:04
 * 文件说明
 *
 * @author xuejiaming
 */
public interface QueryPrepareParseResult extends PredicatePrepareParseResult{
    @Override
    EntityQueryExpressionBuilder getEntityExpressionBuilder();
    EasyQuerySqlExpression getEasyQuerySqlExpression();
}
