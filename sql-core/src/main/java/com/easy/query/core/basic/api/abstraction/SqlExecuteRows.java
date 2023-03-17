package com.easy.query.core.basic.api.abstraction;

import com.easy.query.core.basic.jdbc.con.EasyConnectionManager;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.exception.EasyQueryConcurrentException;

/**
 * @FileName: SqlExecuteRows.java
 * @Description: 文件说明
 * @Date: 2023/3/17 17:15
 * @Created by xuejiaming
 */
public interface SqlExecuteRows {
    /**
     * 返回受影响行数
     * @return
     */
    long executeRows();

    /**
     * 当执行受影响行数不符合 expectRows 将会抛出 {@link EasyQueryConcurrentException}
     * @param expectRows
     * @param msg
     */
    default void executeRows(long expectRows, String msg) {
        executeRows(expectRows, msg, null);
    }

    /**
     * 当执行受影响行数不符合 expectRows 将会抛出 {@link EasyQueryConcurrentException}
     * @param expectRows
     * @param msg
     * @param code
     */

     void executeRows(long expectRows, String msg, String code);
}
