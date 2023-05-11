package com.easy.query.core.expression.sql.builder.impl;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.expression.parser.core.SqlWherePredicate;
import com.easy.query.core.expression.parser.core.SqlColumnSetter;
import com.easy.query.core.configuration.dialect.Dialect;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.EntityTableAvailable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;
import com.easy.query.core.expression.sql.expression.impl.TableSqlExpression;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.util.ClassUtil;

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
    private final EasyQueryRuntimeContext runtimeContext;
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
    public String getTableName() {
        return dialect.getQuoteName(doGetTableName());
    }

    protected String doGetTableName() {

        String tableName = entityTable.getTableName();
        if (tableName == null) {
            throw new EasyQueryException("table " + ClassUtil.getSimpleName(entityTable.getEntityClass()) + " cant found mapping table name");
        }
        if (tableNameAs != null) {
            return tableNameAs.apply(tableName);
        }
        return tableName;
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
    public SqlExpression<SqlWherePredicate<Object>> getLogicDeleteQueryFilterExpression() {
        if (getEntityMetadata().enableLogicDelete()) {
            return getEntityMetadata().getLogicDeleteMetadata().getLogicDeletePredicateFilterExpression();
        }
        return null;
    }

    @Override
    public SqlExpression<SqlColumnSetter<Object>> getLogicDeletedSqlExpression() {
        if (getEntityMetadata().enableLogicDelete()) {
            return getEntityMetadata().getLogicDeleteMetadata().getLogicDeletedSqlExpression();
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
    public EasyTableSqlExpression toExpression() {
        TableSqlExpression tableSqlExpression = new TableSqlExpression(entityTable, multiTableType,runtimeContext);
        tableSqlExpression.setTableNameAs(tableNameAs);
        return tableSqlExpression;
    }
}
