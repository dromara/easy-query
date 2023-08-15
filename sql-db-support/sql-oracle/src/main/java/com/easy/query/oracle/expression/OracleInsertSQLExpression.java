package com.easy.query.oracle.expression;

import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;
import com.easy.query.core.expression.sql.expression.impl.InsertSQLExpressionImpl;

/**
 * create time 2023/8/15 17:23
 * 文件说明
 *
 * @author xuejiaming
 */
public class OracleInsertSQLExpression  extends InsertSQLExpressionImpl {
    public OracleInsertSQLExpression(EntitySQLExpressionMetadata entitySQLExpressionMetadata, EntityTableSQLExpression table) {
        super(entitySQLExpressionMetadata, table);
    }
}
