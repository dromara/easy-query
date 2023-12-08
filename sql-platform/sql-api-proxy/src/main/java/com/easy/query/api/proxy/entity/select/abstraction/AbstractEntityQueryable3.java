package com.easy.query.api.proxy.entity.select.abstraction;

import com.easy.query.api.proxy.entity.select.EntityQueryable3;
import com.easy.query.api.proxy.entity.select.extension.queryable3.override.AbstractOverrideEntityQueryable3;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/23 22:08
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractEntityQueryable3<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2, T3Proxy extends ProxyEntity<T3Proxy, T3>, T3>
        extends AbstractOverrideEntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3>
        implements EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> {


    protected final T2Proxy t2Proxy;
    protected final T3Proxy t3Proxy;

    public AbstractEntityQueryable3(T1Proxy t1Proxy, T2Proxy t2Proxy, T3Proxy t3Proxy, ClientQueryable3<T1, T2, T3> entityQueryable) {
        super(t1Proxy,entityQueryable);
        this.t2Proxy = t2Proxy.create(entityQueryable.getSQLEntityExpressionBuilder().getTable(1).getEntityTable(),t1Proxy.getEntitySQLContext());
        this.t3Proxy = t3Proxy.create(entityQueryable.getSQLEntityExpressionBuilder().getTable(2).getEntityTable(),t1Proxy.getEntitySQLContext());
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
    public EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> getQueryable3() {
        return this;
    }

    @Override
    public ClientQueryable3<T1, T2, T3> getClientQueryable3() {
        return entityQueryable3;
    }
}
