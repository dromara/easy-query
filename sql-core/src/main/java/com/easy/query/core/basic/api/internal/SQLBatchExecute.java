package com.easy.query.core.basic.api.internal;

/**
 * create time 2023/7/9 09:38
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLBatchExecute<TChain> {
    /**
     * 使用当前方法并且链接字符串添加`rewriteBatchedStatements=true`后将以executeBatch执行无法返回正确的受影响行数,
     * 具体受jdbc驱动影响是否实现返回具体行数
     * This method performs batch operations.
     * When using this method with the connection string appended with `rewriteBatchedStatements=true`,
     * the executeBatch() method will be used to execute the batch,
     * but it may not return the correct number of affected rows
     * @return
     */
    default TChain batch(){
        return batch(true);
    }

    /**
     *
     * 使用当前方法并且链接字符串添加`rewriteBatchedStatements=true`后将以executeBatch执行无法返回正确的受影响行数,
     * 具体受jdbc驱动影响是否实现返回具体行数
     * This method performs batch operations.
     * When using this method with the connection string appended with `rewriteBatchedStatements=true`,
     * the executeBatch() method will be used to execute the batch,
     * but it may not return the correct number of affected rows
     * @param use use execute batch or not use execute batch
     * @return
     */
    TChain batch(boolean use);
}
