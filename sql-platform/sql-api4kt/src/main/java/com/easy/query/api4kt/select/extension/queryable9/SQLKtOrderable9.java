package com.easy.query.api4kt.select.extension.queryable9;

import com.easy.query.api4kt.select.KtQueryable9;
import com.easy.query.api4kt.sql.SQLKtOrderBySelector;
import com.easy.query.api4kt.sql.impl.SQLKtOrderByColumnSelectorImpl;
import com.easy.query.core.common.tuple.Tuple9;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression9;

/**
 * create time 2023/8/18 09:48
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtOrderable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> extends ClientKtQueryable9Available<T1, T2, T3, T4, T5, T6, T7, T8, T9>, KtQueryable9Available<T1, T2, T3, T4, T5, T6, T7, T8, T9> {

    default KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByAsc(SQLExpression9<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>, SQLKtOrderBySelector<T7>, SQLKtOrderBySelector<T8>, SQLKtOrderBySelector<T9>> selectExpression) {
        getClientQueryable9().orderByAsc((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9) -> {
            selectExpression.apply(new SQLKtOrderByColumnSelectorImpl<>(selector1), new SQLKtOrderByColumnSelectorImpl<>(selector2), new SQLKtOrderByColumnSelectorImpl<>(selector3), new SQLKtOrderByColumnSelectorImpl<>(selector4), new SQLKtOrderByColumnSelectorImpl<>(selector5), new SQLKtOrderByColumnSelectorImpl<>(selector6), new SQLKtOrderByColumnSelectorImpl<>(selector7), new SQLKtOrderByColumnSelectorImpl<>(selector8), new SQLKtOrderByColumnSelectorImpl<>(selector9));
        });
        return getQueryable9();
    }

    default KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByAsc(boolean condition, SQLExpression9<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>, SQLKtOrderBySelector<T7>, SQLKtOrderBySelector<T8>, SQLKtOrderBySelector<T9>> selectExpression) {
        getClientQueryable9().orderByAsc(condition, (selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9) -> {
            selectExpression.apply(new SQLKtOrderByColumnSelectorImpl<>(selector1), new SQLKtOrderByColumnSelectorImpl<>(selector2), new SQLKtOrderByColumnSelectorImpl<>(selector3), new SQLKtOrderByColumnSelectorImpl<>(selector4), new SQLKtOrderByColumnSelectorImpl<>(selector5), new SQLKtOrderByColumnSelectorImpl<>(selector6), new SQLKtOrderByColumnSelectorImpl<>(selector7), new SQLKtOrderByColumnSelectorImpl<>(selector8), new SQLKtOrderByColumnSelectorImpl<>(selector9));
        });
        return getQueryable9();
    }

    default KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByAscMerge(SQLExpression1<Tuple9<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>, SQLKtOrderBySelector<T7>, SQLKtOrderBySelector<T8>, SQLKtOrderBySelector<T9>>> selectExpression) {
        return orderByAscMerge(true, selectExpression);
    }

    default KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByAscMerge(boolean condition, SQLExpression1<Tuple9<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>, SQLKtOrderBySelector<T7>, SQLKtOrderBySelector<T8>, SQLKtOrderBySelector<T9>>> selectExpression) {
        return orderByAsc(condition, (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            selectExpression.apply(new Tuple9<>(t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }

    default KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByDesc(SQLExpression9<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>, SQLKtOrderBySelector<T7>, SQLKtOrderBySelector<T8>, SQLKtOrderBySelector<T9>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    default KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByDesc(boolean condition, SQLExpression9<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>, SQLKtOrderBySelector<T7>, SQLKtOrderBySelector<T8>, SQLKtOrderBySelector<T9>> selectExpression) {
        getClientQueryable9().orderByDesc(condition, (selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9) -> {
            selectExpression.apply(new SQLKtOrderByColumnSelectorImpl<>(selector1), new SQLKtOrderByColumnSelectorImpl<>(selector2), new SQLKtOrderByColumnSelectorImpl<>(selector3), new SQLKtOrderByColumnSelectorImpl<>(selector4), new SQLKtOrderByColumnSelectorImpl<>(selector5), new SQLKtOrderByColumnSelectorImpl<>(selector6), new SQLKtOrderByColumnSelectorImpl<>(selector7), new SQLKtOrderByColumnSelectorImpl<>(selector8), new SQLKtOrderByColumnSelectorImpl<>(selector9));
        });
        return getQueryable9();
    }

    default KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByDescMerge(SQLExpression1<Tuple9<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>, SQLKtOrderBySelector<T7>, SQLKtOrderBySelector<T8>, SQLKtOrderBySelector<T9>>> selectExpression) {
        return orderByDescMerge(true, selectExpression);
    }

    default KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByDescMerge(boolean condition, SQLExpression1<Tuple9<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>, SQLKtOrderBySelector<T7>, SQLKtOrderBySelector<T8>, SQLKtOrderBySelector<T9>>> selectExpression) {
        return orderByDesc(condition, (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            selectExpression.apply(new Tuple9<>(t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }

}

