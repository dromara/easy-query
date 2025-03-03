//package com.easy.query.core.proxy.sql.include;
//
//import com.easy.query.core.expression.lambda.SQLExpression1;
//
///**
// * create time 2025/3/3 21:28
// * 文件说明
// *
// * @author xuejiaming
// */
//public class IncludeMany {
//
//    public static IncludeExpression of(IncludeManyAvailable... includeExpressions) {
//        IncludeExpression includeExpression = new IncludeExpression();
//        for (IncludeManyAvailable includeManyAvailable : includeExpressions) {
//            if (includeManyAvailable != null) {
//                includeExpression.append(includeManyAvailable);
//            }
//        }
//        return includeExpression;
//    }
//
//    public static IncludeManyAvailable includeIf(boolean condition, IncludeManyAvailable includeManyAvailable) {
//        if (!condition) {
//            return null;
//        }
//        return includeManyAvailable;
//    }
//
//    public static <T extends IncludeManyAvailable> IncludeManyAvailable includeWhere(T includeManyAvailable, SQLExpression1<T> whereExpression) {
//        return includeWhere(true,includeManyAvailable, whereExpression);
//    }
//    public static <T extends IncludeManyAvailable> IncludeManyAvailable includeWhere(boolean condition, T includeManyAvailable, SQLExpression1<T> whereExpression) {
//        if (!condition) {
//            return null;
//        }
//        return new DefaultIncludeManyFilterAvailable(includeManyAvailable, whereExpression);
//    }
//}
