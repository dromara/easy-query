package com.easy.query.core.proxy.sql;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.SQLPredicate;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContext;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContextImpl;
import com.easy.query.core.util.EasyArrayUtil;

/**
 * create time 2023/12/1 22:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class PredicateExpression {
    public static SQLPredicate and(SQLPredicate... sqlPredicates) {
        return and(true, sqlPredicates);
    }

    public static SQLPredicate and(boolean condition, SQLPredicate... sqlPredicates) {
        if (condition && EasyArrayUtil.isNotEmpty(sqlPredicates)) {
            SQLPredicate firstSQLPredicate = sqlPredicates[0];
            for (int i = 1; i < sqlPredicates.length; i++) {
                firstSQLPredicate = firstSQLPredicate.and(sqlPredicates[i]);
            }
            return firstSQLPredicate;
        }
        return SQLPredicate.empty;
    }

    public static SQLPredicate or(SQLPredicate... sqlPredicates) {
        return or(true, sqlPredicates);
    }

    public static SQLPredicate or(boolean condition, SQLPredicate... sqlPredicates) {
        if (condition && EasyArrayUtil.isNotEmpty(sqlPredicates)) {
            SQLPredicate firstSQLPredicate = sqlPredicates[0];
            for (int i = 1; i < sqlPredicates.length; i++) {
                firstSQLPredicate = firstSQLPredicate.or(sqlPredicates[i]);
            }
            return firstSQLPredicate;
        }
        return SQLPredicate.empty;
    }

    public static SQLPredicate sql(String sqlSegment) {
        return sql(true, sqlSegment, f -> {
        });
    }


    public static SQLPredicate sql(boolean condition, String sqlSegment) {
        if (condition) {
            return sql(true, sqlSegment, f -> {
            });
        }
        return SQLPredicate.empty;
    }

    public static SQLPredicate sql(String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume) {
        return sql(true,sqlSegment,contextConsume);
    }

    public static SQLPredicate sql(boolean condition, String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume) {
        if (condition) {
            return new SQLPredicateImpl(f -> {
                f.sqlNativeSegment(sqlSegment, c -> {
                    contextConsume.apply(new SQLNativeProxyExpressionContextImpl(c));
                });
            });
        }
        return SQLPredicate.empty;
    }
}
