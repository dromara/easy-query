package com.easy.query.api.proxy.entity.select.abstraction;

import com.easy.query.api.proxy.entity.select.EntityQueryable2;
import com.easy.query.api.proxy.entity.select.extension.queryable2.override.AbstractOverrideEntityQueryable2;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/23 22:08
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractEntityQueryable2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2>
        extends AbstractOverrideEntityQueryable2<T1Proxy, T1, T2Proxy, T2>
        implements EntityQueryable2<T1Proxy, T1, T2Proxy, T2> {


    protected final T2Proxy t2Proxy;

    public AbstractEntityQueryable2(T1Proxy t1Proxy, T2Proxy t2Proxy, ClientQueryable2<T1, T2> entityQueryable) {
        super(t1Proxy, entityQueryable);
        this.t2Proxy = t2Proxy.create(entityQueryable.getSQLEntityExpressionBuilder().getTable(1).getEntityTable());
    }


    @Override
    public T2Proxy get2Proxy() {
        return t2Proxy;
    }

    @Override
    public ClientQueryable2<T1, T2> getClientQueryable2() {
        return entityQueryable2;
    }

    @Override
    public EntityQueryable2<T1Proxy, T1, T2Proxy, T2> getQueryable2() {
        return this;
    }
}
