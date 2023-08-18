package com.easy.query.api4kt.select.extension.queryable8;

import com.easy.query.api4kt.select.KtQueryable8;
import com.easy.query.api4kt.sql.SQLKtGroupBySelector;
import com.easy.query.api4kt.sql.impl.SQLKtGroupBySelectorImpl;
import com.easy.query.core.common.tuple.Tuple8;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression8;

/**
 * create time 2023/8/18 09:36
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtGroupable8<T1, T2, T3, T4, T5, T6, T7, T8> extends ClientKtQueryable8Available<T1, T2, T3, T4, T5, T6, T7, T8>, KtQueryable8Available<T1, T2, T3, T4, T5, T6, T7, T8> {

    default KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> groupBy(SQLExpression8<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>, SQLKtGroupBySelector<T4>, SQLKtGroupBySelector<T5>, SQLKtGroupBySelector<T6>, SQLKtGroupBySelector<T7>, SQLKtGroupBySelector<T8>> selectExpression) {
        getClientQueryable8().groupBy((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8) -> {
            selectExpression.apply(new SQLKtGroupBySelectorImpl<>(selector1), new SQLKtGroupBySelectorImpl<>(selector2), new SQLKtGroupBySelectorImpl<>(selector3), new SQLKtGroupBySelectorImpl<>(selector4), new SQLKtGroupBySelectorImpl<>(selector5), new SQLKtGroupBySelectorImpl<>(selector6), new SQLKtGroupBySelectorImpl<>(selector7), new SQLKtGroupBySelectorImpl<>(selector8));
        });
        return getQueryable8();
    }

    default KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> groupBy(boolean condition, SQLExpression8<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>, SQLKtGroupBySelector<T4>, SQLKtGroupBySelector<T5>, SQLKtGroupBySelector<T6>, SQLKtGroupBySelector<T7>, SQLKtGroupBySelector<T8>> selectExpression) {
        getClientQueryable8().groupBy(condition, (selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8) -> {
            selectExpression.apply(new SQLKtGroupBySelectorImpl<>(selector1), new SQLKtGroupBySelectorImpl<>(selector2), new SQLKtGroupBySelectorImpl<>(selector3), new SQLKtGroupBySelectorImpl<>(selector4), new SQLKtGroupBySelectorImpl<>(selector5), new SQLKtGroupBySelectorImpl<>(selector6), new SQLKtGroupBySelectorImpl<>(selector7), new SQLKtGroupBySelectorImpl<>(selector8));
        });
        return getQueryable8();
    }

    default KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> groupByMerge(SQLExpression1<Tuple8<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>, SQLKtGroupBySelector<T4>, SQLKtGroupBySelector<T5>, SQLKtGroupBySelector<T6>, SQLKtGroupBySelector<T7>, SQLKtGroupBySelector<T8>>> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> groupByMerge(boolean condition, SQLExpression1<Tuple8<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>, SQLKtGroupBySelector<T4>, SQLKtGroupBySelector<T5>, SQLKtGroupBySelector<T6>, SQLKtGroupBySelector<T7>, SQLKtGroupBySelector<T8>>> selectExpression) {
        return groupBy(condition, (t1,t2, t3, t4, t5, t6, t7, t8) -> {
            selectExpression.apply(new Tuple8<>(t1,t2, t3, t4, t5, t6, t7, t8));
        });
    }
}
