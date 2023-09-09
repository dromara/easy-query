package com.easy.query.api.proxy.select.abstraction;

import com.easy.query.api.proxy.select.ProxyQueryable10;
import com.easy.query.api.proxy.select.extension.queryable10.override.AbstractOverrideProxyQueryable10;
import com.easy.query.core.basic.api.select.ClientQueryable10;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/23 22:08
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractProxyQueryable10<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6,
        T7Proxy extends ProxyEntity<T7Proxy, T7>, T7,
        T8Proxy extends ProxyEntity<T8Proxy, T8>, T8,
        T9Proxy extends ProxyEntity<T9Proxy, T9>, T9,
        T10Proxy extends ProxyEntity<T10Proxy, T10>, T10>
        extends AbstractOverrideProxyQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10>
        implements ProxyQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> {


    protected final T2Proxy t2Proxy;
    protected final T3Proxy t3Proxy;
    protected final T4Proxy t4Proxy;
    protected final T5Proxy t5Proxy;
    protected final T6Proxy t6Proxy;
    protected final T7Proxy t7Proxy;
    protected final T8Proxy t8Proxy;
    protected final T9Proxy t9Proxy;
    protected final T10Proxy t10Proxy;

    public AbstractProxyQueryable10(T1Proxy t1Proxy, T2Proxy t2Proxy, T3Proxy t3Proxy, T4Proxy t4Proxy, T5Proxy t5Proxy, T6Proxy t6Proxy, T7Proxy t7Proxy, T8Proxy t8Proxy, T9Proxy t9Proxy, T10Proxy t10Proxy, ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9,T10> entityQueryable) {
        super(t1Proxy, entityQueryable);
        this.t2Proxy = t2Proxy.create(entityQueryable.getSQLEntityExpressionBuilder().getTable(1).getEntityTable());
        this.t3Proxy = t3Proxy.create(entityQueryable.getSQLEntityExpressionBuilder().getTable(2).getEntityTable());
        this.t4Proxy = t4Proxy.create(entityQueryable.getSQLEntityExpressionBuilder().getTable(3).getEntityTable());
        this.t5Proxy = t5Proxy.create(entityQueryable.getSQLEntityExpressionBuilder().getTable(4).getEntityTable());
        this.t6Proxy = t6Proxy.create(entityQueryable.getSQLEntityExpressionBuilder().getTable(5).getEntityTable());
        this.t7Proxy = t7Proxy.create(entityQueryable.getSQLEntityExpressionBuilder().getTable(6).getEntityTable());
        this.t8Proxy = t8Proxy.create(entityQueryable.getSQLEntityExpressionBuilder().getTable(7).getEntityTable());
        this.t9Proxy = t9Proxy.create(entityQueryable.getSQLEntityExpressionBuilder().getTable(8).getEntityTable());
        this.t10Proxy = t10Proxy.create(entityQueryable.getSQLEntityExpressionBuilder().getTable(9).getEntityTable());
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
    public T6Proxy get6Proxy() {
        return t6Proxy;
    }

    @Override
    public T7Proxy get7Proxy() {
        return t7Proxy;
    }

    @Override
    public T8Proxy get8Proxy() {
        return t8Proxy;
    }

    @Override
    public T9Proxy get9Proxy() {
        return t9Proxy;
    }

    @Override
    public T10Proxy get10Proxy() {
        return t10Proxy;
    }

    @Override
    public ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9,T10> getClientQueryable10() {
        return entityQueryable10;
    }

    @Override
    public ProxyQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9,T10Proxy,T10> getQueryable10() {
        return this;
    }
}

