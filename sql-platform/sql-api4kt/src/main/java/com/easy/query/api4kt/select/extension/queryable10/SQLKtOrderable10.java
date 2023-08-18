package com.easy.query.api4kt.select.extension.queryable10;

import com.easy.query.api4kt.select.KtQueryable10;
import com.easy.query.api4kt.sql.SQLKtOrderBySelector;
import com.easy.query.api4kt.sql.impl.SQLKtOrderByColumnSelectorImpl;
import com.easy.query.core.common.tuple.Tuple10;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression10;

/**
 * create time 2023/8/18 09:48
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtOrderable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> extends ClientKtQueryable10Available<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, KtQueryable10Available<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> {

    default KtQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> orderByAsc(SQLExpression10<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>, SQLKtOrderBySelector<T7>, SQLKtOrderBySelector<T8>, SQLKtOrderBySelector<T9>, SQLKtOrderBySelector<T10>> selectExpression) {
        getClientQueryable10().orderByAsc((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9, selector10) -> {
            selectExpression.apply(new SQLKtOrderByColumnSelectorImpl<>(selector1), new SQLKtOrderByColumnSelectorImpl<>(selector2), new SQLKtOrderByColumnSelectorImpl<>(selector3), new SQLKtOrderByColumnSelectorImpl<>(selector4), new SQLKtOrderByColumnSelectorImpl<>(selector5), new SQLKtOrderByColumnSelectorImpl<>(selector6), new SQLKtOrderByColumnSelectorImpl<>(selector7), new SQLKtOrderByColumnSelectorImpl<>(selector8), new SQLKtOrderByColumnSelectorImpl<>(selector9), new SQLKtOrderByColumnSelectorImpl<>(selector10));
        });
        return getQueryable10();
    }

    default KtQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> orderByAsc(boolean condition, SQLExpression10<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>, SQLKtOrderBySelector<T7>, SQLKtOrderBySelector<T8>, SQLKtOrderBySelector<T9>, SQLKtOrderBySelector<T10>> selectExpression) {
        getClientQueryable10().orderByAsc(condition, (selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9, selector10) -> {
            selectExpression.apply(new SQLKtOrderByColumnSelectorImpl<>(selector1), new SQLKtOrderByColumnSelectorImpl<>(selector2), new SQLKtOrderByColumnSelectorImpl<>(selector3), new SQLKtOrderByColumnSelectorImpl<>(selector4), new SQLKtOrderByColumnSelectorImpl<>(selector5), new SQLKtOrderByColumnSelectorImpl<>(selector6), new SQLKtOrderByColumnSelectorImpl<>(selector7), new SQLKtOrderByColumnSelectorImpl<>(selector8), new SQLKtOrderByColumnSelectorImpl<>(selector9), new SQLKtOrderByColumnSelectorImpl<>(selector10));
        });
        return getQueryable10();
    }

    default KtQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> orderByAscMerge(SQLExpression1<Tuple10<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>, SQLKtOrderBySelector<T7>, SQLKtOrderBySelector<T8>, SQLKtOrderBySelector<T9>, SQLKtOrderBySelector<T10>>> selectExpression) {
        return orderByAscMerge(true, selectExpression);
    }

    default KtQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> orderByAscMerge(boolean condition, SQLExpression1<Tuple10<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>, SQLKtOrderBySelector<T7>, SQLKtOrderBySelector<T8>, SQLKtOrderBySelector<T9>, SQLKtOrderBySelector<T10>>> selectExpression) {
        return orderByAsc(condition, (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) -> {
            selectExpression.apply(new Tuple10<>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
        });
    }

    default KtQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> orderByDesc(SQLExpression10<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>, SQLKtOrderBySelector<T7>, SQLKtOrderBySelector<T8>, SQLKtOrderBySelector<T9>, SQLKtOrderBySelector<T10>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    default KtQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> orderByDesc(boolean condition, SQLExpression10<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>, SQLKtOrderBySelector<T7>, SQLKtOrderBySelector<T8>, SQLKtOrderBySelector<T9>, SQLKtOrderBySelector<T10>> selectExpression) {
        getClientQueryable10().orderByDesc(condition, (selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9, selector10) -> {
            selectExpression.apply(new SQLKtOrderByColumnSelectorImpl<>(selector1), new SQLKtOrderByColumnSelectorImpl<>(selector2), new SQLKtOrderByColumnSelectorImpl<>(selector3), new SQLKtOrderByColumnSelectorImpl<>(selector4), new SQLKtOrderByColumnSelectorImpl<>(selector5), new SQLKtOrderByColumnSelectorImpl<>(selector6), new SQLKtOrderByColumnSelectorImpl<>(selector7), new SQLKtOrderByColumnSelectorImpl<>(selector8), new SQLKtOrderByColumnSelectorImpl<>(selector9), new SQLKtOrderByColumnSelectorImpl<>(selector10));
        });
        return getQueryable10();
    }

    default KtQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> orderByDescMerge(SQLExpression1<Tuple10<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>, SQLKtOrderBySelector<T7>, SQLKtOrderBySelector<T8>, SQLKtOrderBySelector<T9>, SQLKtOrderBySelector<T10>>> selectExpression) {
        return orderByDescMerge(true, selectExpression);
    }

    default KtQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> orderByDescMerge(boolean condition, SQLExpression1<Tuple10<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>, SQLKtOrderBySelector<T7>, SQLKtOrderBySelector<T8>, SQLKtOrderBySelector<T9>, SQLKtOrderBySelector<T10>>> selectExpression) {
        return orderByDesc(condition, (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) -> {
            selectExpression.apply(new Tuple10<>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
        });
    }
}

