package com.easy.query.api.proxy.select.abstraction;

import com.easy.query.api.proxy.select.extension.queryable4.AbstractOverrideProxyQueryable4;
import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/23 22:08
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractProxyQueryable4<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2, T3Proxy extends ProxyEntity<T3Proxy, T3>, T3, T4Proxy extends ProxyEntity<T4Proxy, T4>, T4>
        extends AbstractOverrideProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> {


    protected final T1Proxy t1Proxy;
    protected final T2Proxy t2Proxy;
    protected final T3Proxy t3Proxy;
    protected final T4Proxy t4Proxy;

    public AbstractProxyQueryable4(T1Proxy t1Proxy, T2Proxy t2Proxy, T3Proxy t3Proxy, T4Proxy t4Proxy, ClientQueryable4<T1, T2, T3, T4> entityQueryable) {
        super(entityQueryable);
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = entityQueryable.getSQLEntityExpressionBuilder();
        this.t1Proxy = t1Proxy.create(sqlEntityExpressionBuilder.getTable(0).getEntityTable(),sqlEntityExpressionBuilder,getRuntimeContext());
        this.t2Proxy = t2Proxy.create(sqlEntityExpressionBuilder.getTable(1).getEntityTable(),sqlEntityExpressionBuilder,getRuntimeContext());
        this.t3Proxy = t3Proxy.create(sqlEntityExpressionBuilder.getTable(2).getEntityTable(),sqlEntityExpressionBuilder,getRuntimeContext());
        this.t4Proxy = t4Proxy.create(sqlEntityExpressionBuilder.getTable(3).getEntityTable(),sqlEntityExpressionBuilder,getRuntimeContext());
    }

    @Override
    public T1Proxy get1Proxy() {
        return t1Proxy;
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

}

