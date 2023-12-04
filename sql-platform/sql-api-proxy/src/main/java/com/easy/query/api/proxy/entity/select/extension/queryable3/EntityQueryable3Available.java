package com.easy.query.api.proxy.entity.select.extension.queryable3;

import com.easy.query.api.proxy.entity.select.EntityQueryable3;
import com.easy.query.api.proxy.entity.select.extension.EntityAvailable;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/17 11:14
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityQueryable3Available<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1
        , T2Proxy extends ProxyEntity<T2Proxy, T2>
        , T2, T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> extends EntityAvailable<T1Proxy, T1> {
    EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> getQueryable3();
    T2Proxy get2Proxy();
    T3Proxy get3Proxy();
}
