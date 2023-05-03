package com.easy.query.core.basic.jdbc.executor.internal.command;

import com.easy.query.core.basic.jdbc.executor.internal.result.ExecuteResult;

import java.sql.SQLException;

/**
 * create time 2023/4/21 08:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface JdbcCommand<T extends ExecuteResult> extends AutoCloseable{
    T execute() throws SQLException;
}
