package org.easy.query.core.basic.jdbc.con;

import org.easy.query.core.basic.jdbc.con.DefaultEasyConnection;
import org.easy.query.core.basic.jdbc.con.EasyConnection;
import org.easy.query.core.basic.jdbc.con.EasyConnectionManager;
import org.easy.query.core.basic.jdbc.tx.DefaultTransaction;
import org.easy.query.core.basic.jdbc.tx.Transaction;
import org.easy.query.core.exception.EasyQueryException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @FileName: DefaultConnectionManager.java
 * @Description: 文件说明
 * @Date: 2023/2/21 08:57
 * @Created by xuejiaming
 */
public class DefaultConnectionManager implements EasyConnectionManager {
    private final ThreadLocal<Transaction> threadTx = ThreadLocal.withInitial(() -> null);
    private final ThreadLocal<EasyConnection> threadConnection = new ThreadLocal<>();
    protected final DataSource dataSource;

    public DefaultConnectionManager(DataSource dataSource){

        this.dataSource = dataSource;
    }
    @Override
    public Transaction beginTransaction(Integer isolationLevel) {
        DefaultTransaction defaultTransaction = new DefaultTransaction(isolationLevel,this);
        threadTx.set(defaultTransaction);
        return defaultTransaction;
    }

    @Override
    public EasyConnection getEasyConnection(){
        Transaction transaction = threadTx.get();
        if(transaction!=null){
            EasyConnection easyConnection = threadConnection.get();
            if(easyConnection==null){

                easyConnection=doGetEasyConnection(transaction.getIsolationLevel());
                easyConnection.setAutoCommit(false);
                threadConnection.set(easyConnection);
            }
            return easyConnection;
        }
        return doGetEasyConnection(null);

    }
    protected EasyConnection doGetEasyConnection(Integer isolationLevel){
        try {
            return new DefaultEasyConnection(dataSource.getConnection(),isolationLevel);
        } catch (SQLException e) {
            throw new EasyQueryException(e);
        }
    }

    @Override
    public boolean currentThreadInTransaction() {
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
        if(!currentThreadInTransaction()){
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
