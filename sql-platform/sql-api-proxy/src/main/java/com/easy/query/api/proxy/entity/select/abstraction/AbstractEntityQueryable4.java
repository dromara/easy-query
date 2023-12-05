package com.easy.query.api.proxy.entity.select.abstraction;

import com.easy.query.api.proxy.entity.select.EntityQueryable4;
import com.easy.query.api.proxy.entity.select.extension.queryable4.override.AbstractOverrideEntityQueryable4;
import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/23 22:08
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractEntityQueryable4<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2, T3Proxy extends ProxyEntity<T3Proxy, T3>, T3, T4Proxy extends ProxyEntity<T4Proxy, T4>, T4>
        extends AbstractOverrideEntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4>
        implements EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> {


    protected final T2Proxy t2Proxy;
    protected final T3Proxy t3Proxy;
    protected final T4Proxy t4Proxy;

    public AbstractEntityQueryable4(T1Proxy t1Proxy, T2Proxy t2Proxy, T3Proxy t3Proxy, T4Proxy t4Proxy, ClientQueryable4<T1, T2, T3, T4> entityQueryable) {
        super(t1Proxy, entityQueryable);
        this.t2Proxy = t2Proxy.create(entityQueryable.getSQLEntityExpressionBuilder().getTable(1).getEntityTable());
        this.t3Proxy = t3Proxy.create(entityQueryable.getSQLEntityExpressionBuilder().getTable(2).getEntityTable());
        this.t4Proxy = t4Proxy.create(entityQueryable.getSQLEntityExpressionBuilder().getTable(3).getEntityTable());
    }

    @Override
    public T2Proxy get2Proxy() {
        return t2Proxy;
    }

    @Override
    public T3Proxy get3Proxy() {
        return t3Proxy;
    }

    @Override
    public T4Proxy get4Proxy() {
        return t4Proxy;
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> getClientQueryable4() {
        return entityQueryable4;
    }


    @Override
    public EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> getQueryable4() {
        return this;
    }
}

