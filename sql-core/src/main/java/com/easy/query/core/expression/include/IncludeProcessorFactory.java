package com.easy.query.core.expression.include;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.sql.include.IncludeParserResult;
import com.easy.query.core.expression.sql.include.RelationExtraEntity;

import java.util.Collection;

/**
 * create time 2023/7/16 20:59
 * 文件说明
 *
 * @author xuejiaming
 */
public interface IncludeProcessorFactory {
    IncludeProcessor createIncludeProcess(IncludeParserResult includeParserResult, QueryRuntimeContext runtimeContext);
}
