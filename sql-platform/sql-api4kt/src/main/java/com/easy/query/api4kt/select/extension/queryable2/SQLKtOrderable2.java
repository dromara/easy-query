package com.easy.query.api4kt.select.extension.queryable2;

import com.easy.query.api4kt.select.KtQueryable2;
import com.easy.query.api4kt.sql.SQLKtOrderBySelector;
import com.easy.query.api4kt.sql.impl.SQLKtOrderByColumnSelectorImpl;
import com.easy.query.core.common.tuple.Tuple2;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;

/**
 * create time 2023/8/16 08:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtOrderable2<T1, T2> extends ClientKtQueryable2Available<T1, T2>, KtQueryable2Available<T1, T2> {

    default KtQueryable2<T1, T2> orderByAsc(SQLExpression2<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>> selectExpression) {
        getClientQueryable2().orderByAsc((selector1, selector2) -> {
            selectExpression.apply(new SQLKtOrderByColumnSelectorImpl<>(selector1), new SQLKtOrderByColumnSelectorImpl<>(selector2));
        });
        return getQueryable2();
    }

    default KtQueryable2<T1, T2> orderByAsc(boolean condition, SQLExpression2<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>> selectExpression) {
        getClientQueryable2().orderByAsc(condition, (selector1, selector2) -> {
            selectExpression.apply(new SQLKtOrderByColumnSelectorImpl<>(selector1), new SQLKtOrderByColumnSelectorImpl<>(selector2));
        });
        return getQueryable2();
    }

    default KtQueryable2<T1, T2> orderByAscMerge(SQLExpression1<Tuple2<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>>> selectExpression) {
        return orderByAscMerge(true, selectExpression);
    }

    default KtQueryable2<T1, T2> orderByAscMerge(boolean condition, SQLExpression1<Tuple2<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>>> selectExpression) {
        return orderByAsc(condition, (t1, t2) -> {
            selectExpression.apply(new Tuple2<>(t1, t2));
        });
    }


    default KtQueryable2<T1, T2> orderByDesc(SQLExpression2<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    default KtQueryable2<T1, T2> orderByDesc(boolean condition, SQLExpression2<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>> selectExpression) {
        getClientQueryable2().orderByDesc(condition, (selector1, selector2) -> {
            selectExpression.apply(new SQLKtOrderByColumnSelectorImpl<>(selector1), new SQLKtOrderByColumnSelectorImpl<>(selector2));
        });
        return getQueryable2();
    }

    default KtQueryable2<T1, T2> orderByDescMerge(SQLExpression1<Tuple2<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>>> selectExpression) {
        return orderByDescMerge(true, selectExpression);
    }

    default KtQueryable2<T1, T2> orderByDescMerge(boolean condition, SQLExpression1<Tuple2<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>>> selectExpression) {
        return orderByDesc(condition, (t1, t2) -> {
            selectExpression.apply(new Tuple2<>(t1, t2));
        });
    }

}
