package com.easy.query.kingbase.es.expression;

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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * create time 2023/7/28 21:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class KingbaseESInsertSQLExpression extends InsertSQLExpressionImpl {
    public KingbaseESInsertSQLExpression(EntitySQLExpressionMetadata entitySQLExpressionMetadata, EntityTableSQLExpression table) {
        super(entitySQLExpressionMetadata, table);
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        EasySQLExpressionUtil.expressionInvokeRoot(toSQLContext);
        EntityTableSQLExpression easyTableSQLExpression = tables.get(0);
        String tableName = easyTableSQLExpression.toSQL(toSQLContext);
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(tableName).append(" (");

        Iterator<SQLSegment> iterator = columns.getSQLSegments().iterator();
        SQLSegment firstColumn = iterator.next();

        sql.append(getColumnNameWithOwner(firstColumn,toSQLContext));
        while(iterator.hasNext()){
            SQLSegment next = iterator.next();
            sql.append(",").append(getColumnNameWithOwner(next,toSQLContext));
        }

        sql.append(") VALUES (").append(columns.toSQL(toSQLContext)).append(")");

        ExpressionContext expressionContext = entitySQLExpressionMetadata.getExpressionContext();
        if(expressionContext.getBehavior().hasBehavior(EasyBehaviorEnum.ON_DUPLICATE_KEY_IGNORE)){
            sql.append(" ON CONFLICT DO NOTHING");
        } else if (expressionContext.getBehavior().hasBehavior(EasyBehaviorEnum.ON_DUPLICATE_KEY_UPDATE)) {
            QueryRuntimeContext runtimeContext = getRuntimeContext();
            EntityMetadata entityMetadata = easyTableSQLExpression.getEntityMetadata();
            TableAvailable entityTable = easyTableSQLExpression.getEntityTable();
            Collection<String> keyProperties = entityMetadata.getKeyProperties();

            Collection<String> constraintPropertyNames = getConstraintPropertyName(entityMetadata,keyProperties);

            StringBuilder duplicateKeyUpdateSql = new StringBuilder();
            SQLBuilderSegment realDuplicateKeyUpdateColumns = getRealDuplicateKeyUpdateColumns();
            List<SQLSegment> realDuplicateKeyUpdateColumnsSQLSegments = realDuplicateKeyUpdateColumns.getSQLSegments();
            Set<String> duplicateKeyUpdateColumnsSet = getColumnsSet(columns);
            for (SQLSegment sqlSegment : realDuplicateKeyUpdateColumnsSQLSegments) {
                if (!(sqlSegment instanceof InsertUpdateSetColumnSQLSegment)) {
                    throw new EasyQueryInvalidOperationException("insert not support:" + EasyBehaviorEnum.ON_DUPLICATE_KEY_UPDATE.name()+",column type:"+ EasyClassUtil.getSimpleName(sqlSegment.getClass()));
                }
                InsertUpdateSetColumnSQLSegment sqlEntitySegment = (InsertUpdateSetColumnSQLSegment) sqlSegment;
                String propertyName = sqlEntitySegment.getPropertyName();
                if(constraintPropertyNames.contains(propertyName)||keyProperties.contains(propertyName)||!duplicateKeyUpdateColumnsSet.contains(propertyName)){
                    continue;
                }
                if(duplicateKeyUpdateSql.length()!=0){
                    duplicateKeyUpdateSql.append(", ");
                }
                String quoteName = sqlEntitySegment.getColumnNameWithOwner(toSQLContext);
                duplicateKeyUpdateSql.append(quoteName).append(" = ").append("EXCLUDED.").append(quoteName);
            }
            if(duplicateKeyUpdateSql.length()>0){
                sql.append(" ON CONFLICT (");
                Iterator<String> constraintPropertyIterator = constraintPropertyNames.iterator();
                String firstConstraintProperty = constraintPropertyIterator.next();
                sql.append(getQuoteName(entityTable,runtimeContext,firstConstraintProperty));
                while (constraintPropertyIterator.hasNext()){
                    String nextConstraintProperty = constraintPropertyIterator.next();
                    sql.append(",").append(getQuoteName(entityTable,runtimeContext,nextConstraintProperty));
                }
                sql.append(") DO UPDATE SET ")
                        .append(duplicateKeyUpdateSql);
            }
        }
        //SET column1 = EXCLUDED.column1, column2 = EXCLUDED.column2
        return sql.toString();
    }

    private String getQuoteName(TableAvailable entityTable,QueryRuntimeContext runtimeContext, String constraintProperty){
        String keyColumnName = entityTable.getColumnName(constraintProperty);
        return  EasySQLExpressionUtil.getQuoteName(runtimeContext, keyColumnName);
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
            throw new EasyQueryInvalidOperationException(EasyClassUtil.getSimpleName(entityMetadata.getEntityClass()) + " no constraint property");
        }
        return constraintKeys;
    }
}
