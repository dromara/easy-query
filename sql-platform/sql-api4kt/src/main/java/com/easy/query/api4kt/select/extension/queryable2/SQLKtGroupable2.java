package com.easy.query.api4kt.select.extension.queryable2;

import com.easy.query.api4kt.select.KtQueryable2;
import com.easy.query.api4kt.sql.SQLKtGroupBySelector;
import com.easy.query.api4kt.sql.impl.SQLKtGroupBySelectorImpl;
import com.easy.query.core.common.tuple.Tuple2;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtGroupable2<T1, T2> extends ClientKtQueryable2Available<T1,T2>, KtQueryable2Available<T1,T2> {


    default KtQueryable2<T1, T2> groupBy(SQLExpression2<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>> selectExpression) {
        getClientQueryable2().groupBy((selector1, selector2) -> {
            selectExpression.apply(new SQLKtGroupBySelectorImpl<>(selector1), new SQLKtGroupBySelectorImpl<>(selector2));
        });
        return getQueryable2();
    }

    default KtQueryable2<T1, T2> groupBy(boolean condition, SQLExpression2<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>> selectExpression) {
        getClientQueryable2().groupBy(condition, (selector1, selector2) -> {
            selectExpression.apply(new SQLKtGroupBySelectorImpl<>(selector1), new SQLKtGroupBySelectorImpl<>(selector2));
        });
        return getQueryable2();
    }

    default KtQueryable2<T1, T2> groupByMerge(SQLExpression1<Tuple2<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>>> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default KtQueryable2<T1, T2> groupByMerge(boolean condition, SQLExpression1<Tuple2<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>>> selectExpression) {
        return groupBy(condition, (t1, t2) -> {
            selectExpression.apply(new Tuple2<>(t1, t2));
        });
    }
}
