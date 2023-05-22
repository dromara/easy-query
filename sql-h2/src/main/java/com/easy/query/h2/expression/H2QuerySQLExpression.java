package com.easy.query.h2.expression;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.sql.expression.impl.QuerySQLExpressionImpl;

/**
 * create time 2023/5/17 22:36
 * 文件说明
 *
 * @author xuejiaming
 */
public class H2QuerySQLExpression extends QuerySQLExpressionImpl {
    public H2QuerySQLExpression(QueryRuntimeContext runtimeContext) {
        super(runtimeContext);
    }
}
