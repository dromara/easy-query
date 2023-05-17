package com.easy.query.core.expression.executor.parser;


import com.easy.query.core.expression.sql.builder.EntityPredicateExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EasyEntityPredicateSQLExpression;

/**
 * create time 2023/4/26 08:20
 * 文件说明
 *
 * @author xuejiaming
 */
public interface PredicatePrepareParseResult extends PrepareParseResult{
    EasyEntityPredicateSQLExpression getEasyEntityPredicateSQLExpression();
    @Override
    EntityPredicateExpressionBuilder getEntityExpressionBuilder();
}
