package com.easy.query.api.proxy.base;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.ProxyEntity;

import java.time.LocalDateTime;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class LocalDateTimeProxy implements ProxyEntity<LocalDateTimeProxy, LocalDateTime> {

    public static final LocalDateTimeProxy DEFAULT = new LocalDateTimeProxy();
    private static final Class<LocalDateTime> entityClass = LocalDateTime.class;

    private final TableAvailable table;

    private LocalDateTimeProxy() {
        this.table = null;
    }

    public LocalDateTimeProxy(TableAvailable table) {
        this.table = table;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public Class<LocalDateTime> getEntityClass() {
        return entityClass;
    }

    @Override
    public LocalDateTimeProxy create(TableAvailable table) {
        return new LocalDateTimeProxy(table);
    }
}