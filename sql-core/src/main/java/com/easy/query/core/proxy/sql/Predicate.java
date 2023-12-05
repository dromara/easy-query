package com.easy.query.core.proxy.sql;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.SQLAggregatePredicateExpression;
import com.easy.query.core.proxy.SQLPredicateExpression;
import com.easy.query.core.proxy.impl.SQLAggregatePredicateImpl;
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
public class Predicate {
    public static SQLPredicateExpression and(SQLPredicateExpression... sqlPredicates) {
        return and(true, sqlPredicates);
    }

    public static SQLPredicateExpression and(boolean condition, SQLPredicateExpression... sqlPredicates) {
        if (condition && EasyArrayUtil.isNotEmpty(sqlPredicates)) {
            SQLPredicateExpression firstSQLPredicate = sqlPredicates[0];
            for (int i = 1; i < sqlPredicates.length; i++) {
                firstSQLPredicate = firstSQLPredicate.and(sqlPredicates[i]);
            }
            return firstSQLPredicate;
        }
        return SQLPredicateExpression.empty;
    }

    public static SQLPredicateExpression or(SQLPredicateExpression... sqlPredicates) {
        return or(true, sqlPredicates);
    }

    public static SQLPredicateExpression or(boolean condition, SQLPredicateExpression... sqlPredicates) {
        if (condition && EasyArrayUtil.isNotEmpty(sqlPredicates)) {
            SQLPredicateExpression firstSQLPredicate = sqlPredicates[0];
            for (int i = 1; i < sqlPredicates.length; i++) {
                firstSQLPredicate = firstSQLPredicate.or(sqlPredicates[i]);
            }
            return firstSQLPredicate;
        }
        return SQLPredicateExpression.empty;
    }


    public static SQLAggregatePredicateExpression and(SQLAggregatePredicateExpression... sqlPredicates) {
        return and(true, sqlPredicates);
    }

    public static SQLAggregatePredicateExpression and(boolean condition, SQLAggregatePredicateExpression... sqlPredicates) {
        if (condition && EasyArrayUtil.isNotEmpty(sqlPredicates)) {
            SQLAggregatePredicateExpression firstSQLPredicate = sqlPredicates[0];
            for (int i = 1; i < sqlPredicates.length; i++) {
                firstSQLPredicate = firstSQLPredicate.and(sqlPredicates[i]);
            }
            return firstSQLPredicate;
        }
        return SQLAggregatePredicateExpression.empty;
    }

    public static SQLAggregatePredicateExpression or(SQLAggregatePredicateExpression... sqlPredicates) {
        return or(true, sqlPredicates);
    }

    public static SQLAggregatePredicateExpression or(boolean condition, SQLAggregatePredicateExpression... sqlPredicates) {
        if (condition && EasyArrayUtil.isNotEmpty(sqlPredicates)) {
            SQLAggregatePredicateExpression firstSQLPredicate = sqlPredicates[0];
            for (int i = 1; i < sqlPredicates.length; i++) {
                firstSQLPredicate = firstSQLPredicate.or(sqlPredicates[i]);
            }
            return firstSQLPredicate;
        }
        return SQLAggregatePredicateExpression.empty;
    }

    public static SQLPredicateExpression sql(String sqlSegment) {
        return sql(true, sqlSegment, f -> {
        });
    }


    public static SQLPredicateExpression sql(boolean condition, String sqlSegment) {
        if (condition) {
            return sql(true, sqlSegment, f -> {
            });
        }
        return SQLPredicateExpression.empty;
    }

    public static SQLPredicateExpression sql(String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume) {
        return sql(true,sqlSegment,contextConsume);
    }

    public static SQLPredicateExpression sql(boolean condition, String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume) {
        if (condition) {
            return new SQLPredicateImpl(f -> {
                f.sqlNativeSegment(sqlSegment, c -> {
                    contextConsume.apply(new SQLNativeProxyExpressionContextImpl(c));
                });
            });
        }
        return SQLPredicateExpression.empty;
    }

    public static class Aggregate{

        public static SQLAggregatePredicateExpression sql(String sqlSegment) {
            return sql(true, sqlSegment, f -> {
            });
        }


        public static SQLAggregatePredicateExpression sql(boolean condition, String sqlSegment) {
            if (condition) {
                return sql(true, sqlSegment, f -> {
                });
            }
            return SQLAggregatePredicateExpression.empty;
        }

        public static SQLAggregatePredicateExpression sql(String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume) {
            return sql(true,sqlSegment,contextConsume);
        }

        public static SQLAggregatePredicateExpression sql(boolean condition, String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume) {
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
            return SQLAggregatePredicateExpression.empty;
        }
    }
}
