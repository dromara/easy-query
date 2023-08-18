package com.easy.query.api4kt.select.extension.queryable8;

import com.easy.query.api4kt.select.KtQueryable8;
import com.easy.query.api4kt.sql.SQLKtOrderBySelector;
import com.easy.query.api4kt.sql.impl.SQLKtOrderByColumnSelectorImpl;
import com.easy.query.core.common.tuple.Tuple8;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression8;

/**
 * create time 2023/8/18 09:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtOrderable8<T1, T2, T3, T4, T5, T6, T7, T8> extends ClientKtQueryable8Available<T1, T2, T3, T4, T5, T6, T7, T8>, KtQueryable8Available<T1, T2, T3, T4, T5, T6, T7, T8> {

    default KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> orderByAsc(SQLExpression8<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>, SQLKtOrderBySelector<T7>, SQLKtOrderBySelector<T8>> selectExpression) {
        getClientQueryable8().orderByAsc((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8) -> {
            selectExpression.apply(new SQLKtOrderByColumnSelectorImpl<>(selector1), new SQLKtOrderByColumnSelectorImpl<>(selector2), new SQLKtOrderByColumnSelectorImpl<>(selector3), new SQLKtOrderByColumnSelectorImpl<>(selector4), new SQLKtOrderByColumnSelectorImpl<>(selector5), new SQLKtOrderByColumnSelectorImpl<>(selector6), new SQLKtOrderByColumnSelectorImpl<>(selector7), new SQLKtOrderByColumnSelectorImpl<>(selector8));
        });
        return getQueryable8();
    }

    default KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> orderByAsc(boolean condition, SQLExpression8<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>, SQLKtOrderBySelector<T7>, SQLKtOrderBySelector<T8>> selectExpression) {
        getClientQueryable8().orderByAsc(condition, (selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8) -> {
            selectExpression.apply(new SQLKtOrderByColumnSelectorImpl<>(selector1), new SQLKtOrderByColumnSelectorImpl<>(selector2), new SQLKtOrderByColumnSelectorImpl<>(selector3), new SQLKtOrderByColumnSelectorImpl<>(selector4), new SQLKtOrderByColumnSelectorImpl<>(selector5), new SQLKtOrderByColumnSelectorImpl<>(selector6), new SQLKtOrderByColumnSelectorImpl<>(selector7), new SQLKtOrderByColumnSelectorImpl<>(selector8));
        });
        return getQueryable8();
    }

    default KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> orderByAscMerge(SQLExpression1<Tuple8<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>, SQLKtOrderBySelector<T7>, SQLKtOrderBySelector<T8>>> selectExpression) {
        return orderByAscMerge(true, selectExpression);
    }

    default KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> orderByAscMerge(boolean condition, SQLExpression1<Tuple8<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>, SQLKtOrderBySelector<T7>, SQLKtOrderBySelector<T8>>> selectExpression) {
        return orderByAsc(condition, (t1, t2, t3, t4, t5, t6, t7, t8) -> {
            selectExpression.apply(new Tuple8<>(t1, t2, t3, t4, t5, t6, t7, t8));
        });
    }

    default KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> orderByDesc(SQLExpression8<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>, SQLKtOrderBySelector<T7>, SQLKtOrderBySelector<T8>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    default KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> orderByDesc(boolean condition, SQLExpression8<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>, SQLKtOrderBySelector<T7>, SQLKtOrderBySelector<T8>> selectExpression) {
        getClientQueryable8().orderByDesc(condition, (selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8) -> {
            selectExpression.apply(new SQLKtOrderByColumnSelectorImpl<>(selector1), new SQLKtOrderByColumnSelectorImpl<>(selector2), new SQLKtOrderByColumnSelectorImpl<>(selector3), new SQLKtOrderByColumnSelectorImpl<>(selector4), new SQLKtOrderByColumnSelectorImpl<>(selector5), new SQLKtOrderByColumnSelectorImpl<>(selector6), new SQLKtOrderByColumnSelectorImpl<>(selector7), new SQLKtOrderByColumnSelectorImpl<>(selector8));
        });
        return getQueryable8();
    }

    default KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> orderByDescMerge(SQLExpression1<Tuple8<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>, SQLKtOrderBySelector<T7>, SQLKtOrderBySelector<T8>>> selectExpression) {
        return orderByDescMerge(true, selectExpression);
    }

    default KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> orderByDescMerge(boolean condition, SQLExpression1<Tuple8<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>, SQLKtOrderBySelector<T7>, SQLKtOrderBySelector<T8>>> selectExpression) {
        return orderByDesc(condition, (t1, t2, t3, t4, t5, t6, t7, t8) -> {
            selectExpression.apply(new Tuple8<>(t1, t2, t3, t4, t5, t6, t7, t8));
        });
    }

}

