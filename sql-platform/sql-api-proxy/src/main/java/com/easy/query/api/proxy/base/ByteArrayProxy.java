package com.easy.query.api.proxy.base;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.AbstractProxyEntity;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class ByteArrayProxy extends AbstractProxyEntity<ByteArrayProxy, Byte[]> {
    public static ByteArrayProxy createTable() {
        return new ByteArrayProxy();
    }
    private static final Class<Byte[]> entityClass = Byte[].class;


    private ByteArrayProxy() {
    }


    @Override
    public Class<Byte[]> getEntityClass() {
        return entityClass;
    }
}