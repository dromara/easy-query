package com.easy.query.core.basic.api.database;

import com.easy.query.core.basic.jdbc.tx.Transaction;

import java.util.List;
import java.util.function.Consumer;

/**
 * create time 2025/1/26 08:41
 * 文件说明
 *
 * @author xuejiaming
 */
public class CodeFirstCommandTxArg {
    private final Transaction transaction;
    public final String sql;
    private Consumer<Transaction> transactionConsumer;


    public CodeFirstCommandTxArg(Transaction transaction, String sql) {
        this.transaction = transaction;
        this.sql = sql;
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
