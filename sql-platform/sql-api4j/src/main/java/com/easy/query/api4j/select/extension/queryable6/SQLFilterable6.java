package com.easy.query.api4j.select.extension.queryable6;

import com.easy.query.api4j.select.Queryable6;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.api4j.sql.impl.SQLWherePredicateImpl;
import com.easy.query.core.common.tuple.Tuple6;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression6;

/**
 * create time 2023/8/18 09:25
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLFilterable6<T1, T2, T3, T4, T5, T6> extends ClientQueryable6Available<T1, T2, T3, T4, T5, T6>, Queryable6Available<T1, T2, T3, T4, T5, T6> {

    default Queryable6<T1, T2, T3, T4, T5, T6> where(SQLExpression6<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>> whereExpression) {
        getClientQueryable6().where((wherePredicate1, wherePredicate2, wherePredicate3, wherePredicate4, wherePredicate5, wherePredicate6) -> {
            whereExpression.apply(new SQLWherePredicateImpl<>(wherePredicate1), new SQLWherePredicateImpl<>(wherePredicate2), new SQLWherePredicateImpl<>(wherePredicate3), new SQLWherePredicateImpl<>(wherePredicate4), new SQLWherePredicateImpl<>(wherePredicate5), new SQLWherePredicateImpl<>(wherePredicate6));
        });
        return getQueryable6();
    }

    default Queryable6<T1, T2, T3, T4, T5, T6> where(boolean condition, SQLExpression6<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>> whereExpression) {
        getClientQueryable6().where(condition, (wherePredicate1, wherePredicate2, wherePredicate3, wherePredicate4, wherePredicate5, wherePredicate6) -> {
            whereExpression.apply(new SQLWherePredicateImpl<>(wherePredicate1), new SQLWherePredicateImpl<>(wherePredicate2), new SQLWherePredicateImpl<>(wherePredicate3), new SQLWherePredicateImpl<>(wherePredicate4), new SQLWherePredicateImpl<>(wherePredicate5), new SQLWherePredicateImpl<>(wherePredicate6));
        });
        return getQueryable6();
    }

    default Queryable6<T1, T2, T3, T4, T5, T6> whereMerge(SQLExpression1<Tuple6<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>>> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default Queryable6<T1, T2, T3, T4, T5, T6> whereMerge(boolean condition, SQLExpression1<Tuple6<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>>> whereExpression) {
        return where(condition, (t1, t2, t3, t4, t5, t6) -> {
            whereExpression.apply(new Tuple6<>(t1, t2, t3, t4, t5, t6));
        });
    }
}