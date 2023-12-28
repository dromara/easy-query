package com.easy.query.api.proxy.entity.select.extension.queryable;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLGroupByExpression;

/**
 * create time 2023/12/4 10:22
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityGroupable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> {

    default EntityQueryable<T1Proxy, T1> groupByFlat(SQLFuncExpression1<T1Proxy, SQLGroupByExpression> selectExpression) {
        return groupByFlat(true, selectExpression);
    }

    EntityQueryable<T1Proxy, T1> groupByFlat(boolean condition, SQLFuncExpression1<T1Proxy, SQLGroupByExpression> selectExpression);
    <TRProxy extends ProxyEntity<TRProxy, TR> & SQLGroupByExpression , TR> EntityQueryable<TRProxy, TR> groupBy(SQLFuncExpression1<T1Proxy, SQLFuncExpression1<T1Proxy,TRProxy>> selectExpression);
}
