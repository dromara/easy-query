package org.easy.query.core.basic;

import org.easy.query.core.basic.jdbc.Transaction;

import java.sql.SQLException;

/**
 * @FileName: ConnectionManager.java
 * @Description: 文件说明
 * @Date: 2023/2/21 08:56
 * @Created by xuejiaming
 */
public interface EasyConnectionManager {
    default Transaction beginTransaction(){
       return beginTransaction(null);
    }
    Transaction beginTransaction(Integer isolationLevel);
    EasyConnection getEasyConnection();
    boolean currentThreadInTransaction();
    void clear();
    void closeEasyCConnection(EasyConnection easyConnection);
    void commit();
    void rollback();
}
