package com.easy.query.core.expression.many;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.NavigateMetadata;

/**
 * create time 2025/3/19 16:14
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ToManySubquerySQLStrategy {
    <T> ClientQueryable<T> toManySubquery(ClientQueryable<T> clientQueryable, TableAvailable leftTable, NavigateMetadata navigateMetadata, QueryRuntimeContext runtimeContext);
}
