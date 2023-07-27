package com.easy.query.mssql.expression;

import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.expression.sql.expression.impl.DeleteSQLExpressionImpl;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;

/**
 * create time 2023/7/27 17:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class MsSQLDeleteSQLExpression extends DeleteSQLExpressionImpl {
    public MsSQLDeleteSQLExpression(EntitySQLExpressionMetadata entitySQLExpressionMetadata, EntityTableSQLExpression table) {
        super(entitySQLExpressionMetadata, table);
    }
}
