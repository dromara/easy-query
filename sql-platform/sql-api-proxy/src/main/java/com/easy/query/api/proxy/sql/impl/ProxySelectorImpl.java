package com.easy.query.api.proxy.sql.impl;

import com.easy.query.api.proxy.sql.ProxySelector;
import com.easy.query.core.expression.builder.Selector;

/**
 * create time 2023/6/22 22:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class ProxySelectorImpl implements ProxySelector {
    private final Selector selector;

    public ProxySelectorImpl(Selector selector){

        this.selector = selector;
    }
    @Override
    public Selector getSelector() {
        return selector;
    }

}
