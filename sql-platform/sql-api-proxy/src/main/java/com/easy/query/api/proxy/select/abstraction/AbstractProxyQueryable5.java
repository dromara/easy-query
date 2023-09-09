package com.easy.query.api.proxy.select.abstraction;

import com.easy.query.api.proxy.select.ProxyQueryable5;
import com.easy.query.api.proxy.select.extension.queryable5.override.AbstractOverrideProxyQueryable5;
import com.easy.query.core.basic.api.select.ClientQueryable5;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/23 22:08
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractProxyQueryable5<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5>
        extends AbstractOverrideProxyQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5>
        implements ProxyQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> {


    protected final T2Proxy t2Proxy;
    protected final T3Proxy t3Proxy;
    protected final T4Proxy t4Proxy;
    protected final T5Proxy t5Proxy;

    public AbstractProxyQueryable5(T1Proxy t1Proxy, T2Proxy t2Proxy, T3Proxy t3Proxy, T4Proxy t4Proxy, T5Proxy t5Proxy, ClientQueryable5<T1, T2, T3, T4, T5> entityQueryable) {
        super(t1Proxy, entityQueryable);
        this.t2Proxy = t2Proxy.create(entityQueryable.getSQLEntityExpressionBuilder().getTable(1).getEntityTable());
        this.t3Proxy = t3Proxy.create(entityQueryable.getSQLEntityExpressionBuilder().getTable(2).getEntityTable());
        this.t4Proxy = t4Proxy.create(entityQueryable.getSQLEntityExpressionBuilder().getTable(3).getEntityTable());
        this.t5Proxy = t5Proxy.create(entityQueryable.getSQLEntityExpressionBuilder().getTable(4).getEntityTable());
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
    public T5Proxy get5Proxy() {
        return t5Proxy;
    }

    @Override
    public ClientQueryable5<T1, T2, T3, T4, T5> getClientQueryable5() {
        return entityQueryable5;
    }


    @Override
    public ProxyQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> getQueryable5() {
        return this;
    }
}

