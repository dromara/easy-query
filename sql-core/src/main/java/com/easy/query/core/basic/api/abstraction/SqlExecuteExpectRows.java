package com.easy.query.core.basic.api.abstraction;

import com.easy.query.core.exception.EasyQueryConcurrentException;

/**
 * @FileName: SqlExecuteRows.java
 * @Description: update delete执行抽象
 * @Date: 2023/3/17 17:15
 * @author xuejiaming
 */
public interface SqlExecuteExpectRows extends SqlExecuteRows{

    /**
     * 当执行受影响行数不符合 expectRows 将会抛出 {@link EasyQueryConcurrentException}
     * @param expectRows
     * @param msg
     */
    default void executeRows(long expectRows, String msg) {
        executeRows(expectRows, msg, null);
    }

    /**
     * 当前执行在事务里面就不需要重新开启事务,如果不在事务内部单独开启事务执行,并且抛出响应的异常保证原子性
     * 当执行受影响行数不符合 expectRows 将会抛出 {@link EasyQueryConcurrentException}
     * @param expectRows
     * @param msg
     * @param code
     */

     void executeRows(long expectRows, String msg, String code);
}
