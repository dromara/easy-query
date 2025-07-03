package com.easy.query.core.trigger;

import com.easy.query.core.context.QueryRuntimeContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * create time 2025/7/3 13:32
 * 文件说明
 *
 * @author xuejiaming
 */
public class TriggerEvent {
    @NotNull
    private final Class<?> entityClass;
    @Nullable
    private final List<?> entities;
    @NotNull
    private final TriggerTypeEnum type;
    @NotNull
    private final QueryRuntimeContext runtimeContext;

    public TriggerEvent(@NotNull Class<?> entityClass, @Nullable List<?> entities, @NotNull TriggerTypeEnum type, @NotNull QueryRuntimeContext runtimeContext){
        this.entityClass = entityClass;
        this.entities = entities;
        this.type = type;
        this.runtimeContext = runtimeContext;
    }

    @NotNull
    public Class<?> getEntityClass() {
        return entityClass;
    }

    @Nullable
    public List<?> getEntities() {
        return entities;
    }

    @NotNull
    public TriggerTypeEnum getType() {
        return type;
    }

    @NotNull
    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }
}
