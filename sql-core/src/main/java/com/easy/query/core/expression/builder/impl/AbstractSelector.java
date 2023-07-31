package com.easy.query.core.expression.builder.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SQLNativeSegment;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.SQLEntityAliasSegment;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.factory.SQLSegmentFactory;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContextImpl;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.SQLAnonymousUnionEntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.include.ColumnIncludeExpression;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;
import com.easy.query.core.util.EasyUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * create time 2023/6/22 21:14
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractSelector<TChain> {
    protected final QueryRuntimeContext runtimeContext;
    protected final SQLSegmentFactory sqlSegmentFactory;
    protected final SQLBuilderSegment sqlBuilderSegment;
    protected final EntityQueryExpressionBuilder entityQueryExpressionBuilder;
    protected final ExpressionContext expressionContext;

    public AbstractSelector(EntityQueryExpressionBuilder entityQueryExpressionBuilder, SQLBuilderSegment sqlBuilderSegment) {
        this.entityQueryExpressionBuilder = entityQueryExpressionBuilder;

        this.expressionContext = entityQueryExpressionBuilder.getExpressionContext();
        this.runtimeContext = expressionContext.getRuntimeContext();
        this.sqlSegmentFactory = runtimeContext.getSQLSegmentFactory();
        this.sqlBuilderSegment = sqlBuilderSegment;
    }

    public ExpressionContext getExpressionContext(){
        return expressionContext;
    }
    public EntityQueryExpressionBuilder getEntityQueryExpressionBuilder(){
        return entityQueryExpressionBuilder;
    }
    public TChain column(TableAvailable table, String property) {
        ColumnSegment columnSegment = sqlSegmentFactory.createColumnSegment(table, property, runtimeContext, null);
        sqlBuilderSegment.append(columnSegment);
        return (TChain) this;
    }
    public TChain columnInclude(TableAvailable table, String selfProperty, String aliasProperty, SQLExpression1<AsSelector> includeSelectorExpression) {
        NavigateMetadata navigateMetadata = table.getEntityMetadata().getNavigateNotNull(selfProperty);
        Map<String, ColumnIncludeExpression> propertyColumnIncludeExpressionMap = expressionContext.getColumnIncludeMaps().computeIfAbsent(table, k -> new HashMap<>());
        propertyColumnIncludeExpressionMap.put(navigateMetadata.getSelfPropertyOrPrimary(),new ColumnIncludeExpression(table,selfProperty,aliasProperty,includeSelectorExpression));
        return (TChain) this;
    }
    
    public TChain sqlNativeSegment(String sqlSegment, SQLExpression1<SQLNativeExpressionContext> contextConsume){
        Objects.requireNonNull(contextConsume,"sql native context consume cannot be null");
        SQLNativeExpressionContextImpl sqlNativeExpressionContext=new SQLNativeExpressionContextImpl();
        contextConsume.apply(sqlNativeExpressionContext);
        SQLNativeSegment columnSegment = sqlSegmentFactory.createSQLNativeSegment(runtimeContext, sqlSegment, sqlNativeExpressionContext);
        sqlBuilderSegment.append(columnSegment);
        return (TChain) this;
    }

    public TChain columnIgnore(TableAvailable table, String property) {
        sqlBuilderSegment.getSQLSegments().removeIf(sqlSegment -> {
            if (sqlSegment instanceof SQLEntitySegment) {
                SQLEntitySegment sqlEntitySegment = (SQLEntitySegment) sqlSegment;
                return Objects.equals(sqlEntitySegment.getTable(), table) && Objects.equals(sqlEntitySegment.getPropertyName(), property);
            }
            return false;
        });
        return (TChain) this;
    }

    public TChain columnAll(TableAvailable table) {
        if (table.isAnonymous()) {
            EntityTableExpressionBuilder entityTableExpressionBuilder = EasyCollectionUtil.firstOrDefault(entityQueryExpressionBuilder.getTables(), t -> Objects.equals(table, t.getEntityTable()), null);
            if(entityTableExpressionBuilder==null){
                throw new EasyQueryInvalidOperationException("not found table in expression context:"+EasyClassUtil.getSimpleName(table.getEntityClass()));
            }
            if(!(entityTableExpressionBuilder instanceof AnonymousEntityTableExpressionBuilder)){
                throw new EasyQueryInvalidOperationException("anonymous table is not AnonymousEntityTableExpressionBuilder:"+EasyClassUtil.getSimpleName(table.getEntityClass()));
            }
            columnAnonymousAll((AnonymousEntityTableExpressionBuilder) entityTableExpressionBuilder);
        } else {
            boolean queryLargeColumn = expressionContext.getBehavior().hasBehavior(EasyBehaviorEnum.QUERY_LARGE_COLUMN);
            EntityMetadata entityMetadata = table.getEntityMetadata();
            Collection<ColumnMetadata> columns = entityMetadata.getColumns();
            for (ColumnMetadata columnMetadata : columns) {
                if (!columnAllQueryLargeColumn(queryLargeColumn, columnMetadata)) {
                    continue;
                }
                column(table,columnMetadata.getPropertyName());
//                ColumnSegment columnSegment = sqlSegmentFactory.createColumnSegment(table, columnMetadata.getPropertyName(), runtimeContext, null);
//                sqlBuilderSegment.append(columnSegment);
            }
        }
        return (TChain) this;
    }

    private EntityQueryExpressionBuilder getEntityQueryExpressionBuilder(EntityQueryExpressionBuilder entityQueryExpressionBuilder) {

        if (entityQueryExpressionBuilder instanceof SQLAnonymousUnionEntityQueryExpressionBuilder) {
            List<EntityQueryExpressionBuilder> entityQueryExpressionBuilders = ((SQLAnonymousUnionEntityQueryExpressionBuilder) entityQueryExpressionBuilder).getEntityQueryExpressionBuilders();
            EntityQueryExpressionBuilder first = EasyCollectionUtil.first(entityQueryExpressionBuilders);
            return getEntityQueryExpressionBuilder(first);
        } else if (EasySQLSegmentUtil.isEmpty(entityQueryExpressionBuilder.getProjects())) {
            if (EasyCollectionUtil.isSingle(entityQueryExpressionBuilder.getTables())) {
                EntityTableExpressionBuilder entityQueryExpressionBuilderTable = entityQueryExpressionBuilder.getTable(0);
                if (entityQueryExpressionBuilderTable instanceof AnonymousEntityTableExpressionBuilder) {
                    return getAnonymousTableQueryExpressionBuilder((AnonymousEntityTableExpressionBuilder) entityQueryExpressionBuilderTable);
                }
            }
        }
        return entityQueryExpressionBuilder;
    }

    private EntityQueryExpressionBuilder getAnonymousTableQueryExpressionBuilder(AnonymousEntityTableExpressionBuilder table) {
        EntityQueryExpressionBuilder entityQueryExpressionBuilder = table.getEntityQueryExpressionBuilder();
        return getEntityQueryExpressionBuilder(entityQueryExpressionBuilder);
    }

    protected TChain columnAnonymousAll(AnonymousEntityTableExpressionBuilder table) {
        EntityQueryExpressionBuilder queryExpressionBuilder = getAnonymousTableQueryExpressionBuilder(table);
        if (EasySQLSegmentUtil.isNotEmpty(queryExpressionBuilder.getProjects())) {

            List<SQLSegment> sqlSegments = queryExpressionBuilder.getProjects().getSQLSegments();
            //匿名表内部设定的不查询
            for (SQLSegment sqlSegment : sqlSegments) {

                if (sqlSegment instanceof SQLEntityAliasSegment) {
                    SQLEntityAliasSegment sqlEntityAliasSegment = (SQLEntityAliasSegment) sqlSegment;

                    String propertyName = EasyUtil.getAnonymousPropertyName(sqlEntityAliasSegment, table.getEntityTable());
                    if (propertyName != null) {
                        ColumnSegment columnSegment = sqlSegmentFactory.createColumnSegment(table.getEntityTable(), propertyName, runtimeContext, sqlEntityAliasSegment.getAlias());
                        sqlBuilderSegment.append(columnSegment);
                    } else {
                        ColumnSegment columnSegment = sqlSegmentFactory.createAnonymousColumnSegment(table.getEntityTable(), runtimeContext, sqlEntityAliasSegment.getAlias());
                        sqlBuilderSegment.append(columnSegment);
                    }
                } else {
                    throw new EasyQueryException("columnAll not found column:" + EasyClassUtil.getInstanceSimpleName(sqlSegment));
                }
            }
        }
        return (TChain) this;
    }

    protected boolean columnAllQueryLargeColumn(boolean queryLargeColumn, ColumnMetadata columnMetadata) {
        //如果不查询的情况下当列是非大列才可以查询
        if (!queryLargeColumn) {
            return !columnMetadata.isLarge();
        }
        return true;
    }

//    public TChain columnConstAs(String columnConst, String alias) {
//        ColumnAsConstSegment columnSegment = sqlSegmentFactory.createColumnAsConstSegment(null, runtimeContext, columnConst, alias);
//        sqlBuilderSegment.append(columnSegment);
//        return (TChain) this;
//    }
}
