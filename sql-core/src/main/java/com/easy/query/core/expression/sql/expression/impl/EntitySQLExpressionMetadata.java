package com.easy.query.core.expression.sql.expression.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.sql.TableContext;

/**
 * create time 2023/5/26 21:44
 * 文件说明
 *
 * @author xuejiaming
 */
public class EntitySQLExpressionMetadata {
    private final TableContext tableContext;
    private final QueryRuntimeContext runtimeContext;

    public EntitySQLExpressionMetadata(TableContext tableContext, QueryRuntimeContext runtimeContext){

        this.tableContext = tableContext;
        this.runtimeContext = runtimeContext;
    }

    public TableContext getTableContext() {
        return tableContext;
    }

    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }
}
