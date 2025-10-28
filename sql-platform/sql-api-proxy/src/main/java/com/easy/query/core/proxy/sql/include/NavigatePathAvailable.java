package com.easy.query.core.proxy.sql.include;

import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyNavValueAvailable;

/**
 * create time 2025/10/28 13:08
 * 文件说明
 *
 * @author xuejiaming
 */
public interface NavigatePathAvailable<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends ProxyNavValueAvailable {

    T1Proxy __createNavigatePathEmpty();
}
