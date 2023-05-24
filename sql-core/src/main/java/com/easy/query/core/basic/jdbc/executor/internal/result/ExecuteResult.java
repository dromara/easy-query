package com.easy.query.core.basic.jdbc.executor.internal.result;

import java.sql.SQLException;

/**
 * create time 2023/4/14 11:37
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ExecuteResult extends AutoCloseable {
    @Override
    void close() throws SQLException;
}
