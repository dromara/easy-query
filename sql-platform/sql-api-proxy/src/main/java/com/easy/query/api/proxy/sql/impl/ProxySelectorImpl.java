package com.easy.query.api.proxy.sql.impl;

import com.easy.query.api.proxy.core.base.SQLColumn;
import com.easy.query.api.proxy.sql.ProxyColumnPropertyFunction;
import com.easy.query.api.proxy.sql.ProxySelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

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
    public ProxySelector columns(SQLColumn<?>... column) {
        return null;
    }

    @Override
    public ProxySelector column(SQLColumn<?> column) {
        return null;
    }

    @Override
    public ProxySelector columnFunc(ProxyColumnPropertyFunction proxyColumnPropertyFunction) {
        return null;
    }

    @Override
    public ProxySelector columnIgnore(SQLColumn<?> column) {
        return null;
    }

    @Override
    public ProxySelector columnAll(TableAvailable table) {
        return null;
    }
}
