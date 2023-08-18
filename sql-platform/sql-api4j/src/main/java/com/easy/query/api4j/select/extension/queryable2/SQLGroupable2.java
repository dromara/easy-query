package com.easy.query.api4j.select.extension.queryable2;

import com.easy.query.api4j.select.Queryable2;
import com.easy.query.api4j.sql.SQLGroupBySelector;
import com.easy.query.api4j.sql.impl.SQLGroupBySelectorImpl;
import com.easy.query.core.common.tuple.Tuple2;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLGroupable2<T1, T2> extends ClientQueryable2Available<T1,T2>, Queryable2Available<T1,T2> {


    default Queryable2<T1, T2> groupBy(SQLExpression2<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>> selectExpression) {
        getClientQueryable2().groupBy((selector1, selector2) -> {
            selectExpression.apply(new SQLGroupBySelectorImpl<>(selector1), new SQLGroupBySelectorImpl<>(selector2));
        });
        return getQueryable2();
    }

    default Queryable2<T1, T2> groupBy(boolean condition, SQLExpression2<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>> selectExpression) {
        getClientQueryable2().groupBy(condition, (selector1, selector2) -> {
            selectExpression.apply(new SQLGroupBySelectorImpl<>(selector1), new SQLGroupBySelectorImpl<>(selector2));
        });
        return getQueryable2();
    }

    default Queryable2<T1, T2> groupByMerge(SQLExpression1<Tuple2<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>>> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default Queryable2<T1, T2> groupByMerge(boolean condition, SQLExpression1<Tuple2<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>>> selectExpression) {
        return groupBy(condition, (t, t1) -> {
            selectExpression.apply(new Tuple2<>(t, t1));
        });
    }
}
