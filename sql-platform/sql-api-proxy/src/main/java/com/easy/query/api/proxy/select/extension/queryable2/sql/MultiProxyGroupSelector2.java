package com.easy.query.api.proxy.select.extension.queryable2.sql;

import com.easy.query.api.proxy.sql.ProxyGroupSelector;

/**
 * create time 2023/9/9 22:25
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MultiProxyGroupSelector2<T1Proxy, T2Proxy> extends ProxyGroupSelector {
    /**
     * 第一张表
     * @return
     */
    T1Proxy t();

    /**
     * 第二张表
     * @return
     */
    T2Proxy t1();
}
