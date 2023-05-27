package com.easy.query.core.basic.jdbc.tx;

import com.easy.query.core.basic.jdbc.con.ConnectionManager;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;


/**
 * @FileName: DefaultTransaction.java
 * @Description: 文件说明
 * @Date: 2023/2/20 22:11
 * @author xuejiaming
 */
public class DefaultTransaction implements Transaction {

    private final Integer isolationLevel;
    private final ConnectionManager connectionManager;
    private boolean open;
    private boolean closed;
    private List<TransactionListener> transactionListeners;

    public DefaultTransaction(Integer isolationLevel, ConnectionManager connectionManager) {
        this.isolationLevel = isolationLevel;
        this.connectionManager = connectionManager;

        this.open=true;
        this.closed=false;
    }

    @Override
    public Integer getIsolationLevel() {
        return isolationLevel;
    }

    private void listenerInvoke(Consumer<TransactionListener> consumer){
        if(EasyCollectionUtil.isNotEmpty(this.transactionListeners)){
            for (TransactionListener transactionListener : this.transactionListeners) {
                consumer.accept(transactionListener);
            }
        }
    }
    @Override
    public void commit() {
        listenerInvoke(TransactionListener::beforeCommit);
        connectionManager.commit();
        listenerInvoke(TransactionListener::afterCommit);
        open=false;
    }

    @Override
    public void rollback() {
        listenerInvoke(TransactionListener::beforeRollback);
        connectionManager.rollback();
        listenerInvoke(TransactionListener::afterRollback);
        open=false;
    }

    private void close(boolean closing){
        if(closed){
            return;
        }

        listenerInvoke(TransactionListener::beforeClose);
        if(closing&&this.open){
            this.rollback();
        }
        closed=true;
        listenerInvoke(TransactionListener::afterClose);
    }
    @Override
    public void close() {
        close(true);
    }

    @Override
    public void registerListener(TransactionListener transactionBehavior) {
        if(transactionListeners==null){
            //一般不需要注册很多个arraylist相对比较浪费内存
            transactionListeners=new ArrayList<>();
        }
        this.transactionListeners.add(transactionBehavior);
    }
}
