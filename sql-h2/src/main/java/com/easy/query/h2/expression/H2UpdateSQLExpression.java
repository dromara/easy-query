package com.easy.query.h2.expression;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.expression.sql.expression.impl.UpdateSQLExpressionImpl;

/**
 * create time 2023/5/17 22:38
 * 文件说明
 *
 * @author xuejiaming
 */
public class H2UpdateSQLExpression extends UpdateSQLExpressionImpl {

    public H2UpdateSQLExpression(QueryRuntimeContext runtimeContext, EntityTableSQLExpression table) {
        super(runtimeContext, table);
    }
}
