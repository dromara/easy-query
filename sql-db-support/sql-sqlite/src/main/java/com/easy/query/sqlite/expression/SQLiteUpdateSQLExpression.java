package com.easy.query.sqlite.expression;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;
import com.easy.query.core.expression.sql.expression.impl.UpdateSQLExpressionImpl;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

/**
 * create time 2023/5/17 22:38
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLiteUpdateSQLExpression extends UpdateSQLExpressionImpl {
    private static final Log log = LogFactory.getLog(UpdateSQLExpressionImpl.class);

    public SQLiteUpdateSQLExpression(EntitySQLExpressionMetadata entitySQLExpressionMetadata, EntityTableSQLExpression entityTableSQLExpression) {
        super(entitySQLExpressionMetadata, entityTableSQLExpression);
    }


    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        EasySQLExpressionUtil.expressionInvokeRoot(toSQLContext);
        if (EasySQLSegmentUtil.isEmpty(setColumns)) {
            log.warn("'UPDATE' statement without 'SET',not generate sql execute");
            return null;
        }
//        EntityTableSQLExpression easyTableSQLExpression = tables.get(0);
//        String tableName = easyTableSQLExpression.toSQL(toSQLContext);
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ");
        if (toSQLContext.getToTableContext().getTableSize() > 1) {
            EntityTableSQLExpression entityTableSQLExpression = tables.get(0);
            toSQLContext.setTableAlias(entityTableSQLExpression.getEntityTable(),null);
        }

        EasySQLExpressionUtil.joinUpdateDeleteTableAppend(sql, tables, toSQLContext);
        sql.append(" SET ");
        sql.append(setColumns.toSQL(toSQLContext));
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
//        return "UPDATE " + buildSQLTableOrJoin(tables) + " SET " + setColumns.toSQL(toSQLContext) + " WHERE " +
//                where.toSQL(toSQLContext);
    }

}
