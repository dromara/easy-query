package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.segment.SQLLazySegement;
import com.easy.query.core.expression.segment.impl.AbstractSQLNativeLazySegmentImpl;
import com.easy.query.core.expression.segment.scec.context.core.SQLNativeExpression;
import com.easy.query.core.expression.sql.builder.ExpressionContext;

import java.util.function.Function;

/**
 * create time 2023/7/30 21:06
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLNativeLazyPredicateImpl extends AbstractSQLNativeLazySegmentImpl implements Predicate {
    public SQLNativeLazyPredicateImpl(ExpressionContext expressionContext, SQLLazySegement sqlLazySegement, Function<String,String> sqlSegmentFunction, SQLNativeExpression sqlNativeExpression) {
        super(expressionContext,sqlLazySegement,sqlSegmentFunction, sqlNativeExpression);
    }

    @Override
    public Predicate cloneSQLColumnSegment() {
        return new SQLNativeLazyPredicateImpl(expressionContext,sqlLazySegement,sqlSegmentFunction, sqlNativeExpression);
    }

    @Override
    public SQLPredicateCompare getOperator() {
        throw new UnsupportedOperationException();
    }
}
