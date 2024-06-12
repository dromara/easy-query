package com.easy.query.mssql.expression;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.InsertUpdateSetColumnSQLSegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;
import com.easy.query.core.expression.sql.expression.impl.InsertSQLExpressionImpl;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * create time 2023/7/27 17:56
 * 文件说明
 *
 * @author xuejiaming
 */
public class MsSQLInsertSQLExpression extends InsertSQLExpressionImpl {

    public MsSQLInsertSQLExpression(EntitySQLExpressionMetadata entitySQLExpressionMetadata, EntityTableSQLExpression table) {
        super(entitySQLExpressionMetadata, table);
    }

    /**
     * 支持insert or update
     * 代码参考 <a href="https://github.com/dotnetcore/FreeSql">FreeSQL</a>
     *
     * @param toSQLContext
     * @return
     */
    @Override
    public String toSQL(ToSQLContext toSQLContext) {
//        List<SQLSegment> sqlSegments = columns.getSQLSegments();
        ExpressionContext expressionContext = entitySQLExpressionMetadata.getExpressionContext();
        boolean insertOrIgnore = expressionContext.getBehavior().hasBehavior(EasyBehaviorEnum.ON_DUPLICATE_KEY_IGNORE);
        boolean insertOrUpdate = expressionContext.getBehavior().hasBehavior(EasyBehaviorEnum.ON_DUPLICATE_KEY_UPDATE);
        if (!insertOrIgnore && !insertOrUpdate) {
            return super.toSQL(toSQLContext);
        } else {
            EasySQLExpressionUtil.expressionInvokeRoot(toSQLContext);
            QueryRuntimeContext runtimeContext = expressionContext.getRuntimeContext();
            EntityTableSQLExpression easyTableSQLExpression = tables.get(0);
            EntityMetadata entityMetadata = easyTableSQLExpression.getEntityMetadata();
            TableAvailable entityTable = easyTableSQLExpression.getEntityTable();
            String tableName = easyTableSQLExpression.toSQL(toSQLContext);

            Collection<String> keyProperties = entityMetadata.getKeyProperties();

            Collection<String> constraintPropertyNames = getConstraintPropertyName(keyProperties);
            Set<String> duplicateKeyUpdateColumnsSet = getColumnsSet(columns);

            StringBuilder sql = new StringBuilder("MERGE INTO ");
            sql.append(tableName).append(" t1 USING (SELECT ");


            List<String> sqlColumns = new ArrayList<>(columns.getSQLSegments().size());
            StringBuilder mergeAliasSql = new StringBuilder();
            for (SQLSegment sqlSegment : columns.getSQLSegments()) {
                if (!(sqlSegment instanceof InsertUpdateSetColumnSQLSegment)) {
                    throw new EasyQueryInvalidOperationException("insert not support:" + EasyBehaviorEnum.ON_DUPLICATE_KEY_UPDATE.name() + ",column type:" + EasyClassUtil.getSimpleName(sqlSegment.getClass()));
                }
                InsertUpdateSetColumnSQLSegment sqlEntitySegment = (InsertUpdateSetColumnSQLSegment) sqlSegment;
                String propertyName = sqlEntitySegment.getPropertyName();
                if (constraintPropertyNames.contains(propertyName) || keyProperties.contains(propertyName) || !duplicateKeyUpdateColumnsSet.contains(propertyName)) {
                    continue;
                }
                if (mergeAliasSql.length() != 0) {
                    mergeAliasSql.append(",");
                }

                String columnNameWithOwner = getColumnNameWithOwner(sqlEntitySegment, toSQLContext);
                sqlColumns.add(columnNameWithOwner);
                mergeAliasSql.append(sqlEntitySegment.toSQL(toSQLContext)).append(" AS ").append(columnNameWithOwner);
            }
            sql.append(mergeAliasSql);
            sql.append(" ) t2 ");
            sql.append("ON (");

            Iterator<String> constraintPropertyIterator = constraintPropertyNames.iterator();
            String firstConstraintProperty = constraintPropertyIterator.next();
            String firstQuoteName = getQuoteName(entityTable, runtimeContext, firstConstraintProperty);
            sql.append("t1.").append(firstQuoteName).append(" = ").append("t2.").append(firstQuoteName);
            while (constraintPropertyIterator.hasNext()) {
                String nextConstraintProperty = constraintPropertyIterator.next();
                String quoteName = getQuoteName(entityTable, runtimeContext, nextConstraintProperty);
                sql.append(" AND t1.").append(quoteName).append(" = ").append("t2.").append(quoteName);
            }
            sql.append(") ");
            if (insertOrUpdate) {

                StringBuilder duplicateKeyUpdateSql = new StringBuilder();
                SQLBuilderSegment realDuplicateKeyUpdateColumns = getRealDuplicateKeyUpdateColumns();
                List<SQLSegment> realDuplicateKeyUpdateColumnsSQLSegments = realDuplicateKeyUpdateColumns.getSQLSegments();
                for (SQLSegment sqlSegment : realDuplicateKeyUpdateColumnsSQLSegments) {
                    if (!(sqlSegment instanceof InsertUpdateSetColumnSQLSegment)) {
                        throw new EasyQueryInvalidOperationException("insert not support:" + EasyBehaviorEnum.ON_DUPLICATE_KEY_UPDATE.name() + ",column type:" + EasyClassUtil.getSimpleName(sqlSegment.getClass()));
                    }
                    InsertUpdateSetColumnSQLSegment sqlEntitySegment = (InsertUpdateSetColumnSQLSegment) sqlSegment;
                    String propertyName = sqlEntitySegment.getPropertyName();
                    if (constraintPropertyNames.contains(propertyName) || keyProperties.contains(propertyName) || !duplicateKeyUpdateColumnsSet.contains(propertyName)) {
                        continue;
                    }
                    if (duplicateKeyUpdateSql.length() != 0) {
                        duplicateKeyUpdateSql.append(",");
                    }
                    String quoteName = sqlEntitySegment.getColumnNameWithOwner(toSQLContext);
                    duplicateKeyUpdateSql.append("t1.").append(quoteName).append(" = ").append("t2.").append(quoteName);
                }
                if (duplicateKeyUpdateSql.length() > 0) {
                    sql.append("WHEN MATCHED THEN UPDATE SET ");
                    sql.append(duplicateKeyUpdateSql).append(" ");
                }
            }
            sql.append("WHEN NOT MATCHED THEN INSERT (");
            sql.append(String.join(",", sqlColumns));
            sql.append(") VALUES (");
            sql.append(sqlColumns.stream().map(o -> "t2." + o).collect(Collectors.joining(",")));
            sql.append(");");

            return sql.toString();
        }
    }

    private String getQuoteName(TableAvailable entityTable, QueryRuntimeContext runtimeContext, String constraintProperty) {
        String keyColumnName = entityTable.getColumnName(constraintProperty);
        return EasySQLExpressionUtil.getQuoteName(runtimeContext, keyColumnName);
    }

    protected Collection<String> getConstraintPropertyName(Collection<String> keyProperties) {
        if (EasyCollectionUtil.isEmpty(duplicateKeys)) {
            return keyProperties;
        }
        return duplicateKeys;
    }
}