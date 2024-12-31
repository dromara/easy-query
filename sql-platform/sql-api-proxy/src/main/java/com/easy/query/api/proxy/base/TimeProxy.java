package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

import java.sql.Time;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class TimeProxy extends AbstractBasicProxyEntity<TimeProxy, Time> {
    private static final Class<Time> entityClass = Time.class;

    public TimeProxy(Time val) {
        set(val);
    }


    public TimeProxy(PropTypeColumn<Time> propTypeColumn) {
        set(propTypeColumn);
    }

    @Override
    public Class<Time> getEntityClass() {
        return entityClass;
    }

}