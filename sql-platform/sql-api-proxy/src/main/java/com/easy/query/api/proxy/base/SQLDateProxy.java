package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.PropTypeColumn;

import java.sql.Date;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLDateProxy extends AbstractBasicProxyEntity<SQLDateProxy, Date> {

    private static final Class<java.sql.Date> entityClass = java.sql.Date.class;

    public SQLDateProxy(Date val) {
        set(val);
    }

    public SQLDateProxy(PropTypeColumn<Date> propTypeColumn) {
        set(propTypeColumn);
    }

    @Override
    public Class<java.sql.Date> getEntityClass() {
        return entityClass;
    }
}