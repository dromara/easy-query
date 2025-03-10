package com.easy.query.core.basic.api.database;

import com.easy.query.core.basic.jdbc.tx.Transaction;

import java.util.function.Consumer;

/**
 * create time 2025/1/26 08:41
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultCodeFirstCommandTxArg implements CodeFirstCommandTxArg {
    private final String sql;
    private Consumer<Transaction> transactionConsumer;


    public DefaultCodeFirstCommandTxArg(String sql) {
        this.sql = sql;
    }

    @Override
    public String getSQL() {
        return sql;
    }

    public void commit() {
        transactionConsumer = Transaction::commit;
    }

    public void rollback() {
        transactionConsumer = Transaction::rollback;
    }

    public Consumer<Transaction> getTransactionConsumer() {
        if (transactionConsumer == null) {
            return tx -> {
            };
        }
        return transactionConsumer;
    }
}
