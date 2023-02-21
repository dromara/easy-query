package org.easy.query.mysql;

import org.easy.query.core.abstraction.EasyQueryRuntimeContext;
import org.easy.query.core.basic.EasyConnection;
import org.easy.query.core.basic.EasyConnectionManager;
import org.easy.query.core.basic.jdbc.DefaultTransaction;
import org.easy.query.core.basic.jdbc.Transaction;
import org.easy.query.core.exception.JDQCException;
import org.easy.query.core.impl.SelectContext;
import org.easy.query.mysql.base.MySQLSelect1;
import org.easy.query.core.abstraction.client.JQDCClient;
import org.easy.query.core.abstraction.sql.Select1;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @FileName: DefaultJQDCClient.java
 * @Description: 文件说明
 * @Date: 2023/2/5 21:28
 * @Created by xuejiaming
 */
public class MySQLJQDCClient implements JQDCClient {
    private final EasyQueryRuntimeContext runtimeContext;
    public MySQLJQDCClient(EasyQueryRuntimeContext runtimeContext){

        this.runtimeContext = runtimeContext;
    }

    @Override
    public EasyQueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    @Override
    public <T1> Select1<T1> select(Class<T1> clazz) {
        return new MySQLSelect1<>(clazz,new SelectContext(runtimeContext));
    }

    @Override
    public <T1> Select1<T1> select(Class<T1> clazz, String alias) {
        return new MySQLSelect1<>(clazz,new SelectContext(runtimeContext,alias));
    }
    @Override
    public Transaction beginTransaction(Integer isolationLevel) {
        EasyConnectionManager connectionManager = runtimeContext.getConnectionManager();
        return connectionManager.beginTransaction(isolationLevel);
    }

}
