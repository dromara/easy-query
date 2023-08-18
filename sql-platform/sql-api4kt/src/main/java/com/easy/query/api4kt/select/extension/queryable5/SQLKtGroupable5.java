package com.easy.query.api4kt.select.extension.queryable5;

import com.easy.query.api4kt.select.KtQueryable5;
import com.easy.query.api4kt.sql.SQLKtGroupBySelector;
import com.easy.query.api4kt.sql.impl.SQLKtGroupBySelectorImpl;
import com.easy.query.core.common.tuple.Tuple5;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression5;

/**
 * create time 2023/8/18 09:33
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtGroupable5<T1, T2, T3, T4, T5> extends ClientKtQueryable5Available<T1, T2, T3, T4, T5>, KtQueryable5Available<T1, T2, T3, T4, T5> {

    default KtQueryable5<T1, T2, T3, T4, T5> groupBy(SQLExpression5<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>, SQLKtGroupBySelector<T4>, SQLKtGroupBySelector<T5>> selectExpression) {
        getClientQueryable5().groupBy((selector1, selector2, selector3, selector4, selector5) -> {
            selectExpression.apply(new SQLKtGroupBySelectorImpl<>(selector1), new SQLKtGroupBySelectorImpl<>(selector2), new SQLKtGroupBySelectorImpl<>(selector3), new SQLKtGroupBySelectorImpl<>(selector4), new SQLKtGroupBySelectorImpl<>(selector5));
        });
        return getQueryable5();
    }

    default KtQueryable5<T1, T2, T3, T4, T5> groupBy(boolean condition, SQLExpression5<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>, SQLKtGroupBySelector<T4>, SQLKtGroupBySelector<T5>> selectExpression) {
        getClientQueryable5().groupBy(condition, (selector1, selector2, selector3, selector4, selector5) -> {
            selectExpression.apply(new SQLKtGroupBySelectorImpl<>(selector1), new SQLKtGroupBySelectorImpl<>(selector2), new SQLKtGroupBySelectorImpl<>(selector3), new SQLKtGroupBySelectorImpl<>(selector4), new SQLKtGroupBySelectorImpl<>(selector5));
        });
        return getQueryable5();
    }

    default KtQueryable5<T1, T2, T3, T4, T5> groupByMerge(SQLExpression1<Tuple5<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>, SQLKtGroupBySelector<T4>, SQLKtGroupBySelector<T5>>> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default KtQueryable5<T1, T2, T3, T4, T5> groupByMerge(boolean condition, SQLExpression1<Tuple5<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>, SQLKtGroupBySelector<T4>, SQLKtGroupBySelector<T5>>> selectExpression) {
        return groupBy(condition, (t1, t2, t3, t4, t5) -> {
            selectExpression.apply(new Tuple5<>(t1, t2, t3, t4, t5));
        });
    }
}