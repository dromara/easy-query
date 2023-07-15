package com.easy.query.api.proxy.base;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.ProxyEntity;

import java.sql.Time;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class TimeProxy implements ProxyEntity<TimeProxy, Time> {

    public static final TimeProxy DEFAULT = new TimeProxy();
    private static final Class<Time> entityClass = Time.class;

    private final TableAvailable table;

    private TimeProxy() {
        this.table = null;
    }

    public TimeProxy(TableAvailable table) {
        this.table = table;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public Class<Time> getEntityClass() {
        return entityClass;
    }

    @Override
    public TimeProxy create(TableAvailable table) {
        return new TimeProxy(table);
    }
}