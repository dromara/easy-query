package com.easy.query.api4j.select.extension.queryable9;

import com.easy.query.api4j.select.Queryable9;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.api4j.sql.impl.SQLWherePredicateImpl;
import com.easy.query.core.common.tuple.Tuple9;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression9;

/**
 * create time 2023/8/18 09:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLFilterable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> extends ClientQueryable9Available<T1, T2, T3, T4, T5, T6, T7, T8, T9>, Queryable9Available<T1, T2, T3, T4, T5, T6, T7, T8, T9> {

    default Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> where(SQLExpression9<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>, SQLWherePredicate<T9>> whereExpression) {
        getClientQueryable9().where((wherePredicate1, wherePredicate2, wherePredicate3, wherePredicate4, wherePredicate5, wherePredicate6, wherePredicate7, wherePredicate8, wherePredicate9) -> {
            whereExpression.apply(new SQLWherePredicateImpl<>(wherePredicate1), new SQLWherePredicateImpl<>(wherePredicate2), new SQLWherePredicateImpl<>(wherePredicate3), new SQLWherePredicateImpl<>(wherePredicate4), new SQLWherePredicateImpl<>(wherePredicate5), new SQLWherePredicateImpl<>(wherePredicate6), new SQLWherePredicateImpl<>(wherePredicate7), new SQLWherePredicateImpl<>(wherePredicate8), new SQLWherePredicateImpl<>(wherePredicate9));
        });
        return getQueryable9();
    }

    default Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> where(boolean condition, SQLExpression9<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>, SQLWherePredicate<T9>> whereExpression) {
        getClientQueryable9().where(condition, (wherePredicate1, wherePredicate2, wherePredicate3, wherePredicate4, wherePredicate5, wherePredicate6, wherePredicate7, wherePredicate8, wherePredicate9) -> {
            whereExpression.apply(new SQLWherePredicateImpl<>(wherePredicate1), new SQLWherePredicateImpl<>(wherePredicate2), new SQLWherePredicateImpl<>(wherePredicate3), new SQLWherePredicateImpl<>(wherePredicate4), new SQLWherePredicateImpl<>(wherePredicate5), new SQLWherePredicateImpl<>(wherePredicate6), new SQLWherePredicateImpl<>(wherePredicate7), new SQLWherePredicateImpl<>(wherePredicate8), new SQLWherePredicateImpl<>(wherePredicate9));
        });
        return getQueryable9();
    }

    default Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> whereMerge(SQLExpression1<Tuple9<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>, SQLWherePredicate<T9>>> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> whereMerge(boolean condition, SQLExpression1<Tuple9<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>, SQLWherePredicate<T9>>> whereExpression) {
        return where(condition, (t, t1, t3, t4, t5, t6, t7, t8, t9) -> {
            whereExpression.apply(new Tuple9<>(t, t1, t3, t4, t5, t6, t7, t8, t9));
        });
    }
}
