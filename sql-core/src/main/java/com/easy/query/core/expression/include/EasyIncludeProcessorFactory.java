package com.easy.query.core.expression.include;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.metadata.NavigateMetadata;

import java.util.Collection;

/**
 * create time 2023/7/16 21:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyIncludeProcessorFactory implements IncludeProcessorFactory{
    @Override
    public <TEntity> IncludeProcessor createIncludeProcess(Collection<TEntity> entities, NavigateMetadata selfNavigateMetadata, QueryRuntimeContext runtimeContext) {
        return new EasyIncludeProcess(entities,selfNavigateMetadata,runtimeContext);
    }
}
