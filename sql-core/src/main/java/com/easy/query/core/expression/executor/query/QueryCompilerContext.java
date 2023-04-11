package com.easy.query.core.expression.executor.query;

import java.util.Set;

/**
 * create time 2023/4/11 08:23
 * 文件说明
 *
 * @author xuejiaming
 */
public interface QueryCompilerContext {
    Set<Class<?>> getShardingEntities();
    boolean isShardingQuery();
    boolean isSingleShardingQuery();
}
