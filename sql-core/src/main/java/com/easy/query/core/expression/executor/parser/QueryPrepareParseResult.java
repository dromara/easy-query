package com.easy.query.core.expression.executor.parser;

import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EntityQuerySQLExpression;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;

/**
 * create time 2023/4/24 23:04
 * 文件说明
 *
 * @author xuejiaming
 */
public interface QueryPrepareParseResult extends PredicatePrepareParseResult{
    @Override
    EntityQuerySQLExpression getEntityPredicateSQLExpression();
    @Override
    EntityQueryExpressionBuilder getEntityExpressionBuilder();
    long getOriginalOffset();
    long getOriginalRows();
    default boolean isPaginationQuery(){
        return getOriginalOffset()>0|| getOriginalRows()>0;
    }
    boolean isStartsWithGroupByInOrderBy();
    void setStartsWithGroupByInOrderBy(boolean startsWithGroupByInOrderBy);
    SequenceParseResult getSequenceParseResult();
    default boolean isSeqQuery(){
        return getSequenceParseResult()!=null;
    }

    int getMaxShardingQueryLimit();
    ConnectionModeEnum getConnectionMode();
}
