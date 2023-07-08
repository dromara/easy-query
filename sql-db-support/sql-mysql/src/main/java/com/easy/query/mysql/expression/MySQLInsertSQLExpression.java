package com.easy.query.mysql.expression;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;
import com.easy.query.core.expression.sql.expression.impl.InsertSQLExpressionImpl;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * create time 2023/5/17 22:36
 * 文件说明
 *
 * @author xuejiaming
 */
public class MySQLInsertSQLExpression extends InsertSQLExpressionImpl {
    public MySQLInsertSQLExpression(EntitySQLExpressionMetadata entitySQLExpressionMetadata, EntityTableSQLExpression table) {
        super(entitySQLExpressionMetadata, table);
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        EasySQLExpressionUtil.expressionInvokeRoot(toSQLContext);
        EntityTableSQLExpression easyTableSQLExpression = tables.get(0);
        String tableName = easyTableSQLExpression.toSQL(toSQLContext);
        List<SQLSegment> sqlSegments = columns.getSQLSegments();
        int insertColumns = sqlSegments.size();
        ExpressionContext expressionContext = entitySQLExpressionMetadata.getExpressionContext();
        boolean hasIgnore = expressionContext.getBehavior().hasBehavior(EasyBehaviorEnum.ON_DUPLICATE_KEY_IGNORE);
        StringBuilder sql = new StringBuilder(hasIgnore ? "INSERT IGNORE INTO " : "INSERT INTO ");
        sql.append(tableName).append(" (").append(columns.toSQL(toSQLContext)).append(") VALUES (");
        sql.append("?");
        for (int i = 0; i < insertColumns - 1; i++) {
            sql.append(",?");
        }
        sql.append(")");
        if (!hasIgnore && expressionContext.getBehavior().hasBehavior(EasyBehaviorEnum.ON_DUPLICATE_KEY_UPDATE)) {
            QueryRuntimeContext runtimeContext = getRuntimeContext();
            EntityMetadata entityMetadata = easyTableSQLExpression.getEntityMetadata();
            TableAvailable entityTable = easyTableSQLExpression.getEntityTable();
            Collection<String> keyProperties = entityMetadata.getKeyProperties();
            StringBuilder duplicateKeyUpdateSql = new StringBuilder();
            SQLBuilderSegment realDuplicateKeyUpdateColumns = getRealDuplicateKeyUpdateColumns();
            List<SQLSegment> realDuplicateKeyUpdateColumnsSQLSegments = realDuplicateKeyUpdateColumns.getSQLSegments();
            Set<String> duplicateKeyUpdateColumnsSet = getColumnsSet(columns);
            for (SQLSegment sqlSegment : realDuplicateKeyUpdateColumnsSQLSegments) {
                if (!(sqlSegment instanceof SQLEntitySegment)) {
                    throw new EasyQueryInvalidOperationException("insert not support:" + EasyBehaviorEnum.ON_DUPLICATE_KEY_UPDATE.name()+",column type:"+ EasyClassUtil.getSimpleName(sqlSegment.getClass()));
                }
                SQLEntitySegment sqlEntitySegment = (SQLEntitySegment) sqlSegment;
                String propertyName = sqlEntitySegment.getPropertyName();
                if(keyProperties.contains(propertyName)||!duplicateKeyUpdateColumnsSet.contains(propertyName)){
                    continue;
                }
                if(duplicateKeyUpdateSql.length()!=0){
                    duplicateKeyUpdateSql.append(", ");
                }
                String columnName = entityTable.getColumnName(propertyName);
                String quoteName = EasySQLExpressionUtil.getQuoteName(runtimeContext, columnName);
                duplicateKeyUpdateSql.append(quoteName).append(" = ").append("VALUES(").append(quoteName).append(")");
            }
            if(duplicateKeyUpdateSql.length()>0){
                sql.append(" ON DUPLICATE KEY UPDATE ");
                sql.append(duplicateKeyUpdateSql);
            }
        }
        return sql.toString();
    }
}
