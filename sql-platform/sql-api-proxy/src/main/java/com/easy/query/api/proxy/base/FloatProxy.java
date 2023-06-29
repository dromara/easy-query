package com.easy.query.api.proxy.base;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class FloatProxy implements ProxyEntity<FloatProxy, Float> {

    public static final FloatProxy DEFAULT = new FloatProxy();
    private static final Class<Float> entityClass = Float.class;

    private final TableAvailable table;

    private FloatProxy() {
        this.table = null;
    }

    public FloatProxy(TableAvailable table) {
        this.table = table;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public Class<Float> getEntityClass() {
        return entityClass;
    }

    @Override
    public FloatProxy create(TableAvailable table) {
        return new FloatProxy(table);
    }
}