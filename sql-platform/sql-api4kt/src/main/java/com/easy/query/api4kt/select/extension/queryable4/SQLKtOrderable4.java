package com.easy.query.api4kt.select.extension.queryable4;

import com.easy.query.api4kt.select.KtQueryable4;
import com.easy.query.api4kt.sql.SQLKtOrderBySelector;
import com.easy.query.api4kt.sql.impl.SQLKtOrderByColumnSelectorImpl;
import com.easy.query.core.common.tuple.Tuple4;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression4;

/**
 * create time 2023/8/16 08:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtOrderable4<T1, T2, T3, T4> extends ClientKtQueryable4Available<T1, T2, T3, T4>, KtQueryable4Available<T1, T2, T3, T4> {

    default KtQueryable4<T1, T2, T3, T4> orderByAsc(SQLExpression4<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>> selectExpression) {
        getClientQueryable4().orderByAsc((selector1, selector2, selector3,selector4) -> {
            selectExpression.apply(new SQLKtOrderByColumnSelectorImpl<>(selector1), new SQLKtOrderByColumnSelectorImpl<>(selector2), new SQLKtOrderByColumnSelectorImpl<>(selector3), new SQLKtOrderByColumnSelectorImpl<>(selector4));
        });
        return getQueryable4();
    }

    default KtQueryable4<T1, T2, T3, T4> orderByAsc(boolean condition, SQLExpression4<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>> selectExpression) {
        getClientQueryable4().orderByAsc(condition, (selector1, selector2, selector3,selector4) -> {
            selectExpression.apply(new SQLKtOrderByColumnSelectorImpl<>(selector1), new SQLKtOrderByColumnSelectorImpl<>(selector2), new SQLKtOrderByColumnSelectorImpl<>(selector3), new SQLKtOrderByColumnSelectorImpl<>(selector4));
        });
        return getQueryable4();
    }

    default KtQueryable4<T1, T2, T3, T4> orderByAscMerge(SQLExpression1<Tuple4<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>>> selectExpression) {
        return orderByAscMerge(true, selectExpression);
    }

    default KtQueryable4<T1, T2, T3, T4> orderByAscMerge(boolean condition, SQLExpression1<Tuple4<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>>> selectExpression) {
        return orderByAsc(condition, (t1, t2,t3, t4) -> {
            selectExpression.apply(new Tuple4<>(t1, t2,t3, t4));
        });
    }


    default KtQueryable4<T1, T2, T3, T4> orderByDesc(SQLExpression4<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    default KtQueryable4<T1, T2, T3, T4> orderByDesc(boolean condition, SQLExpression4<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>> selectExpression) {
        getClientQueryable4().orderByDesc(condition, (selector1, selector2, selector3,selector4) -> {
            selectExpression.apply(new SQLKtOrderByColumnSelectorImpl<>(selector1), new SQLKtOrderByColumnSelectorImpl<>(selector2), new SQLKtOrderByColumnSelectorImpl<>(selector3), new SQLKtOrderByColumnSelectorImpl<>(selector4));
        });
        return getQueryable4();
    }

    default KtQueryable4<T1, T2, T3, T4> orderByDescMerge(SQLExpression1<Tuple4<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>>> selectExpression) {
        return orderByDescMerge(true, selectExpression);
    }

    default KtQueryable4<T1, T2, T3, T4> orderByDescMerge(boolean condition, SQLExpression1<Tuple4<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>, SQLKtOrderBySelector<T4>>> selectExpression) {
        return orderByDesc(condition, (t1, t2,t3, t4) -> {
            selectExpression.apply(new Tuple4<>(t1, t2,t3, t4));
        });
    }

}
