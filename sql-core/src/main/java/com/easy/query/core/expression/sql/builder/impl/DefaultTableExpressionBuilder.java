package com.easy.query.core.expression.sql.builder.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author xuejiaming
 * @FileName: EasyEntityTableExpressionSegment.java
 * @Description: 文件说明
 * create time 2023/3/3 23:31
 */
public class DefaultTableExpressionBuilder implements EntityTableExpressionBuilder {

    protected final TableAvailable entityTable;
    protected MultiTableTypeEnum multiTableType;
    private final ExpressionContext expressionContext;
    protected final QueryRuntimeContext runtimeContext;
    protected PredicateSegment on;
    protected PredicateSegment filterOn;
    protected Supplier<Boolean> tableLogicDel;
    protected Function<String, String> tableNameAs;
    protected Function<String, String> schemaAs;
    protected Function<String, String> linkAs;
    protected BiFunction<String, String, String> segmentAs;

    public DefaultTableExpressionBuilder(TableAvailable entityTable, MultiTableTypeEnum multiTableType, ExpressionContext expressionContext) {
        this.entityTable = entityTable;
        this.multiTableType = multiTableType;
        this.expressionContext = expressionContext;
        this.runtimeContext = expressionContext.getRuntimeContext();
    }

    @Override
    public void setMultiTableType(MultiTableTypeEnum multiTableType) {
        this.multiTableType = multiTableType;
    }

    @Override
    public EntityMetadata getEntityMetadata() {
        return entityTable.getEntityMetadata();
    }

    @Override
    public MultiTableTypeEnum getMultiTableType() {
        return multiTableType;
    }

    @Override
    public String getColumnName(String propertyName) {
        return entityTable.getColumnName(propertyName);
    }

    @Override
    public void setTableNameAs(Function<String, String> tableNameAs) {
        this.tableNameAs = tableNameAs;
    }

    @Override
    public void setTableLogicDelete(Supplier<Boolean> tableLogicDel) {
        this.tableLogicDel = tableLogicDel;
    }

    @Override
    public void setSchemaAs(Function<String, String> schemaAs) {
        this.schemaAs = schemaAs;
    }

    @Override
    public void setTableLinkAs(Function<String, String> linkAs) {
        this.linkAs = linkAs;
    }

    @Override
    public void setTableSegmentAs(BiFunction<String, String, String> segmentAs) {
        this.segmentAs = segmentAs;
    }

    @Override
    public void asAlias(String alias) {
        this.entityTable.asAlias(alias);
    }

    @Override
    public SQLActionExpression1<WherePredicate<Object>> getLogicDeleteQueryFilterExpression() {
        if (getEntityMetadata().enableLogicDelete()) {
            Boolean logicDel = tableLogicDelValue();
            if (logicDel == null || logicDel) {
                return getEntityMetadata().getLogicDeleteMetadata().getLogicDeletePredicateFilterExpression();
            }
        }
        return null;
    }

    private Boolean tableLogicDelValue() {
        if (this.tableLogicDel == null) {
            return null;
        }
        return tableLogicDel.get();
    }

    @Override
    public SQLActionExpression1<ColumnSetter<Object>> getLogicDeletedSQLExpression() {
        if (getEntityMetadata().enableLogicDelete()) {
            return getEntityMetadata().getLogicDeleteMetadata().getLogicDeletedSQLExpression();
        }
        return null;
    }

    @Override
    public EntityTableExpressionBuilder copyEntityTableExpressionBuilder() {


        EntityTableExpressionBuilder tableExpressionBuilder = runtimeContext.getExpressionBuilderFactory().createEntityTableExpressionBuilder(entityTable, multiTableType, expressionContext);
        if (on != null) {
            on.copyTo(tableExpressionBuilder.getOn());
        }
        tableExpressionBuilder.setTableNameAs(this.tableNameAs);
        tableExpressionBuilder.setSchemaAs(this.schemaAs);
        tableExpressionBuilder.setTableLinkAs(this.linkAs);
        tableExpressionBuilder.setTableLogicDelete(this.tableLogicDel);
        tableExpressionBuilder.setTableSegmentAs(this.segmentAs);
        return tableExpressionBuilder;
    }

    @Override
    public Class<?> getEntityClass() {
        return entityTable.getEntityClass();
    }

    @Override
    public PredicateSegment getOn() {
        if (on == null) {
            on = new AndPredicateSegment(true);
        }
        return on;
    }

    @Override
    public PredicateSegment getFilterOn() {
        if (filterOn == null) {
            filterOn = new AndPredicateSegment(true);
        }
        return filterOn;
    }

    @Override
    public boolean hasOn() {
        return EasySQLSegmentUtil.isNotEmpty(on);
    }

    @Override
    public boolean hasFilterOn() {
        return EasySQLSegmentUtil.isNotEmpty(filterOn);
    }

    @Override
    public TableAvailable getEntityTable() {
        return entityTable;
    }

    @Override
    public EntityTableSQLExpression toExpression() {
        EntityTableSQLExpression tableSQLExpression = runtimeContext.getExpressionFactory().createEntityTableSQLExpression(entityTable, multiTableType, runtimeContext);
        tableSQLExpression.setTableNameAs(tableNameAs);
        tableSQLExpression.setSchemaAs(schemaAs);
        tableSQLExpression.setLinkAs(linkAs);
        tableSQLExpression.setTableSegmentAs(segmentAs);
        return tableSQLExpression;
    }
}
