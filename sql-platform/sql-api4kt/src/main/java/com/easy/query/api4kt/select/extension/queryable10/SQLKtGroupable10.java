package com.easy.query.api4kt.select.extension.queryable10;

import com.easy.query.api4kt.select.KtQueryable10;
import com.easy.query.api4kt.sql.SQLKtGroupBySelector;
import com.easy.query.api4kt.sql.impl.SQLKtGroupBySelectorImpl;
import com.easy.query.core.common.tuple.Tuple10;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression10;

/**
 * create time 2023/8/18 09:36
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtGroupable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> extends ClientKtQueryable10Available<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, KtQueryable10Available<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> {

    default KtQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> groupBy(SQLExpression10<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>, SQLKtGroupBySelector<T4>, SQLKtGroupBySelector<T5>, SQLKtGroupBySelector<T6>, SQLKtGroupBySelector<T7>, SQLKtGroupBySelector<T8>, SQLKtGroupBySelector<T9>, SQLKtGroupBySelector<T10>> selectExpression) {
        getClientQueryable10().groupBy((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9, selector10) -> {
            selectExpression.apply(new SQLKtGroupBySelectorImpl<>(selector1), new SQLKtGroupBySelectorImpl<>(selector2), new SQLKtGroupBySelectorImpl<>(selector3), new SQLKtGroupBySelectorImpl<>(selector4), new SQLKtGroupBySelectorImpl<>(selector5), new SQLKtGroupBySelectorImpl<>(selector6), new SQLKtGroupBySelectorImpl<>(selector7), new SQLKtGroupBySelectorImpl<>(selector8), new SQLKtGroupBySelectorImpl<>(selector9), new SQLKtGroupBySelectorImpl<>(selector10));
        });
        return getQueryable10();
    }

    default KtQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> groupBy(boolean condition, SQLExpression10<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>, SQLKtGroupBySelector<T4>, SQLKtGroupBySelector<T5>, SQLKtGroupBySelector<T6>, SQLKtGroupBySelector<T7>, SQLKtGroupBySelector<T8>, SQLKtGroupBySelector<T9>, SQLKtGroupBySelector<T10>> selectExpression) {
        getClientQueryable10().groupBy(condition, (selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9, selector10) -> {
            selectExpression.apply(new SQLKtGroupBySelectorImpl<>(selector1), new SQLKtGroupBySelectorImpl<>(selector2), new SQLKtGroupBySelectorImpl<>(selector3), new SQLKtGroupBySelectorImpl<>(selector4), new SQLKtGroupBySelectorImpl<>(selector5), new SQLKtGroupBySelectorImpl<>(selector6), new SQLKtGroupBySelectorImpl<>(selector7), new SQLKtGroupBySelectorImpl<>(selector8), new SQLKtGroupBySelectorImpl<>(selector9), new SQLKtGroupBySelectorImpl<>(selector10));
        });
        return getQueryable10();
    }

    default KtQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> groupByMerge(SQLExpression1<Tuple10<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>, SQLKtGroupBySelector<T4>, SQLKtGroupBySelector<T5>, SQLKtGroupBySelector<T6>, SQLKtGroupBySelector<T7>, SQLKtGroupBySelector<T8>, SQLKtGroupBySelector<T9>, SQLKtGroupBySelector<T10>>> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default KtQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> groupByMerge(boolean condition, SQLExpression1<Tuple10<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>, SQLKtGroupBySelector<T4>, SQLKtGroupBySelector<T5>, SQLKtGroupBySelector<T6>, SQLKtGroupBySelector<T7>, SQLKtGroupBySelector<T8>, SQLKtGroupBySelector<T9>, SQLKtGroupBySelector<T10>>> selectExpression) {
        return groupBy(condition, (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) -> {
            selectExpression.apply(new Tuple10<>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
        });
    }
}