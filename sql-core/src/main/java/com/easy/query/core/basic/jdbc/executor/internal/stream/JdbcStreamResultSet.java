package com.easy.query.core.basic.jdbc.executor.internal.stream;

import java.sql.SQLException;

/**
 * create time 2023/7/31 16:57
 * 文件说明
 *
 * @author xuejiaming
 */
public interface JdbcStreamResultSet<T> extends AutoCloseable {
    StreamIterable<T> getStreamResult()  throws SQLException;

    @Override
    void close() throws SQLException;
}
