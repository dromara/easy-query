package com.easy.query.core.sharding.merge.result;

import com.easy.query.core.basic.jdbc.executor.internal.common.ExecutionUnit;

/**
 * create time 2023/5/13 00:35
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ExecutionUnitStreamResult extends StreamResultSet{
    ExecutionUnit getExecutionUnit();
}
