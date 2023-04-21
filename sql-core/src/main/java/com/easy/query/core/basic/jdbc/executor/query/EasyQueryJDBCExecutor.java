package com.easy.query.core.basic.jdbc.executor.query;

import com.easy.query.core.sharding.merge.executor.internal.ExecuteResult;

/**
 * create time 2023/4/21 08:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyQueryJDBCExecutor extends AutoCloseable{
    ExecuteResult execute();
}
