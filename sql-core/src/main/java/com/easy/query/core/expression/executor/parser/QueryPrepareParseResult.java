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
    EasyQuerySqlExpression getEasyEntityPredicateSqlExpression();
    @Override
    EntityQueryExpressionBuilder getEntityExpressionBuilder();
    long getOffset();
    long getRows();
    boolean isSharding();
    boolean isStartsWithGroupByInOrderBy();
    void setStartsWithGroupByInOrderBy(boolean startsWithGroupByInOrderBy);
    SequenceParseResult getSequenceParseResult();
}
