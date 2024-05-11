package com.easy.query.test.entity.proxy;

import com.easy.query.core.proxy.AbstractProxyEntity;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2024/5/9 20:30
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class MyAbstractProxyEntity<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity> extends AbstractProxyEntity<TProxy, TEntity> {
    @Override
    protected MySQLStringTypeColumn<TProxy> getStringTypeColumn(String property) {
        return new MySQLStringTypeColumnImpl<>(entitySQLContext, table, property);
    }
}
