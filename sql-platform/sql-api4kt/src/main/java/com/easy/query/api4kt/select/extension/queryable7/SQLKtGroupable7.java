package com.easy.query.api4kt.select.extension.queryable7;

import com.easy.query.api4kt.select.KtQueryable7;
import com.easy.query.api4kt.sql.SQLKtGroupBySelector;
import com.easy.query.api4kt.sql.impl.SQLKtGroupBySelectorImpl;
import com.easy.query.core.common.tuple.Tuple7;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression7;

/**
 * create time 2023/8/18 09:33
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtGroupable7<T1, T2, T3, T4, T5, T6, T7> extends ClientKtQueryable7Available<T1, T2, T3, T4, T5, T6, T7>, KtQueryable7Available<T1, T2, T3, T4, T5, T6, T7> {

    default KtQueryable7<T1, T2, T3, T4, T5, T6, T7> groupBy(SQLExpression7<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>, SQLKtGroupBySelector<T4>, SQLKtGroupBySelector<T5>, SQLKtGroupBySelector<T6>, SQLKtGroupBySelector<T7>> selectExpression) {
        getClientQueryable7().groupBy((selector1, selector2, selector3, selector4, selector5, selector6, selector7) -> {
            selectExpression.apply(new SQLKtGroupBySelectorImpl<>(selector1), new SQLKtGroupBySelectorImpl<>(selector2), new SQLKtGroupBySelectorImpl<>(selector3), new SQLKtGroupBySelectorImpl<>(selector4), new SQLKtGroupBySelectorImpl<>(selector5), new SQLKtGroupBySelectorImpl<>(selector6), new SQLKtGroupBySelectorImpl<>(selector7));
        });
        return getQueryable7();
    }

    default KtQueryable7<T1, T2, T3, T4, T5, T6, T7> groupBy(boolean condition, SQLExpression7<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>, SQLKtGroupBySelector<T4>, SQLKtGroupBySelector<T5>, SQLKtGroupBySelector<T6>, SQLKtGroupBySelector<T7>> selectExpression) {
        getClientQueryable7().groupBy(condition, (selector1, selector2, selector3, selector4, selector5, selector6, selector7) -> {
            selectExpression.apply(new SQLKtGroupBySelectorImpl<>(selector1), new SQLKtGroupBySelectorImpl<>(selector2), new SQLKtGroupBySelectorImpl<>(selector3), new SQLKtGroupBySelectorImpl<>(selector4), new SQLKtGroupBySelectorImpl<>(selector5), new SQLKtGroupBySelectorImpl<>(selector6), new SQLKtGroupBySelectorImpl<>(selector7));
        });
        return getQueryable7();
    }

    default KtQueryable7<T1, T2, T3, T4, T5, T6, T7> groupByMerge(SQLExpression1<Tuple7<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>, SQLKtGroupBySelector<T4>, SQLKtGroupBySelector<T5>, SQLKtGroupBySelector<T6>, SQLKtGroupBySelector<T7>>> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default KtQueryable7<T1, T2, T3, T4, T5, T6, T7> groupByMerge(boolean condition, SQLExpression1<Tuple7<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>, SQLKtGroupBySelector<T4>, SQLKtGroupBySelector<T5>, SQLKtGroupBySelector<T6>, SQLKtGroupBySelector<T7>>> selectExpression) {
        return groupBy(condition, (t1, t2,  t3, t4, t5, t6, t7) -> {
            selectExpression.apply(new Tuple7<>(t1, t2,  t3, t4, t5, t6, t7));
        });
    }
}
