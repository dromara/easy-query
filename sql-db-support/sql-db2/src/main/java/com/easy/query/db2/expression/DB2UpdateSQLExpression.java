package com.easy.query.db2.expression;

import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;
import com.easy.query.core.expression.sql.expression.impl.UpdateSQLExpressionImpl;

/**
 * create time 2023/7/27 17:57
 * 文件说明
 *
 * @author xuejiaming
 */
public class DB2UpdateSQLExpression extends UpdateSQLExpressionImpl {
    public DB2UpdateSQLExpression(EntitySQLExpressionMetadata entitySQLExpressionMetadata) {
        super(entitySQLExpressionMetadata);
    }
}
