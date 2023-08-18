package com.easy.query.api4j.select.extension.queryable7;

import com.easy.query.api4j.select.Queryable7;
import com.easy.query.api4j.sql.SQLOrderBySelector;
import com.easy.query.api4j.sql.impl.SQLOrderByColumnSelectorImpl;
import com.easy.query.core.common.tuple.Tuple7;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression7;

/**
 * create time 2023/8/18 09:44
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLOrderable7<T1, T2, T3, T4, T5, T6, T7> extends ClientQueryable7Available<T1, T2, T3, T4, T5, T6, T7>, Queryable7Available<T1, T2, T3, T4, T5, T6, T7> {

    default Queryable7<T1, T2, T3, T4, T5, T6, T7> orderByAsc(SQLExpression7<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>, SQLOrderBySelector<T7>> selectExpression) {
        getClientQueryable7().orderByAsc((selector1, selector2, selector3, selector4, selector5, selector6, selector7) -> {
            selectExpression.apply(new SQLOrderByColumnSelectorImpl<>(selector1), new SQLOrderByColumnSelectorImpl<>(selector2), new SQLOrderByColumnSelectorImpl<>(selector3), new SQLOrderByColumnSelectorImpl<>(selector4), new SQLOrderByColumnSelectorImpl<>(selector5), new SQLOrderByColumnSelectorImpl<>(selector6), new SQLOrderByColumnSelectorImpl<>(selector7));
        });
        return getQueryable7();
    }

    default Queryable7<T1, T2, T3, T4, T5, T6, T7> orderByAsc(boolean condition, SQLExpression7<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>, SQLOrderBySelector<T7>> selectExpression) {
        getClientQueryable7().orderByAsc(condition, (selector1, selector2, selector3, selector4, selector5, selector6, selector7) -> {
            selectExpression.apply(new SQLOrderByColumnSelectorImpl<>(selector1), new SQLOrderByColumnSelectorImpl<>(selector2), new SQLOrderByColumnSelectorImpl<>(selector3), new SQLOrderByColumnSelectorImpl<>(selector4), new SQLOrderByColumnSelectorImpl<>(selector5), new SQLOrderByColumnSelectorImpl<>(selector6), new SQLOrderByColumnSelectorImpl<>(selector7));
        });
        return getQueryable7();
    }

    default Queryable7<T1, T2, T3, T4, T5, T6, T7> orderByAscMerge(SQLExpression1<Tuple7<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>, SQLOrderBySelector<T7>>> selectExpression) {
        return orderByAscMerge(true, selectExpression);
    }

    default Queryable7<T1, T2, T3, T4, T5, T6, T7> orderByAscMerge(boolean condition, SQLExpression1<Tuple7<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>, SQLOrderBySelector<T7>>> selectExpression) {
        return orderByAsc(condition, (t, t1, t2, t3, t4, t5, t6) -> {
            selectExpression.apply(new Tuple7<>(t, t1, t2, t3, t4, t5, t6));
        });
    }

    default Queryable7<T1, T2, T3, T4, T5, T6, T7> orderByDesc(SQLExpression7<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>, SQLOrderBySelector<T7>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    default Queryable7<T1, T2, T3, T4, T5, T6, T7> orderByDesc(boolean condition, SQLExpression7<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>, SQLOrderBySelector<T7>> selectExpression) {
        getClientQueryable7().orderByDesc(condition, (selector1, selector2, selector3, selector4, selector5, selector6, selector7) -> {
            selectExpression.apply(new SQLOrderByColumnSelectorImpl<>(selector1), new SQLOrderByColumnSelectorImpl<>(selector2), new SQLOrderByColumnSelectorImpl<>(selector3), new SQLOrderByColumnSelectorImpl<>(selector4), new SQLOrderByColumnSelectorImpl<>(selector5), new SQLOrderByColumnSelectorImpl<>(selector6), new SQLOrderByColumnSelectorImpl<>(selector7));
        });
        return getQueryable7();
    }

    default Queryable7<T1, T2, T3, T4, T5, T6, T7> orderByDescMerge(SQLExpression1<Tuple7<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>, SQLOrderBySelector<T7>>> selectExpression) {
        return orderByDescMerge(true, selectExpression);
    }

    default Queryable7<T1, T2, T3, T4, T5, T6, T7> orderByDescMerge(boolean condition, SQLExpression1<Tuple7<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>, SQLOrderBySelector<T3>, SQLOrderBySelector<T4>, SQLOrderBySelector<T5>, SQLOrderBySelector<T6>, SQLOrderBySelector<T7>>> selectExpression) {
        return orderByDesc(condition, (t, t1, t2, t3, t4, t5, t6) -> {
            selectExpression.apply(new Tuple7<>(t, t1, t2, t3, t4, t5, t6));
        });
    }
}

