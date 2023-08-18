package com.easy.query.api4j.select.extension.queryable5;

import com.easy.query.api4j.select.Queryable5;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.api4j.sql.impl.SQLWherePredicateImpl;
import com.easy.query.core.common.tuple.Tuple5;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression5;

/**
 * create time 2023/8/18 09:21
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLFilterable5<T1, T2, T3, T4, T5> extends ClientQueryable5Available<T1, T2, T3, T4, T5>, Queryable5Available<T1, T2, T3, T4, T5> {

    default Queryable5<T1, T2, T3, T4, T5> where(SQLExpression5<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>> whereExpression) {
        getClientQueryable5().where((wherePredicate1, wherePredicate2, wherePredicate3, wherePredicate4, wherePredicate5) -> {
            whereExpression.apply(new SQLWherePredicateImpl<>(wherePredicate1), new SQLWherePredicateImpl<>(wherePredicate2), new SQLWherePredicateImpl<>(wherePredicate3), new SQLWherePredicateImpl<>(wherePredicate4), new SQLWherePredicateImpl<>(wherePredicate5));
        });
        return getQueryable5();
    }

    default Queryable5<T1, T2, T3, T4, T5> where(boolean condition, SQLExpression5<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>> whereExpression) {
        getClientQueryable5().where(condition, (wherePredicate1, wherePredicate2, wherePredicate3, wherePredicate4, wherePredicate5) -> {
            whereExpression.apply(new SQLWherePredicateImpl<>(wherePredicate1), new SQLWherePredicateImpl<>(wherePredicate2), new SQLWherePredicateImpl<>(wherePredicate3), new SQLWherePredicateImpl<>(wherePredicate4), new SQLWherePredicateImpl<>(wherePredicate5));
        });
        return getQueryable5();
    }

    default Queryable5<T1, T2, T3, T4, T5> whereMerge(SQLExpression1<Tuple5<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>>> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default Queryable5<T1, T2, T3, T4, T5> whereMerge(boolean condition, SQLExpression1<Tuple5<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>>> whereExpression) {
        return where(condition, (t, t1, t3, t4, t5) -> {
            whereExpression.apply(new Tuple5<>(t, t1, t3, t4, t5));
        });
    }
}
