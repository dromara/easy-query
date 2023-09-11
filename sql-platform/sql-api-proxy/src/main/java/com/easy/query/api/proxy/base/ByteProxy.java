package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.AbstractProxyEntity;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class ByteProxy extends AbstractProxyEntity<ByteProxy, Byte> {
    public static ByteProxy createTable() {
        return new ByteProxy();
    }
    private static final Class<Byte> entityClass = Byte.class;


    private ByteProxy() {
    }

    @Override
    public Class<Byte> getEntityClass() {
        return entityClass;
    }
}