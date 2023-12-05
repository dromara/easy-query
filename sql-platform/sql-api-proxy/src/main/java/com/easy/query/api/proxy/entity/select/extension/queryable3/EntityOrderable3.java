package com.easy.query.api.proxy.entity.select.extension.queryable3;

import com.easy.query.api.proxy.entity.select.EntityQueryable3;
import com.easy.query.core.common.tuple.Tuple3;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression3;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLOrderByExpression;

/**
 * create time 2023/8/16 08:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityOrderable3<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> extends ClientEntityQueryable3Available<T1, T2, T3>, EntityQueryable3Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> {

    default EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> orderBy(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLOrderByExpression> selectExpression) {
        return orderBy(true, selectExpression);
    }

    default EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> orderBy(boolean condition, SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLOrderByExpression> selectExpression) {
        if (condition) {
            getClientQueryable3().orderByAsc((selector1, selector2, selector3) -> {
                SQLOrderByExpression sqlOrderSelect = selectExpression.apply(get1Proxy(), get2Proxy(), get3Proxy());
                sqlOrderSelect.accept(selector1.getOrderSelector());
            });
        }
        return getQueryable3();
    }

    default EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> orderByMerge(SQLFuncExpression1<Tuple3<T1Proxy, T2Proxy, T3Proxy>, SQLOrderByExpression> selectExpression) {
        return orderByMerge(true, selectExpression);
    }

    default EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> orderByMerge(boolean condition, SQLFuncExpression1<Tuple3<T1Proxy, T2Proxy, T3Proxy>, SQLOrderByExpression> selectExpression) {
        return orderBy(condition, (t1, t2, t3) -> {
            return selectExpression.apply(new Tuple3<>(t1, t2, t3));
        });
    }

}
