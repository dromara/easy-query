package com.easy.query.clickhouse.expression;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.expression.sql.expression.impl.DeleteSQLExpressionImpl;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * create time 2023/5/17 22:39
 * 文件说明
 *
 * @author xuejiaming
 */
public class ClickHouseDeleteSQLExpression extends DeleteSQLExpressionImpl {

    public ClickHouseDeleteSQLExpression(EntitySQLExpressionMetadata entitySQLExpressionMetadata, EntityTableSQLExpression table) {
        super(entitySQLExpressionMetadata, table);
    }
    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        if(tables.size()>1){
            throw new UnsupportedOperationException();
        }

        EasySQLExpressionUtil.expressionInvokeRoot(toSQLContext);
        EntityTableSQLExpression easyTableSQLExpression = tables.get(0);
        String tableName = easyTableSQLExpression.toSQL(toSQLContext);

        StringBuilder sql = new StringBuilder();
        sql.append("ALTER TABLE ");
        sql.append(tableName);
        sql.append(" DELETE ");
        EasySQLExpressionUtil.joinUpdateDeleteTableAppend(sql, tables, toSQLContext);
        sql.append(" WHERE ");
        sql.append(where.toSQL(toSQLContext));
        return sql.toString();
    }
}
