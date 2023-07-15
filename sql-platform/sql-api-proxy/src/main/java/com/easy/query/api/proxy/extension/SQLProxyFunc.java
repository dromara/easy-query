package com.easy.query.api.proxy.extension;

import com.easy.query.api.proxy.extension.casewhen.CaseWhenProxyBuilder;
import com.easy.query.api.proxy.sql.ProxyAsSelector;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/7/3 08:26
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLProxyFunc {
    public static <TRProxy extends ProxyEntity<TRProxy, TR>, TR> CaseWhenProxyBuilder<TRProxy,TR> caseWhenBuilder(ProxyAsSelector<TRProxy,TR> proxyAsSelector){
        return new CaseWhenProxyBuilder<>(proxyAsSelector);
    }
}
