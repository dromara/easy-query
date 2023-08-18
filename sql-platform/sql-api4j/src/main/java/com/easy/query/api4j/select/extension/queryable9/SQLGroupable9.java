package com.easy.query.api4j.select.extension.queryable9;

import com.easy.query.api4j.select.Queryable9;
import com.easy.query.api4j.sql.SQLGroupBySelector;
import com.easy.query.api4j.sql.impl.SQLGroupBySelectorImpl;
import com.easy.query.core.common.tuple.Tuple9;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression9;

/**
 * create time 2023/8/18 09:36
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLGroupable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> extends ClientQueryable9Available<T1, T2, T3, T4, T5, T6, T7, T8, T9>, Queryable9Available<T1, T2, T3, T4, T5, T6, T7, T8, T9> {

    default Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> groupBy(SQLExpression9<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>, SQLGroupBySelector<T4>, SQLGroupBySelector<T5>, SQLGroupBySelector<T6>, SQLGroupBySelector<T7>, SQLGroupBySelector<T8>, SQLGroupBySelector<T9>> selectExpression) {
        getClientQueryable9().groupBy((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9) -> {
            selectExpression.apply(new SQLGroupBySelectorImpl<>(selector1), new SQLGroupBySelectorImpl<>(selector2), new SQLGroupBySelectorImpl<>(selector3), new SQLGroupBySelectorImpl<>(selector4), new SQLGroupBySelectorImpl<>(selector5), new SQLGroupBySelectorImpl<>(selector6), new SQLGroupBySelectorImpl<>(selector7), new SQLGroupBySelectorImpl<>(selector8), new SQLGroupBySelectorImpl<>(selector9));
        });
        return getQueryable9();
    }

    default Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> groupBy(boolean condition, SQLExpression9<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>, SQLGroupBySelector<T4>, SQLGroupBySelector<T5>, SQLGroupBySelector<T6>, SQLGroupBySelector<T7>, SQLGroupBySelector<T8>, SQLGroupBySelector<T9>> selectExpression) {
        getClientQueryable9().groupBy(condition, (selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9) -> {
            selectExpression.apply(new SQLGroupBySelectorImpl<>(selector1), new SQLGroupBySelectorImpl<>(selector2), new SQLGroupBySelectorImpl<>(selector3), new SQLGroupBySelectorImpl<>(selector4), new SQLGroupBySelectorImpl<>(selector5), new SQLGroupBySelectorImpl<>(selector6), new SQLGroupBySelectorImpl<>(selector7), new SQLGroupBySelectorImpl<>(selector8), new SQLGroupBySelectorImpl<>(selector9));
        });
        return getQueryable9();
    }

    default Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> groupByMerge(SQLExpression1<Tuple9<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>, SQLGroupBySelector<T4>, SQLGroupBySelector<T5>, SQLGroupBySelector<T6>, SQLGroupBySelector<T7>, SQLGroupBySelector<T8>, SQLGroupBySelector<T9>>> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> groupByMerge(boolean condition, SQLExpression1<Tuple9<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>, SQLGroupBySelector<T4>, SQLGroupBySelector<T5>, SQLGroupBySelector<T6>, SQLGroupBySelector<T7>, SQLGroupBySelector<T8>, SQLGroupBySelector<T9>>> selectExpression) {
        return groupBy(condition, (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            selectExpression.apply(new Tuple9<>(t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }
}
