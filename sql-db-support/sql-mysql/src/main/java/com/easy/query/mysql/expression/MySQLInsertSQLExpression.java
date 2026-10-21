package com.easy.query.mysql.expression;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.configuration.EasyQueryOption;
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
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;

import java.util.Collection;
import java.util.Iterator;
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
//        List<SQLSegment> sqlSegments = columns.getSQLSegments();
        ExpressionContext expressionContext = entitySQLExpressionMetadata.getExpressionContext();
        QueryRuntimeContext runtimeContext = getRuntimeContext();
        EasyQueryOption easyQueryOption = runtimeContext.getQueryConfiguration().getEasyQueryOption();
        boolean ignoreToUpdateKey = easyQueryOption.isMysqlInsertIgnoreToUpdateKey();
        boolean hasIgnore = expressionContext.getBehavior().hasBehavior(EasyBehaviorEnum.ON_DUPLICATE_KEY_IGNORE);
        StringBuilder sql = new StringBuilder(hasIgnore && !ignoreToUpdateKey ? "INSERT IGNORE INTO " : "INSERT INTO ");
        sql.append(tableName).append(" (");

        Iterator<SQLSegment> iterator = columns.getSQLSegments().iterator();
        SQLSegment firstColumn = iterator.next();

        sql.append(getColumnNameWithOwner(firstColumn, toSQLContext));
        while (iterator.hasNext()) {
            SQLSegment next = iterator.next();
            sql.append(",").append(getColumnNameWithOwner(next, toSQLContext));
        }

        sql.append(") VALUES (").append(columns.toSQL(toSQLContext)).append(")");
        EntityMetadata entityMetadata = easyTableSQLExpression.getEntityMetadata();
        TableAvailable entityTable = easyTableSQLExpression.getEntityTable();
        if (!hasIgnore && expressionContext.getBehavior().hasBehavior(EasyBehaviorEnum.ON_DUPLICATE_KEY_UPDATE)) {
            Collection<String> keyProperties = entityMetadata.getKeyProperties();
            StringBuilder duplicateKeyUpdateSql = new StringBuilder();
            SQLBuilderSegment realDuplicateKeyUpdateColumns = getRealDuplicateKeyUpdateColumns();
            List<SQLSegment> realDuplicateKeyUpdateColumnsSQLSegments = realDuplicateKeyUpdateColumns.getSQLSegments();
            Set<String> duplicateKeyUpdateColumnsSet = getColumnsSet(columns);
            for (SQLSegment sqlSegment : realDuplicateKeyUpdateColumnsSQLSegments) {
                if (!(sqlSegment instanceof SQLEntitySegment)) {
                    throw new EasyQueryInvalidOperationException("insert not support:" + EasyBehaviorEnum.ON_DUPLICATE_KEY_UPDATE.name() + ",column type:" + EasyClassUtil.getSimpleName(sqlSegment.getClass()));
                }
                SQLEntitySegment sqlEntitySegment = (SQLEntitySegment) sqlSegment;
                String propertyName = sqlEntitySegment.getPropertyName();
                if (keyProperties.contains(propertyName) || !duplicateKeyUpdateColumnsSet.contains(propertyName)) {
                    continue;
                }
                if (duplicateKeyUpdateSql.length() != 0) {
                    duplicateKeyUpdateSql.append(", ");
                }
                String columnName = entityTable.getColumnName(propertyName);
                String quoteName = EasySQLExpressionUtil.getQuoteName(runtimeContext, columnName);
                duplicateKeyUpdateSql.append(quoteName).append(" = ").append("VALUES(").append(quoteName).append(")");
            }
            if (duplicateKeyUpdateSql.length() > 0) {
                sql.append(" ON DUPLICATE KEY UPDATE ");
                sql.append(duplicateKeyUpdateSql);
            }
        }else if(ignoreToUpdateKey){
            sql.append(" ON DUPLICATE KEY UPDATE ");
            //获取非自增主键

            Collection<String> keyProperties = entityMetadata.getKeyProperties();
            StringBuilder duplicateKeyUpdateSql = new StringBuilder();
            for (String keyProperty : keyProperties) {
                if (duplicateKeyUpdateSql.length() != 0) {
                    duplicateKeyUpdateSql.append(", ");
                }
                ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(keyProperty);
                if(columnMetadata.isGeneratedKey()){
                    continue;
                }
                String columnName = columnMetadata.getName();
                String quoteName = EasySQLExpressionUtil.getQuoteName(runtimeContext, columnName);
                duplicateKeyUpdateSql.append(quoteName).append(" = ").append("VALUES(").append(quoteName).append(")");
            }
            if(duplicateKeyUpdateSql.length() == 0){
                throw new EasyQueryInvalidOperationException("[MySQL INSERT IGNORE INTO TO DUPLICATE KEY UPDATE] No non-auto-increment primary key found.");
            }
            sql.append(duplicateKeyUpdateSql);
        }
        return sql.toString();
    }
}
