package com.easy.query.api.proxy.entity.select.extension.queryable3;

import com.easy.query.api.proxy.entity.select.EntityQueryable3;
import com.easy.query.core.common.tuple.MergeTuple3;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression3;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLGroupByExpression;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityGroupable3<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> extends ClientEntityQueryable3Available<T1, T2, T3>, EntityQueryable3Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> {


    default EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> groupBy(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLGroupByExpression> selectExpression) {
        return groupBy(true, selectExpression);
    }

    default EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> groupBy(boolean condition, SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLGroupByExpression> selectExpression) {
        if (condition) {
            getClientQueryable3().groupBy((selector1, selector2, selector3) -> {
                SQLGroupByExpression sqlGroupSelect = selectExpression.apply(get1Proxy(), get2Proxy(), get3Proxy());
                sqlGroupSelect.accept(selector1.getGroupSelector());
            });
        }
        return getQueryable3();
    }

    default EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> groupByMerge(SQLFuncExpression1<MergeTuple3<T1Proxy, T2Proxy, T3Proxy>, SQLGroupByExpression> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> groupByMerge(boolean condition, SQLFuncExpression1<MergeTuple3<T1Proxy, T2Proxy, T3Proxy>, SQLGroupByExpression> selectExpression) {
        return groupBy(condition, (t1, t2, t3) -> {
            return selectExpression.apply(new MergeTuple3<>(t1, t2, t3));
        });
    }
}
