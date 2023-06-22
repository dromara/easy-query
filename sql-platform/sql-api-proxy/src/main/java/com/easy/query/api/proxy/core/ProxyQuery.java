package com.easy.query.api.proxy.core;

import com.easy.query.api.proxy.core.base.TableProxy;

/**
 * create time 2023/6/21 23:37
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyQuery<TProxy extends ProxyQuery<TProxy, TEntity>, TEntity>
        extends TableProxy<TProxy, TEntity> {
}
