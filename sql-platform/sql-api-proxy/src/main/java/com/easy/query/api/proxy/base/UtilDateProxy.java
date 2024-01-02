package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

import java.util.Date;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class UtilDateProxy extends AbstractBasicProxyEntity<UtilDateProxy, Date> {
    public static UtilDateProxy createTable() {
        return new UtilDateProxy();
    }

    private static final Class<java.util.Date> entityClass = java.util.Date.class;


    private UtilDateProxy() {
    }
    public UtilDateProxy(Date val) {
        set(val);
    }
    public UtilDateProxy(PropTypeColumn<Date> propTypeColumn) {
        set(propTypeColumn);
    }

    @Override
    public Class<java.util.Date> getEntityClass() {
        return entityClass;
    }
}