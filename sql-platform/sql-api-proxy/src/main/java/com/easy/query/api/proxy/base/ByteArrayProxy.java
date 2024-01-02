package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.PropTypeColumn;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class ByteArrayProxy extends AbstractBasicProxyEntity<ByteArrayProxy, Byte[]> {
    public static ByteArrayProxy createTable() {
        return new ByteArrayProxy();
    }
    private static final Class<Byte[]> entityClass = Byte[].class;


    private ByteArrayProxy() {
    }
    public ByteArrayProxy(Byte[] val) {
        set(val);
    }

    public ByteArrayProxy(PropTypeColumn<Byte[]> propTypeColumn) {
        set(propTypeColumn);
    }

    @Override
    public Class<Byte[]> getEntityClass() {
        return entityClass;
    }
}