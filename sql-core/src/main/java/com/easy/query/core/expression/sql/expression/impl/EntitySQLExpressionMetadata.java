package com.easy.query.core.expression.sql.expression.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.sql.TableContext;
import com.easy.query.core.expression.sql.builder.ExpressionContext;

/**
 * create time 2023/5/26 21:44
 * 文件说明
 *
 * @author xuejiaming
 */
public class EntitySQLExpressionMetadata {
    private final ExpressionContext expressionContext;
    private final TableContext tableContext;
    private final QueryRuntimeContext runtimeContext;

    public EntitySQLExpressionMetadata(ExpressionContext expressionContext, QueryRuntimeContext runtimeContext) {
        this(expressionContext, expressionContext.getTableContext(), runtimeContext);
    }

    public EntitySQLExpressionMetadata(ExpressionContext expressionContext, TableContext tableContext, QueryRuntimeContext runtimeContext) {
        this.expressionContext = expressionContext;

        this.tableContext = tableContext;
        this.runtimeContext = runtimeContext;
    }

    public TableContext getTableContext() {
        return tableContext;
    }

    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    public ExpressionContext getExpressionContext() {
        return expressionContext;
    }
}
