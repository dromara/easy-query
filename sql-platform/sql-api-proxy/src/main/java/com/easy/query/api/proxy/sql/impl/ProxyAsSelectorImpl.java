package com.easy.query.api.proxy.sql.impl;

import com.easy.query.api.proxy.core.ProxyQuery;
import com.easy.query.api.proxy.sql.ProxyAsSelector;
import com.easy.query.core.expression.builder.AsSelector;

/**
 * create time 2023/6/23 23:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class ProxyAsSelectorImpl<TRProxy extends ProxyQuery<TRProxy, TR>, TR> implements ProxyAsSelector<TRProxy,TR> {
    private final AsSelector asSelector;

    public ProxyAsSelectorImpl(AsSelector asSelector){

        this.asSelector = asSelector;
    }
    @Override
    public AsSelector getAsSelector() {
        return asSelector;
    }
}
