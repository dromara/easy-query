package com.easy.query.core.basic.jdbc.con;

import com.easy.query.core.basic.jdbc.tx.DefaultTransaction;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQuerySQLException;
import com.easy.query.core.sharding.EasyQueryDataSource;

import java.sql.SQLException;

/**
 * @author xuejiaming
 * @FileName: DefaultConnectionManager.java
 * @Description: 文件说明
 * @Date: 2023/2/21 08:57
 */
public class DefaultConnectionManager implements EasyConnectionManager {
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
        if(threadTx.get()!=null){
            throw new EasyQuerySQLException("repeat begin transaction");
        }
        DefaultTransaction defaultTransaction = new DefaultTransaction(isolationLevel, this);
        threadTx.set(defaultTransaction);
        return defaultTransaction;
    }

    @Override
    public EasyConnection getEasyConnection() {
        return getEasyConnection(easyDataSource.getDefaultDataSourceName(), ConnectionStrategyEnum.ShareConnection);
    }

    @Override
    public EasyConnection getEasyConnection(String dataSourceName, ConnectionStrategyEnum connectionStrategy) {

        if (ConnectionStrategyEnum.ShareConnection.equals(connectionStrategy)) {
            Transaction transaction = threadTx.get();
            if (transaction != null) {
                EasyDataSourceConnection easyDataSourceConnection = threadDataSourceConnection.get();
                if (easyDataSourceConnection == null) {
                    easyDataSourceConnection = easyDataSourceConnectionFactory.create();
                    threadDataSourceConnection.set(easyDataSourceConnection);
                }
                EasyConnection easyConnection = easyDataSourceConnection.getEasyConnectionOrNull(dataSourceName);
                if (easyConnection == null) {
                    easyConnection = easyConnectionFactory.createEasyConnection(dataSourceName, transaction.getIsolationLevel(),connectionStrategy);
                    easyConnection.setAutoCommit(false);
                    easyDataSourceConnection.putIfAbsent(dataSourceName, easyConnection);
                }
                return easyConnection;
            }
        }
        return easyConnectionFactory.createEasyConnection(dataSourceName, null,connectionStrategy);
    }

    @Override
    public boolean currentThreadInTransaction() {
        return easyCurrentThreadInTransaction();
    }

    protected boolean easyCurrentThreadInTransaction() {
        return threadTx.get() != null;
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
        if (!this.easyCurrentThreadInTransaction()) {
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
//        EasyConnection easyConnection = threadConnection.get();
//        try {
//            if (easyConnection == null) {
//                return;
//            }
//            try {
//                easyConnection.commit();
//            } finally {
//                easyConnection.close();
//            }
//        } finally {
//            clear();
//        }

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
