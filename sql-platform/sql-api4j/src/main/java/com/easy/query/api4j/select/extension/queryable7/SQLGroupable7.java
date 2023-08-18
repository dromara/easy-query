package com.easy.query.api4j.select.extension.queryable7;

import com.easy.query.api4j.select.Queryable7;
import com.easy.query.api4j.sql.SQLGroupBySelector;
import com.easy.query.api4j.sql.impl.SQLGroupBySelectorImpl;
import com.easy.query.core.common.tuple.Tuple7;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression7;

/**
 * create time 2023/8/18 09:33
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLGroupable7<T1, T2, T3, T4, T5, T6, T7> extends ClientQueryable7Available<T1, T2, T3, T4, T5, T6, T7>, Queryable7Available<T1, T2, T3, T4, T5, T6, T7> {

    default Queryable7<T1, T2, T3, T4, T5, T6, T7> groupBy(SQLExpression7<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>, SQLGroupBySelector<T4>, SQLGroupBySelector<T5>, SQLGroupBySelector<T6>, SQLGroupBySelector<T7>> selectExpression) {
        getClientQueryable7().groupBy((selector1, selector2, selector3, selector4, selector5, selector6, selector7) -> {
            selectExpression.apply(new SQLGroupBySelectorImpl<>(selector1), new SQLGroupBySelectorImpl<>(selector2), new SQLGroupBySelectorImpl<>(selector3), new SQLGroupBySelectorImpl<>(selector4), new SQLGroupBySelectorImpl<>(selector5), new SQLGroupBySelectorImpl<>(selector6), new SQLGroupBySelectorImpl<>(selector7));
        });
        return getQueryable7();
    }

    default Queryable7<T1, T2, T3, T4, T5, T6, T7> groupBy(boolean condition, SQLExpression7<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>, SQLGroupBySelector<T4>, SQLGroupBySelector<T5>, SQLGroupBySelector<T6>, SQLGroupBySelector<T7>> selectExpression) {
        getClientQueryable7().groupBy(condition, (selector1, selector2, selector3, selector4, selector5, selector6, selector7) -> {
            selectExpression.apply(new SQLGroupBySelectorImpl<>(selector1), new SQLGroupBySelectorImpl<>(selector2), new SQLGroupBySelectorImpl<>(selector3), new SQLGroupBySelectorImpl<>(selector4), new SQLGroupBySelectorImpl<>(selector5), new SQLGroupBySelectorImpl<>(selector6), new SQLGroupBySelectorImpl<>(selector7));
        });
        return getQueryable7();
    }

    default Queryable7<T1, T2, T3, T4, T5, T6, T7> groupByMerge(SQLExpression1<Tuple7<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>, SQLGroupBySelector<T4>, SQLGroupBySelector<T5>, SQLGroupBySelector<T6>, SQLGroupBySelector<T7>>> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default Queryable7<T1, T2, T3, T4, T5, T6, T7> groupByMerge(boolean condition, SQLExpression1<Tuple7<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>, SQLGroupBySelector<T4>, SQLGroupBySelector<T5>, SQLGroupBySelector<T6>, SQLGroupBySelector<T7>>> selectExpression) {
        return groupBy(condition, (t1, t2,  t3, t4, t5, t6, t7) -> {
            selectExpression.apply(new Tuple7<>(t1, t2,  t3, t4, t5, t6, t7));
        });
    }
}
