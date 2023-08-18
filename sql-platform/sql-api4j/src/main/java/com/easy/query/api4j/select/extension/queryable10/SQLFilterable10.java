package com.easy.query.api4j.select.extension.queryable10;

import com.easy.query.api4j.select.Queryable10;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.api4j.sql.impl.SQLWherePredicateImpl;
import com.easy.query.core.common.tuple.Tuple10;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression10;

/**
 * create time 2023/8/18 09:27
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLFilterable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> extends ClientQueryable10Available<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, Queryable10Available<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> {

    default Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> where(SQLExpression10<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>, SQLWherePredicate<T9>, SQLWherePredicate<T10>> whereExpression) {
        getClientQueryable10().where((wherePredicate1, wherePredicate2, wherePredicate3, wherePredicate4, wherePredicate5, wherePredicate6, wherePredicate7, wherePredicate8, wherePredicate9, wherePredicate10) -> {
            whereExpression.apply(new SQLWherePredicateImpl<>(wherePredicate1), new SQLWherePredicateImpl<>(wherePredicate2), new SQLWherePredicateImpl<>(wherePredicate3), new SQLWherePredicateImpl<>(wherePredicate4), new SQLWherePredicateImpl<>(wherePredicate5), new SQLWherePredicateImpl<>(wherePredicate6), new SQLWherePredicateImpl<>(wherePredicate7), new SQLWherePredicateImpl<>(wherePredicate8), new SQLWherePredicateImpl<>(wherePredicate9), new SQLWherePredicateImpl<>(wherePredicate10));
        });
        return getQueryable10();
    }

    default Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> where(boolean condition, SQLExpression10<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>, SQLWherePredicate<T9>, SQLWherePredicate<T10>> whereExpression) {
        getClientQueryable10().where(condition, (wherePredicate1, wherePredicate2, wherePredicate3, wherePredicate4, wherePredicate5, wherePredicate6, wherePredicate7, wherePredicate8, wherePredicate9, wherePredicate10) -> {
            whereExpression.apply(new SQLWherePredicateImpl<>(wherePredicate1), new SQLWherePredicateImpl<>(wherePredicate2), new SQLWherePredicateImpl<>(wherePredicate3), new SQLWherePredicateImpl<>(wherePredicate4), new SQLWherePredicateImpl<>(wherePredicate5), new SQLWherePredicateImpl<>(wherePredicate6), new SQLWherePredicateImpl<>(wherePredicate7), new SQLWherePredicateImpl<>(wherePredicate8), new SQLWherePredicateImpl<>(wherePredicate9), new SQLWherePredicateImpl<>(wherePredicate10));
        });
        return getQueryable10();
    }

    default Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> whereMerge(SQLExpression1<Tuple10<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>, SQLWherePredicate<T9>, SQLWherePredicate<T10>>> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> whereMerge(boolean condition, SQLExpression1<Tuple10<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>, SQLWherePredicate<T5>, SQLWherePredicate<T6>, SQLWherePredicate<T7>, SQLWherePredicate<T8>, SQLWherePredicate<T9>, SQLWherePredicate<T10>>> whereExpression) {
        return where(condition, (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) -> {
            whereExpression.apply(new Tuple10<>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
        });
    }
}
