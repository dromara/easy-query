package com.easy.query.core.proxy;

/**
 * create time 2023/11/25 22:38
 * 当前注解只需要让数据库对象继承即可
 *
 * @author xuejiaming
 */
public interface ProxyEntityAvailable<TEntity, TProxy extends ProxyEntity<TProxy, TEntity>> {
}
