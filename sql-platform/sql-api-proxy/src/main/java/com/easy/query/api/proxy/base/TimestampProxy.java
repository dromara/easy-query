package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

import java.sql.Timestamp;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class TimestampProxy extends AbstractBasicProxyEntity<TimestampProxy, Timestamp> {
    public static TimestampProxy createTable() {
        return new TimestampProxy();
    }
    private static final Class<Timestamp> entityClass = Timestamp.class;

    private TimestampProxy() {
    }
    public TimestampProxy(Timestamp val) {
        set(val);
    }

    public TimestampProxy(PropTypeColumn<Timestamp> propTypeColumn) {
        set(propTypeColumn);
    }


    @Override
    public Class<Timestamp> getEntityClass() {
        return entityClass;
    }

}