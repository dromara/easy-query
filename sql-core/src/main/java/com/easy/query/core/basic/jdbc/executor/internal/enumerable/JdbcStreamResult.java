package com.easy.query.core.basic.jdbc.executor.internal.enumerable;

import com.easy.query.core.basic.api.select.ResultSetContext;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.executor.internal.command.JdbcCommand;
import com.easy.query.core.basic.jdbc.executor.internal.result.QueryExecuteResult;

import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * create time 2023/7/31 16:57
 * 文件说明
 *
 * @author xuejiaming
 */
public interface JdbcStreamResult<T> extends AutoCloseable {
    JdbcCommand<QueryExecuteResult> getJdbcCommand();
    StreamIterable<T> getStreamIterable() throws SQLException;

    ExecutorContext getExecutorContext();

    /**
     * 分批处理
     *
     * @param size  每个批次大小
     * @param chunk 处理每批数据，返回true表示继续返回false表示中断
     * @throws SQLException 抛出数据库异常
     */
    void toChunkIf(int size, Predicate<List<T>> chunk) throws SQLException;

    /**
     * 分批处理
     *
     * @param size  每个批次大小
     * @param chunk 处理每批数据
     * @throws SQLException 抛出数据库异常
     */
    default void toChunk(int size, Consumer<List<T>> chunk) throws SQLException {
        toChunkIf(size, list -> {
            chunk.accept(list);
            return list.size() <= size;
        });
    }

    @Override
    void close() throws SQLException;
}
