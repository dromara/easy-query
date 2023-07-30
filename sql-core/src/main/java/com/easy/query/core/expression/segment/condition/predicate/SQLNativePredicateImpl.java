package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.segment.AbstractSQLNativeSegmentImpl;
import com.easy.query.core.expression.segment.SQLColumnSegment;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;

/**
 * create time 2023/7/30 21:06
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLNativePredicateImpl extends AbstractSQLNativeSegmentImpl implements Predicate {
    public SQLNativePredicateImpl(QueryRuntimeContext runtimeContext, String sqlSegment, SQLNativeExpressionContext sqlConstExpressionContext) {
        super(runtimeContext, sqlSegment, sqlConstExpressionContext);
    }

    @Override
    public SQLColumnSegment cloneSQLColumnSegment() {
        return new SQLNativePredicateImpl(runtimeContext,sqlSegment,sqlConstExpressionContext);
    }

    @Override
    public SQLPredicateCompare getOperator() {
        return null;
    }
}
