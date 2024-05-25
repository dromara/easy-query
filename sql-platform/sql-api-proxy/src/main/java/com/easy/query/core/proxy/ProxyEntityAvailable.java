package com.easy.query.core.proxy;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;

/**
 * create time 2023/11/25 22:38
 * 当前注解只需要让数据库对象继承即可
 *
 * @author xuejiaming
 */
public interface ProxyEntityAvailable<TEntity, TProxy extends ProxyEntity<TProxy, TEntity>> {
    /**
     * 这个方法即将废弃掉
     * 并且后续更新插件将不会实现该接口
     * @return
     */
    @Deprecated
    default Class<TProxy> proxyTableClass() {
        throw new EasyQueryInvalidOperationException("proxyTableClass not support");
    }
}
