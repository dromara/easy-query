package com.easy.query.core.expression.sql.builder.impl;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.config.IDialect;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.sql.expression.EasySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;
import com.easy.query.core.expression.sql.expression.impl.TableSqlExpression;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import com.easy.query.core.expression.parser.abstraction.SqlPredicate;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.util.ClassUtil;
import com.easy.query.core.util.LambdaUtil;

import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: EasyEntityTableExpressionSegment.java
 * @Description: 文件说明
 * @Date: 2023/3/3 23:31
 */
public class TableExpressionBuilder implements EntityTableExpressionBuilder {

    protected final EntityMetadata entityMetadata;
    protected final int index;
    protected final String alias;
    protected final MultiTableTypeEnum multiTableType;
    private final EasyQueryRuntimeContext runtimeContext;
    private final IDialect dialect;
    protected PredicateSegment on;
    protected Function<String, String> tableNameAs;

    public TableExpressionBuilder(EntityMetadata entityMetadata, int index, String alias, MultiTableTypeEnum multiTableType, EasyQueryRuntimeContext runtimeContext) {
        this.entityMetadata = entityMetadata;

        this.index = index;
        this.alias = alias;
        this.multiTableType = multiTableType;
        this.runtimeContext = runtimeContext;
        this.dialect = runtimeContext.getEasyQueryConfiguration().getDialect();
    }

    @Override
    public EntityMetadata getEntityMetadata() {
        return entityMetadata;
    }

    @Override
    public <T1> String getPropertyName(Property<T1, ?> column) {
        return LambdaUtil.getPropertyName(column);
    }

    @Override
    public String getColumnName(String propertyName) {
        return this.entityMetadata.getColumnName(propertyName);
    }

    @Override
    public String getTableName() {
        return dialect.getQuoteName(doGetTableName());
    }

    protected String doGetTableName() {

        String tableName = entityMetadata.getTableName();
        if (tableName == null) {
            throw new EasyQueryException("table " + ClassUtil.getSimpleName(entityMetadata.getEntityClass()) + " cant found mapping table name");
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
    public SqlExpression<SqlPredicate<Object>> getLogicDeleteQueryFilterExpression() {
        if (entityMetadata.enableLogicDelete()) {
            return entityMetadata.getLogicDeleteMetadata().getLogicDeletePredicateFilterExpression();
        }
        return null;
    }

    @Override
    public SqlExpression<SqlColumnSetter<Object>> getLogicDeletedSqlExpression() {
        if (entityMetadata.enableLogicDelete()) {
            return entityMetadata.getLogicDeleteMetadata().getLogicDeletedSqlExpression();
        }
        return null;
    }

    @Override
    public EntityTableExpressionBuilder copyEntityTableExpressionBuilder() {

        TableExpressionBuilder tableExpressionBuilder = new TableExpressionBuilder(entityMetadata, index, alias, multiTableType,runtimeContext);
        if (on != null) {
            on.copyTo(tableExpressionBuilder.getOn());
        }
        tableExpressionBuilder.tableNameAs = this.tableNameAs;
        return tableExpressionBuilder;
    }

    @Override
    public Class<?> getEntityClass() {
        return entityMetadata.getEntityClass();
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
        return alias;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public EasyTableSqlExpression toExpression() {
        TableSqlExpression tableSqlExpression = new TableSqlExpression(entityMetadata, index, alias, multiTableType,runtimeContext);
        tableSqlExpression.setTableNameAs(tableNameAs);
        return tableSqlExpression;
    }
}
