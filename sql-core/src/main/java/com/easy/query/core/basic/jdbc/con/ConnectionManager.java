package com.easy.query.core.basic.jdbc.con;

import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.enums.con.ConnectionStrategyEnum;

import java.util.List;

/**
 * @FileName: ConnectionManager.java
 * @Description: 文件说明
 * @Date: 2023/2/21 08:56
 * @author xuejiaming
 */
public interface ConnectionManager {
    default Transaction beginTransaction(){
       return beginTransaction(null);
    }

    /**
     * Params:
     * level – one of the following Connection constants:
     * Connection.TRANSACTION_READ_UNCOMMITTED,
     * Connection.TRANSACTION_READ_COMMITTED,
     * Connection.TRANSACTION_REPEATABLE_READ,
     * Connection.TRANSACTION_SERIALIZABLE.
     * (Note that Connection.TRANSACTION_NONE cannot be used because it specifies that transactions are not supported.)
     * @param isolationLevel
     * @return
     */
    Transaction beginTransaction(Integer isolationLevel);
    List<EasyConnection> getEasyConnections(int count,String dataSourceName, ConnectionStrategyEnum connectionStrategy);
    default boolean isOpenTransaction(){
        return getTransactionOrNull()!=null;
    }
    Transaction getTransactionOrNull();
    boolean currentThreadInTransaction();
    void clear();
    void closeEasyConnection(EasyConnection easyConnection);

    /**
     * 提交
     */
    void commit();

    /**
     * 回滚,异常后会自动执行
     */
    void rollback();
}
