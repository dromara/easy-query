package com.easy.query.api.proxy.base;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShortProxy implements ProxyEntity<ShortProxy, Short> {

    public static final ShortProxy DEFAULT = new ShortProxy();
    private static final Class<Short> entityClass = Short.class;

    private final TableAvailable table;

    private ShortProxy() {
        this.table = null;
    }

    public ShortProxy(TableAvailable table) {
        this.table = table;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public Class<Short> getEntityClass() {
        return entityClass;
    }

    @Override
    public ShortProxy create(TableAvailable table) {
        return new ShortProxy(table);
    }
}