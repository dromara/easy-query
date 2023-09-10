package com.easy.query.api.proxy.select.extension.queryable.sql;

import com.easy.query.api.proxy.sql.ProxySelector;

/**
 * create time 2023/9/10 13:06
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MultiProxySelector1<T1Proxy> extends ProxySelector {
    /**
     * 第一张表
     *
     * @return
     */
    T1Proxy t();
}
