package com.easy.query.api.proxy.select.abstraction;

import com.easy.query.api.proxy.select.extension.queryable2.AbstractOverrideProxyQueryable2;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/23 22:08
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractProxyQueryable2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> extends AbstractOverrideProxyQueryable2<T1Proxy, T1,T2Proxy,T2> {

    protected final T1Proxy t1Proxy;
    protected final QueryRuntimeContext runtimeContext;
    protected final ClientQueryable2<T1,T2> entityQueryable;
    private final T2Proxy t2Proxy;

    public AbstractProxyQueryable2(T1Proxy t1Proxy,T2Proxy t2Proxy, ClientQueryable2<T1,T2> entityQueryable) {
        super(entityQueryable);
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = entityQueryable.getSQLEntityExpressionBuilder();
        this.runtimeContext = sqlEntityExpressionBuilder.getRuntimeContext();
        this.t1Proxy = t1Proxy.create(sqlEntityExpressionBuilder.getTable(0).getEntityTable());
        this.t2Proxy = t2Proxy.create(sqlEntityExpressionBuilder.getTable(1).getEntityTable());
        this.entityQueryable = entityQueryable;
    }

    @Override
    public T1Proxy get1Proxy() {
        return t1Proxy;
    }
    @Override
    public T2Proxy get2Proxy() {
        return t2Proxy;
    }

}
