package com.easy.query.sqlite.expression;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.InsertUpdateSetColumnSQLSegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;
import com.easy.query.core.expression.sql.expression.impl.InsertSQLExpressionImpl;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * create time 2023/5/17 22:36
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLiteInsertSQLExpression extends InsertSQLExpressionImpl {
    public SQLiteInsertSQLExpression(EntitySQLExpressionMetadata entitySQLExpressionMetadata, EntityTableSQLExpression table) {
        super(entitySQLExpressionMetadata, table);
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
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
            if(insertOrUpdate){
                StringBuilder sql = new StringBuilder("REPLACE INTO ");
                sql.append(tableName).append(" (");
                Iterator<SQLSegment> iterator = columns.getSQLSegments().iterator();
                SQLSegment firstColumn = iterator.next();
                sql.append(getColumnNameWithOwner(firstColumn, toSQLContext));
                while (iterator.hasNext()) {
                    SQLSegment next = iterator.next();
                    sql.append(",").append(getColumnNameWithOwner(next, toSQLContext));
                }

                sql.append(") VALUES (").append(columns.toSQL(toSQLContext)).append(")");
                return sql.toString();
            }else{

                StringBuilder sql = new StringBuilder("INSERT INTO ");
                sql.append(tableName).append(" (");
                Iterator<SQLSegment> iterator = columns.getSQLSegments().iterator();
                SQLSegment firstColumn = iterator.next();
                sql.append(getColumnNameWithOwner(firstColumn, toSQLContext));
                while (iterator.hasNext()) {
                    SQLSegment next = iterator.next();
                    sql.append(",").append(getColumnNameWithOwner(next, toSQLContext));
                }

                sql.append(") SELECT ").append(columns.toSQL(toSQLContext))
                        .append(" WHERE NOT EXISTS(SELECT 1 FROM ")
                        .append(tableName).append(" t ")
                        .append(" WHERE (");

                Collection<String> keyProperties = entityMetadata.getKeyProperties();
                Collection<String> constraintPropertyNames = getConstraintPropertyName(entityMetadata, keyProperties);
                Map<String, InsertUpdateSetColumnSQLSegment> columnsMap = getColumnsMap();

                Iterator<String> constraintPropertyIterator = constraintPropertyNames.iterator();

                String firstConstraintProperty = constraintPropertyIterator.next();
                String firstQuoteName = getQuoteName(entityTable, runtimeContext, firstConstraintProperty);
                InsertUpdateSetColumnSQLSegment insertUpdateSetColumnSQLSegment = columnsMap.get(firstConstraintProperty);
                if(insertUpdateSetColumnSQLSegment==null){
                    throw new EasyQueryInvalidOperationException("insert not support:" + EasyBehaviorEnum.ON_DUPLICATE_KEY_IGNORE.name() + ",constraint column name:" + firstConstraintProperty);
                }
                String parameter = insertUpdateSetColumnSQLSegment.toSQL(toSQLContext);
                sql.append("t.").append(firstQuoteName).append(" = ").append(parameter);
                while (constraintPropertyIterator.hasNext()) {
                    String nextConstraintProperty = constraintPropertyIterator.next();
                    String quoteName = getQuoteName(entityTable, runtimeContext, nextConstraintProperty);
                    InsertUpdateSetColumnSQLSegment nextInsertUpdateSetColumnSQLSegment = columnsMap.get(nextConstraintProperty);
                    if(nextInsertUpdateSetColumnSQLSegment==null){
                        throw new EasyQueryInvalidOperationException("insert not support:" + EasyBehaviorEnum.ON_DUPLICATE_KEY_IGNORE.name() + ",constraint column name:" + nextConstraintProperty);
                    }
                    String nextParameter = nextInsertUpdateSetColumnSQLSegment.toSQL(toSQLContext);
                    sql.append(" AND t.").append(quoteName).append(" = ").append(nextParameter);
                }
                sql.append(")")
                        .append(")");
                return sql.toString();
            }

        }

    }

    private Map<String,InsertUpdateSetColumnSQLSegment> getColumnsMap(){
        HashMap<String, InsertUpdateSetColumnSQLSegment> columnsMap = new HashMap<>();
        for (SQLSegment sqlSegment : columns.getSQLSegments()) {
            if (!(sqlSegment instanceof InsertUpdateSetColumnSQLSegment)) {
                throw new EasyQueryInvalidOperationException("insert not support:" + EasyBehaviorEnum.ON_DUPLICATE_KEY_IGNORE.name() + ",column type:" + EasyClassUtil.getSimpleName(sqlSegment.getClass()));
            }
            InsertUpdateSetColumnSQLSegment sqlEntitySegment = (InsertUpdateSetColumnSQLSegment) sqlSegment;
            columnsMap.put(sqlEntitySegment.getPropertyName(),sqlEntitySegment);
        }
        return columnsMap;
    }

    protected Collection<String> getConstraintPropertyName(EntityMetadata entityMetadata, Collection<String> keyProperties) {
        if (EasyCollectionUtil.isEmpty(duplicateKeys)) {
            return getConstraintPropertyName0(entityMetadata,keyProperties);
        }
        return getConstraintPropertyName0(entityMetadata,duplicateKeys);
    }

    private Collection<String> getConstraintPropertyName0(EntityMetadata entityMetadata, Collection<String> columns) {
        Set<String> constraintKeys = columns.stream().filter(o -> !entityMetadata.getColumnNotNull(o).isGeneratedKey()).collect(Collectors.toSet());
        if (EasyCollectionUtil.isEmpty(constraintKeys)) {
            throw new EasyQueryInvalidOperationException(EasyClassUtil.getSimpleName(entityMetadata.getEntityClass()) + " no constraint property,Please ensure that the constrained property is not generate key column.");
        }
        return constraintKeys;
    }
    private String getQuoteName(TableAvailable entityTable, QueryRuntimeContext runtimeContext, String constraintProperty) {
        String keyColumnName = entityTable.getColumnName(constraintProperty);
        return EasySQLExpressionUtil.getQuoteName(runtimeContext, keyColumnName);
    }
}
