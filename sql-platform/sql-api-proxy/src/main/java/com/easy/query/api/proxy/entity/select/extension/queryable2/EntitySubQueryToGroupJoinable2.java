package com.easy.query.api.proxy.entity.select.extension.queryable2;

import com.easy.query.api.proxy.entity.select.EntityQueryable2;
import com.easy.query.core.common.ValueHolder;
import com.easy.query.core.expression.lambda.SQLFuncExpression2;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.ManyPropColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;

/**
 * create time 2025/3/7 21:40
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntitySubQueryToGroupJoinable2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> extends ClientEntityQueryable2Available<T1, T2>, EntityQueryable2Available<T1Proxy, T1, T2Proxy, T2> {
    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR extends ProxyEntityAvailable<TR, TRProxy>> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> subQueryToGroupJoin(SQLFuncExpression2<T1Proxy, T2Proxy, ManyPropColumn<TRProxy, TR>> manyPropColumnExpression) {
        return subQueryToGroupJoin(true, manyPropColumnExpression);
    }

    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR extends ProxyEntityAvailable<TR, TRProxy>> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> subQueryToGroupJoin(boolean condition, SQLFuncExpression2<T1Proxy, T2Proxy, ManyPropColumn<TRProxy, TR>> manyPropColumnExpression) {
        if (condition) {

            ValueHolder<ManyPropColumn<TRProxy, TR>> valueHolder = new ValueHolder<>();
            get1Proxy().getEntitySQLContext()._include(() -> {
                ManyPropColumn<TRProxy, TR> value = manyPropColumnExpression.apply(get1Proxy(), get2Proxy());
                valueHolder.setValue(value);
            });
            TableAvailable table = valueHolder.getValue().getOriginalTable();
            String value = valueHolder.getValue().getValue();
            getClientQueryable2().subQueryToGroupJoin((mj1, mj2) -> mj1.subQueryProperty(table, value));

        }
        return getQueryable2();
    }

}
