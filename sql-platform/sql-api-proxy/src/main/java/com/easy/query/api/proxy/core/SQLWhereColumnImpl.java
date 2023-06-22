package com.easy.query.api.proxy.core;

import java.io.Serializable;

/**
 * create time 2023/6/21 16:56
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLWhereColumnImpl<TProxy> implements SQLWhereColumn<TProxy>, Serializable {
    private final TProxy proxy;
    private final String property;

    public SQLWhereColumnImpl(TProxy tableDef,String property){

        this.proxy = tableDef;
        this.property = property;
    }

    @Override
    public TProxy eq(Object value) {
        return proxy;
    }

    @Override
    public TProxy like(Object value) {
        return proxy;
    }

    @Override
    public TProxy isNull() {
        return proxy;
    }

    @Override
    public TProxy isNotNull() {
        return proxy;
    }

}
