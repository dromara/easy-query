package com.easy.query.core.basic.jdbc.tx;

/**
 * @FileName: Transaction.java
 * @Description: 文件说明
 * create time 2023/2/20 22:11
 * @author xuejiaming
 */
public interface Transaction extends AutoCloseable {
    Integer getIsolationLevel();
    void commit();
    void rollback();
    void close();
    void registerListener(TransactionListener transactionBehavior);
}
