package com.easy.query.clickhouse.expression;

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
public class ClickHouseUpdateSQLExpression extends UpdateSQLExpressionImpl {
    private static final Log log = LogFactory.getLog(ClickHouseUpdateSQLExpression.class);

    public ClickHouseUpdateSQLExpression(EntitySQLExpressionMetadata entitySQLExpressionMetadata, EntityTableSQLExpression entityTableSQLExpression) {
        super(entitySQLExpressionMetadata, entityTableSQLExpression);
    }


    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        if(tables.size()>1){
            throw new UnsupportedOperationException();
        }
        EasySQLExpressionUtil.expressionInvokeRoot(toSQLContext);
        if (EasySQLSegmentUtil.isEmpty(setColumns)) {
            log.warn("'UPDATE' statement without 'SET',not generate sql execute");
            return null;
        }
        EntityTableSQLExpression easyTableSQLExpression = tables.get(0);
        String tableName = easyTableSQLExpression.toSQL(toSQLContext);
        StringBuilder sql = new StringBuilder();
        sql.append("ALTER TABLE ");
        sql.append(tableName);
        sql.append(" UPDATE ");
        sql.append(setColumns.toSQL(toSQLContext));
        sql.append(" WHERE ");
        sql.append(where.toSQL(toSQLContext));
        return sql.toString();
//        return "UPDATE " + buildSQLTableOrJoin(tables) + " SET " + setColumns.toSQL(toSQLContext) + " WHERE " +
//                where.toSQL(toSQLContext);
    }
}
