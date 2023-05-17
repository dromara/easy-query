package com.easy.query.core.expression.sql.builder.impl;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.expression.parser.core.SQLWherePredicate;
import com.easy.query.core.expression.parser.core.SQLColumnSetter;
import com.easy.query.core.configuration.dialect.Dialect;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.expression.EntityTableAvailable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.expression.EasyTableSQLExpression;
import com.easy.query.core.expression.sql.expression.impl.TableSQLExpression;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.expression.lambda.SQLExpression;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;

import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: EasyEntityTableExpressionSegment.java
 * @Description: 文件说明
 * @Date: 2023/3/3 23:31
 */
public class TableExpressionBuilder implements EntityTableExpressionBuilder {

    protected final TableAvailable entityTable;
    protected final MultiTableTypeEnum multiTableType;
    protected final EasyQueryRuntimeContext runtimeContext;
    private final Dialect dialect;
    protected PredicateSegment on;
    protected Function<String, String> tableNameAs;
    public TableExpressionBuilder(EntityMetadata entityMetadata, int index, String alias, MultiTableTypeEnum multiTableType, EasyQueryRuntimeContext runtimeContext) {
        this(new EntityTableAvailable(index,entityMetadata,alias),multiTableType,runtimeContext);

    }
    public TableExpressionBuilder(TableAvailable entityTable, MultiTableTypeEnum multiTableType, EasyQueryRuntimeContext runtimeContext) {
        this.entityTable = entityTable;

        this.multiTableType = multiTableType;
        this.runtimeContext = runtimeContext;
        this.dialect = runtimeContext.getEasyQueryConfiguration().getDialect();
    }

    @Override
    public EntityMetadata getEntityMetadata() {
        return entityTable.getEntityMetadata();
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
    public boolean tableNameIsAs() {
        return tableNameAs != null;
    }

    @Override
    public SQLExpression<SQLWherePredicate<Object>> getLogicDeleteQueryFilterExpression() {
        if (getEntityMetadata().enableLogicDelete()) {
            return getEntityMetadata().getLogicDeleteMetadata().getLogicDeletePredicateFilterExpression();
        }
        return null;
    }

    @Override
    public SQLExpression<SQLColumnSetter<Object>> getLogicDeletedSQLExpression() {
        if (getEntityMetadata().enableLogicDelete()) {
            return getEntityMetadata().getLogicDeleteMetadata().getLogicDeletedSQLExpression();
        }
        return null;
    }

    @Override
    public EntityTableExpressionBuilder copyEntityTableExpressionBuilder() {

        TableExpressionBuilder tableExpressionBuilder = new TableExpressionBuilder(entityTable, multiTableType,runtimeContext);
        if (on != null) {
            on.copyTo(tableExpressionBuilder.getOn());
        }
        tableExpressionBuilder.tableNameAs = this.tableNameAs;
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
    public boolean hasOn() {
        return on != null && on.isNotEmpty();
    }

    @Override
    public String getAlias() {
        return entityTable.getAlias();
    }

    @Override
    public int getIndex() {
        return entityTable.getIndex();
    }

    @Override
    public TableAvailable getEntityTable() {
        return entityTable;
    }

    @Override
    public EasyTableSQLExpression toExpression() {
        TableSQLExpression tableSQLExpression = new TableSQLExpression(entityTable, multiTableType,runtimeContext);
        tableSQLExpression.setTableNameAs(tableNameAs);
        return tableSQLExpression;
    }
}
