package com.easy.query.kingbase.es.expression;

import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;
import com.easy.query.core.expression.sql.expression.impl.UpdateSQLExpressionImpl;

/**
 * create time 2023/7/28 21:25
 * 文件说明
 *
 * @author xuejiaming
 */
public class KingbaseESUpdateSQLExpression extends UpdateSQLExpressionImpl {
    public KingbaseESUpdateSQLExpression(EntitySQLExpressionMetadata entitySQLExpressionMetadata, EntityTableSQLExpression table) {
        super(entitySQLExpressionMetadata, table);
    }
}
