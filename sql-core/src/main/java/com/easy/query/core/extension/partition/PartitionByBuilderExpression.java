//package com.easy.query.core.extension.partition;
//
//import com.easy.query.core.common.tuple.Tuple2;
//import com.easy.query.core.context.QueryRuntimeContext;
//import com.easy.query.core.expression.builder.Filter;
//import com.easy.query.core.expression.builder.OrderSelector;
//import com.easy.query.core.expression.lambda.SQLExpression1;
//import com.easy.query.core.expression.parser.core.available.TableAvailable;
//import com.easy.query.core.expression.segment.scec.expression.ColumnPropertyExpressionImpl;
//import com.easy.query.core.expression.segment.scec.expression.ParamExpression;
//import com.easy.query.core.expression.sql.builder.ExpressionContext;
//import com.easy.query.core.extension.casewhen.CaseWhenBuilderExpression;
//import com.easy.query.core.func.SQLFunction;
//import com.easy.query.core.func.def.impl.RowNumberSQLFunction;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * create time 2024/8/4 14:36
// * 文件说明
// *
// * @author xuejiaming
// */
//public class PartitionByBuilderExpression {
//    private final QueryRuntimeContext runtimeContext;
//    private final ExpressionContext expressionContext;
//    private ParamExpression partitionByParamExpression;
//    private SQLExpression1<OrderSelector> orderSelectorSQLExpression;
//
//
//    public PartitionByBuilderExpression(QueryRuntimeContext runtimeContext, ExpressionContext expressionContext) {
//        this.runtimeContext = runtimeContext;
//        this.expressionContext = expressionContext;
//    }
//
//    public PartitionByBuilderExpression partitionBy(ParamExpression paramExpression) {
//        partitionByParamExpression = paramExpression;
//        return this;
//    }
//
//    public PartitionByBuilderExpression partitionBy(TableAvailable table, String property) {
//        partitionByParamExpression = new ColumnPropertyExpressionImpl(table, property);
//        return this;
//    }
//    public SQLFunction orderBy(SQLExpression1<OrderSelector> orderSelectorExpression) {
//        orderSelectorSQLExpression = orderSelectorExpression;
//        return new RowNumberSQLFunction(partitionByParamExpression);
//    }
//}
