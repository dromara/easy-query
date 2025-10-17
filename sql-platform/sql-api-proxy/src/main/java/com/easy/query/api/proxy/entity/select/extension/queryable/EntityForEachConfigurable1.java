package com.easy.query.api.proxy.entity.select.extension.queryable;

import com.easy.query.core.basic.api.select.executor.MethodQuery;
import com.easy.query.core.proxy.ProxyEntity;

import java.util.function.Consumer;

/**
 * create time 2025/10/17 12:03
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityForEachConfigurable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> {
    MethodQuery<T1> forEach(Consumer<T1> mapConfigure);
}
