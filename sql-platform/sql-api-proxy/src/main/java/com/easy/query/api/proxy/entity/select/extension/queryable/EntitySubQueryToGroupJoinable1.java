package com.easy.query.api.proxy.entity.select.extension.queryable;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ManyPropColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;

/**
 * create time 2025/3/7 21:40
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntitySubQueryToGroupJoinable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> {
    /**
     * 请使用{@link #subQueryToGroupJoin(SQLFuncExpression1)}
     * @param manyPropColumnExpression
     * @return
     * @param <T2Proxy>
     * @param <T2>
     */
    @Deprecated
    default <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>> EntityQueryable<T1Proxy, T1> manyJoin(SQLFuncExpression1<T1Proxy, ManyPropColumn<T2Proxy, T2>> manyPropColumnExpression) {
        return subQueryToGroupJoin(true, manyPropColumnExpression);
    }

    /**
     * 请使用{@link #subQueryToGroupJoin(boolean, SQLFuncExpression1)}
     *
     * @param condition
     * @param manyPropColumnExpression
     * @param <T2Proxy>
     * @param <T2>
     * @return
     */
    @Deprecated
    default <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>> EntityQueryable<T1Proxy, T1> manyJoin(boolean condition, SQLFuncExpression1<T1Proxy, ManyPropColumn<T2Proxy, T2>> manyPropColumnExpression) {
        return subQueryToGroupJoin(condition, manyPropColumnExpression);
    }
    default <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>> EntityQueryable<T1Proxy, T1> subQueryToGroupJoin(SQLFuncExpression1<T1Proxy, ManyPropColumn<T2Proxy, T2>> manyPropColumnExpression) {
        return subQueryToGroupJoin(true, manyPropColumnExpression);
    }
    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>> EntityQueryable<T1Proxy, T1> subQueryToGroupJoin(boolean condition, SQLFuncExpression1<T1Proxy, ManyPropColumn<T2Proxy, T2>> manyPropColumnExpression);
}
