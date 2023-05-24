package com.easy.query.h2.expression;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.expression.sql.expression.impl.InsertSQLExpressionImpl;

/**
 * create time 2023/5/17 22:36
 * 文件说明
 *
 * @author xuejiaming
 */
public class H2InsertSQLExpression extends InsertSQLExpressionImpl {
    public H2InsertSQLExpression(QueryRuntimeContext runtimeContext, EntityTableSQLExpression table) {
        super(runtimeContext, table);
    }
}
