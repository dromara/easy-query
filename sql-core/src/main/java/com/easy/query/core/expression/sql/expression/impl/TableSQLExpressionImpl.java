package com.easy.query.core.expression.sql.expression.impl;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.configuration.dialect.Dialect;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;
import com.easy.query.core.util.EasyStringUtil;

import java.util.function.Function;


/**
 * create time 2023/4/22 21:15
 * 文件说明
 *
 * @author xuejiaming
 */
public class TableSQLExpressionImpl implements EntityTableSQLExpression {

    protected final MultiTableTypeEnum multiTableType;
    protected final QueryRuntimeContext runtimeContext;
    protected final Dialect dialect;
    protected final TableAvailable entityTable;
    protected PredicateSegment on;
    protected Function<String, String> tableNameAs;
    protected Function<String, String> schemaAs;

    public TableSQLExpressionImpl(TableAvailable entityTable, MultiTableTypeEnum multiTableType, QueryRuntimeContext runtimeContext) {
        this.entityTable = entityTable;
        this.multiTableType = multiTableType;
        this.runtimeContext = runtimeContext;
        this.dialect = runtimeContext.getQueryConfiguration().getDialect();
    }

    @Override
    public EntityMetadata getEntityMetadata() {
        return entityTable.getEntityMetadata();
    }

    @Override
    public int getIndex() {
        return entityTable.getIndex();
    }

    @Override
    public TableAvailable getEntityTable() {
        return entityTable;
    }


    public Function<String, String> getTableNameAs() {
        return tableNameAs;
    }

    @Override
    public void setTableNameAs(Function<String, String> tableNameAs) {
        this.tableNameAs = tableNameAs;
    }

    @Override
    public void setSchemaAs(Function<String, String> schemaAs) {
        this.schemaAs = schemaAs;
    }

    @Override
    public boolean tableNameIsAs() {
        return this.tableNameAs != null;
    }

    public String getSelectTableSource() {
        return multiTableType.getAppendSQL();
    }

    @Override
    public String getTableName() {
        String tableName = dialect.getQuoteName(doGetTableName());
        String schema = doGetSchema();
        if (EasyStringUtil.isNotBlank(schema)) {
            return dialect.getQuoteName(schema) + "." + tableName;
        }
        return tableName;
    }

    protected String doGetSchema() {
        if (entityTable.hasSchema() || schemaAs != null) {
            String schema = entityTable.getSchema();
            return schemaAs.apply(schema);
        }
        return null;
    }

    protected String doGetTableName() {
        String tableName = entityTable.getTableName();
        if (tableName == null) {
            throw new EasyQueryException("table " + EasyClassUtil.getSimpleName(entityTable.getEntityClass()) + " cant found mapping table name");
        }
        if (tableNameAs != null) {
            return tableNameAs.apply(tableName);
        }
        return tableName;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        EasySQLExpressionUtil.expressionInvokeRoot(toSQLContext);
        EasySQLExpressionUtil.tableSQLExpressionRewrite(toSQLContext,this);
        //如果当前对象没有映射到表那么直接抛错
        StringBuilder sql = new StringBuilder();

        sql.append(getSelectTableSource()).append(getTableName());
        String tableAlias = EasySQLExpressionUtil.getTableAlias(toSQLContext, entityTable);
        if (tableAlias != null) {
            sql.append(" ").append(tableAlias);
        }
        return sql.toString();
    }


    @Override
    public PredicateSegment getOn() {
        return on;
    }
    @Override
    public void setOn(PredicateSegment predicateSegment) {
        this.on = predicateSegment;
    }

    @Override
    public EntityTableSQLExpression cloneSQLExpression() {
        EntityTableSQLExpression tableSQLExpression = runtimeContext.getExpressionFactory().createEntityTableSQLExpression(entityTable, multiTableType,runtimeContext);
        if(EasySQLSegmentUtil.isNotEmpty(on)){
            PredicateSegment predicateSegment = on.clonePredicateSegment();
            tableSQLExpression.setOn(predicateSegment);
        }
        return tableSQLExpression;
    }
}
