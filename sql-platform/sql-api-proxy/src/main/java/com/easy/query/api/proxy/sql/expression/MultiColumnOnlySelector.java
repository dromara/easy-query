package com.easy.query.api.proxy.sql.expression;

import com.easy.query.api.proxy.sql.ProxyColumnOnlySelector;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/9/17 21:51
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MultiColumnOnlySelector<T1Proxy extends ProxyEntity<T1Proxy,T1>,T1> extends ProxyColumnOnlySelector<T1Proxy,T1> {
    /**
     * 第一张表
     *
     * @return
     */
    T1Proxy t();

}
