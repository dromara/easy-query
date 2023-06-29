package com.easy.query.api.proxy.base;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.ProxyEntity;

import java.time.LocalTime;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class LocalTimeProxy implements ProxyEntity<LocalTimeProxy, LocalTime> {

    public static final LocalTimeProxy DEFAULT = new LocalTimeProxy();
    private static final Class<LocalTime> entityClass = LocalTime.class;

    private final TableAvailable table;

    private LocalTimeProxy() {
        this.table = null;
    }

    public LocalTimeProxy(TableAvailable table) {
        this.table = table;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public Class<LocalTime> getEntityClass() {
        return entityClass;
    }

    @Override
    public LocalTimeProxy create(TableAvailable table) {
        return new LocalTimeProxy(table);
    }
}