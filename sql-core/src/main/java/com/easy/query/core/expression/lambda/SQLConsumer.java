package com.easy.query.core.expression.lambda;

import java.sql.SQLException;

/**
 * create time 2023/11/23 19:09
 * 文件说明
 *
 * @author xuejiaming
 */
@FunctionalInterface
public interface SQLConsumer<T> {

    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     */
    void accept(T t) throws SQLException;
}
