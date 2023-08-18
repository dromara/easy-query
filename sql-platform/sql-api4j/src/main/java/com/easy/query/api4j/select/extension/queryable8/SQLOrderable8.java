package com.easy.query.api4j.select.extension.queryable8;

import com.easy.query.api4j.select.Queryable8;
import com.easy.query.api4j.sql.SQLOrderBySelector;
import com.easy.query.api4j.sql.impl.SQLOrderByColumnSelectorImpl;
import com.easy.query.core.common.tuple.Tuple8;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression8;

/**
 * create time 2023/8/18 09:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLOrderable8<T1, T2, T3, T4, T5, T6, T7, T8> extends ClientQueryable8Available<T1, T2, T3, T4, T5, T6, T7, T8>, Queryable8Available<T1, T2, T3, T4, T5, T6, T7, T8> {

    default Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> orderByAsc(SQLExpression8<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>, SQLOrderBySelector<T7>, SQLOrderBySelector<T8>> selectExpression) {
        getClientQueryable8().orderByAsc((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8) -> {
            selectExpression.apply(new SQLOrderByColumnSelectorImpl<>(selector1), new SQLOrderByColumnSelectorImpl<>(selector2), new SQLOrderByColumnSelectorImpl<>(selector3), new SQLOrderByColumnSelectorImpl<>(selector4), new SQLOrderByColumnSelectorImpl<>(selector5), new SQLOrderByColumnSelectorImpl<>(selector6), new SQLOrderByColumnSelectorImpl<>(selector7), new SQLOrderByColumnSelectorImpl<>(selector8));
        });
        return getQueryable8();
    }

    default Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> orderByAsc(boolean condition, SQLExpression8<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>, SQLOrderBySelector<T7>, SQLOrderBySelector<T8>> selectExpression) {
        getClientQueryable8().orderByAsc(condition, (selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8) -> {
            selectExpression.apply(new SQLOrderByColumnSelectorImpl<>(selector1), new SQLOrderByColumnSelectorImpl<>(selector2), new SQLOrderByColumnSelectorImpl<>(selector3), new SQLOrderByColumnSelectorImpl<>(selector4), new SQLOrderByColumnSelectorImpl<>(selector5), new SQLOrderByColumnSelectorImpl<>(selector6), new SQLOrderByColumnSelectorImpl<>(selector7), new SQLOrderByColumnSelectorImpl<>(selector8));
        });
        return getQueryable8();
    }

    default Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> orderByAscMerge(SQLExpression1<Tuple8<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>, SQLOrderBySelector<T7>, SQLOrderBySelector<T8>>> selectExpression) {
        return orderByAscMerge(true, selectExpression);
    }

    default Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> orderByAscMerge(boolean condition, SQLExpression1<Tuple8<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>, SQLOrderBySelector<T7>, SQLOrderBySelector<T8>>> selectExpression) {
        return orderByAsc(condition, (t, t1, t2, t3, t4, t5, t6, t7) -> {
            selectExpression.apply(new Tuple8<>(t, t1, t2, t3, t4, t5, t6, t7));
        });
    }

    default Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> orderByDesc(SQLExpression8<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>, SQLOrderBySelector<T7>, SQLOrderBySelector<T8>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    default Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> orderByDesc(boolean condition, SQLExpression8<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>, SQLOrderBySelector<T7>, SQLOrderBySelector<T8>> selectExpression) {
        getClientQueryable8().orderByDesc(condition, (selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8) -> {
            selectExpression.apply(new SQLOrderByColumnSelectorImpl<>(selector1), new SQLOrderByColumnSelectorImpl<>(selector2), new SQLOrderByColumnSelectorImpl<>(selector3), new SQLOrderByColumnSelectorImpl<>(selector4), new SQLOrderByColumnSelectorImpl<>(selector5), new SQLOrderByColumnSelectorImpl<>(selector6), new SQLOrderByColumnSelectorImpl<>(selector7), new SQLOrderByColumnSelectorImpl<>(selector8));
        });
        return getQueryable8();
    }

    default Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> orderByDescMerge(SQLExpression1<Tuple8<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>, SQLOrderBySelector<T7>, SQLOrderBySelector<T8>>> selectExpression) {
        return orderByDescMerge(true, selectExpression);
    }

    default Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> orderByDescMerge(boolean condition, SQLExpression1<Tuple8<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>, SQLOrderBySelector<T7>, SQLOrderBySelector<T8>>> selectExpression) {
        return orderByDesc(condition, (t, t1, t2, t3, t4, t5, t6, t7) -> {
            selectExpression.apply(new Tuple8<>(t, t1, t2, t3, t4, t5, t6, t7));
        });
    }

}

