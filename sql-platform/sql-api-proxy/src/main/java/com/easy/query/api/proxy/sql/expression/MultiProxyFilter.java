package com.easy.query.api.proxy.sql.expression;

import com.easy.query.api.proxy.sql.ProxyFilter;

/**
 * create time 2023/9/17 22:23
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MultiProxyFilter<T1Proxy> extends ProxyFilter {
    T1Proxy t();
}
