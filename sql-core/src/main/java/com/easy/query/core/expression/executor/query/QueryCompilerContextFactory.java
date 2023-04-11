package com.easy.query.core.expression.executor.query;

import com.easy.query.core.expression.executor.parser.PrepareParseResult;

/**
 * create time 2023/4/11 08:23
 * 文件说明
 *
 * @author xuejiaming
 */
public interface QueryCompilerContextFactory {
    QueryCompilerContext create(PrepareParseResult prepareParseResult);
}
