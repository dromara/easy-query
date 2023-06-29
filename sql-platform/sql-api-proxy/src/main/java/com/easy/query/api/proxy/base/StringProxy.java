package com.easy.query.api.proxy.base;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class StringProxy implements ProxyEntity<StringProxy, String> {

    public static final StringProxy DEFAULT = new StringProxy();
    private static final Class<String> entityClass = String.class;

    private final TableAvailable table;

    private StringProxy() {
        this.table = null;
    }

    public StringProxy(TableAvailable table) {
        this.table = table;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public Class<String> getEntityClass() {
        return entityClass;
    }

    @Override
    public StringProxy create(TableAvailable table) {
        return new StringProxy(table);
    }
}