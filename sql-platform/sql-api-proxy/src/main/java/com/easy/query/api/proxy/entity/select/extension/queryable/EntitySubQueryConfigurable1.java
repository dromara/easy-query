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
public interface EntitySubQueryConfigurable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> {
    /**
     * 请使用{@link #subQueryConfigure(SQLFuncExpression1, SQLFuncExpression1)}
     * @param manyPropColumnExpression
     * @param adapterExpression
     * @return
     * @param <T2Proxy>
     * @param <T2>
     */
    @Deprecated
    default <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>> EntityQueryable<T1Proxy, T1> manyConfigure(SQLFuncExpression1<T1Proxy, ManyPropColumn<T2Proxy,T2>> manyPropColumnExpression,
                                                                                                                                           SQLFuncExpression1<EntityQueryable<T2Proxy,T2>,EntityQueryable<T2Proxy,T2>> adapterExpression) {
        return subQueryConfigure(true, manyPropColumnExpression,adapterExpression);
    } 
    default <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>> EntityQueryable<T1Proxy, T1> subQueryConfigure(SQLFuncExpression1<T1Proxy, ManyPropColumn<T2Proxy,T2>> manyPropColumnExpression,
                                                                                                                                           SQLFuncExpression1<EntityQueryable<T2Proxy,T2>,EntityQueryable<T2Proxy,T2>> adapterExpression) {
        return subQueryConfigure(true, manyPropColumnExpression,adapterExpression);
    }

    /**
     * 请使用{@link #subQueryConfigure(boolean, SQLFuncExpression1, SQLFuncExpression1)}
     * @param condition
     * @param manyPropColumnExpression
     * @param adapterExpression
     * @return
     * @param <T2Proxy>
     * @param <T2>
     */
    @Deprecated
   default  <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>> EntityQueryable<T1Proxy, T1> manyConfigure(boolean condition, SQLFuncExpression1<T1Proxy, ManyPropColumn<T2Proxy,T2>> manyPropColumnExpression,
                                                                                                                                           SQLFuncExpression1<EntityQueryable<T2Proxy,T2>,EntityQueryable<T2Proxy,T2>> adapterExpression){
        return subQueryConfigure(condition, manyPropColumnExpression,adapterExpression);
   }

    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>> EntityQueryable<T1Proxy, T1> subQueryConfigure(boolean condition, SQLFuncExpression1<T1Proxy, ManyPropColumn<T2Proxy,T2>> manyPropColumnExpression,
                                                                                                                                           SQLFuncExpression1<EntityQueryable<T2Proxy,T2>,EntityQueryable<T2Proxy,T2>> adapterExpression);

}
