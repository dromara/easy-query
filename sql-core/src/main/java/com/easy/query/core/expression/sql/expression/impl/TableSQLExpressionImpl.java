package com.easy.query.core.expression.sql.expression.impl;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;
import com.easy.query.core.util.EasyToSQLUtil;

import java.util.function.BiFunction;
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
    protected final SQLKeyword SQLKeyWord;
    protected final TableAvailable entityTable;
    protected PredicateSegment on;
    protected Function<String, String> tableNameAs;
    protected Function<String, String> schemaAs;
    protected Function<String, String> linkAs;
    protected BiFunction<String, String, String> segmentAs;

    public TableSQLExpressionImpl(TableAvailable entityTable, MultiTableTypeEnum multiTableType, QueryRuntimeContext runtimeContext) {
        this.entityTable = entityTable;
        this.multiTableType = multiTableType;
        this.runtimeContext = runtimeContext;
        this.SQLKeyWord = runtimeContext.getQueryConfiguration().getDialect();
    }

    @Override
    public EntityMetadata getEntityMetadata() {
        return entityTable.getEntityMetadata();
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
    public void setLinkAs(Function<String, String> linkAs) {
        this.linkAs = linkAs;
    }

    @Override
    public void setTableSegmentAs(BiFunction<String, String, String> segmentAs) {
        this.segmentAs = segmentAs;
    }

    @Override
    public boolean tableNameIsAs() {
        return this.tableNameAs != null;
    }

    public String getSelectTableSource() {
        if (linkAs != null) {
            return linkAs.apply(multiTableType.getAppendSQL());
        }
        return multiTableType.getAppendSQL();
    }

    @Override
    public String getTableName() {
        return EasyToSQLUtil.getSchemaTableName(SQLKeyWord, entityTable.getEntityMetadata(), entityTable.getTableName(), schemaAs, tableNameAs);
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        EasySQLExpressionUtil.expressionInvokeRoot(toSQLContext);
        EasySQLExpressionUtil.tableSQLExpressionRewrite(toSQLContext, this);
        //如果当前对象没有映射到表那么直接抛错
        StringBuilder sql = new StringBuilder();
        boolean none = MultiTableTypeEnum.NONE == multiTableType;
        if (!none) {
            sql.append(" ");
        }
        sql.append(getSelectTableSource());
        if (!none) {
            sql.append(" ");
        }
        sql.append(getTableSegment(toSQLContext));
        return sql.toString();
    }

    private String getTableSegment(ToSQLContext toSQLContext) {
        String tableName = getTableName();
        String tableAlias = EasySQLExpressionUtil.getTableAlias(toSQLContext, entityTable);
        if (segmentAs != null) {
            return segmentAs.apply(tableName, tableAlias);
        } else {
            StringBuilder sql = new StringBuilder();
            sql.append(tableName);
            if (tableAlias != null) {
                sql.append(" ").append(tableAlias);
            }
            return sql.toString();
        }
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
        EntityTableSQLExpression tableSQLExpression = runtimeContext.getExpressionFactory().createEntityTableSQLExpression(entityTable, multiTableType, runtimeContext);
        if (EasySQLSegmentUtil.isNotEmpty(on)) {
            PredicateSegment predicateSegment = on.clonePredicateSegment();
            tableSQLExpression.setOn(predicateSegment);
        }
        tableSQLExpression.setTableNameAs(tableNameAs);
        tableSQLExpression.setSchemaAs(schemaAs);
        tableSQLExpression.setLinkAs(linkAs);
        return tableSQLExpression;
    }
}
