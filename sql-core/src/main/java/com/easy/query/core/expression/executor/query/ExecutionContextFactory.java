package com.easy.query.core.expression.executor.query;

import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.expression.executor.parser.ExecutionContext;
import com.easy.query.core.expression.executor.parser.PrepareParseResult;

import java.util.List;

/**
 * create time 2023/4/11 08:23
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ExecutionContextFactory {
    ExecutionContext createEntityExecutionContext(PrepareParseResult prepareParseResult);
    ExecutionContext createJDBCExecutionContext(String sql, List<SQLParameter> parameters);
}
