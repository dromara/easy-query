package com.easy.query.api.proxy.base;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class BooleanProxy implements ProxyEntity<BooleanProxy, Boolean> {

    public static final BooleanProxy DEFAULT = new BooleanProxy();
    private static final Class<Boolean> entityClass = Boolean.class;

    private final TableAvailable table;

    private BooleanProxy() {
        this.table = null;
    }

    public BooleanProxy(TableAvailable table) {
        this.table = table;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public Class<Boolean> getEntityClass() {
        return entityClass;
    }

    @Override
    public BooleanProxy create(TableAvailable table) {
        return new BooleanProxy(table);
    }

    @Override
    public Boolean createEntity() {
        return null;
    }
}