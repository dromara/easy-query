package com.easy.query.pgsql.expression;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SQLEntitySegment;
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
import java.util.List;
import java.util.Objects;

/**
 * create time 2023/5/17 22:36
 * 文件说明
 *
 * @author xuejiaming
 */
public class PostgresSQLInsertSQLExpression extends InsertSQLExpressionImpl {
    public PostgresSQLInsertSQLExpression(EntitySQLExpressionMetadata entitySQLExpressionMetadata, EntityTableSQLExpression table) {
        super(entitySQLExpressionMetadata, table);
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        EasySQLExpressionUtil.expressionInvokeRoot(toSQLContext);
        EntityTableSQLExpression easyTableSQLExpression = tables.get(0);
        String tableName = easyTableSQLExpression.toSQL(toSQLContext);
        List<SQLSegment> sqlSegments = columns.getSQLSegments();
        int insertColumns = sqlSegments.size();
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(tableName).append(" (").append(columns.toSQL(toSQLContext)).append(") VALUES (");
        sql.append("?");
        for (int i = 0; i < insertColumns - 1; i++) {
            sql.append(",?");
        }
        sql.append(")");

        ExpressionContext expressionContext = entitySQLExpressionMetadata.getExpressionContext();
        if(expressionContext.getBehavior().hasBehavior(EasyBehaviorEnum.ON_DUPLICATE_KEY_IGNORE)){
            sql.append(" ON CONFLICT DO NOTHING");
        } else if (expressionContext.getBehavior().hasBehavior(EasyBehaviorEnum.ON_DUPLICATE_KEY_UPDATE)) {
            QueryRuntimeContext runtimeContext = getRuntimeContext();
            EntityMetadata entityMetadata = easyTableSQLExpression.getEntityMetadata();
            TableAvailable entityTable = easyTableSQLExpression.getEntityTable();
            Collection<String> keyProperties = entityMetadata.getKeyProperties();
            if(EasyCollectionUtil.isNotSingle(keyProperties)){
                throw new EasyQueryInvalidOperationException("not found single key, cant use:"+EasyBehaviorEnum.ON_DUPLICATE_KEY_UPDATE.name()+",entity :"+ EasyClassUtil.getSimpleName(entityMetadata.getEntityClass()));
            }
            String keyPropertyName = EasyCollectionUtil.first(keyProperties);

            StringBuilder duplicateKeyUpdateSql = new StringBuilder();
            for (SQLSegment sqlSegment : sqlSegments) {
                if (!(sqlSegment instanceof SQLEntitySegment)) {
                    throw new EasyQueryInvalidOperationException("insert not support:" + EasyBehaviorEnum.ON_DUPLICATE_KEY_UPDATE.name()+",column type:"+ EasyClassUtil.getSimpleName(sqlSegment.getClass()));
                }
                SQLEntitySegment sqlEntitySegment = (SQLEntitySegment) sqlSegment;
                String propertyName = sqlEntitySegment.getPropertyName();
                if(Objects.equals(propertyName,keyPropertyName)){
                    continue;
                }
                if(duplicateKeyUpdateSql.length()!=0){
                    duplicateKeyUpdateSql.append(", ");
                }
                String columnName = entityTable.getColumnName(propertyName);
                String quoteName = EasySQLExpressionUtil.getQuoteName(runtimeContext, columnName);
                duplicateKeyUpdateSql.append(quoteName).append(" = ").append("EXCLUDED.").append(quoteName);
            }
            if(duplicateKeyUpdateSql.length()>0){
                String keyColumnName = entityTable.getColumnName(keyPropertyName);
                String quoteKeyName = EasySQLExpressionUtil.getQuoteName(runtimeContext, keyColumnName);
                sql.append(" ON CONFLICT (").append(quoteKeyName).append(") DO UPDATE SET ")
                        .append(duplicateKeyUpdateSql);
            }
        }
        //SET column1 = EXCLUDED.column1, column2 = EXCLUDED.column2
        return sql.toString();
    }
}
