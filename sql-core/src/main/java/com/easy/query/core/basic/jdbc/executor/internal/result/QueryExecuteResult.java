package com.easy.query.core.basic.jdbc.executor.internal.result;

import com.easy.query.core.sharding.merge.result.StreamResultSet;

/**
 * create time 2023/4/14 16:20
 * 文件说明
 *
 * @author xuejiaming
 */
public interface QueryExecuteResult extends ExecuteResult {
    StreamResultSet getStreamResult();
}
