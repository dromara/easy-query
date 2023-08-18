package com.easy.query.api4kt.select.extension.queryable3;

import com.easy.query.api4kt.select.KtQueryable3;
import com.easy.query.api4kt.sql.SQLKtGroupBySelector;
import com.easy.query.api4kt.sql.impl.SQLKtGroupBySelectorImpl;
import com.easy.query.core.common.tuple.Tuple3;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression3;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtGroupable3<T1, T2, T3> extends ClientKtQueryable3Available<T1, T2, T3>, KtQueryable3Available<T1, T2, T3> {


    default KtQueryable3<T1, T2, T3> groupBy(SQLExpression3<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>> selectExpression) {
        getClientQueryable3().groupBy((selector1, selector2, selector3) -> {
            selectExpression.apply(new SQLKtGroupBySelectorImpl<>(selector1), new SQLKtGroupBySelectorImpl<>(selector2), new SQLKtGroupBySelectorImpl<>(selector3));
        });
        return getQueryable3();
    }

    default KtQueryable3<T1, T2, T3> groupBy(boolean condition, SQLExpression3<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>> selectExpression) {
        getClientQueryable3().groupBy(condition, (selector1, selector2, selector3) -> {
            selectExpression.apply(new SQLKtGroupBySelectorImpl<>(selector1), new SQLKtGroupBySelectorImpl<>(selector2), new SQLKtGroupBySelectorImpl<>(selector3));
        });
        return getQueryable3();
    }

    default KtQueryable3<T1, T2, T3> groupByMerge(SQLExpression1<Tuple3<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>>> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default KtQueryable3<T1, T2, T3> groupByMerge(boolean condition, SQLExpression1<Tuple3<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>>> selectExpression) {
        return groupBy(condition, (t1, t2, t3) -> {
            selectExpression.apply(new Tuple3<>(t1,t2,t3));
        });
    }
}
