package com.easy.query.api4j.select.extension.queryable5;

import com.easy.query.api4j.select.Queryable5;
import com.easy.query.api4j.sql.SQLOrderBySelector;
import com.easy.query.api4j.sql.impl.SQLOrderByColumnSelectorImpl;
import com.easy.query.core.common.tuple.Tuple5;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression5;

/**
 * create time 2023/8/18 09:43
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLOrderable5<T1, T2, T3, T4, T5> extends ClientQueryable5Available<T1, T2, T3, T4, T5>, Queryable5Available<T1, T2, T3, T4, T5> {

    default Queryable5<T1, T2, T3, T4, T5> orderByAsc(SQLExpression5<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>> selectExpression) {
        getClientQueryable5().orderByAsc((selector1, selector2, selector3, selector4, selector5) -> {
            selectExpression.apply(new SQLOrderByColumnSelectorImpl<>(selector1), new SQLOrderByColumnSelectorImpl<>(selector2), new SQLOrderByColumnSelectorImpl<>(selector3), new SQLOrderByColumnSelectorImpl<>(selector4), new SQLOrderByColumnSelectorImpl<>(selector5));
        });
        return getQueryable5();
    }

    default Queryable5<T1, T2, T3, T4, T5> orderByAsc(boolean condition, SQLExpression5<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>> selectExpression) {
        getClientQueryable5().orderByAsc(condition, (selector1, selector2, selector3, selector4, selector5) -> {
            selectExpression.apply(new SQLOrderByColumnSelectorImpl<>(selector1), new SQLOrderByColumnSelectorImpl<>(selector2), new SQLOrderByColumnSelectorImpl<>(selector3), new SQLOrderByColumnSelectorImpl<>(selector4), new SQLOrderByColumnSelectorImpl<>(selector5));
        });
        return getQueryable5();
    }

    default Queryable5<T1, T2, T3, T4, T5> orderByAscMerge(SQLExpression1<Tuple5<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>>> selectExpression) {
        return orderByAscMerge(true, selectExpression);
    }

    default Queryable5<T1, T2, T3, T4, T5> orderByAscMerge(boolean condition, SQLExpression1<Tuple5<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>>> selectExpression) {
        return orderByAsc(condition, (t1, t2, t3, t4, t5) -> {
            selectExpression.apply(new Tuple5<>(t1, t2, t3, t4, t5));
        });
    }

    default Queryable5<T1, T2, T3, T4, T5> orderByDesc(SQLExpression5<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    default Queryable5<T1, T2, T3, T4, T5> orderByDesc(boolean condition, SQLExpression5<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>> selectExpression) {
        getClientQueryable5().orderByDesc(condition, (selector1, selector2, selector3, selector4, selector5) -> {
            selectExpression.apply(new SQLOrderByColumnSelectorImpl<>(selector1), new SQLOrderByColumnSelectorImpl<>(selector2), new SQLOrderByColumnSelectorImpl<>(selector3), new SQLOrderByColumnSelectorImpl<>(selector4), new SQLOrderByColumnSelectorImpl<>(selector5));
        });
        return getQueryable5();
    }

    default Queryable5<T1, T2, T3, T4, T5> orderByDescMerge(SQLExpression1<Tuple5<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>>> selectExpression) {
        return orderByDescMerge(true, selectExpression);
    }

    default Queryable5<T1, T2, T3, T4, T5> orderByDescMerge(boolean condition, SQLExpression1<Tuple5<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>>> selectExpression) {
        return orderByDesc(condition, (t1, t2, t3, t4, t5) -> {
            selectExpression.apply(new Tuple5<>(t1, t2, t3, t4, t5));
        });
    }
}
