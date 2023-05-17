package com.easy.query.core.basic.api.internal;

import com.easy.query.core.exception.EasyQueryConcurrentException;

/**
 * create time 2023/4/8 12:21
 * update delete 返回受影响行数执行接口
 * @author xuejiaming
 */
public interface SQLExecuteExpectRows extends SQLExecuteRows {

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
     * @param expectRows 期望返回行数
     * @param msg 自定义错误消息
     * @param code 自定义业务错误码
     */

     void executeRows(long expectRows, String msg, String code);
}
