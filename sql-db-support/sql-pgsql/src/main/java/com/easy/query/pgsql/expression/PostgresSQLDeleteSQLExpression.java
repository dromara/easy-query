package com.easy.query.pgsql.expression;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.expression.sql.expression.impl.DeleteSQLExpressionImpl;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;
import com.easy.query.core.util.EasySQLExpressionUtil;

import java.util.List;

/**
 * create time 2023/5/17 22:39
 * 文件说明
 *
 * @author xuejiaming
 */
public class PostgresSQLDeleteSQLExpression extends DeleteSQLExpressionImpl {

    public PostgresSQLDeleteSQLExpression(EntitySQLExpressionMetadata entitySQLExpressionMetadata, EntityTableSQLExpression table) {
        super(entitySQLExpressionMetadata, table);
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {

        EasySQLExpressionUtil.expressionInvokeRoot(toSQLContext);

        StringBuilder sql = new StringBuilder();
        sql.append("DELETE ");
        sql.append("FROM ");

        EntityTableSQLExpression firstTable = tables.get(0);
        sql.append(firstTable.toSQL(toSQLContext));

        if (tables.size() > 1) {
            List<EntityTableSQLExpression> joinTables = tables.subList(1, tables.size());
            EasySQLExpressionUtil.pgSQLUpdateDeleteJoinAndWhere(sql, joinTables, toSQLContext, where, MultiTableTypeEnum.USING);
        } else {
            sql.append(" WHERE ");
            sql.append(where.toSQL(toSQLContext));
        }
        return sql.toString();
    }
}
