package com.easy.query.api.proxy.base;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class ByteProxy implements ProxyEntity<ByteProxy, Byte> {

    public static final ByteProxy DEFAULT = new ByteProxy();
    private static final Class<Byte> entityClass = Byte.class;

    private final TableAvailable table;

    private ByteProxy() {
        this.table = null;
    }

    public ByteProxy(TableAvailable table) {
        this.table = table;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public Class<Byte> getEntityClass() {
        return entityClass;
    }

    @Override
    public ByteProxy create(TableAvailable table) {
        return new ByteProxy(table);
    }
}