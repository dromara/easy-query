package com.easy.query.sqlite.expression;

import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;
import com.easy.query.core.expression.sql.expression.impl.UpdateSQLExpressionImpl;

/**
 * create time 2023/5/17 22:38
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLiteUpdateSQLExpression extends UpdateSQLExpressionImpl {

    public SQLiteUpdateSQLExpression(EntitySQLExpressionMetadata entitySQLExpressionMetadata, EntityTableSQLExpression entityTableSQLExpression) {
        super(entitySQLExpressionMetadata, entityTableSQLExpression);
    }
}
