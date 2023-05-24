package com.easy.query.core.expression.parser.impl;

import com.easy.query.core.basic.api.select.Queryable;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.parser.core.SQLWherePredicate;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SubQueryColumnSegmentImpl;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.SQLColumnAsSelector;
import com.easy.query.core.expression.segment.ColumnSegmentImpl;
import com.easy.query.core.expression.segment.FuncColumnSegmentImpl;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.util.EasyLambdaUtil;

import java.util.Collection;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @FileName: DefaultSqSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/8 00:10
 * @author xuejiaming
 */
public class DefaultSQLColumnAsSelector<T1, TR> extends AbstractSQLColumnSelector<T1, SQLColumnAsSelector<T1, TR>> implements SQLColumnAsSelector<T1, TR> {

    protected final Class<TR> resultClass;
    protected final EntityMetadata resultEntityMetadata;

    public DefaultSQLColumnAsSelector(int index, EntityQueryExpressionBuilder EntityQueryExpressionBuilder, SQLBuilderSegment sqlSegment0Builder, Class<TR> resultClass) {
        super(index, EntityQueryExpressionBuilder, sqlSegment0Builder);
        this.resultClass = resultClass;
        this.resultEntityMetadata = EntityQueryExpressionBuilder.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(resultClass);
    }

    @Override
    public SQLColumnAsSelector<T1, TR> columnAs(Property<T1, ?> column, Property<TR, ?> alias) {
        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(index);
        String propertyName = EasyLambdaUtil.getPropertyName(column);
        String aliasColumnName = getResultColumnName(alias);
        sqlSegmentBuilder.append(new ColumnSegmentImpl(table.getEntityTable(), propertyName, entityQueryExpressionBuilder.getRuntimeContext(), aliasColumnName));
        return this;
    }

    @Override
    public <TSubQuery> SQLColumnAsSelector<T1, TR> columnSubQueryAs(Function<SQLWherePredicate<T1>,Queryable<TSubQuery>> subQueryableFunc, Property<TR, TSubQuery> alias) {
        SQLExpressionProvider<T1> sqlExpressionProvider = runtimeContext.getSQLExpressionInvokeFactory().createSQLExpressionProvider(index, entityQueryExpressionBuilder);
        SQLWherePredicate<T1> sqlWherePredicate = sqlExpressionProvider.getSQLWherePredicate();
        Queryable<TSubQuery> subQueryable = subQueryableFunc.apply(sqlWherePredicate);
        extract(subQueryable);
        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(index);
        String aliasColumnName = getResultColumnName(alias);
        SubQueryColumnSegmentImpl subQueryColumnSegment = new SubQueryColumnSegmentImpl(table.getEntityTable(), subQueryable, aliasColumnName, runtimeContext);
        sqlSegmentBuilder.append(subQueryColumnSegment);
        return this;
    }

    public <T2> void extract(Queryable<T2> subQueryable){

        EntityQueryExpressionBuilder subQueryableSQLEntityExpressionBuilder = subQueryable.getSQLEntityExpressionBuilder();
        entityQueryExpressionBuilder.getExpressionContext().extract(subQueryableSQLEntityExpressionBuilder.getExpressionContext());
    }

    protected String getResultColumnName(Property<TR, ?> alias){
        String aliasPropertyName = EasyLambdaUtil.getPropertyName(alias);
        ColumnMetadata columnMetadata = resultEntityMetadata.getColumnNotNull(aliasPropertyName);
        return columnMetadata.getName();
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public SQLColumnAsSelector<T1, TR> columnAll() {

        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(index);
        if(table.getEntityClass().equals(resultClass)){
              super.columnAll();
            return this;
        }else{
            return  columnAll(table);
        }
    }
    private SQLColumnAsSelector<T1, TR> columnAll(EntityTableExpressionBuilder table){
        if (table instanceof AnonymousEntityTableExpressionBuilder) {
            columnAnonymousAll((AnonymousEntityTableExpressionBuilder) table);
        } else {
            EntityMetadata entityMetadata = table.getEntityMetadata();
            Collection<String> properties = entityMetadata.getProperties();
            for (String property : properties) {
                ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(property);
                String columnName = columnMetadata.getName();
                String aliasPropertyName = resultEntityMetadata.getPropertyNameOrNull(columnName);
                if(aliasPropertyName!=null){
                    ColumnMetadata resultColumnMetadata = resultEntityMetadata.getColumnNotNull(aliasPropertyName);
                    String aliasColumnName = resultColumnMetadata.getName();
                    String alias = Objects.equals(columnName,aliasColumnName)?null:aliasColumnName;
                    sqlSegmentBuilder.append(new ColumnSegmentImpl(table.getEntityTable(), property, entityQueryExpressionBuilder.getRuntimeContext(),alias));
                }
            }
        }
        return this;
    }

    @Override
    public SQLColumnAsSelector<T1, TR> columnFuncAs(ColumnPropertyFunction columnPropertyFunction, Property<TR, ?> alias) {
        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(index);
        String propertyName = columnPropertyFunction.getPropertyName();
        ColumnFunction columnFunction = columnPropertyFunction.getColumnFunction();
        String columnAsName = alias == null ? table.getColumnName(propertyName) : getResultColumnName(alias);
        sqlSegmentBuilder.append(new FuncColumnSegmentImpl(table.getEntityTable(), propertyName, entityQueryExpressionBuilder.getRuntimeContext(), columnFunction, columnAsName));
        return this;
    }

}
