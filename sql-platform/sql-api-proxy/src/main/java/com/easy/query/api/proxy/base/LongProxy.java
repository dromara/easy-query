package com.easy.query.api.proxy.base;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class LongProxy implements ProxyEntity<LongProxy, Long> {

    public static final LongProxy DEFAULT = new LongProxy();
    private static final Class<Long> entityClass = Long.class;

    private final TableAvailable table;

    private LongProxy() {
        this.table = null;
    }

    public LongProxy(TableAvailable table) {
        this.table = table;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public Class<Long> getEntityClass() {
        return entityClass;
    }

    @Override
    public LongProxy create(TableAvailable table) {
        return new LongProxy(table);
    }
}