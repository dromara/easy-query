package com.easy.query.core.expression.include;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.sql.include.IncludeParserResult;

import java.util.Collection;

/**
 * create time 2023/7/16 20:59
 * 文件说明
 *
 * @author xuejiaming
 */
public interface IncludeProcessorFactory {
    <TEntity> IncludeProcessor createIncludeProcess(Collection<TEntity> entities, IncludeParserResult includeParserResult, QueryRuntimeContext runtimeContext);
}
