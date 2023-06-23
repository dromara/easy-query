package com.easy.query.api.proxy.sql;

import com.easy.query.api.proxy.core.ProxyQuery;
import com.easy.query.api.proxy.core.base.SQLColumn;
import com.easy.query.core.expression.builder.OrderSelector;

/**
 * create time 2023/6/23 15:01
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyOrderSelector {
    OrderSelector getOrderSelector();


    OrderSelector columns(SQLColumn<?>... column);
    OrderSelector column(SQLColumn<?> column);

    OrderSelector columnFunc(ProxyColumnPropertyFunction proxyColumnPropertyFunction);

    <TProxy extends ProxyQuery<TProxy, TEntity>, TEntity> OrderSelector columnConst(TProxy proxy, String columnConst);

    OrderSelector columnIgnore(SQLColumn<?> column);

    <TProxy extends ProxyQuery<TProxy, TEntity>, TEntity> OrderSelector columnAll(TProxy proxy);
}
