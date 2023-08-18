package com.easy.query.api4j.select.extension.queryable10;

import com.easy.query.api4j.select.Queryable10;
import com.easy.query.api4j.sql.SQLOrderBySelector;
import com.easy.query.api4j.sql.impl.SQLOrderByColumnSelectorImpl;
import com.easy.query.core.common.tuple.Tuple10;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression10;

/**
 * create time 2023/8/18 09:48
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLOrderable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> extends ClientQueryable10Available<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, Queryable10Available<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> {

    default Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> orderByAsc(SQLExpression10<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>, SQLOrderBySelector<T7>, SQLOrderBySelector<T8>, SQLOrderBySelector<T9>, SQLOrderBySelector<T10>> selectExpression) {
        getClientQueryable10().orderByAsc((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9, selector10) -> {
            selectExpression.apply(new SQLOrderByColumnSelectorImpl<>(selector1), new SQLOrderByColumnSelectorImpl<>(selector2), new SQLOrderByColumnSelectorImpl<>(selector3), new SQLOrderByColumnSelectorImpl<>(selector4), new SQLOrderByColumnSelectorImpl<>(selector5), new SQLOrderByColumnSelectorImpl<>(selector6), new SQLOrderByColumnSelectorImpl<>(selector7), new SQLOrderByColumnSelectorImpl<>(selector8), new SQLOrderByColumnSelectorImpl<>(selector9), new SQLOrderByColumnSelectorImpl<>(selector10));
        });
        return getQueryable10();
    }

    default Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> orderByAsc(boolean condition, SQLExpression10<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>, SQLOrderBySelector<T7>, SQLOrderBySelector<T8>, SQLOrderBySelector<T9>, SQLOrderBySelector<T10>> selectExpression) {
        getClientQueryable10().orderByAsc(condition, (selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9, selector10) -> {
            selectExpression.apply(new SQLOrderByColumnSelectorImpl<>(selector1), new SQLOrderByColumnSelectorImpl<>(selector2), new SQLOrderByColumnSelectorImpl<>(selector3), new SQLOrderByColumnSelectorImpl<>(selector4), new SQLOrderByColumnSelectorImpl<>(selector5), new SQLOrderByColumnSelectorImpl<>(selector6), new SQLOrderByColumnSelectorImpl<>(selector7), new SQLOrderByColumnSelectorImpl<>(selector8), new SQLOrderByColumnSelectorImpl<>(selector9), new SQLOrderByColumnSelectorImpl<>(selector10));
        });
        return getQueryable10();
    }

    default Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> orderByAscMerge(SQLExpression1<Tuple10<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>, SQLOrderBySelector<T7>, SQLOrderBySelector<T8>, SQLOrderBySelector<T9>, SQLOrderBySelector<T10>>> selectExpression) {
        return orderByAscMerge(true, selectExpression);
    }

    default Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> orderByAscMerge(boolean condition, SQLExpression1<Tuple10<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>, SQLOrderBySelector<T7>, SQLOrderBySelector<T8>, SQLOrderBySelector<T9>, SQLOrderBySelector<T10>>> selectExpression) {
        return orderByAsc(condition, (t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            selectExpression.apply(new Tuple10<>(t, t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }

    default Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> orderByDesc(SQLExpression10<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>, SQLOrderBySelector<T7>, SQLOrderBySelector<T8>, SQLOrderBySelector<T9>, SQLOrderBySelector<T10>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    default Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> orderByDesc(boolean condition, SQLExpression10<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>, SQLOrderBySelector<T7>, SQLOrderBySelector<T8>, SQLOrderBySelector<T9>, SQLOrderBySelector<T10>> selectExpression) {
        getClientQueryable10().orderByDesc(condition, (selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9, selector10) -> {
            selectExpression.apply(new SQLOrderByColumnSelectorImpl<>(selector1), new SQLOrderByColumnSelectorImpl<>(selector2), new SQLOrderByColumnSelectorImpl<>(selector3), new SQLOrderByColumnSelectorImpl<>(selector4), new SQLOrderByColumnSelectorImpl<>(selector5), new SQLOrderByColumnSelectorImpl<>(selector6), new SQLOrderByColumnSelectorImpl<>(selector7), new SQLOrderByColumnSelectorImpl<>(selector8), new SQLOrderByColumnSelectorImpl<>(selector9), new SQLOrderByColumnSelectorImpl<>(selector10));
        });
        return getQueryable10();
    }

    default Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> orderByDescMerge(SQLExpression1<Tuple10<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>, SQLOrderBySelector<T7>, SQLOrderBySelector<T8>, SQLOrderBySelector<T9>, SQLOrderBySelector<T10>>> selectExpression) {
        return orderByDescMerge(true, selectExpression);
    }

    default Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> orderByDescMerge(boolean condition, SQLExpression1<Tuple10<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>, SQLOrderBySelector<T7>, SQLOrderBySelector<T8>, SQLOrderBySelector<T9>, SQLOrderBySelector<T10>>> selectExpression) {
        return orderByDesc(condition, (t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            selectExpression.apply(new Tuple10<>(t, t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }
}

