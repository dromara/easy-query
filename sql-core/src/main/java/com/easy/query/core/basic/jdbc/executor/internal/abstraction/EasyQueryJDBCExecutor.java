package com.easy.query.core.basic.jdbc.executor.internal.abstraction;

import com.easy.query.core.basic.jdbc.executor.internal.ExecuteResult;

/**
 * create time 2023/4/21 08:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyQueryJDBCExecutor<T extends ExecuteResult> extends AutoCloseable{
    T execute();
}
