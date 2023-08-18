package com.easy.query.api4kt.select.extension.queryable6;

import com.easy.query.api4kt.select.KtQueryable6;
import com.easy.query.api4kt.sql.SQLKtOrderBySelector;
import com.easy.query.api4kt.sql.impl.SQLKtOrderByColumnSelectorImpl;
import com.easy.query.core.common.tuple.Tuple6;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression6;

/**
 * create time 2023/8/18 09:44
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtOrderable6<T1, T2, T3, T4, T5, T6> extends ClientKtQueryable6Available<T1, T2, T3, T4, T5, T6>, KtQueryable6Available<T1, T2, T3, T4, T5, T6> {

    default KtQueryable6<T1, T2, T3, T4, T5, T6> orderByAsc(SQLExpression6<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>> selectExpression) {
        getClientQueryable6().orderByAsc((selector1, selector2, selector3, selector4, selector5, selector6) -> {
            selectExpression.apply(new SQLKtOrderByColumnSelectorImpl<>(selector1), new SQLKtOrderByColumnSelectorImpl<>(selector2), new SQLKtOrderByColumnSelectorImpl<>(selector3), new SQLKtOrderByColumnSelectorImpl<>(selector4), new SQLKtOrderByColumnSelectorImpl<>(selector5), new SQLKtOrderByColumnSelectorImpl<>(selector6));
        });
        return getQueryable6();
    }

    default KtQueryable6<T1, T2, T3, T4, T5, T6> orderByAsc(boolean condition, SQLExpression6<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>> selectExpression) {
        getClientQueryable6().orderByAsc(condition, (selector1, selector2, selector3, selector4, selector5, selector6) -> {
            selectExpression.apply(new SQLKtOrderByColumnSelectorImpl<>(selector1), new SQLKtOrderByColumnSelectorImpl<>(selector2), new SQLKtOrderByColumnSelectorImpl<>(selector3), new SQLKtOrderByColumnSelectorImpl<>(selector4), new SQLKtOrderByColumnSelectorImpl<>(selector5), new SQLKtOrderByColumnSelectorImpl<>(selector6));
        });
        return getQueryable6();
    }

    default KtQueryable6<T1, T2, T3, T4, T5, T6> orderByAscMerge(SQLExpression1<Tuple6<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>>> selectExpression) {
        return orderByAscMerge(true, selectExpression);
    }

    default KtQueryable6<T1, T2, T3, T4, T5, T6> orderByAscMerge(boolean condition, SQLExpression1<Tuple6<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>>> selectExpression) {
        return orderByAsc(condition, (t1, t2, t3, t4, t5, t6) -> {
            selectExpression.apply(new Tuple6<>(t1, t2, t3, t4, t5, t6));
        });
    }

    default KtQueryable6<T1, T2, T3, T4, T5, T6> orderByDesc(SQLExpression6<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    default KtQueryable6<T1, T2, T3, T4, T5, T6> orderByDesc(boolean condition, SQLExpression6<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>> selectExpression) {
        getClientQueryable6().orderByDesc(condition, (selector1, selector2, selector3, selector4, selector5, selector6) -> {
            selectExpression.apply(new SQLKtOrderByColumnSelectorImpl<>(selector1), new SQLKtOrderByColumnSelectorImpl<>(selector2), new SQLKtOrderByColumnSelectorImpl<>(selector3), new SQLKtOrderByColumnSelectorImpl<>(selector4), new SQLKtOrderByColumnSelectorImpl<>(selector5), new SQLKtOrderByColumnSelectorImpl<>(selector6));
        });
        return getQueryable6();
    }

    default KtQueryable6<T1, T2, T3, T4, T5, T6> orderByDescMerge(SQLExpression1<Tuple6<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>>> selectExpression) {
        return orderByDescMerge(true, selectExpression);
    }

    default KtQueryable6<T1, T2, T3, T4, T5, T6> orderByDescMerge(boolean condition, SQLExpression1<Tuple6<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>>> selectExpression) {
        return orderByDesc(condition, (t1, t2, t3, t4, t5, t6) -> {
            selectExpression.apply(new Tuple6<>(t1, t2, t3, t4, t5, t6));
        });
    }
}

