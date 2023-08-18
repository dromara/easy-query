package com.easy.query.api4kt.select.extension.queryable3;

import com.easy.query.api4kt.select.KtQueryable3;
import com.easy.query.api4kt.sql.SQLKtOrderBySelector;
import com.easy.query.api4kt.sql.impl.SQLKtOrderByColumnSelectorImpl;
import com.easy.query.core.common.tuple.Tuple3;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression3;

/**
 * create time 2023/8/16 08:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtOrderable3<T1, T2, T3> extends ClientKtQueryable3Available<T1, T2, T3>, KtQueryable3Available<T1, T2, T3> {

    default KtQueryable3<T1, T2, T3> orderByAsc(SQLExpression3<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>> selectExpression) {
        getClientQueryable3().orderByAsc((selector1, selector2, selector3) -> {
            selectExpression.apply(new SQLKtOrderByColumnSelectorImpl<>(selector1), new SQLKtOrderByColumnSelectorImpl<>(selector2), new SQLKtOrderByColumnSelectorImpl<>(selector3));
        });
        return getQueryable3();
    }

    default KtQueryable3<T1, T2, T3> orderByAsc(boolean condition, SQLExpression3<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>> selectExpression) {
        getClientQueryable3().orderByAsc(condition, (selector1, selector2, selector3) -> {
            selectExpression.apply(new SQLKtOrderByColumnSelectorImpl<>(selector1), new SQLKtOrderByColumnSelectorImpl<>(selector2), new SQLKtOrderByColumnSelectorImpl<>(selector3));
        });
        return getQueryable3();
    }

    default KtQueryable3<T1, T2, T3> orderByAscMerge(SQLExpression1<Tuple3<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>>> selectExpression) {
        return orderByAscMerge(true, selectExpression);
    }

    default KtQueryable3<T1, T2, T3> orderByAscMerge(boolean condition, SQLExpression1<Tuple3<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>>> selectExpression) {
        return orderByAsc(condition, (t1, t2, t3) -> {
            selectExpression.apply(new Tuple3<>(t1, t2, t3));
        });
    }


    default KtQueryable3<T1, T2, T3> orderByDesc(SQLExpression3<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    default KtQueryable3<T1, T2, T3> orderByDesc(boolean condition, SQLExpression3<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>> selectExpression) {
        getClientQueryable3().orderByDesc(condition, (selector1, selector2, selector3) -> {
            selectExpression.apply(new SQLKtOrderByColumnSelectorImpl<>(selector1), new SQLKtOrderByColumnSelectorImpl<>(selector2), new SQLKtOrderByColumnSelectorImpl<>(selector3));
        });
        return getQueryable3();
    }

    default KtQueryable3<T1, T2, T3> orderByDescMerge(SQLExpression1<Tuple3<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>>> selectExpression) {
        return orderByDescMerge(true, selectExpression);
    }

    default KtQueryable3<T1, T2, T3> orderByDescMerge(boolean condition, SQLExpression1<Tuple3<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>>> selectExpression) {
        return orderByDesc(condition, (t1, t2, t3) -> {
            selectExpression.apply(new Tuple3<>(t1, t2, t3));
        });
    }

}
