package com.easy.query.api4kt.select.extension.queryable9;

import com.easy.query.api4kt.select.KtQueryable9;
import com.easy.query.api4kt.sql.SQLKtGroupBySelector;
import com.easy.query.api4kt.sql.impl.SQLKtGroupBySelectorImpl;
import com.easy.query.core.common.tuple.Tuple9;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression9;

/**
 * create time 2023/8/18 09:36
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtGroupable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> extends ClientKtQueryable9Available<T1, T2, T3, T4, T5, T6, T7, T8, T9>, KtQueryable9Available<T1, T2, T3, T4, T5, T6, T7, T8, T9> {

    default KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> groupBy(SQLExpression9<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>, SQLKtGroupBySelector<T4>, SQLKtGroupBySelector<T5>, SQLKtGroupBySelector<T6>, SQLKtGroupBySelector<T7>, SQLKtGroupBySelector<T8>, SQLKtGroupBySelector<T9>> selectExpression) {
        getClientQueryable9().groupBy((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9) -> {
            selectExpression.apply(new SQLKtGroupBySelectorImpl<>(selector1), new SQLKtGroupBySelectorImpl<>(selector2), new SQLKtGroupBySelectorImpl<>(selector3), new SQLKtGroupBySelectorImpl<>(selector4), new SQLKtGroupBySelectorImpl<>(selector5), new SQLKtGroupBySelectorImpl<>(selector6), new SQLKtGroupBySelectorImpl<>(selector7), new SQLKtGroupBySelectorImpl<>(selector8), new SQLKtGroupBySelectorImpl<>(selector9));
        });
        return getQueryable9();
    }

    default KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> groupBy(boolean condition, SQLExpression9<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>, SQLKtGroupBySelector<T4>, SQLKtGroupBySelector<T5>, SQLKtGroupBySelector<T6>, SQLKtGroupBySelector<T7>, SQLKtGroupBySelector<T8>, SQLKtGroupBySelector<T9>> selectExpression) {
        getClientQueryable9().groupBy(condition, (selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9) -> {
            selectExpression.apply(new SQLKtGroupBySelectorImpl<>(selector1), new SQLKtGroupBySelectorImpl<>(selector2), new SQLKtGroupBySelectorImpl<>(selector3), new SQLKtGroupBySelectorImpl<>(selector4), new SQLKtGroupBySelectorImpl<>(selector5), new SQLKtGroupBySelectorImpl<>(selector6), new SQLKtGroupBySelectorImpl<>(selector7), new SQLKtGroupBySelectorImpl<>(selector8), new SQLKtGroupBySelectorImpl<>(selector9));
        });
        return getQueryable9();
    }

    default KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> groupByMerge(SQLExpression1<Tuple9<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>, SQLKtGroupBySelector<T4>, SQLKtGroupBySelector<T5>, SQLKtGroupBySelector<T6>, SQLKtGroupBySelector<T7>, SQLKtGroupBySelector<T8>, SQLKtGroupBySelector<T9>>> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> groupByMerge(boolean condition, SQLExpression1<Tuple9<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>, SQLKtGroupBySelector<T4>, SQLKtGroupBySelector<T5>, SQLKtGroupBySelector<T6>, SQLKtGroupBySelector<T7>, SQLKtGroupBySelector<T8>, SQLKtGroupBySelector<T9>>> selectExpression) {
        return groupBy(condition, (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            selectExpression.apply(new Tuple9<>(t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }
}
