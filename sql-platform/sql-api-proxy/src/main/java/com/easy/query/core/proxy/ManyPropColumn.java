package com.easy.query.core.proxy;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2025/3/8 08:28
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ManyPropColumn<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends PropColumn{

    String getNavValue();

    T1Proxy getProxy();
    TableAvailable getOriginalTable();
}
