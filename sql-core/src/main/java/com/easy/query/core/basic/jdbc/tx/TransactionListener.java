package com.easy.query.core.basic.jdbc.tx;

/**
 * create time 2023/5/27 13:52
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TransactionListener {
    default void beforeCommit(){

    }
    default void afterCommit(){

    }
    default void beforeRollback(){

    }
    default void afterRollback(){

    }
    default void beforeClose(){

    }
    default void afterClose(){

    }
}
