package com.easy.query.api.proxy.select.extension.queryable.sql;

import com.easy.query.api.proxy.sql.ProxyOrderSelector;

/**
 * create time 2023/9/9 22:25
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MultiProxyOrderSelector1<T1Proxy> extends ProxyOrderSelector {
    /**
     * 第一张表
     * @return
     */
    T1Proxy t();
}
