//package com.easy.query.core.proxy.sql;
//
//import com.easy.query.core.expression.lambda.SQLExpression1;
//import com.easy.query.core.proxy.SQLOrderByExpression;
//import com.easy.query.core.proxy.impl.SQLOrderSelectImpl;
//import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContext;
//import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContextImpl;
//import com.easy.query.core.util.EasyArrayUtil;
//
///**
// * create time 2023/12/2 17:02
// * 文件说明
// *
// * @author xuejiaming
// */
//public class OrderBy {
//    public static SQLOrderByExpression of(SQLOrderByExpression... orderByExpressions){
//        if(EasyArrayUtil.isNotEmpty(orderByExpressions)){
//            SQLOrderByExpression firstOrderByExpression = orderByExpressions[0];
//            for (int i = 1; i < orderByExpressions.length; i++) {
//                firstOrderByExpression = firstOrderByExpression.thenBy(orderByExpressions[i]);
//            }
//            return firstOrderByExpression;
//        }
//        return SQLOrderByExpression.empty;
//    }
//    public static SQLOrderByExpression sql(String sqlSegment) {
//        return sql(true, sqlSegment, f -> {
//        });
//    }
//
//
//    public static SQLOrderByExpression sql(boolean condition, String sqlSegment) {
//        if (condition) {
//            return sql(true, sqlSegment, f -> {
//            });
//        }
//        return SQLOrderByExpression.empty;
//    }
//
//    public static SQLOrderByExpression sql(String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume) {
//        return sql(true,sqlSegment,contextConsume);
//    }
//
//    public static SQLOrderByExpression sql(boolean condition, String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume) {
//        if (condition) {
//            return new SQLOrderSelectImpl(f -> {
//                f.sqlNativeSegment(sqlSegment, c -> {
//                    contextConsume.apply(new SQLNativeProxyExpressionContextImpl(c));
//                });
//            });
//        }
//        return SQLOrderByExpression.empty;
//    }
//}
