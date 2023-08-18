package com.easy.query.api4j.select.extension.queryable8;

import com.easy.query.api4j.select.Queryable8;
import com.easy.query.api4j.sql.SQLGroupBySelector;
import com.easy.query.api4j.sql.impl.SQLGroupBySelectorImpl;
import com.easy.query.core.common.tuple.Tuple8;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression8;

/**
 * create time 2023/8/18 09:36
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLGroupable8<T1, T2, T3, T4, T5, T6, T7, T8> extends ClientQueryable8Available<T1, T2, T3, T4, T5, T6, T7, T8>, Queryable8Available<T1, T2, T3, T4, T5, T6, T7, T8> {

    default Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> groupBy(SQLExpression8<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>, SQLGroupBySelector<T4>, SQLGroupBySelector<T5>, SQLGroupBySelector<T6>, SQLGroupBySelector<T7>, SQLGroupBySelector<T8>> selectExpression) {
        getClientQueryable8().groupBy((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8) -> {
            selectExpression.apply(new SQLGroupBySelectorImpl<>(selector1), new SQLGroupBySelectorImpl<>(selector2), new SQLGroupBySelectorImpl<>(selector3), new SQLGroupBySelectorImpl<>(selector4), new SQLGroupBySelectorImpl<>(selector5), new SQLGroupBySelectorImpl<>(selector6), new SQLGroupBySelectorImpl<>(selector7), new SQLGroupBySelectorImpl<>(selector8));
        });
        return getQueryable8();
    }

    default Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> groupBy(boolean condition, SQLExpression8<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>, SQLGroupBySelector<T4>, SQLGroupBySelector<T5>, SQLGroupBySelector<T6>, SQLGroupBySelector<T7>, SQLGroupBySelector<T8>> selectExpression) {
        getClientQueryable8().groupBy(condition, (selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8) -> {
            selectExpression.apply(new SQLGroupBySelectorImpl<>(selector1), new SQLGroupBySelectorImpl<>(selector2), new SQLGroupBySelectorImpl<>(selector3), new SQLGroupBySelectorImpl<>(selector4), new SQLGroupBySelectorImpl<>(selector5), new SQLGroupBySelectorImpl<>(selector6), new SQLGroupBySelectorImpl<>(selector7), new SQLGroupBySelectorImpl<>(selector8));
        });
        return getQueryable8();
    }

    default Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> groupByMerge(SQLExpression1<Tuple8<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>, SQLGroupBySelector<T4>, SQLGroupBySelector<T5>, SQLGroupBySelector<T6>, SQLGroupBySelector<T7>, SQLGroupBySelector<T8>>> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> groupByMerge(boolean condition, SQLExpression1<Tuple8<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>, SQLGroupBySelector<T4>, SQLGroupBySelector<T5>, SQLGroupBySelector<T6>, SQLGroupBySelector<T7>, SQLGroupBySelector<T8>>> selectExpression) {
        return groupBy(condition, (t, t1, t3, t4, t5, t6, t7, t8) -> {
            selectExpression.apply(new Tuple8<>(t, t1, t3, t4, t5, t6, t7, t8));
        });
    }
}
