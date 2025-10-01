package com.easy.query.duckdb.expression;

import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;
import com.easy.query.core.expression.sql.expression.impl.QuerySQLExpressionImpl;

/**
 * create time 2023/5/17 22:36
 * 文件说明
 *
 * @author xuejiaming
 */
public class DuckDBSQLQuerySQLExpression extends QuerySQLExpressionImpl {
    public DuckDBSQLQuerySQLExpression(EntitySQLExpressionMetadata entitySQLExpressionMetadata) {
        super(entitySQLExpressionMetadata);
    }

}
