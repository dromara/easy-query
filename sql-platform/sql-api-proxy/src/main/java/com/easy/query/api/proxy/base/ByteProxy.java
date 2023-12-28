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
public class ByteProxy extends AbstractBasicProxyEntity<ByteProxy, Byte> {
    public static ByteProxy createTable() {
        return new ByteProxy();
    }
    private static final Class<Byte> entityClass = Byte.class;


    private ByteProxy() {
    }
    public ByteProxy(Byte val) {
        set(val);
    }


    public ByteProxy(SQLColumn<?,Byte> sqlColumn) {
        set(sqlColumn);
    }


    public <TResult extends DSLSQLFunctionAvailable & PropTypeColumn<Byte>> ByteProxy(TResult sqlFunctionAvailable) {
        set(sqlFunctionAvailable);
    }

    @Override
    public Class<Byte> getEntityClass() {
        return entityClass;
    }
}