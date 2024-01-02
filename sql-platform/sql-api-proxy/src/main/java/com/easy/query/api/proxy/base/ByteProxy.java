package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.PropTypeColumn;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class ByteProxy extends AbstractBasicProxyEntity<ByteProxy, Byte> {
    public static ByteProxy createTable() {
        return new ByteProxy();
    }
    private static final Class<Byte> entityClass = Byte.class;


    private ByteProxy() {
    }
    public ByteProxy(Byte val) {
        set(val);
    }



    public ByteProxy(PropTypeColumn<Byte> propTypeColumn) {
        set(propTypeColumn);
    }

    @Override
    public Class<Byte> getEntityClass() {
        return entityClass;
    }
}