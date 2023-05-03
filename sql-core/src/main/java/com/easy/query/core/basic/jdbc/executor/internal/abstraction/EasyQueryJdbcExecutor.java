package com.easy.query.core.basic.jdbc.executor.internal.abstraction;

import com.easy.query.core.basic.jdbc.executor.internal.ExecuteResult;

import java.sql.SQLException;

/**
 * create time 2023/4/21 08:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyQueryJdbcExecutor<T extends ExecuteResult> extends AutoCloseable{
    T execute() throws SQLException;
}
