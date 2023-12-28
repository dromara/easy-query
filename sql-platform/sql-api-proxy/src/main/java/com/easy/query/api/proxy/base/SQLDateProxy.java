package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

import java.sql.Date;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLDateProxy extends AbstractBasicProxyEntity<SQLDateProxy, Date> {

    public static SQLDateProxy createTable() {
        return new SQLDateProxy();
    }
    private static final Class<java.sql.Date> entityClass = java.sql.Date.class;

    private SQLDateProxy() {
    }
    public SQLDateProxy(Date val) {
        set(val);
    }


    public SQLDateProxy(SQLColumn<?,Date> sqlColumn) {
        set(sqlColumn);
    }


    public <TResult extends DSLSQLFunctionAvailable & PropTypeColumn<Date>> SQLDateProxy(TResult sqlFunctionAvailable) {
        set(sqlFunctionAvailable);
    }

    @Override
    public Class<java.sql.Date> getEntityClass() {
        return entityClass;
    }
}