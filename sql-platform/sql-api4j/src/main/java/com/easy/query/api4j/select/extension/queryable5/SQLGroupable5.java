package com.easy.query.api4j.select.extension.queryable5;

import com.easy.query.api4j.select.Queryable5;
import com.easy.query.api4j.sql.SQLGroupBySelector;
import com.easy.query.api4j.sql.impl.SQLGroupBySelectorImpl;
import com.easy.query.core.common.tuple.Tuple5;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression5;

/**
 * create time 2023/8/18 09:33
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLGroupable5<T1, T2, T3, T4, T5> extends ClientQueryable5Available<T1, T2, T3, T4, T5>, Queryable5Available<T1, T2, T3, T4, T5> {

    default Queryable5<T1, T2, T3, T4, T5> groupBy(SQLExpression5<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>, SQLGroupBySelector<T4>, SQLGroupBySelector<T5>> selectExpression) {
        getClientQueryable5().groupBy((selector1, selector2, selector3, selector4, selector5) -> {
            selectExpression.apply(new SQLGroupBySelectorImpl<>(selector1), new SQLGroupBySelectorImpl<>(selector2), new SQLGroupBySelectorImpl<>(selector3), new SQLGroupBySelectorImpl<>(selector4), new SQLGroupBySelectorImpl<>(selector5));
        });
        return getQueryable5();
    }

    default Queryable5<T1, T2, T3, T4, T5> groupBy(boolean condition, SQLExpression5<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>, SQLGroupBySelector<T4>, SQLGroupBySelector<T5>> selectExpression) {
        getClientQueryable5().groupBy(condition, (selector1, selector2, selector3, selector4, selector5) -> {
            selectExpression.apply(new SQLGroupBySelectorImpl<>(selector1), new SQLGroupBySelectorImpl<>(selector2), new SQLGroupBySelectorImpl<>(selector3), new SQLGroupBySelectorImpl<>(selector4), new SQLGroupBySelectorImpl<>(selector5));
        });
        return getQueryable5();
    }

    default Queryable5<T1, T2, T3, T4, T5> groupByMerge(SQLExpression1<Tuple5<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>, SQLGroupBySelector<T4>, SQLGroupBySelector<T5>>> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default Queryable5<T1, T2, T3, T4, T5> groupByMerge(boolean condition, SQLExpression1<Tuple5<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>, SQLGroupBySelector<T4>, SQLGroupBySelector<T5>>> selectExpression) {
        return groupBy(condition, (t1, t2, t3, t4, t5) -> {
            selectExpression.apply(new Tuple5<>(t1, t2, t3, t4, t5));
        });
    }
}