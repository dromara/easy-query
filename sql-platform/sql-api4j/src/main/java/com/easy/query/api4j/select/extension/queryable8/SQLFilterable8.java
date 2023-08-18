package com.easy.query.api4j.select.extension.queryable8;

import com.easy.query.api4j.select.Queryable8;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.api4j.sql.impl.SQLWherePredicateImpl;
import com.easy.query.core.common.tuple.Tuple8;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression8;

/**
 * create time 2023/8/18 09:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLFilterable8<T1, T2, T3, T4, T5, T6, T7, T8> extends ClientQueryable8Available<T1, T2, T3, T4, T5, T6, T7, T8>, Queryable8Available<T1, T2, T3, T4, T5, T6, T7, T8> {

    default Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> where(SQLExpression8<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>> whereExpression) {
        getClientQueryable8().where((wherePredicate1, wherePredicate2, wherePredicate3, wherePredicate4, wherePredicate5, wherePredicate6, wherePredicate7, wherePredicate8) -> {
            whereExpression.apply(new SQLWherePredicateImpl<>(wherePredicate1), new SQLWherePredicateImpl<>(wherePredicate2), new SQLWherePredicateImpl<>(wherePredicate3), new SQLWherePredicateImpl<>(wherePredicate4), new SQLWherePredicateImpl<>(wherePredicate5), new SQLWherePredicateImpl<>(wherePredicate6), new SQLWherePredicateImpl<>(wherePredicate7), new SQLWherePredicateImpl<>(wherePredicate8));
        });
        return getQueryable8();
    }

    default Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> where(boolean condition, SQLExpression8<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>> whereExpression) {
        getClientQueryable8().where(condition, (wherePredicate1, wherePredicate2, wherePredicate3, wherePredicate4, wherePredicate5, wherePredicate6, wherePredicate7, wherePredicate8) -> {
            whereExpression.apply(new SQLWherePredicateImpl<>(wherePredicate1), new SQLWherePredicateImpl<>(wherePredicate2), new SQLWherePredicateImpl<>(wherePredicate3), new SQLWherePredicateImpl<>(wherePredicate4), new SQLWherePredicateImpl<>(wherePredicate5), new SQLWherePredicateImpl<>(wherePredicate6), new SQLWherePredicateImpl<>(wherePredicate7), new SQLWherePredicateImpl<>(wherePredicate8));
        });
        return getQueryable8();
    }

    default Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> whereMerge(SQLExpression1<Tuple8<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>>> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> whereMerge(boolean condition, SQLExpression1<Tuple8<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>>> whereExpression) {
        return where(condition, (t1,t2, t3, t4, t5, t6, t7, t8) -> {
            whereExpression.apply(new Tuple8<>(t1,t2, t3, t4, t5, t6, t7, t8));
        });
    }
}