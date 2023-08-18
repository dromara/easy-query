package com.easy.query.api4j.select.extension.queryable4;

import com.easy.query.api4j.select.Queryable4;
import com.easy.query.api4j.sql.SQLOrderBySelector;
import com.easy.query.api4j.sql.impl.SQLOrderByColumnSelectorImpl;
import com.easy.query.core.common.tuple.Tuple4;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression4;

/**
 * create time 2023/8/16 08:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLOrderable4<T1, T2, T3, T4> extends ClientQueryable4Available<T1, T2, T3, T4>, Queryable4Available<T1, T2, T3, T4> {

    default Queryable4<T1, T2, T3, T4> orderByAsc(SQLExpression4<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>> selectExpression) {
        getClientQueryable4().orderByAsc((selector1, selector2, selector3,selector4) -> {
            selectExpression.apply(new SQLOrderByColumnSelectorImpl<>(selector1), new SQLOrderByColumnSelectorImpl<>(selector2), new SQLOrderByColumnSelectorImpl<>(selector3), new SQLOrderByColumnSelectorImpl<>(selector4));
        });
        return getQueryable4();
    }

    default Queryable4<T1, T2, T3, T4> orderByAsc(boolean condition, SQLExpression4<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>> selectExpression) {
        getClientQueryable4().orderByAsc(condition, (selector1, selector2, selector3,selector4) -> {
            selectExpression.apply(new SQLOrderByColumnSelectorImpl<>(selector1), new SQLOrderByColumnSelectorImpl<>(selector2), new SQLOrderByColumnSelectorImpl<>(selector3), new SQLOrderByColumnSelectorImpl<>(selector4));
        });
        return getQueryable4();
    }

    default Queryable4<T1, T2, T3, T4> orderByAscMerge(SQLExpression1<Tuple4<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>>> selectExpression) {
        return orderByAscMerge(true, selectExpression);
    }

    default Queryable4<T1, T2, T3, T4> orderByAscMerge(boolean condition, SQLExpression1<Tuple4<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>>> selectExpression) {
        return orderByAsc(condition, (t1, t2,t3, t4) -> {
            selectExpression.apply(new Tuple4<>(t1, t2,t3, t4));
        });
    }


    default Queryable4<T1, T2, T3, T4> orderByDesc(SQLExpression4<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    default Queryable4<T1, T2, T3, T4> orderByDesc(boolean condition, SQLExpression4<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>> selectExpression) {
        getClientQueryable4().orderByDesc(condition, (selector1, selector2, selector3,selector4) -> {
            selectExpression.apply(new SQLOrderByColumnSelectorImpl<>(selector1), new SQLOrderByColumnSelectorImpl<>(selector2), new SQLOrderByColumnSelectorImpl<>(selector3), new SQLOrderByColumnSelectorImpl<>(selector4));
        });
        return getQueryable4();
    }

    default Queryable4<T1, T2, T3, T4> orderByDescMerge(SQLExpression1<Tuple4<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>>> selectExpression) {
        return orderByDescMerge(true, selectExpression);
    }

    default Queryable4<T1, T2, T3, T4> orderByDescMerge(boolean condition, SQLExpression1<Tuple4<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>>> selectExpression) {
        return orderByDesc(condition, (t1, t2,t3, t4) -> {
            selectExpression.apply(new Tuple4<>(t1, t2,t3, t4));
        });
    }

}
