package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

import java.time.LocalDateTime;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class LocalDateTimeProxy extends AbstractBasicProxyEntity<LocalDateTimeProxy, LocalDateTime> {
    public static LocalDateTimeProxy createTable() {
        return new LocalDateTimeProxy();
    }
    private static final Class<LocalDateTime> entityClass = LocalDateTime.class;

    private LocalDateTimeProxy() {
    }
    public LocalDateTimeProxy(LocalDateTime val) {
        set(val);
    }


    public LocalDateTimeProxy(PropTypeColumn<LocalDateTime> propTypeColumn) {
        set(propTypeColumn);
    }

    @Override
    public Class<LocalDateTime> getEntityClass() {
        return entityClass;
    }

}