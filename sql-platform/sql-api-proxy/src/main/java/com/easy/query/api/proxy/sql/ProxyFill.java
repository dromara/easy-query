package com.easy.query.api.proxy.sql;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/9/9 14:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyFill {
    <TRProxyEntity extends ProxyEntity<TRProxyEntity,TREntity>,TREntity> ProxyQueryable<TRProxyEntity,TREntity> with(TRProxyEntity proxy);
    ProxyFill consumeNull(boolean consumeNull);
}
