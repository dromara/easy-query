//package com.easy.query.core.proxy.grouping;
//
//import com.easy.query.core.expression.lambda.SQLExpression1;
//import com.easy.query.core.expression.lambda.SQLFuncExpression1;
//import com.easy.query.core.proxy.PropTypeColumn;
//import com.easy.query.core.proxy.ProxyEntity;
//import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
//import com.easy.query.core.proxy.extension.functions.ColumnNumberFunctionAvailable;
//import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableAnyChainExpression;
//import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableNumberChainExpression;
//import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableStringChainExpression;
//
//import java.math.BigDecimal;
//
///**
// * create time 2025/3/4 22:01
// * 文件说明
// *
// * @author xuejiaming
// */
//public interface SQLSingleGroupQueryable<TSourceProxy> extends SQLGroupQueryable<TSourceProxy> {
//
//    default SQLSingleGroupQueryable<TSourceProxy> orderBy(SQLExpression1<TSourceProxy> orderExpression) {
//        return orderBy(true, orderExpression);
//    }
//
//    SQLSingleGroupQueryable<TSourceProxy> orderBy(boolean condition, SQLExpression1<TSourceProxy> orderExpression);
//
//    TSourceProxy element(int index);
//}
