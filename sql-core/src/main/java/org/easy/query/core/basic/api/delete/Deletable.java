package org.easy.query.core.basic.api.delete;

import org.easy.query.core.exception.EasyQueryConcurrentException;

/**
 * @FileName: Deletable.java
 * @Description: 文件说明
 * @Date: 2023/2/28 12:19
 * @Created by xuejiaming
 */
public interface Deletable<T,TChain> {
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
    String toSql();
    TChain disableLogicDelete();
}
