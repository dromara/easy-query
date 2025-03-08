package com.easy.query.api.proxy.entity.select.extension.queryable;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.common.ValueHolder;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ManyPropColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2025/3/7 21:40
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityManyGroupJoinable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends EntityQueryableAvailable<T1Proxy, T1>, ClientEntityQueryableAvailable<T1> {
    default <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>> EntityQueryable<T1Proxy, T1> manyJoin(SQLFuncExpression1<T1Proxy, ManyPropColumn> manyPropColumnExpression) {
        return manyJoin(true, manyPropColumnExpression);
    }

    default <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>> EntityQueryable<T1Proxy, T1> manyJoin(boolean condition, SQLFuncExpression1<T1Proxy, ManyPropColumn> manyPropColumnExpression) {
        if (condition) {

            T1Proxy proxy = getQueryable().get1Proxy();
            ValueHolder<ManyPropColumn> valueHolder = new ValueHolder<>();
            proxy.getEntitySQLContext()._include(() -> {
                ManyPropColumn value = manyPropColumnExpression.apply(proxy);
                valueHolder.setValue(value);
            });
            getClientQueryable().manyGroupJoin(() -> valueHolder.getValue().getValue());
        }
        return EasyObjectUtil.typeCastNullable(this);
    }

}
