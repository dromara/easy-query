package com.easy.query.core.basic.jdbc.executor.internal.enumerable;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;

import java.sql.SQLException;

/**
 * create time 2023/7/31 16:57
 * 文件说明
 *
 * @author xuejiaming
 */
public interface JdbcStreamResult<T> extends AutoCloseable {
    StreamIterable<T> getStreamIterable()  throws SQLException;
    ExecutorContext getExecutorContext();

    @Override
    void close() throws SQLException;
}
