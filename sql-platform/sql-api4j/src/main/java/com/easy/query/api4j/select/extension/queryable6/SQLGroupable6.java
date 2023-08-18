package com.easy.query.api4j.select.extension.queryable6;

import com.easy.query.api4j.select.Queryable6;
import com.easy.query.api4j.sql.SQLGroupBySelector;
import com.easy.query.api4j.sql.impl.SQLGroupBySelectorImpl;
import com.easy.query.core.common.tuple.Tuple6;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression6;

/**
 * create time 2023/8/18 09:33
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLGroupable6<T1, T2, T3, T4, T5, T6> extends ClientQueryable6Available<T1, T2, T3, T4, T5, T6>, Queryable6Available<T1, T2, T3, T4, T5, T6> {

    default Queryable6<T1, T2, T3, T4, T5, T6> groupBy(SQLExpression6<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>, SQLGroupBySelector<T4>, SQLGroupBySelector<T5>, SQLGroupBySelector<T6>> selectExpression) {
        getClientQueryable6().groupBy((selector1, selector2, selector3, selector4, selector5, selector6) -> {
            selectExpression.apply(new SQLGroupBySelectorImpl<>(selector1), new SQLGroupBySelectorImpl<>(selector2), new SQLGroupBySelectorImpl<>(selector3), new SQLGroupBySelectorImpl<>(selector4), new SQLGroupBySelectorImpl<>(selector5), new SQLGroupBySelectorImpl<>(selector6));
        });
        return getQueryable6();
    }

    default Queryable6<T1, T2, T3, T4, T5, T6> groupBy(boolean condition, SQLExpression6<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>, SQLGroupBySelector<T4>, SQLGroupBySelector<T5>, SQLGroupBySelector<T6>> selectExpression) {
        getClientQueryable6().groupBy(condition, (selector1, selector2, selector3, selector4, selector5, selector6) -> {
            selectExpression.apply(new SQLGroupBySelectorImpl<>(selector1), new SQLGroupBySelectorImpl<>(selector2), new SQLGroupBySelectorImpl<>(selector3), new SQLGroupBySelectorImpl<>(selector4), new SQLGroupBySelectorImpl<>(selector5), new SQLGroupBySelectorImpl<>(selector6));
        });
        return getQueryable6();
    }

    default Queryable6<T1, T2, T3, T4, T5, T6> groupByMerge(SQLExpression1<Tuple6<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>, SQLGroupBySelector<T4>, SQLGroupBySelector<T5>, SQLGroupBySelector<T6>>> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default Queryable6<T1, T2, T3, T4, T5, T6> groupByMerge(boolean condition, SQLExpression1<Tuple6<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>, SQLGroupBySelector<T4>, SQLGroupBySelector<T5>, SQLGroupBySelector<T6>>> selectExpression) {
        return groupBy(condition, (t1, t2, t3, t4, t5, t6) -> {
            selectExpression.apply(new Tuple6<>(t1, t2, t3, t4, t5, t6));
        });
    }
}
