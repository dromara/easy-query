package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.segment.impl.AbstractSQLNativeSegmentImpl;
import com.easy.query.core.expression.segment.scec.context.core.SQLNativeExpression;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.visitor.TableVisitor;
import com.easy.query.core.util.EasySQLSegmentUtil;

/**
 * create time 2023/7/30 21:06
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLNativePredicateImpl extends AbstractSQLNativeSegmentImpl implements Predicate {
    public SQLNativePredicateImpl(ExpressionContext expressionContext, String sqlSegment, SQLNativeExpression sqlNativeExpression) {
        super(expressionContext, sqlSegment, sqlNativeExpression);
    }

    @Override
    public Predicate cloneSQLColumnSegment() {
        return new SQLNativePredicateImpl(expressionContext,sqlSegment, sqlNativeExpression);
    }

    @Override
    public SQLPredicateCompare getOperator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(TableVisitor visitor) {
        EasySQLSegmentUtil.paramExpressionTableVisit(sqlNativeExpression.getExpressions(), visitor);
    }
}
