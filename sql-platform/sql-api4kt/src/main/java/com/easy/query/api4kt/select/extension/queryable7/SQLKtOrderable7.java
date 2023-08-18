package com.easy.query.api4kt.select.extension.queryable7;

import com.easy.query.api4kt.select.KtQueryable7;
import com.easy.query.api4kt.sql.SQLKtOrderBySelector;
import com.easy.query.api4kt.sql.impl.SQLKtOrderByColumnSelectorImpl;
import com.easy.query.core.common.tuple.Tuple7;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression7;

/**
 * create time 2023/8/18 09:44
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtOrderable7<T1, T2, T3, T4, T5, T6, T7> extends ClientKtQueryable7Available<T1, T2, T3, T4, T5, T6, T7>, KtQueryable7Available<T1, T2, T3, T4, T5, T6, T7> {

    default KtQueryable7<T1, T2, T3, T4, T5, T6, T7> orderByAsc(SQLExpression7<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>, SQLKtOrderBySelector<T7>> selectExpression) {
        getClientQueryable7().orderByAsc((selector1, selector2, selector3, selector4, selector5, selector6, selector7) -> {
            selectExpression.apply(new SQLKtOrderByColumnSelectorImpl<>(selector1), new SQLKtOrderByColumnSelectorImpl<>(selector2), new SQLKtOrderByColumnSelectorImpl<>(selector3), new SQLKtOrderByColumnSelectorImpl<>(selector4), new SQLKtOrderByColumnSelectorImpl<>(selector5), new SQLKtOrderByColumnSelectorImpl<>(selector6), new SQLKtOrderByColumnSelectorImpl<>(selector7));
        });
        return getQueryable7();
    }

    default KtQueryable7<T1, T2, T3, T4, T5, T6, T7> orderByAsc(boolean condition, SQLExpression7<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>, SQLKtOrderBySelector<T7>> selectExpression) {
        getClientQueryable7().orderByAsc(condition, (selector1, selector2, selector3, selector4, selector5, selector6, selector7) -> {
            selectExpression.apply(new SQLKtOrderByColumnSelectorImpl<>(selector1), new SQLKtOrderByColumnSelectorImpl<>(selector2), new SQLKtOrderByColumnSelectorImpl<>(selector3), new SQLKtOrderByColumnSelectorImpl<>(selector4), new SQLKtOrderByColumnSelectorImpl<>(selector5), new SQLKtOrderByColumnSelectorImpl<>(selector6), new SQLKtOrderByColumnSelectorImpl<>(selector7));
        });
        return getQueryable7();
    }

    default KtQueryable7<T1, T2, T3, T4, T5, T6, T7> orderByAscMerge(SQLExpression1<Tuple7<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>, SQLKtOrderBySelector<T7>>> selectExpression) {
        return orderByAscMerge(true, selectExpression);
    }

    default KtQueryable7<T1, T2, T3, T4, T5, T6, T7> orderByAscMerge(boolean condition, SQLExpression1<Tuple7<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>, SQLKtOrderBySelector<T7>>> selectExpression) {
        return orderByAsc(condition, (t1, t2, t3, t4, t5, t6, t7) -> {
            selectExpression.apply(new Tuple7<>(t1, t2, t3, t4, t5, t6, t7));
        });
    }

    default KtQueryable7<T1, T2, T3, T4, T5, T6, T7> orderByDesc(SQLExpression7<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>, SQLKtOrderBySelector<T7>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    default KtQueryable7<T1, T2, T3, T4, T5, T6, T7> orderByDesc(boolean condition, SQLExpression7<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>, SQLKtOrderBySelector<T7>> selectExpression) {
        getClientQueryable7().orderByDesc(condition, (selector1, selector2, selector3, selector4, selector5, selector6, selector7) -> {
            selectExpression.apply(new SQLKtOrderByColumnSelectorImpl<>(selector1), new SQLKtOrderByColumnSelectorImpl<>(selector2), new SQLKtOrderByColumnSelectorImpl<>(selector3), new SQLKtOrderByColumnSelectorImpl<>(selector4), new SQLKtOrderByColumnSelectorImpl<>(selector5), new SQLKtOrderByColumnSelectorImpl<>(selector6), new SQLKtOrderByColumnSelectorImpl<>(selector7));
        });
        return getQueryable7();
    }

    default KtQueryable7<T1, T2, T3, T4, T5, T6, T7> orderByDescMerge(SQLExpression1<Tuple7<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>, SQLKtOrderBySelector<T7>>> selectExpression) {
        return orderByDescMerge(true, selectExpression);
    }

    default KtQueryable7<T1, T2, T3, T4, T5, T6, T7> orderByDescMerge(boolean condition, SQLExpression1<Tuple7<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>, SQLKtOrderBySelector<T5>, SQLKtOrderBySelector<T6>, SQLKtOrderBySelector<T7>>> selectExpression) {
        return orderByDesc(condition, (t1, t2, t3, t4, t5, t6, t7) -> {
            selectExpression.apply(new Tuple7<>(t1, t2, t3, t4, t5, t6, t7));
        });
    }
}

