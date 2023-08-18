package com.easy.query.api4j.select.extension.queryable6;

import com.easy.query.api4j.select.Queryable6;
import com.easy.query.api4j.sql.SQLOrderBySelector;
import com.easy.query.api4j.sql.impl.SQLOrderByColumnSelectorImpl;
import com.easy.query.core.common.tuple.Tuple6;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression6;

/**
 * create time 2023/8/18 09:44
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLOrderable6<T1, T2, T3, T4, T5, T6> extends ClientQueryable6Available<T1, T2, T3, T4, T5, T6>, Queryable6Available<T1, T2, T3, T4, T5, T6> {

    default Queryable6<T1, T2, T3, T4, T5, T6> orderByAsc(SQLExpression6<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>> selectExpression) {
        getClientQueryable6().orderByAsc((selector1, selector2, selector3, selector4, selector5, selector6) -> {
            selectExpression.apply(new SQLOrderByColumnSelectorImpl<>(selector1), new SQLOrderByColumnSelectorImpl<>(selector2), new SQLOrderByColumnSelectorImpl<>(selector3), new SQLOrderByColumnSelectorImpl<>(selector4), new SQLOrderByColumnSelectorImpl<>(selector5), new SQLOrderByColumnSelectorImpl<>(selector6));
        });
        return getQueryable6();
    }

    default Queryable6<T1, T2, T3, T4, T5, T6> orderByAsc(boolean condition, SQLExpression6<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>> selectExpression) {
        getClientQueryable6().orderByAsc(condition, (selector1, selector2, selector3, selector4, selector5, selector6) -> {
            selectExpression.apply(new SQLOrderByColumnSelectorImpl<>(selector1), new SQLOrderByColumnSelectorImpl<>(selector2), new SQLOrderByColumnSelectorImpl<>(selector3), new SQLOrderByColumnSelectorImpl<>(selector4), new SQLOrderByColumnSelectorImpl<>(selector5), new SQLOrderByColumnSelectorImpl<>(selector6));
        });
        return getQueryable6();
    }

    default Queryable6<T1, T2, T3, T4, T5, T6> orderByAscMerge(SQLExpression1<Tuple6<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>>> selectExpression) {
        return orderByAscMerge(true, selectExpression);
    }

    default Queryable6<T1, T2, T3, T4, T5, T6> orderByAscMerge(boolean condition, SQLExpression1<Tuple6<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>>> selectExpression) {
        return orderByAsc(condition, (t, t1, t2, t3, t4, t5) -> {
            selectExpression.apply(new Tuple6<>(t, t1, t2, t3, t4, t5));
        });
    }

    default Queryable6<T1, T2, T3, T4, T5, T6> orderByDesc(SQLExpression6<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    default Queryable6<T1, T2, T3, T4, T5, T6> orderByDesc(boolean condition, SQLExpression6<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>> selectExpression) {
        getClientQueryable6().orderByDesc(condition, (selector1, selector2, selector3, selector4, selector5, selector6) -> {
            selectExpression.apply(new SQLOrderByColumnSelectorImpl<>(selector1), new SQLOrderByColumnSelectorImpl<>(selector2), new SQLOrderByColumnSelectorImpl<>(selector3), new SQLOrderByColumnSelectorImpl<>(selector4), new SQLOrderByColumnSelectorImpl<>(selector5), new SQLOrderByColumnSelectorImpl<>(selector6));
        });
        return getQueryable6();
    }

    default Queryable6<T1, T2, T3, T4, T5, T6> orderByDescMerge(SQLExpression1<Tuple6<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>>> selectExpression) {
        return orderByDescMerge(true, selectExpression);
    }

    default Queryable6<T1, T2, T3, T4, T5, T6> orderByDescMerge(boolean condition, SQLExpression1<Tuple6<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>>> selectExpression) {
        return orderByDesc(condition, (t, t1, t2, t3, t4, t5) -> {
            selectExpression.apply(new Tuple6<>(t, t1, t2, t3, t4, t5));
        });
    }
}

