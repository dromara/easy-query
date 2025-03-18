package com.easy.query.sqlite.expression;

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
public class SQLiteDeleteSQLExpression extends DeleteSQLExpressionImpl {

    public SQLiteDeleteSQLExpression(EntitySQLExpressionMetadata entitySQLExpressionMetadata, EntityTableSQLExpression table) {
        super(entitySQLExpressionMetadata, table);
    }
    @Override
    public String toSQL(ToSQLContext toSQLContext) {

        EasySQLExpressionUtil.expressionInvokeRoot(toSQLContext);

        StringBuilder sql = new StringBuilder();
        sql.append("DELETE ");
        if (toSQLContext.getToTableContext().getTableSize() > 1) {
            EntityTableSQLExpression entityTableSQLExpression = tables.get(0);
            toSQLContext.setTableAlias(entityTableSQLExpression.getEntityTable(),null);
        }
        sql.append("FROM ");
        EasySQLExpressionUtil.joinUpdateDeleteTableAppend(sql, tables, toSQLContext);
        sql.append(" WHERE ");
        if (toSQLContext.getToTableContext().getTableSize() > 1) {
            EntityTableSQLExpression entityTableSQLExpression = tables.get(0);
            String tableName = entityTableSQLExpression.getTableName();
            toSQLContext.setTableAlias(entityTableSQLExpression.getEntityTable(), tableName);
        }
        sql.append(where.toSQL(toSQLContext));

        if (toSQLContext.getToTableContext().getTableSize() > 1) {
            toSQLContext.setTableAlias(null, null);
        }
        return sql.toString();
    }
}
