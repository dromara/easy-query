package com.easy.query.core.expression.builder.impl;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.FuncColumnSegment;
import com.easy.query.core.expression.segment.SQLColumnSegment;
import com.easy.query.core.expression.segment.SubQueryColumnSegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;

import java.util.Collection;
import java.util.Objects;

/**
 * create time 2023/6/22 20:56
 * 文件说明
 *
 * @author xuejiaming
 */
public class AsSelectorImpl extends AbstractSelector<AsSelector> implements AsSelector {
    private final EntityQueryExpressionBuilder entityQueryExpressionBuilder;
    protected final Class<?> resultClass;
    protected final EntityMetadata resultEntityMetadata;
    public AsSelectorImpl(EntityQueryExpressionBuilder entityQueryExpressionBuilder, SQLBuilderSegment sqlBuilderSegment, Class<?> resultClass){
        super(entityQueryExpressionBuilder,sqlBuilderSegment);
        this.entityQueryExpressionBuilder = entityQueryExpressionBuilder;

        this.resultClass = resultClass;
        this.resultEntityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(resultClass);
    }

    protected String getResultColumnName(String propertyAlias) {
        ColumnMetadata columnMetadata = resultEntityMetadata.getColumnNotNull(propertyAlias);
        return columnMetadata.getName();
    }
    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }


    @Override
    public AsSelector columnAs(TableAvailable table, String property, String propertyAlias) {
        String aliasColumnName = getResultColumnName(propertyAlias);
        ColumnSegment columnSegment = sqlSegmentFactory.createColumnSegment(table, property, runtimeContext, aliasColumnName);
        sqlBuilderSegment.append(columnSegment);
        return this;
    }
    public <T2> void extract(Query<T2> subQuery) {

        EntityQueryExpressionBuilder subQueryableSQLEntityExpressionBuilder = subQuery.getSQLEntityExpressionBuilder();
        expressionContext.extract(subQueryableSQLEntityExpressionBuilder.getExpressionContext());
    }

    @Override
    public <TSubQuery> AsSelector columnSubQueryAs(SQLFuncExpression<Query<TSubQuery>> subQueryableFunc, String propertyAlias) {
        Query<TSubQuery> subQuery = subQueryableFunc.apply();
        extract(subQuery);
        String aliasColumnName = getResultColumnName(propertyAlias);
        SubQueryColumnSegment subQueryColumnSegment = sqlSegmentFactory.createSubQueryColumnSegment(null, subQuery, aliasColumnName, runtimeContext);
        sqlBuilderSegment.append(subQueryColumnSegment);
        return this;
    }

    @Override
    public AsSelector columnAll(TableAvailable table) {

        if (table.getEntityClass().equals(resultClass)) {
            super.columnAll(table);
            return this;
        } else {
            EntityTableExpressionBuilder tableBuilder = entityQueryExpressionBuilder.getTable(table.getIndex());
            return columnAll(tableBuilder);
        }
    }

    private AsSelector columnAll(EntityTableExpressionBuilder tableBuilder) {
        if (tableBuilder instanceof AnonymousEntityTableExpressionBuilder) {
            columnAnonymousAll((AnonymousEntityTableExpressionBuilder) tableBuilder);
        } else {
            EntityMetadata entityMetadata = tableBuilder.getEntityMetadata();
            Collection<String> properties = entityMetadata.getProperties();
            for (String property : properties) {
                ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(property);
                String columnName = columnMetadata.getName();
                String aliasPropertyName = resultEntityMetadata.getPropertyNameOrNull(columnName);
                if (aliasPropertyName != null) {
                    ColumnMetadata resultColumnMetadata = resultEntityMetadata.getColumnNotNull(aliasPropertyName);
                    String aliasColumnName = resultColumnMetadata.getName();
                    String alias = Objects.equals(columnName,aliasColumnName)?null:aliasColumnName;
                    ColumnSegment columnSegment = sqlSegmentFactory.createColumnSegment(tableBuilder.getEntityTable(), property, runtimeContext, alias);
                    sqlBuilderSegment.append(columnSegment);
                }
            }
        }
        return this;
    }
    @Override
    public AsSelector columnFuncAs(TableAvailable table, ColumnPropertyFunction columnPropertyFunction, String propertyAlias) {
        String propertyName = columnPropertyFunction.getPropertyName();
        ColumnFunction columnFunction = columnPropertyFunction.getColumnFunction();
        String columnAsName = propertyAlias == null ? table.getColumnName(propertyName) : getResultColumnName(propertyAlias);
        FuncColumnSegment funcColumnSegment = sqlSegmentFactory.createFuncColumnSegment(table, propertyName, runtimeContext, columnFunction, columnAsName);
        sqlBuilderSegment.append(funcColumnSegment);
        return this;
    }

    @Override
    public AsSelector sqlColumnAs(SQLColumnSegment sqlColumnSegment, String propertyAlias) {
        String columnAsName = propertyAlias == null ? null :getResultColumnName(propertyAlias);
        SQLColumnSegment sqlColumnAsSegment = sqlSegmentFactory.createSQLColumnAsSegment(sqlColumnSegment, columnAsName,runtimeContext);
        sqlBuilderSegment.append(sqlColumnAsSegment);
        return this;
    }
}
