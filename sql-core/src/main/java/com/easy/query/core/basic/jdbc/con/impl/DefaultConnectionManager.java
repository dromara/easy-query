package com.easy.query.core.basic.jdbc.con.impl;

import com.easy.query.core.enums.con.ConnectionStrategyEnum;
import com.easy.query.core.basic.jdbc.con.EasyConnection;
import com.easy.query.core.basic.jdbc.con.EasyConnectionFactory;
import com.easy.query.core.basic.jdbc.con.ConnectionManager;
import com.easy.query.core.basic.jdbc.con.EasyDataSourceConnection;
import com.easy.query.core.basic.jdbc.con.EasyDataSourceConnectionFactory;
import com.easy.query.core.basic.jdbc.tx.DefaultTransaction;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.sharding.EasyQueryDataSource;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.Collections;
import java.util.List;

/**
 * @author xuejiaming
 * @FileName: DefaultConnectionManager.java
 * @Description: 文件说明
 * @Date: 2023/2/21 08:57
 */
public class DefaultConnectionManager implements ConnectionManager {
    protected final ThreadLocal<Transaction> threadTx = ThreadLocal.withInitial(() -> null);
    protected final ThreadLocal<EasyDataSourceConnection> threadDataSourceConnection = ThreadLocal.withInitial(() -> null);
    protected final EasyQueryDataSource easyDataSource;
    protected final EasyConnectionFactory easyConnectionFactory;
    protected final EasyDataSourceConnectionFactory easyDataSourceConnectionFactory;

    public DefaultConnectionManager(EasyQueryDataSource easyDataSource, EasyConnectionFactory easyConnectionFactory, EasyDataSourceConnectionFactory easyDataSourceConnectionFactory) {

        this.easyDataSource = easyDataSource;
        this.easyConnectionFactory = easyConnectionFactory;
        this.easyDataSourceConnectionFactory = easyDataSourceConnectionFactory;
    }

    @Override
    public Transaction beginTransaction(Integer isolationLevel) {
        //兼容spring 事务
        if(currentThreadInTransaction()){
            throw new EasyQuerySQLCommandException("repeat begin transaction");
        }
        DefaultTransaction defaultTransaction = new DefaultTransaction(isolationLevel, this);
        threadTx.set(defaultTransaction);
        return defaultTransaction;
    }

    @Override
    public List<EasyConnection> getEasyConnections(int count,String dataSourceName, ConnectionStrategyEnum connectionStrategy) {

        if (ConnectionStrategyEnum.ShareConnection.equals(connectionStrategy)) {
            if(count!=1){
                throw new EasyQueryInvalidOperationException("ConnectionStrategyEnum.ShareConnection get connections should 1");
            }
            Transaction transaction = threadTx.get();
            if (transaction != null) {
                EasyDataSourceConnection easyDataSourceConnection = threadDataSourceConnection.get();
                if (easyDataSourceConnection == null) {
                    easyDataSourceConnection = easyDataSourceConnectionFactory.create();
                    threadDataSourceConnection.set(easyDataSourceConnection);
                }
                EasyConnection easyConnection = easyDataSourceConnection.getEasyConnectionOrNull(dataSourceName);
                if (easyConnection == null) {
                    easyConnection = easyConnectionFactory.createEasyConnection(dataSourceName, transaction.getIsolationLevel(), connectionStrategy);
                    easyConnection.setAutoCommit(false);
                    easyDataSourceConnection.putIfAbsent(dataSourceName, easyConnection);
                }
                return Collections.singletonList(easyConnection);
            }
        }
        return easyConnectionFactory.createEasyConnections(count,dataSourceName, null,connectionStrategy);
    }

    @Override
    public Transaction getTransactionOrNull() {
        return threadTx.get();
    }

    @Override
    public boolean currentThreadInTransaction() {
        return isOpenTransaction();
    }

    @Override
    public void clear() {
//        threadConnection.remove();
        threadDataSourceConnection.remove();
        threadTx.remove();
    }

    @Override
    public void closeEasyConnection(EasyConnection easyConnection) {
        if (easyConnection == null) {
            return;
        }
        if (!this.isOpenTransaction()) {
            try {
                easyConnection.close();
            } catch (Exception e) {
                throw new EasyQueryException(e);
            }
        }
    }

    @Override
    public void commit() {
        EasyDataSourceConnection easyDataSourceConnection = threadDataSourceConnection.get();
        if (easyDataSourceConnection == null) {
            clear();
            return;
        }
        try {
            try {
                easyDataSourceConnection.commit();
            } finally {
                easyDataSourceConnection.close();
            }
        } finally {
            clear();
        }
    }

    @Override
    public void rollback() {
        EasyDataSourceConnection easyDataSourceConnection = threadDataSourceConnection.get();
        if (easyDataSourceConnection == null) {
            clear();
            return;
        }
        try {
            try {
                easyDataSourceConnection.rollback();
            } finally {
                easyDataSourceConnection.close();
            }
        } finally {
            clear();
        }
    }
}
