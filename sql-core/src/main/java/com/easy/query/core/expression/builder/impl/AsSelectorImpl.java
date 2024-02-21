package com.easy.query.core.expression.builder.impl;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.EntityMetadataTypeEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.core.ResultColumnInfo;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContextImpl;
import com.easy.query.core.expression.segment.CloneableSQLSegment;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.FuncColumnSegment;
import com.easy.query.core.expression.segment.SQLNativeSegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.SubQueryColumnSegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.impl.SQLFunctionColumnSegmentImpl;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContextImpl;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.SQLFunctionTranslateImpl;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyStringUtil;

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

    public AsSelectorImpl(EntityQueryExpressionBuilder entityQueryExpressionBuilder, SQLBuilderSegment sqlBuilderSegment, Class<?> resultClass) {
        super(entityQueryExpressionBuilder, sqlBuilderSegment);
        this.entityQueryExpressionBuilder = entityQueryExpressionBuilder;

        this.resultClass = resultClass;
        this.resultEntityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(resultClass);
    }

    @Override
    protected ResultColumnInfo getResultColumnName(String propertyAlias) {
        switch (resultEntityMetadata.getEntityMetadataType()) {
            case MAP:
            case BASIC_TYPE:
                return new ResultColumnInfo(null,propertyAlias);
        }
        ColumnMetadata columnMetadata = resultEntityMetadata.getColumnNotNull(propertyAlias);
        return new ResultColumnInfo(columnMetadata,columnMetadata.getName());
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    @Override
    public AsSelector groupKeys(int index) {
        return groupKeysAs(index, null);
    }


    @Override
    public AsSelector columnAs(TableAvailable table, String property, String propertyAlias) {
        ResultColumnInfo resultColumnInfo = getResultColumnName(propertyAlias);
        ColumnSegment columnSegment = sqlSegmentFactory.createColumnSegment(table, property, runtimeContext, resultColumnInfo.getColumnAsName());
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
        ResultColumnInfo resultColumnInfo = getResultColumnName(propertyAlias);
        SubQueryColumnSegment subQueryColumnSegment = sqlSegmentFactory.createSubQueryColumnSegment(null, subQuery, resultColumnInfo.getColumnAsName(), runtimeContext);
        sqlBuilderSegment.append(subQueryColumnSegment);
        return this;
    }

    @Override
    public AsSelector sqlNativeSegment(String sqlSegment, SQLExpression1<SQLNativeExpressionContext> contextConsume) {
        Objects.requireNonNull(contextConsume, "sql native context consume cannot be null");
        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl(expressionContext, runtimeContext);
        sqlNativeExpressionContext.setResultEntityMetadata(resultEntityMetadata);
        contextConsume.apply(sqlNativeExpressionContext);
        SQLNativeSegment columnSegment = sqlSegmentFactory.createSQLNativeSegment(runtimeContext, sqlSegment, sqlNativeExpressionContext);
        sqlBuilderSegment.append(columnSegment);
        return castChain();
    }

    @Override
    protected AsSelector castChain() {
        return this;
    }

    @Override
    public AsSelector columnAll(TableAvailable table) {

        if (table.getEntityClass().equals(resultClass) || resultEntityMetadata.getEntityMetadataType() == EntityMetadataTypeEnum.MAP) {
            super.columnAll(table);
            return this;
        } else {

            EntityTableExpressionBuilder tableBuilder = EasyCollectionUtil.firstOrDefault(entityQueryExpressionBuilder.getTables(), t -> Objects.equals(table, t.getEntityTable()), null);
            if (tableBuilder == null) {
                throw new EasyQueryInvalidOperationException("not found table in expression context:" + EasyClassUtil.getSimpleName(table.getEntityClass()));
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
                if (!columnMetadata.isAutoSelect()) {
                    continue;
                }

                String columnName = columnMetadata.getName();
                String aliasPropertyName = resultEntityMetadata.getPropertyNameOrNull(columnName);
                if (aliasPropertyName != null) {
                    ColumnMetadata resultColumnMetadata = resultEntityMetadata.getColumnNotNull(aliasPropertyName);
                    String aliasColumnName = resultColumnMetadata.getName();
                    String alias = Objects.equals(columnName, aliasColumnName) ? null : aliasColumnName;
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
        String columnAsName = propertyAlias == null ? table.getColumnName(propertyName) : getResultColumnName(propertyAlias).getColumnAsName();
        FuncColumnSegment funcColumnSegment = sqlSegmentFactory.createFuncColumnSegment(table, propertyName, runtimeContext, columnFunction, columnAsName);
        sqlBuilderSegment.append(funcColumnSegment);
        return this;
    }

    @Override
    public AsSelector columnFunc(TableAvailable table, String property, SQLFunction sqlFunction, String propertyAlias, SQLActionExpression sqlActionExpression) {
        if(table==null||property==null){
            if(EasyStringUtil.isBlank(propertyAlias)){
                throw new EasyQueryInvalidOperationException("propertyAlias is bank");
            }
            ResultColumnInfo resultColumnInfo = getResultColumnName(propertyAlias);
            SQLSegment sqlSegment = new SQLFunctionTranslateImpl(sqlFunction)
                    .toSQLSegment(expressionContext, table, runtimeContext, resultColumnInfo.getColumnAsName());
            FuncColumnSegment funcColumnSegment = new SQLFunctionColumnSegmentImpl(table, resultColumnInfo.getColumnMetadata(), runtimeContext, sqlSegment, sqlFunction.getAggregationType(), resultColumnInfo.getColumnAsName());
            sqlBuilderSegment.append(funcColumnSegment);
            sqlActionExpression.apply();
            return this;
        }else{
            ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(property);
            String columnAsName = propertyAlias == null ? columnMetadata.getName() : getResultColumnName(propertyAlias).getColumnAsName();
            SQLSegment sqlSegment = new SQLFunctionTranslateImpl(sqlFunction)
                    .toSQLSegment(expressionContext, table, runtimeContext, columnAsName);
            FuncColumnSegment funcColumnSegment = new SQLFunctionColumnSegmentImpl(table, columnMetadata, runtimeContext, sqlSegment, sqlFunction.getAggregationType(), columnAsName);
            sqlBuilderSegment.append(funcColumnSegment);
            sqlActionExpression.apply();
            return this;
        }
    }

    @Override
    public AsSelector columnFunc(TableAvailable table, SQLFunction sqlFunction, String propertyAlias) {
        ResultColumnInfo resultColumnInfo = getResultColumnName(propertyAlias);
        SQLSegment sqlSegment = new SQLFunctionTranslateImpl(sqlFunction)
                .toSQLSegment(expressionContext, table, runtimeContext, resultColumnInfo.getColumnAsName());
        FuncColumnSegment funcColumnSegment = new SQLFunctionColumnSegmentImpl(table, resultColumnInfo.getColumnMetadata(), runtimeContext, sqlSegment, sqlFunction.getAggregationType(), resultColumnInfo.getColumnAsName());
        sqlBuilderSegment.append(funcColumnSegment);
//        columnAppendSQLFunction(table,property,sqlFunction,propertyAlias);
//        sqlActionExpression.apply();
        return this;
    }
    //    @Override
//    public <T extends SQLFunction> void columnAppendSQLFunction(TableAvailable table, String property, T sqlFunction, String propertyAlias) {
//
//        ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(property);
//        String columnAsName = propertyAlias == null ? columnMetadata.getName() : getResultColumnName(propertyAlias);
//        SQLSegment sqlSegment = new SQLFunctionTranslateImpl(sqlFunction)
//                .toSQLSegment(expressionContext, table, runtimeContext,columnAsName);
//        FuncColumnSegment funcColumnSegment = new SQLFunctionColumnSegmentImpl(table, columnMetadata, runtimeContext, sqlSegment, sqlFunction.getAggregationType(), columnAsName);
//        sqlBuilderSegment.append(funcColumnSegment);
//    }

    @Override
    public AsSelector sqlSegmentAs(CloneableSQLSegment sqlColumnSegment, String propertyAlias) {
        String columnAsName = propertyAlias == null ? null : getResultColumnName(propertyAlias).getColumnAsName();
        CloneableSQLSegment sqlColumnAsSegment = sqlSegmentFactory.createSQLColumnAsSegment(sqlColumnSegment, columnAsName, runtimeContext);
        sqlBuilderSegment.append(sqlColumnAsSegment);
        return this;
    }

    @Override
    public AsSelector sqlFunc(TableAvailable table, SQLFunction sqlFunction) {
            String sqlSegment = sqlFunction.sqlSegment(table);
            sqlNativeSegment(sqlSegment, context->{
                sqlFunction.consume(new SQLNativeChainExpressionContextImpl(table,context));
            });
        return this;
    }
}
