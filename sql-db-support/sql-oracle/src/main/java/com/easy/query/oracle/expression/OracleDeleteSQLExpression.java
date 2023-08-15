package com.easy.query.oracle.expression;

import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.expression.sql.expression.impl.DeleteSQLExpressionImpl;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;

/**
 * create time 2023/8/15 17:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class OracleDeleteSQLExpression extends DeleteSQLExpressionImpl {
    public OracleDeleteSQLExpression(EntitySQLExpressionMetadata entitySQLExpressionMetadata, EntityTableSQLExpression table) {
        super(entitySQLExpressionMetadata, table);
    }
}
