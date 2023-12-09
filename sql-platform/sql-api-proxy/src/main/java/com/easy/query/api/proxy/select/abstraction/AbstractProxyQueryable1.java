package com.easy.query.api.proxy.select.abstraction;

import com.easy.query.api.proxy.select.extension.queryable.AbstractOverrideProxyQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/23 22:08
 * 文件说明
 *
 * @author xuejiaming
 */

public abstract class AbstractProxyQueryable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends AbstractOverrideProxyQueryable<T1Proxy, T1> {

    protected final T1Proxy t1Proxy;

    public AbstractProxyQueryable1(T1Proxy t1Proxy, ClientQueryable<T1> entityQueryable) {
        super(entityQueryable);
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = entityQueryable.getSQLEntityExpressionBuilder();
        this.t1Proxy = t1Proxy.create(sqlEntityExpressionBuilder.getTable(0).getEntityTable(),getRuntimeContext());
    }

    @Override
    public T1Proxy get1Proxy() {
        return t1Proxy;
    }
}
