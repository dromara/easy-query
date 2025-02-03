package com.easy.query.sqlite.expression;

import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.expression.sql.expression.impl.DeleteSQLExpressionImpl;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;

/**
 * create time 2023/5/17 22:39
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLiteDeleteSQLExpression extends DeleteSQLExpressionImpl {

    public SQLiteDeleteSQLExpression(EntitySQLExpressionMetadata entitySQLExpressionMetadata, EntityTableSQLExpression table) {
        super(entitySQLExpressionMetadata, table);
    }
}
