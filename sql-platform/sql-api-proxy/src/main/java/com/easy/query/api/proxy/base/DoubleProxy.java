package com.easy.query.api.proxy.base;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class DoubleProxy implements ProxyEntity<DoubleProxy, Double> {

    public static final DoubleProxy DEFAULT = new DoubleProxy();
    private static final Class<Double> entityClass = Double.class;

    private final TableAvailable table;

    private DoubleProxy() {
        this.table = null;
    }

    public DoubleProxy(TableAvailable table) {
        this.table = table;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public Class<Double> getEntityClass() {
        return entityClass;
    }

    @Override
    public DoubleProxy create(TableAvailable table) {
        return new DoubleProxy(table);
    }
}