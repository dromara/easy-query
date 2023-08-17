package com.easy.query.api4j.select.extension.queryable3;

import com.easy.query.api4j.select.Queryable3;
import com.easy.query.api4j.sql.SQLOrderBySelector;
import com.easy.query.api4j.sql.impl.SQLOrderByColumnSelectorImpl;
import com.easy.query.core.common.tuple.Tuple3;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression3;

/**
 * create time 2023/8/16 08:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLOrderable3<T1, T2, T3> extends ClientQueryable3Available<T1, T2, T3>, Queryable3Available<T1, T2, T3> {

    default Queryable3<T1, T2, T3> orderByAsc(SQLExpression3<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>> selectExpression) {
        getClientQueryable3().orderByAsc((selector1, selector2, selector3) -> {
            selectExpression.apply(new SQLOrderByColumnSelectorImpl<>(selector1), new SQLOrderByColumnSelectorImpl<>(selector2), new SQLOrderByColumnSelectorImpl<>(selector3));
        });
        return getQueryable3();
    }

    default Queryable3<T1, T2, T3> orderByAsc(boolean condition, SQLExpression3<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>> selectExpression) {
        getClientQueryable3().orderByAsc(condition, (selector1, selector2, selector3) -> {
            selectExpression.apply(new SQLOrderByColumnSelectorImpl<>(selector1), new SQLOrderByColumnSelectorImpl<>(selector2), new SQLOrderByColumnSelectorImpl<>(selector3));
        });
        return getQueryable3();
    }

    default Queryable3<T1, T2, T3> orderByAscMerge(SQLExpression1<Tuple3<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>>> selectExpression) {
        return orderByAscMerge(true, selectExpression);
    }

    default Queryable3<T1, T2, T3> orderByAscMerge(boolean condition, SQLExpression1<Tuple3<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>>> selectExpression) {
        return orderByAsc(condition, (t, t1, t2) -> {
            selectExpression.apply(new Tuple3<>(t, t1, t2));
        });
    }


    default Queryable3<T1, T2, T3> orderByDesc(SQLExpression3<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    default Queryable3<T1, T2, T3> orderByDesc(boolean condition, SQLExpression3<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>> selectExpression) {
        getClientQueryable3().orderByDesc(condition, (selector1, selector2, selector3) -> {
            selectExpression.apply(new SQLOrderByColumnSelectorImpl<>(selector1), new SQLOrderByColumnSelectorImpl<>(selector2), new SQLOrderByColumnSelectorImpl<>(selector3));
        });
        return getQueryable3();
    }

    default Queryable3<T1, T2, T3> orderByDescMerge(SQLExpression1<Tuple3<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>>> selectExpression) {
        return orderByDescMerge(true, selectExpression);
    }

    default Queryable3<T1, T2, T3> orderByDescMerge(boolean condition, SQLExpression1<Tuple3<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>>> selectExpression) {
        return orderByDesc(condition, (t, t1, t2) -> {
            selectExpression.apply(new Tuple3<>(t, t1, t2));
        });
    }

}
