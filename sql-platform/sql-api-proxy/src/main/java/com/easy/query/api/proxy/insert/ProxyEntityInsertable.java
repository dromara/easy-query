package com.easy.query.api.proxy.insert;

import com.easy.query.api.proxy.internal.ProxyOnDuplicateKeyUpdate;
import com.easy.query.core.basic.api.insert.ClientInsertable;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/9/17 22:01
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyEntityInsertable<TProxy extends ProxyEntity<TProxy, T>, T> extends ProxyOnDuplicateKeyUpdate<TProxy, T, ProxyEntityInsertable<TProxy, T>> {
    ClientInsertable<T> getClientInsert();
}
