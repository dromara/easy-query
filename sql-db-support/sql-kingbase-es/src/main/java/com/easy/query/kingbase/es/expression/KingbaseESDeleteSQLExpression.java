package com.easy.query.kingbase.es.expression;

import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.expression.sql.expression.impl.DeleteSQLExpressionImpl;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;

/**
 * create time 2023/7/28 21:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class KingbaseESDeleteSQLExpression extends DeleteSQLExpressionImpl {
    public KingbaseESDeleteSQLExpression(EntitySQLExpressionMetadata entitySQLExpressionMetadata, EntityTableSQLExpression table) {
        super(entitySQLExpressionMetadata, table);
    }
}
