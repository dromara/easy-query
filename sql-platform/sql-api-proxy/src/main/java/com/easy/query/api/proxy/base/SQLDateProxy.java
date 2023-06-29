package com.easy.query.api.proxy.base;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLDateProxy implements ProxyEntity<SQLDateProxy, java.sql.Date> {

    public static final SQLDateProxy DEFAULT = new SQLDateProxy();
    private static final Class<java.sql.Date> entityClass = java.sql.Date.class;

    private final TableAvailable table;

    private SQLDateProxy() {
        this.table = null;
    }

    public SQLDateProxy(TableAvailable table) {
        this.table = table;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public Class<java.sql.Date> getEntityClass() {
        return entityClass;
    }

    @Override
    public SQLDateProxy create(TableAvailable table) {
        return new SQLDateProxy(table);
    }
}