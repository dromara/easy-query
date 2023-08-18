package com.easy.query.api4j.select.extension.queryable10;

import com.easy.query.api4j.select.Queryable10;
import com.easy.query.api4j.sql.SQLGroupBySelector;
import com.easy.query.api4j.sql.impl.SQLGroupBySelectorImpl;
import com.easy.query.core.common.tuple.Tuple10;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression10;

/**
 * create time 2023/8/18 09:36
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLGroupable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> extends ClientQueryable10Available<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, Queryable10Available<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> {

    default Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> groupBy(SQLExpression10<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>, SQLGroupBySelector<T4>, SQLGroupBySelector<T5>, SQLGroupBySelector<T6>, SQLGroupBySelector<T7>, SQLGroupBySelector<T8>, SQLGroupBySelector<T9>, SQLGroupBySelector<T10>> selectExpression) {
        getClientQueryable10().groupBy((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9, selector10) -> {
            selectExpression.apply(new SQLGroupBySelectorImpl<>(selector1), new SQLGroupBySelectorImpl<>(selector2), new SQLGroupBySelectorImpl<>(selector3), new SQLGroupBySelectorImpl<>(selector4), new SQLGroupBySelectorImpl<>(selector5), new SQLGroupBySelectorImpl<>(selector6), new SQLGroupBySelectorImpl<>(selector7), new SQLGroupBySelectorImpl<>(selector8), new SQLGroupBySelectorImpl<>(selector9), new SQLGroupBySelectorImpl<>(selector10));
        });
        return getQueryable10();
    }

    default Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> groupBy(boolean condition, SQLExpression10<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>, SQLGroupBySelector<T4>, SQLGroupBySelector<T5>, SQLGroupBySelector<T6>, SQLGroupBySelector<T7>, SQLGroupBySelector<T8>, SQLGroupBySelector<T9>, SQLGroupBySelector<T10>> selectExpression) {
        getClientQueryable10().groupBy(condition, (selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9, selector10) -> {
            selectExpression.apply(new SQLGroupBySelectorImpl<>(selector1), new SQLGroupBySelectorImpl<>(selector2), new SQLGroupBySelectorImpl<>(selector3), new SQLGroupBySelectorImpl<>(selector4), new SQLGroupBySelectorImpl<>(selector5), new SQLGroupBySelectorImpl<>(selector6), new SQLGroupBySelectorImpl<>(selector7), new SQLGroupBySelectorImpl<>(selector8), new SQLGroupBySelectorImpl<>(selector9), new SQLGroupBySelectorImpl<>(selector10));
        });
        return getQueryable10();
    }

    default Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> groupByMerge(SQLExpression1<Tuple10<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>, SQLGroupBySelector<T4>, SQLGroupBySelector<T5>, SQLGroupBySelector<T6>, SQLGroupBySelector<T7>, SQLGroupBySelector<T8>, SQLGroupBySelector<T9>, SQLGroupBySelector<T10>>> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> groupByMerge(boolean condition, SQLExpression1<Tuple10<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>, SQLGroupBySelector<T4>, SQLGroupBySelector<T5>, SQLGroupBySelector<T6>, SQLGroupBySelector<T7>, SQLGroupBySelector<T8>, SQLGroupBySelector<T9>, SQLGroupBySelector<T10>>> selectExpression) {
        return groupBy(condition, (t, t1, t3, t4, t5, t6, t7, t8, t9, t10) -> {
            selectExpression.apply(new Tuple10<>(t, t1, t3, t4, t5, t6, t7, t8, t9, t10));
        });
    }
}