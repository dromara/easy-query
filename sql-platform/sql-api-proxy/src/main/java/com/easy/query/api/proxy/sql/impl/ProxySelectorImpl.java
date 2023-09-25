package com.easy.query.api.proxy.sql.impl;

import com.easy.query.api.proxy.sql.ProxySelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.builder.core.SQLNative;
import com.easy.query.core.util.EasyObjectUtil;

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

    @Override
    public <T> SQLNative<T> getSQLNative() {
        return EasyObjectUtil.typeCastNullable(selector);
    }

    @Override
    public ProxySelector castChain() {
        return this;
    }
}
