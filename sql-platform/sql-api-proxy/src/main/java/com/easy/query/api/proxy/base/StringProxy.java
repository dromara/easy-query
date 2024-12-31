package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class StringProxy extends AbstractBasicProxyEntity<StringProxy, String> {

    private static final Class<String> entityClass = String.class;


    public StringProxy(String val) {
        set(val);
    }


    public StringProxy(SQLColumn<?,String> sqlColumn) {
        set(sqlColumn);
    }
//    public StringProxy(PropTypeColumn<String> sqlColumn) {
//        set(sqlColumn);
//    }


    public <TResult extends DSLSQLFunctionAvailable & PropTypeColumn<String>> StringProxy(TResult sqlFunctionAvailable) {
        set(sqlFunctionAvailable);
    }



    @Override
    public Class<String> getEntityClass() {
        return entityClass;
    }



}