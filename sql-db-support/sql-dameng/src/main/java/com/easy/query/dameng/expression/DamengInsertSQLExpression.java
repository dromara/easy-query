package com.easy.query.dameng.expression;

import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;
import com.easy.query.core.expression.sql.expression.impl.InsertSQLExpressionImpl;

/**
 * create time 2023/7/28 14:06
 * 文件说明
 *
 * @author xuejiaming
 */
public class DamengInsertSQLExpression extends InsertSQLExpressionImpl {
    public DamengInsertSQLExpression(EntitySQLExpressionMetadata entitySQLExpressionMetadata, EntityTableSQLExpression table) {
        super(entitySQLExpressionMetadata, table);
    }
}
