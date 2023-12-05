package com.easy.query.core.proxy;

/**
 * create time 2023/11/25 22:38
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyEntityAvailable<TEntity,TProxy extends ProxyEntity<TProxy, TEntity>> {
    Class<TProxy> proxyTableClass();
}
