package com.easy.query.api4kt.select.extension.queryable4;

import com.easy.query.api4kt.select.KtQueryable4;
import com.easy.query.api4kt.sql.SQLKtGroupBySelector;
import com.easy.query.api4kt.sql.impl.SQLKtGroupBySelectorImpl;
import com.easy.query.core.common.tuple.Tuple4;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression4;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtGroupable4<T1, T2, T3, T4> extends ClientKtQueryable4Available<T1, T2, T3, T4>, KtQueryable4Available<T1, T2, T3, T4> {


    default KtQueryable4<T1, T2, T3, T4> groupBy(SQLExpression4<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>, SQLKtGroupBySelector<T4>> selectExpression) {
        getClientQueryable4().groupBy((selector1, selector2, selector3, selector4) -> {
            selectExpression.apply(new SQLKtGroupBySelectorImpl<>(selector1), new SQLKtGroupBySelectorImpl<>(selector2), new SQLKtGroupBySelectorImpl<>(selector3), new SQLKtGroupBySelectorImpl<>(selector4));
        });
        return getQueryable4();
    }

    default KtQueryable4<T1, T2, T3, T4> groupBy(boolean condition, SQLExpression4<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>, SQLKtGroupBySelector<T4>> selectExpression) {
        getClientQueryable4().groupBy(condition, (selector1, selector2, selector3, selector4) -> {
            selectExpression.apply(new SQLKtGroupBySelectorImpl<>(selector1), new SQLKtGroupBySelectorImpl<>(selector2), new SQLKtGroupBySelectorImpl<>(selector3), new SQLKtGroupBySelectorImpl<>(selector4));
        });
        return getQueryable4();
    }

    default KtQueryable4<T1, T2, T3, T4> groupByMerge(SQLExpression1<Tuple4<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>, SQLKtGroupBySelector<T4>>> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default KtQueryable4<T1, T2, T3, T4> groupByMerge(boolean condition, SQLExpression1<Tuple4<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>, SQLKtGroupBySelector<T4>>> selectExpression) {
        return groupBy(condition, (t1, t2, t3, t4) -> {
            selectExpression.apply(new Tuple4<>(t1, t2, t3, t4));
        });
    }
}
