package com.easy.query.api4j.select.extension.queryable3;

import com.easy.query.api4j.select.Queryable3;
import com.easy.query.api4j.sql.SQLGroupBySelector;
import com.easy.query.api4j.sql.impl.SQLGroupBySelectorImpl;
import com.easy.query.core.common.tuple.Tuple3;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression3;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLGroupable3<T1, T2, T3> extends ClientQueryable3Available<T1, T2, T3>, Queryable3Available<T1, T2, T3> {


    default Queryable3<T1, T2, T3> groupBy(SQLExpression3<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>> selectExpression) {
        getClientQueryable3().groupBy((selector1, selector2, selector3) -> {
            selectExpression.apply(new SQLGroupBySelectorImpl<>(selector1), new SQLGroupBySelectorImpl<>(selector2), new SQLGroupBySelectorImpl<>(selector3));
        });
        return getQueryable3();
    }

    default Queryable3<T1, T2, T3> groupBy(boolean condition, SQLExpression3<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>> selectExpression) {
        getClientQueryable3().groupBy(condition, (selector1, selector2, selector3) -> {
            selectExpression.apply(new SQLGroupBySelectorImpl<>(selector1), new SQLGroupBySelectorImpl<>(selector2), new SQLGroupBySelectorImpl<>(selector3));
        });
        return getQueryable3();
    }

    default Queryable3<T1, T2, T3> groupByMerge(SQLExpression1<Tuple3<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>>> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default Queryable3<T1, T2, T3> groupByMerge(boolean condition, SQLExpression1<Tuple3<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>>> selectExpression) {
        return groupBy(condition, (t, t1,t3) -> {
            selectExpression.apply(new Tuple3<>(t, t1,t3));
        });
    }
}
