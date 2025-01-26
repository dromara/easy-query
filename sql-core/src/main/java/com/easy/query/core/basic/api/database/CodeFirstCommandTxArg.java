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
    private final List<Consumer<Transaction>> transactionConsumer;


    public CodeFirstCommandTxArg(Transaction transaction, String sql, List<Consumer<Transaction>> transactionConsumer){
        this.transaction = transaction;
        this.sql = sql;
        this.transactionConsumer = transactionConsumer;
    }

    public void commit(){
        transactionConsumer.add(tx->tx.commit());
    }
    public void rollback(){
        transactionConsumer.add(tx->tx.rollback());
    }
}
