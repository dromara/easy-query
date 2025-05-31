package com.easy.query.core.proxy;

import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.basic.api.select.Query;

/**
 * create time 2023/11/25 22:38
 * 当前注解只需要让数据库对象继承即可
 *
 * @author xuejiaming
 */
public interface ViewProxyEntityAvailable<TEntity, TProxy extends ProxyEntity<TProxy, TEntity>> extends ProxyEntityAvailable<TEntity, TProxy> {
}
