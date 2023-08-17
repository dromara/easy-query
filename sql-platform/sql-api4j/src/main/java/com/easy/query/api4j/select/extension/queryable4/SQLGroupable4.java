package com.easy.query.api4j.select.extension.queryable4;

import com.easy.query.api4j.select.Queryable4;
import com.easy.query.api4j.sql.SQLGroupBySelector;
import com.easy.query.api4j.sql.impl.SQLGroupBySelectorImpl;
import com.easy.query.core.common.tuple.Tuple4;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression4;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLGroupable4<T1, T2, T3, T4> extends ClientQueryable4Available<T1, T2, T3, T4>, Queryable4Available<T1, T2, T3, T4> {


    default Queryable4<T1, T2, T3, T4> groupBy(SQLExpression4<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>, SQLGroupBySelector<T4>> selectExpression) {
        getClientQueryable4().groupBy((selector1, selector2, selector3, selector4) -> {
            selectExpression.apply(new SQLGroupBySelectorImpl<>(selector1), new SQLGroupBySelectorImpl<>(selector2), new SQLGroupBySelectorImpl<>(selector3), new SQLGroupBySelectorImpl<>(selector4));
        });
        return getQueryable4();
    }

    default Queryable4<T1, T2, T3, T4> groupBy(boolean condition, SQLExpression4<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>, SQLGroupBySelector<T4>> selectExpression) {
        getClientQueryable4().groupBy(condition, (selector1, selector2, selector3, selector4) -> {
            selectExpression.apply(new SQLGroupBySelectorImpl<>(selector1), new SQLGroupBySelectorImpl<>(selector2), new SQLGroupBySelectorImpl<>(selector3), new SQLGroupBySelectorImpl<>(selector4));
        });
        return getQueryable4();
    }

    default Queryable4<T1, T2, T3, T4> groupByMerge(SQLExpression1<Tuple4<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>, SQLGroupBySelector<T4>>> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default Queryable4<T1, T2, T3, T4> groupByMerge(boolean condition, SQLExpression1<Tuple4<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>, SQLGroupBySelector<T4>>> selectExpression) {
        return groupBy(condition, (t, t1, t3, t4) -> {
            selectExpression.apply(new Tuple4<>(t, t1, t3, t4));
        });
    }
}
