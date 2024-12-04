package com.easy.query.core.expression.builder.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.core.ResultColumnInfo;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.CloneableSQLSegment;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.SQLEntityAliasSegment;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.factory.SQLSegmentFactory;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.SQLAnonymousUnionEntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.include.ColumnIncludeExpression;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.IncludeNavigateExpression;
import com.easy.query.core.metadata.IncludeNavigateParams;
import com.easy.query.core.metadata.MapEntityMetadata;
import com.easy.query.core.metadata.NavigateJoinMetadata;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyRelationalUtil;
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

    protected abstract TChain castChain();

    public ExpressionContext getExpressionContext() {
        return expressionContext;
    }

    public EntityQueryExpressionBuilder getEntityQueryExpressionBuilder() {
        return entityQueryExpressionBuilder;
    }

    public TChain groupKeys(int index) {
        return groupKeysAs(index, null);
    }

    public TChain groupKeysAs(int index, String alias) {
        if (EasySQLSegmentUtil.isEmpty(entityQueryExpressionBuilder.getGroup())) {
            throw new EasyQueryInvalidOperationException("not found group in current expression builder");
        }
        List<SQLSegment> sqlSegments = entityQueryExpressionBuilder.getGroup().getSQLSegments();
        if (sqlSegments.size() <= index) {
            throw new EasyQueryInvalidOperationException("current expression builder group keys size:[" + sqlSegments.size() + "],not found keys index:[" + index + "]");
        }
        SQLSegment sqlSegment = sqlSegments.get(index);
        if (sqlSegment instanceof CloneableSQLSegment) {
            CloneableSQLSegment cloneableSQLSegment = ((CloneableSQLSegment) sqlSegment).cloneSQLColumnSegment();
            if (alias != null) {
                ResultColumnInfo resultColumnInfo = getResultColumnName(alias);
                CloneableSQLSegment sqlColumnAsSegment = sqlSegmentFactory.createSQLColumnAsSegment(cloneableSQLSegment, resultColumnInfo.getColumnAsName(), this.runtimeContext);
                sqlBuilderSegment.append(sqlColumnAsSegment);
            } else {
                sqlBuilderSegment.append(cloneableSQLSegment);
            }
        } else {
            throw new EasyQueryInvalidOperationException("group key not instanceof CloneableSQLSegment not support key quick select");
        }
        return castChain();
    }

    protected abstract ResultColumnInfo getResultColumnName(String propertyAlias);


    public TChain columnKeys(TableAvailable table) {
        Collection<String> keyProperties = table.getEntityMetadata().getKeyProperties();
        if (EasyCollectionUtil.isEmpty(keyProperties)) {
            throw new EasyQueryInvalidOperationException(EasyClassUtil.getSimpleName(table.getEntityClass()) + " not found keys");
        }
        for (String keyProperty : keyProperties) {
            column(table, keyProperty);
        }
        return castChain();
    }

    public TChain column(TableAvailable table, String property) {
        ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(property);
        String alias = table.getEntityMetadata() instanceof MapEntityMetadata ? property : null;
        appendColumnMetadata(table, columnMetadata, true, false, false, alias);
        return castChain();
    }

    public TChain columnAs(TableAvailable table, String property, String propertyAlias) {
        ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(property);
        String alias = propertyAlias == null ? null : table.getEntityMetadata().getColumnNotNull(propertyAlias).getName();
        appendColumnMetadata(table, columnMetadata, true, false, false, alias);
        return castChain();
    }

    public TChain columnInclude(TableAvailable table, String selfProperty, String aliasProperty, SQLExpression1<AsSelector> includeSelectorExpression) {
//        TableAvailable entityTable = entityQueryExpressionBuilder.getTable(0).getEntityTable();
//        if(expressionContext.getIncludes().stream().noneMatch(o->o.getIncludeNavigateParams().getTable()==entityTable&&Objects.equals(o.getIncludeNavigateParams().getNavigateMetadata().getPropertyName(),selfProperty))){
//            //不存在就自动columnInclude
//
//        }
//        NavigateMetadata navigateMetadata = table.getEntityMetadata().getNavigateNotNull(selfProperty);
        Map<String, ColumnIncludeExpression> propertyColumnIncludeExpressionMap = expressionContext.getColumnIncludeMaps().computeIfAbsent(table, k -> new HashMap<>());
        propertyColumnIncludeExpressionMap.put(selfProperty, new ColumnIncludeExpression(table, selfProperty, aliasProperty, includeSelectorExpression));
        return castChain();
    }

    protected void autoColumnInclude(TableAvailable table, EntityMetadata targetEntityMetadata) {
        if (expressionContext.hasIncludes()) {
            //如果手动设置过includeMap则不自动设置
            boolean hasColumnIncludeMaps = expressionContext.hasColumnIncludeMaps();
            if (hasColumnIncludeMaps) {
                return;
            }
            for (IncludeNavigateExpression includeNavigateExpression : expressionContext.getIncludes().values()) {
                IncludeNavigateParams includeNavigateParams = includeNavigateExpression.getIncludeNavigateParams();
                if (!includeNavigateParams.isMappingFlat()) {
                    if (includeNavigateParams.getTable() == table) {
                        NavigateMetadata navigateMetadata = includeNavigateParams.getNavigateMetadata();
                        String navigateAutoMappingPropertyName = navigateMetadata.getPropertyName();
                        NavigateMetadata navigateOrNull = targetEntityMetadata.getNavigateOrNull(navigateAutoMappingPropertyName);
                        if ( navigateOrNull!= null) {
                            columnInclude(table, navigateAutoMappingPropertyName, navigateAutoMappingPropertyName, s -> {
                                TableAvailable entityTable = s.getEntityQueryExpressionBuilder().getTable(0).getEntityTable();
                                if (s.getEntityQueryExpressionBuilder().getProjects().isEmpty()) {
                                    s.columnAll(entityTable);
                                }
                                Class<?> navigatePropertyType = navigateOrNull.getNavigatePropertyType();
                                EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(navigatePropertyType);
                                selectAutoIncludeJoin0(s,s.getEntityQueryExpressionBuilder(),entityMetadata);

                            });
                        }
                    }
                }
            }
        }
    }

    private void selectAutoIncludeJoin0(AsSelector asSelector,EntityQueryExpressionBuilder sqlEntityExpressionBuilder, EntityMetadata resultEntityMetadata) {
        Collection<NavigateJoinMetadata> navigateJoinMetadatas = resultEntityMetadata.getNavigateJoinMetadatas();
        if (EasyCollectionUtil.isNotEmpty(navigateJoinMetadatas)) {
            for (NavigateJoinMetadata navigateJoinMetadata : navigateJoinMetadatas) {
                TableAvailable relationTable=null;
                if(navigateJoinMetadata.getMappingPath().length<2){
                    throw new EasyQueryInvalidOperationException("navigate join mapping length < 2");
                }
                for (int i = 0; i < navigateJoinMetadata.getMappingPath().length-1; i++) {
                    String navigateEntityProperty = navigateJoinMetadata.getMappingPath()[i];
                    relationTable = EasyRelationalUtil.getRelationTable(sqlEntityExpressionBuilder, sqlEntityExpressionBuilder.getTable(0).getEntityTable(), navigateEntityProperty);
                }
                String navigateBasicTypeProperty = navigateJoinMetadata.getMappingPath()[navigateJoinMetadata.getMappingPath().length - 1];
                asSelector.columnAs(relationTable,navigateBasicTypeProperty,navigateJoinMetadata.getProperty());
            }
        }
    }
    public TChain columnIgnore(TableAvailable table, String property) {
        sqlBuilderSegment.getSQLSegments().removeIf(sqlSegment -> {
            if (sqlSegment instanceof SQLEntitySegment) {
                SQLEntitySegment sqlEntitySegment = (SQLEntitySegment) sqlSegment;
                return Objects.equals(sqlEntitySegment.getTable(), table) &&
                        (
                                Objects.equals(sqlEntitySegment.getPropertyName(), property)
                                        ||
                                        (sqlEntitySegment.getPropertyName().contains(".") && sqlEntitySegment.getPropertyName().startsWith(property + "."))
                        );
            }
            return false;
        });
        return castChain();
    }

    public TChain columnIfAbsent(TableAvailable table, String property) {
        for (SQLSegment sqlSegment : sqlBuilderSegment.getSQLSegments()) {
            if (sqlSegment instanceof SQLEntitySegment) {
                SQLEntitySegment sqlEntitySegment = (SQLEntitySegment) sqlSegment;
                boolean sameTableAndProperty = Objects.equals(sqlEntitySegment.getTable(), table) &&
                        (
                                Objects.equals(sqlEntitySegment.getPropertyName(), property)
                                        ||
                                        (sqlEntitySegment.getPropertyName().contains(".") && sqlEntitySegment.getPropertyName().startsWith(property + "."))
                        );
                if (sameTableAndProperty) {
                    return castChain();
                }
            }
        }
        column(table, property);
        return castChain();
    }

    public TChain sqlSegmentAs(CloneableSQLSegment sqlColumnSegment) {
        CloneableSQLSegment sqlColumnAsSegment = sqlSegmentFactory.createSQLColumnAsSegment(sqlColumnSegment, null, runtimeContext);
        sqlBuilderSegment.append(sqlColumnAsSegment);
        return castChain();
    }

    public TChain columnAll(TableAvailable table) {
        if (table.isAnonymous()) {

            EntityTableExpressionBuilder entityTableExpressionBuilder = getTableExpressionBuilderByTable(table);
            if (!(entityTableExpressionBuilder instanceof AnonymousEntityTableExpressionBuilder)) {
                throw new EasyQueryInvalidOperationException("anonymous table is not AnonymousEntityTableExpressionBuilder:" + EasyClassUtil.getSimpleName(table.getEntityClass()));
            }
            columnAnonymousAll((AnonymousEntityTableExpressionBuilder) entityTableExpressionBuilder);
        } else {
            boolean queryLargeColumn = expressionContext.getBehavior().hasBehavior(EasyBehaviorEnum.QUERY_LARGE_COLUMN);
            EntityMetadata entityMetadata = table.getEntityMetadata();
            Collection<ColumnMetadata> columns = entityMetadata.getColumns();
            for (ColumnMetadata columnMetadata : columns) {
                appendColumnMetadata(table, columnMetadata, queryLargeColumn, true, true, null);
            }
            autoColumnInclude(table, entityMetadata);
        }
        return castChain();
    }

    /**
     * @param table
     * @param columnMetadata
     * @param queryLargeColumn
     * @param checkAutoSelect   是否需要检查
     * @param ignoreValueObject 是否忽略valueObject
     */
    protected void appendColumnMetadata(TableAvailable table, ColumnMetadata columnMetadata, boolean queryLargeColumn, boolean checkAutoSelect, boolean ignoreValueObject, String alias) {

        if (columnMetadata.isValueObject()) {
            if (!ignoreValueObject) {
                for (ColumnMetadata metadata : columnMetadata.getValueObjectColumnMetadataList()) {
                    appendColumnMetadata(table, metadata, queryLargeColumn, checkAutoSelect, false, alias);
                }
            }
            return;
        }
        if (checkAutoSelect && !columnMetadata.isAutoSelect()) {
            return;
        }
        if (ignoreColumnIfLargeNotQuery(queryLargeColumn, columnMetadata)) {
            return;
        }
        ColumnSegment columnSegment = sqlSegmentFactory.createSelectColumnSegment(table, columnMetadata, expressionContext, alias);
        sqlBuilderSegment.append(columnSegment);
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

    protected EntityQueryExpressionBuilder getAnonymousTableQueryExpressionBuilder(AnonymousEntityTableExpressionBuilder table) {
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
                        ColumnSegment columnSegment = sqlSegmentFactory.createSelectColumnSegment(table.getEntityTable(), propertyName, expressionContext, sqlEntityAliasSegment.getAlias());
                        sqlBuilderSegment.append(columnSegment);
                    } else {
                        ColumnSegment columnSegment = sqlSegmentFactory.createAnonymousColumnSegment(table.getEntityTable(), expressionContext, sqlEntityAliasSegment.getAlias());
                        sqlBuilderSegment.append(columnSegment);
                    }
                } else {
                    throw new EasyQueryException("columnAnonymousAll not found column:" + EasyClassUtil.getInstanceSimpleName(sqlSegment));
                }
            }
        }
        return castChain();
    }

    /**
     * 是否忽略当前列
     *
     * @param queryLargeColumn
     * @param columnMetadata
     * @return
     */
    protected boolean ignoreColumnIfLargeNotQuery(boolean queryLargeColumn, ColumnMetadata columnMetadata) {
        //如果不查询的情况下当列是非大列才可以查询
        if (!queryLargeColumn) {//如果不查询large列那么当前是large列就忽略
            return columnMetadata.isLarge();
        }
        return false;
    }

    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    protected EntityTableExpressionBuilder getTableExpressionBuilderByTable(TableAvailable table){

        EntityTableExpressionBuilder tableBuilder = EasyCollectionUtil.firstOrDefaultOrElseGet(entityQueryExpressionBuilder.getTables(), t -> Objects.equals(table, t.getEntityTable()), ()->{
            return EasyCollectionUtil.firstOrDefault(entityQueryExpressionBuilder.getRelationTables().values(), t -> Objects.equals(table, t.getEntityTable()), null);
        });
        if (tableBuilder == null) {
            throw new EasyQueryInvalidOperationException("not found table in expression context:" + EasyClassUtil.getSimpleName(table.getEntityClass()));
        }
        return tableBuilder;
    }
}
