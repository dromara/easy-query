package com.easy.query.pgsql.expression;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;
import com.easy.query.core.expression.sql.expression.impl.UpdateSQLExpressionImpl;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.util.Iterator;
import java.util.List;

/**
 * create time 2023/5/17 22:38
 * 文件说明
 *
 * @author xuejiaming
 */
public class PostgresSQLUpdateSQLExpression extends UpdateSQLExpressionImpl {
    private static final Log log = LogFactory.getLog(PostgresSQLUpdateSQLExpression.class);

    public PostgresSQLUpdateSQLExpression(EntitySQLExpressionMetadata entitySQLExpressionMetadata, EntityTableSQLExpression entityTableSQLExpression) {
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
        EntityTableSQLExpression firstTable = tables.get(0);
        sql.append(firstTable.toSQL(toSQLContext));
        sql.append(" SET ");
        toSQLContext.setTableAliasNull(firstTable.getEntityTable());
        sql.append(setColumns.toSQL(toSQLContext));
        toSQLContext.setTableAliasNull(null);

        if (tables.size() > 1) {
            List<EntityTableSQLExpression> joinTables = tables.subList(1, tables.size());
            EasySQLExpressionUtil.pgSQLUpdateDeleteJoinAndWhere(sql, joinTables, toSQLContext, where, MultiTableTypeEnum.FROM);
        } else {
            sql.append(" WHERE ");
            sql.append(where.toSQL(toSQLContext));
        }
        return sql.toString();
//        return "UPDATE " + buildSQLTableOrJoin(tables) + " SET " + setColumns.toSQL(toSQLContext) + " WHERE " +
//                where.toSQL(toSQLContext);
    }

}
