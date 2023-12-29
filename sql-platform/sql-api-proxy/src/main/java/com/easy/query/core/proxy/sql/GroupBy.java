//package com.easy.query.core.proxy.sql;
//
///**
// * create time 2023/12/2 17:02
// * 文件说明
// *
// * @author xuejiaming
// */
//public class GroupBy {
////    public static SQLGroupByExpression sql(String sqlSegment) {
////        return sql(true, sqlSegment, f -> {
////        });
////    }
////
////
////    public static SQLGroupByExpression sql(boolean condition, String sqlSegment) {
////        if (condition) {
////            return sql(true, sqlSegment, f -> {
////            });
////        }
////        return SQLGroupByExpression.empty;
////    }
////
////    public static SQLGroupByExpression sql(String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume) {
////        return sql(true,sqlSegment,contextConsume);
////    }
////
////    public static SQLGroupByExpression sql(boolean condition, String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume) {
////        if (condition) {
////            return new SQLGroupSelectImpl(f -> {
////                f.sqlNativeSegment(sqlSegment, c -> {
////                    contextConsume.apply(new SQLNativeProxyExpressionContextImpl(c));
////                });
////            });
////        }
////        return SQLGroupByExpression.empty;
////    }
//}
