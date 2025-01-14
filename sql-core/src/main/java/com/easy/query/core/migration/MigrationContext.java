package com.easy.query.core.migration;


import com.easy.query.core.context.QueryRuntimeContext;

import java.util.List;

/**
 * create time 2025/1/11 13:51
 * 文件说明
 *
 * @author xuejiaming
 */
public class MigrationContext {

    private final List<Class<?>> entities;
    private final QueryRuntimeContext runtimeContext;

    public MigrationContext(List<Class<?>> entities,QueryRuntimeContext runtimeContext) {
        this.entities = entities;
        this.runtimeContext = runtimeContext;
    }

    public List<Class<?>> getEntities() {
        return entities;
    }

    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }
}
