package com.easy.query.core.proxy.sql;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.SQLAggregatePredicate;
import com.easy.query.core.proxy.impl.SQLAggregatePredicateImpl;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContext;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContextImpl;
import com.easy.query.core.util.EasyArrayUtil;

/**
 * create time 2023/12/1 22:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class HavingExpression {
    public static SQLAggregatePredicate and(SQLAggregatePredicate... sqlPredicates) {
        return and(true, sqlPredicates);
    }

    public static SQLAggregatePredicate and(boolean condition, SQLAggregatePredicate... sqlPredicates) {
        if (condition && EasyArrayUtil.isNotEmpty(sqlPredicates)) {
            SQLAggregatePredicate firstSQLPredicate = sqlPredicates[0];
            for (int i = 1; i < sqlPredicates.length; i++) {
                firstSQLPredicate = firstSQLPredicate.and(sqlPredicates[i]);
            }
            return firstSQLPredicate;
        }
        return SQLAggregatePredicate.empty;
    }

    public static SQLAggregatePredicate or(SQLAggregatePredicate... sqlPredicates) {
        return or(true, sqlPredicates);
    }

    public static SQLAggregatePredicate or(boolean condition, SQLAggregatePredicate... sqlPredicates) {
        if (condition && EasyArrayUtil.isNotEmpty(sqlPredicates)) {
            SQLAggregatePredicate firstSQLPredicate = sqlPredicates[0];
            for (int i = 1; i < sqlPredicates.length; i++) {
                firstSQLPredicate = firstSQLPredicate.or(sqlPredicates[i]);
            }
            return firstSQLPredicate;
        }
        return SQLAggregatePredicate.empty;
    }

    public static SQLAggregatePredicate sql(String sqlSegment) {
        return sql(true, sqlSegment, f -> {
        });
    }


    public static SQLAggregatePredicate sql(boolean condition, String sqlSegment) {
        if (condition) {
            return sql(true, sqlSegment, f -> {
            });
        }
        return SQLAggregatePredicate.empty;
    }

    public static SQLAggregatePredicate sql(String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume) {
        return sql(true,sqlSegment,contextConsume);
    }

    public static SQLAggregatePredicate sql(boolean condition, String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume) {
        if (condition) {
            return new SQLAggregatePredicateImpl(f -> {
                f.sqlNativeSegment(sqlSegment, c -> {
                    contextConsume.apply(new SQLNativeProxyExpressionContextImpl(c));
                });
            },f->{
                f.sqlNativeSegment(sqlSegment, c -> {
                    contextConsume.apply(new SQLNativeProxyExpressionContextImpl(c));
                });
            });
        }
        return SQLAggregatePredicate.empty;
    }
}
