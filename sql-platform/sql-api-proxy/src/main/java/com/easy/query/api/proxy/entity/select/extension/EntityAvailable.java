package com.easy.query.api.proxy.entity.select.extension;

import com.easy.query.core.expression.parser.core.available.RuntimeContextAvailable;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/12/4 11:04
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityAvailable<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends RuntimeContextAvailable {

    T1Proxy get1Proxy();
}
