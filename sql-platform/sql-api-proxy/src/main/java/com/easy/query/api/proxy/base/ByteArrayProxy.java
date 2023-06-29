package com.easy.query.api.proxy.base;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class ByteArrayProxy implements ProxyEntity<ByteArrayProxy, Byte[]> {

    public static final ByteArrayProxy DEFAULT = new ByteArrayProxy();
    private static final Class<Byte[]> entityClass = Byte[].class;

    private final TableAvailable table;

    private ByteArrayProxy() {
        this.table = null;
    }

    public ByteArrayProxy(TableAvailable table) {
        this.table = table;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public Class<Byte[]> getEntityClass() {
        return entityClass;
    }

    @Override
    public ByteArrayProxy create(TableAvailable table) {
        return new ByteArrayProxy(table);
    }
}