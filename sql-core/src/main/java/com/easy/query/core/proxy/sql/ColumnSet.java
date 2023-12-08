//package com.easy.query.core.proxy.sql;
//
//import com.easy.query.core.proxy.SQLColumnSetExpression;
//import com.easy.query.core.util.EasyArrayUtil;
//
///**
// * create time 2023/12/8 10:58
// * 文件说明
// *
// * @author xuejiaming
// */
//public class ColumnSet {
//    public static SQLColumnSetExpression setValues(SQLColumnSetExpression... columnSetExpressions) {
//        return setValues(true, columnSetExpressions);
//    }
//
//    public static SQLColumnSetExpression setValues(boolean condition, SQLColumnSetExpression... columnSetExpressions) {
//        if (condition && EasyArrayUtil.isNotEmpty(columnSetExpressions)) {
//            SQLColumnSetExpression firstSQLColumnSet = columnSetExpressions[0];
//            for (int i = 1; i < columnSetExpressions.length; i++) {
//                firstSQLColumnSet = firstSQLColumnSet.then(columnSetExpressions[i]);
//            }
//            return firstSQLColumnSet;
//        }
//        return SQLColumnSetExpression.empty;
//    }
//}
