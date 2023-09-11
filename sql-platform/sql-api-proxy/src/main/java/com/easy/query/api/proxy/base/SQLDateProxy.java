package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.AbstractProxyEntity;

import java.sql.Date;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLDateProxy extends AbstractProxyEntity<SQLDateProxy, Date> {

    public static SQLDateProxy createTable() {
        return new SQLDateProxy();
    }
    private static final Class<java.sql.Date> entityClass = java.sql.Date.class;

    private SQLDateProxy() {
    }

    @Override
    public Class<java.sql.Date> getEntityClass() {
        return entityClass;
    }
}