package com.easy.query.api4j.select.extension.queryable7;

import com.easy.query.api4j.select.Queryable7;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.api4j.sql.impl.SQLWherePredicateImpl;
import com.easy.query.core.common.tuple.Tuple7;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression7;

/**
 * create time 2023/8/18 09:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLFilterable7<T1, T2, T3, T4, T5, T6, T7> extends ClientQueryable7Available<T1, T2, T3, T4, T5, T6, T7>, Queryable7Available<T1, T2, T3, T4, T5, T6, T7> {

    default Queryable7<T1, T2, T3, T4, T5, T6, T7> where(SQLExpression7<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>> whereExpression) {
        getClientQueryable7().where((wherePredicate1, wherePredicate2, wherePredicate3, wherePredicate4, wherePredicate5, wherePredicate6, wherePredicate7) -> {
            whereExpression.apply(new SQLWherePredicateImpl<>(wherePredicate1), new SQLWherePredicateImpl<>(wherePredicate2), new SQLWherePredicateImpl<>(wherePredicate3), new SQLWherePredicateImpl<>(wherePredicate4), new SQLWherePredicateImpl<>(wherePredicate5), new SQLWherePredicateImpl<>(wherePredicate6), new SQLWherePredicateImpl<>(wherePredicate7));
        });
        return getQueryable7();
    }

    default Queryable7<T1, T2, T3, T4, T5, T6, T7> where(boolean condition, SQLExpression7<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>> whereExpression) {
        getClientQueryable7().where(condition, (wherePredicate1, wherePredicate2, wherePredicate3, wherePredicate4, wherePredicate5, wherePredicate6, wherePredicate7) -> {
            whereExpression.apply(new SQLWherePredicateImpl<>(wherePredicate1), new SQLWherePredicateImpl<>(wherePredicate2), new SQLWherePredicateImpl<>(wherePredicate3), new SQLWherePredicateImpl<>(wherePredicate4), new SQLWherePredicateImpl<>(wherePredicate5), new SQLWherePredicateImpl<>(wherePredicate6), new SQLWherePredicateImpl<>(wherePredicate7));
        });
        return getQueryable7();
    }

    default Queryable7<T1, T2, T3, T4, T5, T6, T7> whereMerge(SQLExpression1<Tuple7<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>>> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default Queryable7<T1, T2, T3, T4, T5, T6, T7> whereMerge(boolean condition, SQLExpression1<Tuple7<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>>> whereExpression) {
        return where(condition, (t, t1, t3, t4, t5, t6, t7) -> {
            whereExpression.apply(new Tuple7<>(t, t1, t3, t4, t5, t6, t7));
        });
    }
}
