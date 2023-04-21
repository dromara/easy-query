package com.easy.query.core.expression.executor.query;

import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.expression.executor.parser.ExecutionContext;
import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.sharding.merge.executor.internal.CommandTypeEnum;

import java.util.List;

/**
 * create time 2023/4/11 08:23
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ExecutionContextFactory {
    ExecutionContext createExecutionContext(PrepareParseResult prepareParseResult);
    ExecutionContext createQueryExecutionContext(String sql, List<SQLParameter> parameters);
}
