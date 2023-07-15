package com.easy.query.api.proxy.base;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class IntegerProxy implements ProxyEntity<IntegerProxy, Integer> {

    public static final IntegerProxy DEFAULT = new IntegerProxy();
    private static final Class<Integer> entityClass = Integer.class;

    private final TableAvailable table;

    private IntegerProxy() {
        this.table = null;
    }

    public IntegerProxy(TableAvailable table) {
        this.table = table;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public Class<Integer> getEntityClass() {
        return entityClass;
    }

    @Override
    public IntegerProxy create(TableAvailable table) {
        return new IntegerProxy(table);
    }
}