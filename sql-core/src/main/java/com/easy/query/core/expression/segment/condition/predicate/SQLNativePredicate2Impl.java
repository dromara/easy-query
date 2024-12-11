//package com.easy.query.core.expression.segment.condition.predicate;
//
//import com.easy.query.core.enums.SQLPredicateCompare;
//import com.easy.query.core.expression.segment.SQLSegment;
//import com.easy.query.core.expression.segment.impl.AbstractSQLNativeSegment2Impl;
//import com.easy.query.core.expression.segment.scec.context.core.SQLNativeExpression;
//import com.easy.query.core.expression.sql.builder.ExpressionContext;
//
//import java.util.function.Function;
//
///**
// * create time 2023/7/30 21:06
// * 文件说明
// *
// * @author xuejiaming
// */
//public class SQLNativePredicate2Impl extends AbstractSQLNativeSegment2Impl implements Predicate {
//    public SQLNativePredicate2Impl(ExpressionContext expressionContext, SQLSegment sqlSegment, Function<String, String> sqlSegmentFunction, SQLNativeExpression sqlNativeExpression) {
//        super(expressionContext, sqlSegment,sqlSegmentFunction, sqlNativeExpression);
//    }
//
//    @Override
//    public Predicate cloneSQLColumnSegment() {
//        return new SQLNativePredicate2Impl(expressionContext,sqlSegment,sqlSegmentFunction, sqlNativeExpression);
//    }
//
//    @Override
//    public SQLPredicateCompare getOperator() {
//        throw new UnsupportedOperationException();
//    }
//}
