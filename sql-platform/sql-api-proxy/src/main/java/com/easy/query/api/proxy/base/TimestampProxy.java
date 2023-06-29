package com.easy.query.api.proxy.base;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.ProxyEntity;

import java.sql.Timestamp;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class TimestampProxy implements ProxyEntity<TimestampProxy, Timestamp> {

    public static final TimestampProxy DEFAULT = new TimestampProxy();
    private static final Class<Timestamp> entityClass = Timestamp.class;

    private final TableAvailable table;

    private TimestampProxy() {
        this.table = null;
    }

    public TimestampProxy(TableAvailable table) {
        this.table = table;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public Class<Timestamp> getEntityClass() {
        return entityClass;
    }

    @Override
    public TimestampProxy create(TableAvailable table) {
        return new TimestampProxy(table);
    }
}