package com.easy.query.api4kt.select.extension.queryable6;

import com.easy.query.api4kt.select.KtQueryable6;
import com.easy.query.api4kt.sql.SQLKtGroupBySelector;
import com.easy.query.api4kt.sql.impl.SQLKtGroupBySelectorImpl;
import com.easy.query.core.common.tuple.Tuple6;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression6;

/**
 * create time 2023/8/18 09:33
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtGroupable6<T1, T2, T3, T4, T5, T6> extends ClientKtQueryable6Available<T1, T2, T3, T4, T5, T6>, KtQueryable6Available<T1, T2, T3, T4, T5, T6> {

    default KtQueryable6<T1, T2, T3, T4, T5, T6> groupBy(SQLExpression6<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>, SQLKtGroupBySelector<T4>, SQLKtGroupBySelector<T5>, SQLKtGroupBySelector<T6>> selectExpression) {
        getClientQueryable6().groupBy((selector1, selector2, selector3, selector4, selector5, selector6) -> {
            selectExpression.apply(new SQLKtGroupBySelectorImpl<>(selector1), new SQLKtGroupBySelectorImpl<>(selector2), new SQLKtGroupBySelectorImpl<>(selector3), new SQLKtGroupBySelectorImpl<>(selector4), new SQLKtGroupBySelectorImpl<>(selector5), new SQLKtGroupBySelectorImpl<>(selector6));
        });
        return getQueryable6();
    }

    default KtQueryable6<T1, T2, T3, T4, T5, T6> groupBy(boolean condition, SQLExpression6<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>, SQLKtGroupBySelector<T4>, SQLKtGroupBySelector<T5>, SQLKtGroupBySelector<T6>> selectExpression) {
        getClientQueryable6().groupBy(condition, (selector1, selector2, selector3, selector4, selector5, selector6) -> {
            selectExpression.apply(new SQLKtGroupBySelectorImpl<>(selector1), new SQLKtGroupBySelectorImpl<>(selector2), new SQLKtGroupBySelectorImpl<>(selector3), new SQLKtGroupBySelectorImpl<>(selector4), new SQLKtGroupBySelectorImpl<>(selector5), new SQLKtGroupBySelectorImpl<>(selector6));
        });
        return getQueryable6();
    }

    default KtQueryable6<T1, T2, T3, T4, T5, T6> groupByMerge(SQLExpression1<Tuple6<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>, SQLKtGroupBySelector<T4>, SQLKtGroupBySelector<T5>, SQLKtGroupBySelector<T6>>> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default KtQueryable6<T1, T2, T3, T4, T5, T6> groupByMerge(boolean condition, SQLExpression1<Tuple6<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>, SQLKtGroupBySelector<T4>, SQLKtGroupBySelector<T5>, SQLKtGroupBySelector<T6>>> selectExpression) {
        return groupBy(condition, (t1, t2, t3, t4, t5, t6) -> {
            selectExpression.apply(new Tuple6<>(t1, t2, t3, t4, t5, t6));
        });
    }
}
