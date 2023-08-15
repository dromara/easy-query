package com.easy.query.oracle.expression;

import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;
import com.easy.query.core.expression.sql.expression.impl.UpdateSQLExpressionImpl;

/**
 * create time 2023/8/15 17:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class OracleUpdateSQLExpression extends UpdateSQLExpressionImpl {
    public OracleUpdateSQLExpression(EntitySQLExpressionMetadata entitySQLExpressionMetadata, EntityTableSQLExpression table) {
        super(entitySQLExpressionMetadata, table);
    }
}
