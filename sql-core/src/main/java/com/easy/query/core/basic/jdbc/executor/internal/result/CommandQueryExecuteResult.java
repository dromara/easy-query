package com.easy.query.core.basic.jdbc.executor.internal.result;

import com.easy.query.core.basic.jdbc.executor.internal.common.CommandExecuteUnit;
import com.easy.query.core.basic.jdbc.executor.internal.result.QueryExecuteResult;

/**
 * create time 2023/5/12 23:59
 * 文件说明
 *
 * @author xuejiaming
 */
public interface CommandQueryExecuteResult extends QueryExecuteResult {
    CommandExecuteUnit getCommandExecuteUnit();
}
