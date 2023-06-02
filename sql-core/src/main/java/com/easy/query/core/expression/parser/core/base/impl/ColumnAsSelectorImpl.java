package com.easy.query.core.expression.parser.core.base.impl;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.FuncColumnSegment;
import com.easy.query.core.expression.segment.SubQueryColumnSegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: DefaultSqSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/8 00:10
 */
public class ColumnAsSelectorImpl<T1, TR> extends AbstractColumnSelector<T1, ColumnAsSelector<T1, TR>> implements ColumnAsSelector<T1, TR> {

    protected final Class<TR> resultClass;
    protected final EntityMetadata resultEntityMetadata;

    public ColumnAsSelectorImpl(int index, EntityQueryExpressionBuilder EntityQueryExpressionBuilder, SQLBuilderSegment sqlSegment0Builder, Class<TR> resultClass) {
        super(index, EntityQueryExpressionBuilder, sqlSegment0Builder);
        this.resultClass = resultClass;
        this.resultEntityMetadata = EntityQueryExpressionBuilder.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(resultClass);
    }

    @Override
    public ColumnAsSelector<T1, TR> columnAs(String property, String propertyAlias) {
        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(index);
        String aliasColumnName = getResultColumnName(propertyAlias);
        ColumnSegment columnSegment = sqlSegmentFactory.createColumnSegment(table.getEntityTable(), property, entityQueryExpressionBuilder.getRuntimeContext(), aliasColumnName);
        sqlSegmentBuilder.append(columnSegment);
        return this;
    }

    @Override
    public <TSubQuery> ColumnAsSelector<T1, TR> columnSubQueryAs(Function<WherePredicate<T1>, Query<TSubQuery>> subQueryFunc, String propertyAlias) {
        SQLExpressionProvider<T1> sqlExpressionProvider = runtimeContext.getSQLExpressionInvokeFactory().createSQLExpressionProvider(index, entityQueryExpressionBuilder);
        WherePredicate<T1> sqlWherePredicate = sqlExpressionProvider.getWherePredicate();
        Query<TSubQuery> subQuery = subQueryFunc.apply(sqlWherePredicate);
        extract(subQuery);
        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(index);
        String aliasColumnName = getResultColumnName(propertyAlias);
        SubQueryColumnSegment subQueryColumnSegment = sqlSegmentFactory.createSubQueryColumnSegment(table.getEntityTable(), subQuery, aliasColumnName, runtimeContext);
        sqlSegmentBuilder.append(subQueryColumnSegment);
        return this;
    }

    public <T2> void extract(Query<T2> subQuery) {

        EntityQueryExpressionBuilder subQueryableSQLEntityExpressionBuilder = subQuery.getSQLEntityExpressionBuilder();
        entityQueryExpressionBuilder.getExpressionContext().extract(subQueryableSQLEntityExpressionBuilder.getExpressionContext());
    }

    protected String getResultColumnName(String propertyAlias) {
        ColumnMetadata columnMetadata = resultEntityMetadata.getColumnNotNull(propertyAlias);
        return columnMetadata.getName();
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public ColumnAsSelector<T1, TR> columnAll() {

        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(index);
        if (table.getEntityClass().equals(resultClass)) {
            super.columnAll();
            return this;
        } else {
            return columnAll(table);
        }
    }

    private ColumnAsSelector<T1, TR> columnAll(EntityTableExpressionBuilder table) {
        if (table instanceof AnonymousEntityTableExpressionBuilder) {
            columnAnonymousAll((AnonymousEntityTableExpressionBuilder) table);
        } else {
            EntityMetadata entityMetadata = table.getEntityMetadata();
            Collection<String> properties = entityMetadata.getProperties();
            for (String property : properties) {
                ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(property);
                String columnName = columnMetadata.getName();
                String aliasPropertyName = resultEntityMetadata.getPropertyNameOrNull(columnName);
                if (aliasPropertyName != null) {
                    ColumnMetadata resultColumnMetadata = resultEntityMetadata.getColumnNotNull(aliasPropertyName);
                    String aliasColumnName = resultColumnMetadata.getName();
                    String alias = Objects.equals(columnName,aliasColumnName)?null:aliasColumnName;
                    ColumnSegment columnSegment = sqlSegmentFactory.createColumnSegment(table.getEntityTable(), property, entityQueryExpressionBuilder.getRuntimeContext(), alias);
                    sqlSegmentBuilder.append(columnSegment);
                }
            }
        }
        return this;
    }

    @Override
    public ColumnAsSelector<T1, TR> columnFuncAs(ColumnPropertyFunction columnPropertyFunction, String propertyAlias) {
        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(index);
        String propertyName = columnPropertyFunction.getPropertyName();
        ColumnFunction columnFunction = columnPropertyFunction.getColumnFunction();
        String columnAsName = propertyAlias == null ? table.getColumnName(propertyName) : getResultColumnName(propertyAlias);
        FuncColumnSegment funcColumnSegment = sqlSegmentFactory.createFuncColumnSegment(table.getEntityTable(), propertyName, entityQueryExpressionBuilder.getRuntimeContext(), columnFunction, columnAsName);
        sqlSegmentBuilder.append(funcColumnSegment);
        return this;
    }

}
