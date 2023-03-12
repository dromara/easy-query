package com.easy.query.core.basic.jdbc.tx;

import com.easy.query.core.basic.jdbc.con.EasyConnectionManager;



/**
 * @FileName: DefaultTransaction.java
 * @Description: 文件说明
 * @Date: 2023/2/20 22:11
 * @Created by xuejiaming
 */
public class DefaultTransaction implements Transaction {

    private final Integer isolationLevel;
    private final EasyConnectionManager connectionManager;
    private boolean open;
    private boolean closed;

    public DefaultTransaction(Integer isolationLevel, EasyConnectionManager connectionManager) {
        this.isolationLevel = isolationLevel;
        this.connectionManager = connectionManager;

        this.open=true;
        this.closed=false;
    }

    @Override
    public Integer getIsolationLevel() {
        return isolationLevel;
    }

    @Override
    public void commit() {
        connectionManager.commit();
        open=false;
    }

    @Override
    public void rollback() {
        connectionManager.rollback();
        open=false;
    }

    private void close(boolean closing){
        if(closed){
            return;
        }
        if(closing&&this.open){
            this.rollback();
        }
        closed=true;
    }
    @Override
    public void close() {
        close(true);
    }
}
