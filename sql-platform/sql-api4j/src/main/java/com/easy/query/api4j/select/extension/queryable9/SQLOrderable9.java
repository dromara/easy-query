package com.easy.query.api4j.select.extension.queryable9;

import com.easy.query.api4j.select.Queryable9;
import com.easy.query.api4j.sql.SQLOrderBySelector;
import com.easy.query.api4j.sql.impl.SQLOrderByColumnSelectorImpl;
import com.easy.query.core.common.tuple.Tuple9;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression9;

/**
 * create time 2023/8/18 09:48
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLOrderable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> extends ClientQueryable9Available<T1, T2, T3, T4, T5, T6, T7, T8, T9>, Queryable9Available<T1, T2, T3, T4, T5, T6, T7, T8, T9> {

    default Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByAsc(SQLExpression9<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>, SQLOrderBySelector<T7>, SQLOrderBySelector<T8>, SQLOrderBySelector<T9>> selectExpression) {
        getClientQueryable9().orderByAsc((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9) -> {
            selectExpression.apply(new SQLOrderByColumnSelectorImpl<>(selector1), new SQLOrderByColumnSelectorImpl<>(selector2), new SQLOrderByColumnSelectorImpl<>(selector3), new SQLOrderByColumnSelectorImpl<>(selector4), new SQLOrderByColumnSelectorImpl<>(selector5), new SQLOrderByColumnSelectorImpl<>(selector6), new SQLOrderByColumnSelectorImpl<>(selector7), new SQLOrderByColumnSelectorImpl<>(selector8), new SQLOrderByColumnSelectorImpl<>(selector9));
        });
        return getQueryable9();
    }

    default Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByAsc(boolean condition, SQLExpression9<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>, SQLOrderBySelector<T7>, SQLOrderBySelector<T8>, SQLOrderBySelector<T9>> selectExpression) {
        getClientQueryable9().orderByAsc(condition, (selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9) -> {
            selectExpression.apply(new SQLOrderByColumnSelectorImpl<>(selector1), new SQLOrderByColumnSelectorImpl<>(selector2), new SQLOrderByColumnSelectorImpl<>(selector3), new SQLOrderByColumnSelectorImpl<>(selector4), new SQLOrderByColumnSelectorImpl<>(selector5), new SQLOrderByColumnSelectorImpl<>(selector6), new SQLOrderByColumnSelectorImpl<>(selector7), new SQLOrderByColumnSelectorImpl<>(selector8), new SQLOrderByColumnSelectorImpl<>(selector9));
        });
        return getQueryable9();
    }

    default Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByAscMerge(SQLExpression1<Tuple9<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>, SQLOrderBySelector<T7>, SQLOrderBySelector<T8>, SQLOrderBySelector<T9>>> selectExpression) {
        return orderByAscMerge(true, selectExpression);
    }

    default Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByAscMerge(boolean condition, SQLExpression1<Tuple9<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>, SQLOrderBySelector<T7>, SQLOrderBySelector<T8>, SQLOrderBySelector<T9>>> selectExpression) {
        return orderByAsc(condition, (t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
            selectExpression.apply(new Tuple9<>(t, t1, t2, t3, t4, t5, t6, t7, t8));
        });
    }

    default Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByDesc(SQLExpression9<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>, SQLOrderBySelector<T7>, SQLOrderBySelector<T8>, SQLOrderBySelector<T9>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    default Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByDesc(boolean condition, SQLExpression9<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>, SQLOrderBySelector<T7>, SQLOrderBySelector<T8>, SQLOrderBySelector<T9>> selectExpression) {
        getClientQueryable9().orderByDesc(condition, (selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9) -> {
            selectExpression.apply(new SQLOrderByColumnSelectorImpl<>(selector1), new SQLOrderByColumnSelectorImpl<>(selector2), new SQLOrderByColumnSelectorImpl<>(selector3), new SQLOrderByColumnSelectorImpl<>(selector4), new SQLOrderByColumnSelectorImpl<>(selector5), new SQLOrderByColumnSelectorImpl<>(selector6), new SQLOrderByColumnSelectorImpl<>(selector7), new SQLOrderByColumnSelectorImpl<>(selector8), new SQLOrderByColumnSelectorImpl<>(selector9));
        });
        return getQueryable9();
    }

    default Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByDescMerge(SQLExpression1<Tuple9<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>, SQLOrderBySelector<T7>, SQLOrderBySelector<T8>, SQLOrderBySelector<T9>>> selectExpression) {
        return orderByDescMerge(true, selectExpression);
    }

    default Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByDescMerge(boolean condition, SQLExpression1<Tuple9<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>, SQLOrderBySelector<T7>, SQLOrderBySelector<T8>, SQLOrderBySelector<T9>>> selectExpression) {
        return orderByDesc(condition, (t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
            selectExpression.apply(new Tuple9<>(t, t1, t2, t3, t4, t5, t6, t7, t8));
        });
    }

}

