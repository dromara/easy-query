package com.easy.query.api4kt.select.extension.queryable5;

import com.easy.query.api4kt.select.KtQueryable5;
import com.easy.query.api4kt.sql.SQLKtOrderBySelector;
import com.easy.query.api4kt.sql.impl.SQLKtOrderByColumnSelectorImpl;
import com.easy.query.core.common.tuple.Tuple5;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression5;

/**
 * create time 2023/8/18 09:43
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtOrderable5<T1, T2, T3, T4, T5> extends ClientKtQueryable5Available<T1, T2, T3, T4, T5>, KtQueryable5Available<T1, T2, T3, T4, T5> {

    default KtQueryable5<T1, T2, T3, T4, T5> orderByAsc(SQLExpression5<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>> selectExpression) {
        getClientQueryable5().orderByAsc((selector1, selector2, selector3, selector4, selector5) -> {
            selectExpression.apply(new SQLKtOrderByColumnSelectorImpl<>(selector1), new SQLKtOrderByColumnSelectorImpl<>(selector2), new SQLKtOrderByColumnSelectorImpl<>(selector3), new SQLKtOrderByColumnSelectorImpl<>(selector4), new SQLKtOrderByColumnSelectorImpl<>(selector5));
        });
        return getQueryable5();
    }

    default KtQueryable5<T1, T2, T3, T4, T5> orderByAsc(boolean condition, SQLExpression5<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>> selectExpression) {
        getClientQueryable5().orderByAsc(condition, (selector1, selector2, selector3, selector4, selector5) -> {
            selectExpression.apply(new SQLKtOrderByColumnSelectorImpl<>(selector1), new SQLKtOrderByColumnSelectorImpl<>(selector2), new SQLKtOrderByColumnSelectorImpl<>(selector3), new SQLKtOrderByColumnSelectorImpl<>(selector4), new SQLKtOrderByColumnSelectorImpl<>(selector5));
        });
        return getQueryable5();
    }

    default KtQueryable5<T1, T2, T3, T4, T5> orderByAscMerge(SQLExpression1<Tuple5<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>>> selectExpression) {
        return orderByAscMerge(true, selectExpression);
    }

    default KtQueryable5<T1, T2, T3, T4, T5> orderByAscMerge(boolean condition, SQLExpression1<Tuple5<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>>> selectExpression) {
        return orderByAsc(condition, (t1, t2, t3, t4, t5) -> {
            selectExpression.apply(new Tuple5<>(t1, t2, t3, t4, t5));
        });
    }

    default KtQueryable5<T1, T2, T3, T4, T5> orderByDesc(SQLExpression5<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    default KtQueryable5<T1, T2, T3, T4, T5> orderByDesc(boolean condition, SQLExpression5<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>> selectExpression) {
        getClientQueryable5().orderByDesc(condition, (selector1, selector2, selector3, selector4, selector5) -> {
            selectExpression.apply(new SQLKtOrderByColumnSelectorImpl<>(selector1), new SQLKtOrderByColumnSelectorImpl<>(selector2), new SQLKtOrderByColumnSelectorImpl<>(selector3), new SQLKtOrderByColumnSelectorImpl<>(selector4), new SQLKtOrderByColumnSelectorImpl<>(selector5));
        });
        return getQueryable5();
    }

    default KtQueryable5<T1, T2, T3, T4, T5> orderByDescMerge(SQLExpression1<Tuple5<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>>> selectExpression) {
        return orderByDescMerge(true, selectExpression);
    }

    default KtQueryable5<T1, T2, T3, T4, T5> orderByDescMerge(boolean condition, SQLExpression1<Tuple5<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>>> selectExpression) {
        return orderByDesc(condition, (t1, t2, t3, t4, t5) -> {
            selectExpression.apply(new Tuple5<>(t1, t2, t3, t4, t5));
        });
    }
}
