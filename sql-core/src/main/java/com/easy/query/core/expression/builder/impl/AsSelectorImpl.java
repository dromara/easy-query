package com.easy.query.core.expression.builder.impl;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.EntityMetadataTypeEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.CloneableSQLSegment;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.FuncColumnSegment;
import com.easy.query.core.expression.segment.SQLNativeSegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.SubQueryColumnSegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.scec.context.SQLAliasNativeExpressionContext;
import com.easy.query.core.expression.segment.scec.context.SQLAliasNativeExpressionContextImpl;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContextImpl;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.util.Collection;
import java.util.List;
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
        switch (resultEntityMetadata.getEntityMetadataType()){
            case MAP:
            case BASIC_TYPE:return propertyAlias;
        }
        ColumnMetadata columnMetadata = resultEntityMetadata.getColumnNotNull(propertyAlias);
        return columnMetadata.getName();
    }
    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    @Override
    public AsSelector groupKeys(int index) {
        return groupKeysAs(index,null);
    }

    @Override
    public AsSelector groupKeysAs(int index, String alias) {
        if(EasySQLSegmentUtil.isEmpty(entityQueryExpressionBuilder.getGroup())){
            throw new EasyQueryInvalidOperationException("not found group in current expression builder");
        }
        List<SQLSegment> sqlSegments = entityQueryExpressionBuilder.getGroup().getSQLSegments();
        if(sqlSegments.size()<=index){
            throw new EasyQueryInvalidOperationException("current expression builder group keys size:["+sqlSegments.size()+"],not found keys index:["+index+"]");
        }
        SQLSegment sqlSegment = sqlSegments.get(index);
        if(sqlSegment instanceof CloneableSQLSegment){
            CloneableSQLSegment cloneableSQLSegment = ((CloneableSQLSegment) sqlSegment).cloneSQLColumnSegment();
            if(alias!=null){
                String aliasColumnName = getResultColumnName(alias);
                CloneableSQLSegment sqlColumnAsSegment = sqlSegmentFactory.createSQLColumnAsSegment(cloneableSQLSegment, aliasColumnName, this.runtimeContext);
                sqlBuilderSegment.append(sqlColumnAsSegment);
            }else{
                sqlBuilderSegment.append(cloneableSQLSegment);
            }
        }else{
            throw new EasyQueryInvalidOperationException("group key not instanceof CloneableSQLSegment not support key quick select");
        }
        return this;
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
    public AsSelector sqlNativeSegment(String sqlSegment, SQLExpression1<SQLAliasNativeExpressionContext> contextConsume){
        Objects.requireNonNull(contextConsume,"sql native context consume cannot be null");
        SQLAliasNativeExpressionContextImpl sqlAliasNativeExpressionContext=new SQLAliasNativeExpressionContextImpl(new SQLNativeExpressionContextImpl(expressionContext,runtimeContext),resultEntityMetadata);
        contextConsume.apply(sqlAliasNativeExpressionContext);
        SQLNativeSegment columnSegment = sqlSegmentFactory.createSQLNativeSegment(runtimeContext, sqlSegment, sqlAliasNativeExpressionContext);
        sqlBuilderSegment.append(columnSegment);
        return castChain();
    }

    @Override
    protected AsSelector castChain() {
        return this;
    }

    @Override
    public AsSelector columnAll(TableAvailable table) {

        if (table.getEntityClass().equals(resultClass)||resultEntityMetadata.getEntityMetadataType()== EntityMetadataTypeEnum.MAP) {
            super.columnAll(table);
            return this;
        } else {

            EntityTableExpressionBuilder tableBuilder = EasyCollectionUtil.firstOrDefault(entityQueryExpressionBuilder.getTables(), t -> Objects.equals(table, t.getEntityTable()), null);
            if(tableBuilder==null){
                throw new EasyQueryInvalidOperationException("not found table in expression context:"+ EasyClassUtil.getSimpleName(table.getEntityClass()));
            }
            return columnAll(tableBuilder);
        }
    }

    private AsSelector columnAll(EntityTableExpressionBuilder tableBuilder) {
        if (tableBuilder instanceof AnonymousEntityTableExpressionBuilder) {
            columnAnonymousAll((AnonymousEntityTableExpressionBuilder) tableBuilder);
        } else {
            EntityMetadata entityMetadata = tableBuilder.getEntityMetadata();
            Collection<ColumnMetadata> columns = entityMetadata.getColumns();
            for (ColumnMetadata columnMetadata : columns) {

                String columnName = columnMetadata.getName();
                String aliasPropertyName = resultEntityMetadata.getPropertyNameOrNull(columnName);
                if (aliasPropertyName != null) {
                    ColumnMetadata resultColumnMetadata = resultEntityMetadata.getColumnNotNull(aliasPropertyName);
                    String aliasColumnName = resultColumnMetadata.getName();
                    String alias = Objects.equals(columnName,aliasColumnName)?null:aliasColumnName;
                    ColumnSegment columnSegment = sqlSegmentFactory.createColumnSegment(tableBuilder.getEntityTable(), columnMetadata.getPropertyName(), runtimeContext, alias);
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
    public AsSelector sqlSegmentAs(CloneableSQLSegment sqlColumnSegment, String propertyAlias) {
        String columnAsName = propertyAlias == null ? null :getResultColumnName(propertyAlias);
        CloneableSQLSegment sqlColumnAsSegment = sqlSegmentFactory.createSQLColumnAsSegment(sqlColumnSegment, columnAsName,runtimeContext);
        sqlBuilderSegment.append(sqlColumnAsSegment);
        return this;
    }
}
