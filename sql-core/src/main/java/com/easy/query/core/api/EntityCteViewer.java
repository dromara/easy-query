package com.easy.query.core.api;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.context.QueryRuntimeContext;

import java.util.function.Supplier;

/**
 * create time 2025/5/31 09:36
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityCteViewer<TEntity> {
    Supplier<Query<TEntity>> viewConfigure(QueryRuntimeContext runtimeContext);
}
