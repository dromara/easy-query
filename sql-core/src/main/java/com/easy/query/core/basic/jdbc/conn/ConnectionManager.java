package com.easy.query.core.basic.jdbc.conn;

import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.basic.jdbc.tx.TransactionListener;
import com.easy.query.core.enums.conn.ConnectionStrategyEnum;
import com.easy.query.core.exception.EasyQuerySQLCommandException;

import java.util.List;

/**
 * create time 2023/6/12 12:54
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ConnectionManager {
    default Transaction beginTransaction() {
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
     *
     * @param isolationLevel
     * @return
     * @throws EasyQuerySQLCommandException repeat begin transaction
     */
    Transaction beginTransaction(Integer isolationLevel);

    default Transaction tryBeginTransaction(Integer isolationLevel) {
        Transaction transaction = getTransactionOrNull();
        if (transaction != null) {
            return transaction;
        }
        return beginTransaction(isolationLevel);
    }

    List<EasyConnection> getEasyConnections(int count, String dataSourceName, ConnectionStrategyEnum connectionStrategy);

    default boolean isOpenTransaction() {
        return getTransactionOrNull() != null;
    }

    Transaction getTransactionOrNull();

    /**
     * 表示当前线程是否在事务中,不一定代表开启了事务，也可以是spring开启了事务如果spring没开启也有可能是{@link #beginTransaction}
     *
     * @return
     */
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
