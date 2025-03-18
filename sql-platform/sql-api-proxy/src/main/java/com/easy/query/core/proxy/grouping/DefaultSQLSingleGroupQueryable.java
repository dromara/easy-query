//package com.easy.query.core.proxy.grouping;
//
//import com.easy.query.api.proxy.extension.casewhen.CaseWhenEntityBuilder;
//import com.easy.query.core.expression.lambda.SQLExpression1;
//import com.easy.query.core.expression.lambda.SQLFuncExpression1;
//import com.easy.query.core.proxy.PropTypeColumn;
//import com.easy.query.core.proxy.core.EntitySQLContext;
//import com.easy.query.core.proxy.extension.functions.ColumnNumberFunctionAvailable;
//import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableAnyChainExpression;
//import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableNumberChainExpression;
//import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableStringChainExpression;
//import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableAnyChainExpressionImpl;
//import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableNumberChainExpressionImpl;
//import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableStringChainExpressionImpl;
//import com.easy.query.core.util.EasyObjectUtil;
//
//import java.math.BigDecimal;
//
///**
// * create time 2025/3/5 21:20
// * 文件说明
// *
// * @author xuejiaming
// */
//public class DefaultSQLSingleGroupQueryable<TProxy> extends DefaultSQLGroupQueryable<TProxy> implements SQLSingleGroupQueryable<TProxy> {
//
//    public DefaultSQLSingleGroupQueryable(TProxy groupTable, EntitySQLContext entitySQLContext, SQLExpression1<TProxy> predicate) {
//        super(groupTable,entitySQLContext,predicate);
//    }
//
//    @Override
//    public SQLSingleGroupQueryable<TProxy> orderBy(boolean condition, SQLExpression1<TProxy> orderExpression) {
//        return null;
//    }
//
//    @Override
//    public TProxy element(int index) {
//        return null;
//    }
//}
