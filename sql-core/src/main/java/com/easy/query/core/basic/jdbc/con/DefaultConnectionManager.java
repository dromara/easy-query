package com.easy.query.core.basic.jdbc.con;

import com.easy.query.core.basic.jdbc.tx.DefaultTransaction;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.sharding.EasyDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @FileName: DefaultConnectionManager.java
 * @Description: 文件说明
 * @Date: 2023/2/21 08:57
 * @author xuejiaming
 */
public class DefaultConnectionManager implements EasyConnectionManager {
    private final ThreadLocal<Transaction> threadTx = ThreadLocal.withInitial(() -> null);
    private final ThreadLocal<EasyConnection> threadConnection = new ThreadLocal<>();
    protected final EasyDataSource easyDataSource;

    public DefaultConnectionManager(EasyDataSource easyDataSource){

        this.easyDataSource = easyDataSource;
    }
    @Override
    public Transaction beginTransaction(Integer isolationLevel) {
        DefaultTransaction defaultTransaction = new DefaultTransaction(isolationLevel,this);
        threadTx.set(defaultTransaction);
        return defaultTransaction;
    }

    @Override
    public EasyConnection getEasyConnection(){
        return getEasyConnection(easyDataSource.getDefaultDataSourceName(),ConnectionStrategyEnum.ShareConnection);
    }
    @Override
    public EasyConnection getEasyConnection(String dataSourceName,ConnectionStrategyEnum connectionStrategy){
        if(ConnectionStrategyEnum.ShareConnection.equals(connectionStrategy)){

            Transaction transaction = threadTx.get();
            if(transaction!=null){
                EasyConnection easyConnection = threadConnection.get();
                if(easyConnection==null){

                    easyConnection=doGetEasyConnection(dataSourceName,transaction.getIsolationLevel());
                    easyConnection.setAutoCommit(false);
                    threadConnection.set(easyConnection);
                }
                return easyConnection;
            }
            return doGetEasyConnection(dataSourceName,null);
        }else{
            return doGetEasyConnection(dataSourceName,null);
        }
    }
    protected EasyConnection doGetEasyConnection(String dataSourceName,Integer isolationLevel){
        try {
            return new DefaultEasyConnection(dataSourceName,easyDataSource.getDataSource(dataSourceName).getConnection(),isolationLevel);
        } catch (SQLException e) {
            throw new EasyQueryException(e);
        }
    }

    @Override
    public boolean currentThreadInTransaction() {
        return easyCurrentThreadInTransaction();
    }
    protected boolean easyCurrentThreadInTransaction() {
        return threadTx.get()!=null;
    }

    @Override
    public void clear() {
        threadConnection.remove();
        threadTx.remove();
    }

    @Override
    public void closeEasyConnection(EasyConnection easyConnection) {
        if(easyConnection==null){
            return;
        }
        if(!this.easyCurrentThreadInTransaction()){
            try {
                easyConnection.close();
            } catch (Exception e) {
                throw new EasyQueryException(e);
            }
        }
    }

    @Override
    public void commit() {
        EasyConnection easyConnection = threadConnection.get();
        try{
            if(easyConnection==null){
                return;
            }
            try{
                easyConnection.commit();
            }finally {
                easyConnection.close();
            }
        }finally {
            clear();
        }

    }

    @Override
    public void rollback() {
        EasyConnection easyConnection = threadConnection.get();
        try{
            if(easyConnection==null){
                return;
            }
            try{
                easyConnection.rollback();
            }finally {
                easyConnection.close();
            }
        }finally {
            clear();
        }

    }
}
