package com.easy.query.tsdb.expression;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;
import com.easy.query.core.expression.sql.expression.impl.UpdateSQLExpressionImpl;

/**
 * create time 2023/5/17 22:38
 * 文件说明
 *
 * @author xuejiaming
 */
public class TSDBUpdateSQLExpression extends UpdateSQLExpressionImpl {

    public TSDBUpdateSQLExpression(EntitySQLExpressionMetadata entitySQLExpressionMetadata, EntityTableSQLExpression tableSQLExpression) {
        super(entitySQLExpressionMetadata,tableSQLExpression);
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        throw new UnsupportedOperationException();
    }
}
