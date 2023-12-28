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


    public TimestampProxy(SQLColumn<?,Timestamp> sqlColumn) {
        set(sqlColumn);
    }


    public <TResult extends DSLSQLFunctionAvailable & PropTypeColumn<Timestamp>> TimestampProxy(TResult sqlFunctionAvailable) {
        set(sqlFunctionAvailable);
    }


    @Override
    public Class<Timestamp> getEntityClass() {
        return entityClass;
    }

}