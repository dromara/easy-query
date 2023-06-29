package com.easy.query.api.proxy.base;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class UtilDateProxy implements ProxyEntity<UtilDateProxy, java.util.Date> {

    public static final UtilDateProxy DEFAULT = new UtilDateProxy();
    private static final Class<java.util.Date> entityClass = java.util.Date.class;

    private final TableAvailable table;

    private UtilDateProxy() {
        this.table = null;
    }

    public UtilDateProxy(TableAvailable table) {
        this.table = table;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public Class<java.util.Date> getEntityClass() {
        return entityClass;
    }

    @Override
    public UtilDateProxy create(TableAvailable table) {
        return new UtilDateProxy(table);
    }
}