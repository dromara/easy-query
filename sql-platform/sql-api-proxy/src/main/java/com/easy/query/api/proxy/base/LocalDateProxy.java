package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

import java.time.LocalDate;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class LocalDateProxy extends AbstractBasicProxyEntity<LocalDateProxy, LocalDate> {
    public static LocalDateProxy createTable() {
        return new LocalDateProxy();
    }
    private static final Class<LocalDate> entityClass = LocalDate.class;

    private LocalDateProxy() {
    }
    public LocalDateProxy(LocalDate val) {
        set(val);
    }


    public LocalDateProxy(PropTypeColumn<LocalDate> propTypeColumn) {
        set(propTypeColumn);
    }

    @Override
    public Class<LocalDate> getEntityClass() {
        return entityClass;
    }
}