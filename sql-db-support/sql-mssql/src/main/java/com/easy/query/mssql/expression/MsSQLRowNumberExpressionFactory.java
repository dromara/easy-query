package com.easy.query.mssql.expression;

import com.easy.query.core.expression.sql.expression.EntityQuerySQLExpression;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;

/**
 * create time 2023/8/1 09:26
 * 文件说明
 *
 * @author xuejiaming
 */
public class MsSQLRowNumberExpressionFactory extends MsSQLExpressionFactory{
    @Override
    public EntityQuerySQLExpression createEasyQuerySQLExpression(EntitySQLExpressionMetadata entitySQLExpressionMetadata) {
        return new MsSQLRowNumberQuerySQLExpression(entitySQLExpressionMetadata);
    }
}
