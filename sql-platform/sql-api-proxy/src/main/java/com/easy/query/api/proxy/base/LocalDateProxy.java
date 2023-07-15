package com.easy.query.api.proxy.base;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.ProxyEntity;

import java.time.LocalDate;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class LocalDateProxy implements ProxyEntity<LocalDateProxy, LocalDate> {

    public static final LocalDateProxy DEFAULT = new LocalDateProxy();
    private static final Class<LocalDate> entityClass = LocalDate.class;

    private final TableAvailable table;

    private LocalDateProxy() {
        this.table = null;
    }

    public LocalDateProxy(TableAvailable table) {
        this.table = table;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public Class<LocalDate> getEntityClass() {
        return entityClass;
    }

    @Override
    public LocalDateProxy create(TableAvailable table) {
        return new LocalDateProxy(table);
    }
}