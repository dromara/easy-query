package com.easy.query.api.proxy.select.extension.queryable8.sql;

import com.easy.query.api.proxy.sql.ProxyAggregateFilter;

/**
 * create time 2023/9/10 10:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MultiProxyAggregateFilter8<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy> extends ProxyAggregateFilter {
    /**
     * 第一张表
     *
     * @return
     */
    T1Proxy t();

    /**
     * 第二张表
     *
     * @return
     */
    T2Proxy t1();

    /**
     * 第三张表
     *
     * @return
     */
    T3Proxy t2();

    /**
     * 第四张表
     *
     * @return
     */
    T4Proxy t3();

    /**
     * 第五张表
     *
     * @return
     */
    T5Proxy t4();

    /**
     * 第六张表
     *
     * @return
     */
    T6Proxy t5();

    /**
     * 第七张表
     *
     * @return
     */
    T7Proxy t6();

    /**
     * 第八张表
     *
     * @return
     */
    T8Proxy t7();
}
