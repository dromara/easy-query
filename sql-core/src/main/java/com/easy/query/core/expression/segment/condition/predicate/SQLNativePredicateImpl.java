package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.segment.impl.AbstractSQLNativeSegmentImpl;
import com.easy.query.core.expression.segment.scec.context.core.SQLNativeExpression;

/**
 * create time 2023/7/30 21:06
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLNativePredicateImpl extends AbstractSQLNativeSegmentImpl implements Predicate {
    public SQLNativePredicateImpl(QueryRuntimeContext runtimeContext, String sqlSegment, SQLNativeExpression sqlNativeExpression) {
        super(runtimeContext, sqlSegment, sqlNativeExpression);
    }

    @Override
    public Predicate cloneSQLColumnSegment() {
        return new SQLNativePredicateImpl(runtimeContext,sqlSegment, sqlNativeExpression);
    }

    @Override
    public SQLPredicateCompare getOperator() {
        throw new UnsupportedOperationException();
    }
}
