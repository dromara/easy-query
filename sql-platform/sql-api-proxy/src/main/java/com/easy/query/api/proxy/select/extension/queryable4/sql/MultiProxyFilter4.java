package com.easy.query.api.proxy.select.extension.queryable4.sql;

import com.easy.query.api.proxy.sql.ProxyFilter;

/**
 * create time 2023/9/9 22:25
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MultiProxyFilter4<T1Proxy, T2Proxy, T3Proxy, T4Proxy> extends ProxyFilter {
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

    /**
     * 第三张表
     * @return
     */
    T3Proxy t2();

    /**
     * 第四张表
     * @return
     */
    T4Proxy t3();
}
