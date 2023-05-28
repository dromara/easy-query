package com.easy.query.core.expression.lambda;

import java.sql.SQLException;

/**
 * create time 2023/5/28 13:44
 * 文件说明
 *
 * @author xuejiaming
 */
@FunctionalInterface
public interface SQLSupplier<T> {

    /**
     * Gets a result.
     *
     * @return a result
     */
    T get() throws SQLException;
}