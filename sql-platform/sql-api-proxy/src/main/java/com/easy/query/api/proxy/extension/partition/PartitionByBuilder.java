//package com.easy.query.api.proxy.extension.partition;
//
//import com.easy.query.api.proxy.util.EasyParamExpressionUtil;
//import com.easy.query.core.expression.lambda.SQLActionExpression;
//import com.easy.query.core.extension.partition.PartitionByBuilderExpression;
//import com.easy.query.core.func.SQLFunction;
//import com.easy.query.core.proxy.core.EntitySQLContext;
//import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableAnyChainExpression;
//import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparableAnyChainExpressionImpl;
//
///**
// * create time 2024/8/4 14:46
// * 文件说明
// *
// * @author xuejiaming
// */
//public class PartitionByBuilder {
//    private final PartitionByBuilderExpression partitionByBuilderExpression;
//    private final EntitySQLContext entitySQLContext;
//
//    public PartitionByBuilder(EntitySQLContext entitySQLContext, PartitionByBuilderExpression partitionByBuilderExpression) {
//        this.entitySQLContext = entitySQLContext;
//        this.partitionByBuilderExpression = partitionByBuilderExpression;
//    }
//
//    public <TProperty> ColumnFunctionComparableAnyChainExpression<TProperty> orderBy(SQLActionExpression sqlActionExpression){
//        SQLFunction sqlFunction = partitionByBuilderExpression.orderBy(orderSelector -> {
//            entitySQLContext._orderBy(orderSelector, sqlActionExpression);
//        });
//        return new ColumnFunctionComparableAnyChainExpressionImpl<>(entitySQLContext,null,null, f->sqlFunction);
//    }
//}
