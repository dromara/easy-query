package com.easy.query.core.expression.sql.include;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnSelector;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.include.relation.RelationValueColumnMetadata;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.IncludeNavigateExpression;
import com.easy.query.core.metadata.IncludeNavigateParams;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.metadata.RelationExtraColumn;
import com.easy.query.core.metadata.RelationExtraMetadata;
import com.easy.query.core.util.EasyArrayUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyIncludeUtil;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasyStringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * create time 2023/7/21 10:30
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultIncludeParserEngine implements IncludeParserEngine {

    private <TEntity> List<RelationExtraEntity> getRelationExtraEntities(ExpressionContext expressionContext, List<TEntity> entities) {
        if (EasyCollectionUtil.isEmpty(entities)) {
            return new ArrayList<>();
        }

        List<RelationExtraEntity> relationExtraEntities = new ArrayList<>(entities.size());
        EntityMetadata entityMetadata = expressionContext.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(entities.get(0).getClass());
        //获取当前对象的关联id集合
        RelationExtraMetadata relationExtraMetadata = expressionContext.getRelationExtraMetadata();
        Map<String, RelationExtraColumn> relationExtraColumnMap = relationExtraMetadata.getRelationExtraColumnMap();
        Map<String, RelationExtraColumn> extraColumnMetadata = relationExtraColumnMap.values().stream()
                .map(o -> {
                    if (o.isAppendRelationExtra()) {
                        return o;
                    }
                    ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(o.getPropertyName());
                    return new RelationExtraColumn(o.getPropertyName(), o.getColumnName(), columnMetadata, o.isAppendRelationExtra());
                }).collect(Collectors.toMap(o -> o.getPropertyName(), o -> o, (k1, k2) -> {
                    return k1;
                }));
        int i = 0;
        for (TEntity entity : entities) {
            Map<String, Object> extraColumns = relationExtraMetadata.getRelationExtraColumnList().get(i);
            RelationExtraEntity relationExtraEntity = new RelationExtraEntityImpl(entity, extraColumns, extraColumnMetadata, expressionContext.getRuntimeContext().getRelationValueFactory());
            relationExtraEntities.add(relationExtraEntity);
            i++;
        }
        return relationExtraEntities;
    }

    @Override
    public <TR> IncludeParserResult process(ExpressionContext expressionContext, EntityMetadata entityMetadata, List<TR> result, IncludeNavigateExpression includeExpression) {
        IncludeNavigateParams includeNavigateParams = includeExpression.getIncludeNavigateParams();
        SQLFuncExpression<ClientQueryable<?>> queryableExpression = includeExpression.getSqlFuncExpression();
        NavigateMetadata navigateMetadata = includeNavigateParams.getNavigateMetadata();
        if (navigateMetadata == null) {
            throw new EasyQueryInvalidOperationException("navigateMetadata is null");
        }

        QueryRuntimeContext runtimeContext = expressionContext.getRuntimeContext();
        List<RelationExtraEntity> relationExtraEntities = getRelationExtraEntities(expressionContext, result);
        IncludeParseContext includeParseContext = new IncludeParseContext(includeNavigateParams);
        includeParseContext.setIncludeQueryableExpression(queryableExpression);
//        includeParseContext.setIncludeMappingQueryableFunction(includeNavigateParams.getMappingQueryableFunction());

        includeParseContext.setSelfProperties(navigateMetadata.getSelfPropertiesOrPrimary());
        includeParseContext.setTargetProperties(navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext));
        includeParseContext.setDirectMapping(navigateMetadata.getDirectMapping());
        boolean aliasEntity = !Objects.equals(entityMetadata.getEntityClass(), navigateMetadata.getEntityMetadata().getEntityClass());
//        if (aliasEntity) {
//            String selfPropertyOrPrimary = navigateMetadata.getSelfPropertyOrPrimary();
//            String columnName = getColumnNameByQueryExpressionBuilder(mainEntityQueryExpressionBuilder, includeNavigateParams.getTable(), navigateMetadata.getSelfPropertyOrPrimary());
//            if (columnName == null) {
//                throw new EasyQueryInvalidOperationException("["+EasyClassUtil.getSimpleName(navigateMetadata.getEntityMetadata().getEntityClass())+"] not found relation self property:[" + selfPropertyOrPrimary + "] for navigate property:["+navigateMetadata.getPropertyName()+"] in result:["+EasyClassUtil.getSimpleName(mainEntityQueryExpressionBuilder.getQueryClass())+"]");
//            }
//            includeParseContext.setSelfProperty(entityMetadata.getPropertyNameNotNull(columnName));
//        } else {
//            includeParseContext.setSelfProperty(navigateMetadata.getSelfPropertyOrPrimary());
//        }//entityMetadata.getPropertyNameNotNull(columnName)
        //映射到目标哪个属性值上
        //如果存在映射关系 是否调用了columnInclude
        confirmNavigateProperty(aliasEntity, expressionContext, entityMetadata, includeNavigateParams, includeParseContext);
        if (EasyStringUtil.isBlank(includeParseContext.getNavigatePropertyName())) {
            throw new EasyQueryInvalidOperationException("not found relation navigate property");
        }

        List<List<Object>> relationIds = relationExtraEntities.stream().map(o -> o.getRelationExtraColumns(navigateMetadata.getSelfPropertiesOrPrimary()))
                .filter(o -> !o.isNull()).distinct().map(o -> o.getValues()).collect(Collectors.toList());
//        ColumnMetadata selfRelationColumn = entityMetadata.getColumnNotNull(includeParseContext.getSelfProperty());
//        Property<Object, ?> relationPropertyGetter = selfRelationColumn.getGetterCaller();
//        List<Object> relationIds = result.stream().map(relationPropertyGetter::apply)
//                .filter(Objects::nonNull)
//                .distinct()
//                .collect(Collectors.toList());


        int queryRelationGroupSize = includeNavigateParams.getQueryRelationGroupSize();
        if (RelationTypeEnum.ManyToMany == navigateMetadata.getRelationType() && navigateMetadata.getMappingClass() != null) {
            confirmMappingRows(queryRelationGroupSize, includeParseContext, relationIds);
            EntityMetadata mappingEntityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(navigateMetadata.getMappingClass());

            RelationValueColumnMetadata relationValueColumnMetadata = runtimeContext.getRelationValueColumnMetadataFactory().create(mappingEntityMetadata, navigateMetadata.getTargetMappingProperties());


//            ColumnMetadata mappingTargetColumnMetadata = mappingEntityMetadata.getColumnNotNull(navigateMetadata.getTargetMappingProperties());
//            String targetColumnName = mappingTargetColumnMetadata.getName();

            List<List<Object>> targetIds = includeParseContext.getMappingRows().stream()
                    .map(relationValueColumnMetadata::getRelationValue).filter(o -> !o.isNull())
                    .distinct()
                    .map(o -> o.getValues())
                    .collect(Collectors.toList());
            relationIds.clear();
            relationIds.addAll(targetIds);
        }
        else if (EasyArrayUtil.isNotEmpty(navigateMetadata.getDirectMapping())) {

            confirmMappingRows(queryRelationGroupSize, includeParseContext, relationIds);
            Class<?> directMappingClass = navigateMetadata.getDirectMappingClass(runtimeContext);
            EntityMetadata mappingEntityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(directMappingClass);

            RelationValueColumnMetadata relationValueColumnMetadata = runtimeContext.getRelationValueColumnMetadataFactory().create(mappingEntityMetadata, navigateMetadata.getDirectMappingTargetPropertiesOrPrimary(runtimeContext));


//            ColumnMetadata mappingTargetColumnMetadata = mappingEntityMetadata.getColumnNotNull(navigateMetadata.getTargetMappingProperties());
//            String targetColumnName = mappingTargetColumnMetadata.getName();

            List<List<Object>> targetIds = includeParseContext.getMappingRows().stream()
                    .map(relationValueColumnMetadata::getRelationValue).filter(o -> !o.isNull())
                    .distinct()
                    .map(o -> o.getValues())
                    .collect(Collectors.toList());
            relationIds.clear();
            relationIds.addAll(targetIds);
        }
        //导航属性追踪与否
        List<RelationExtraEntity> includeResult = relationIds.isEmpty() ? EasyCollectionUtil.emptyList() : EasyIncludeUtil.queryableExpressionGroupExecute(queryRelationGroupSize, includeParseContext.getIncludeQueryableExpression(), includeNavigateParams, relationIds, q -> {
            ExpressionContext innerExpressionContext = q.getSQLEntityExpressionBuilder().getExpressionContext();
            List<?> list = q.toList();
            return getRelationExtraEntities(innerExpressionContext, list);
        });
//        if (aliasEntity) {
//            ClientQueryable<?> includeQueryable = includeParseContext.getIncludeQueryableExpression().apply();
//            if (!Objects.equals(navigateMetadata.getNavigatePropertyType(), includeQueryable.queryClass())) {
//
//                EntityQueryExpressionBuilder sqlEntityExpressionBuilder = includeQueryable.getSQLEntityExpressionBuilder();
//                TableAvailable aliasTable = getTableByEntityClass(sqlEntityExpressionBuilder, includeParseContext.getIncludeNavigateParams().getNavigateMetadata().getNavigatePropertyType());
//                if (aliasTable == null) {
//                    throw new EasyQueryInvalidOperationException("not found relation target table:[" + EasyClassUtil.getSimpleName(includeQueryable.queryClass()) + "] in result");
//                }
//                String targetPropertyOrPrimary = navigateMetadata.getTargetPropertyOrPrimary(runtimeContext);
//                String aliasColumnName = getColumnNameByQueryExpressionBuilder(sqlEntityExpressionBuilder, aliasTable, targetPropertyOrPrimary);
//                if (EasyStringUtil.isBlank(aliasColumnName)) {
//                    throw new EasyQueryInvalidOperationException("not found relation target property:[" + targetPropertyOrPrimary + "] in result");
//                }
//                String targetProperty = aliasTable.getEntityMetadata().getPropertyNameNotNull(aliasColumnName);
//                includeParseContext.setTargetProperty(targetProperty);
//            } else {
//                includeParseContext.setTargetProperty(navigateMetadata.getTargetProperty());
//            }
//
//        } else {
//            includeParseContext.setTargetProperty(navigateMetadata.getTargetProperty());
//        }

        return new DefaultIncludeParserResult(entityMetadata,navigateMetadata, relationExtraEntities, navigateMetadata.getRelationType(),
                includeParseContext.getNavigatePropertyName(),
                includeParseContext.getNavigateOriginalPropertyType(),
                includeParseContext.getNavigatePropertyType(),
                includeParseContext.getSelfProperties(),
                includeParseContext.getTargetProperties(),
                includeParseContext.getIncludeNavigateParams().getNavigateMetadata().getMappingClass(),
                includeParseContext.getIncludeNavigateParams().getNavigateMetadata().getSelfMappingProperties(),
                includeParseContext.getIncludeNavigateParams().getNavigateMetadata().getTargetMappingProperties(),
                includeResult,
                includeParseContext.getMappingRows(),
                includeParseContext.getNavigatePropertySetter(),
                includeParseContext.getNavigatePropertyGetter(),
                includeParseContext.getIncludeNavigateParams().getNavigateFlatMetadataList(),
                includeParseContext.getIncludeNavigateParams().getFlatQueryEntityMetadata(),
                includeParseContext.getDirectMapping()
        );
    }

//    private TableAvailable getTableByEntityClass(EntityQueryExpressionBuilder sqlEntityExpressionBuilder, Class<?> entityClass) {
//        for (EntityTableExpressionBuilder table : sqlEntityExpressionBuilder.getTables()) {
//            if (Objects.equals(table.getEntityClass(), entityClass)) {
//                if (EasySQLSegmentUtil.isNotEmpty(sqlEntityExpressionBuilder.getProjects())) {
//                    return table.getEntityTable();
//                }
//            }
//            if (table instanceof AnonymousEntityTableExpressionBuilder) {
//                AnonymousEntityTableExpressionBuilder anonymousEntityTableExpressionBuilder = (AnonymousEntityTableExpressionBuilder) table;
//                EntityQueryExpressionBuilder entityQueryExpressionBuilder = anonymousEntityTableExpressionBuilder.getEntityQueryExpressionBuilder();
//                TableAvailable anonymousTable = getTableByEntityClass(entityQueryExpressionBuilder, entityClass);
//                if (anonymousTable != null) {
//                    return anonymousTable;
//                }
//            }
//        }
//        return null;
//
//    }

    private void confirmMappingRows(int queryRelationGroupSize, IncludeParseContext includeParseContext, List<List<Object>> relationIds) {

        IncludeNavigateParams includeNavigateParams = includeParseContext.getIncludeNavigateParams();

        List<?> mappingRows = EasyIncludeUtil.queryableExpressionGroupExecute(queryRelationGroupSize, includeNavigateParams.getMappingQueryableFunction(), includeNavigateParams, relationIds, Query::toList);
        includeParseContext.setMappingRows(EasyObjectUtil.typeCastNullable(mappingRows));
    }

//    private <TR, TProperty> List<TR> queryableGroupExecute(EasyQueryOption easyQueryOption, ClientQueryable<?> includeQueryable, IncludeNavigateParams includeNavigateParams, List<TProperty> relationIds, SQLFuncExpression1<ClientQueryable<?>, List<TR>> produce) {

    /// /        if(includeQueryable.getSQLEntityExpressionBuilder().hasLimit()){
    /// /            includeNavigateParams.setLimit(true);
    /// /        }
//        return queryableExpressionGroupExecute(easyQueryOption, () -> includeQueryable, includeNavigateParams, relationIds, produce);
//    }

//    private <TR, TProperty> List<TR> queryableExpressionGroupExecute(EasyQueryOption easyQueryOption, SQLFuncExpression<ClientQueryable<?>> includeQueryableExpression, IncludeNavigateParams includeNavigateParams, List<TProperty> relationIds, SQLFuncExpression1<ClientQueryable<?>, List<TR>> produce) {
//        int queryRelationGroupSize = includeNavigateParams.getQueryRelationGroupSize(easyQueryOption.getRelationGroupSize());
//
//        if (relationIds.size() <= queryRelationGroupSize) {
//            includeNavigateParams.getRelationIds().addAll(relationIds);
//            return executeQueryableAndClearParams(includeQueryableExpression.apply(), includeNavigateParams, produce);
//        } else {
//            ArrayList<TR> result = new ArrayList<>(relationIds.size());
//            int i = 0;
//            for (TProperty relationId : relationIds) {
//                i++;
//                includeNavigateParams.getRelationIds().add(relationId);
//                if ((i % queryRelationGroupSize) == 0) {
//                    List<TR> r = executeQueryableAndClearParams(includeQueryableExpression.apply(), includeNavigateParams, produce);
//                    result.addAll(r);
//                }
//            }
//            if (EasyCollectionUtil.isNotEmpty(includeNavigateParams.getRelationIds())) {
//                List<TR> r = executeQueryableAndClearParams(includeQueryableExpression.apply(), includeNavigateParams, produce);
//                result.addAll(r);
//            }
//            return result;
//        }
//    }
//    private ClientQueryable<?> getRealIncludeQueryable(ClientQueryable<?> includeQueryable, IncludeNavigateParams includeNavigateParams) {
//        if(includeNavigateParams.isLimit()){
//            Iterator<Object> iterator = includeNavigateParams.getRelationIds().iterator();
//            Object firstRelationId = iterator.next();
//
//        }
//        return includeQueryable;
//    }
    private <T> List<T> executeQueryableAndClearParams(ClientQueryable<?> mappingQueryable, IncludeNavigateParams includeNavigateParams, SQLFuncExpression1<ClientQueryable<?>, List<T>> produce) {
        List<T> result = produce.apply(mappingQueryable);
        includeNavigateParams.getRelationIds().clear();
        return result;
    }

    private void confirmNavigateProperty(boolean aliasEntity, ExpressionContext expressionContext,
                                         EntityMetadata entityMetadata, IncludeNavigateParams includeNavigateParams,
                                         IncludeParseContext includeParseContext) {

        //映射到目标哪个属性值上
        //如果存在映射关系 是否调用了columnInclude

        boolean hasColumnIncludeMaps = expressionContext.hasColumnIncludeMaps();
        if (hasColumnIncludeMaps) {
            Map<String, ColumnIncludeExpression> propertyColumnIncludeExpressionMap = expressionContext.getColumnIncludeMaps().get(includeNavigateParams.getTable());
            if (propertyColumnIncludeExpressionMap != null) {
                String propertyName = includeParseContext.getIncludeNavigateParams().getNavigateMetadata().getPropertyName();
                ColumnIncludeExpression columnIncludeExpression = propertyColumnIncludeExpressionMap.get(propertyName);
                if (columnIncludeExpression != null) {
                    NavigateMetadata selfNavigateMetadata = includeNavigateParams.getNavigateMetadata();
                    NavigateMetadata aliasNavigateMetadata = entityMetadata.getNavigateNotNull(columnIncludeExpression.getAliasProperty());
                    if (includeParseContext.getIncludeNavigateParams().getNavigateMetadata().getRelationType() != aliasNavigateMetadata.getRelationType()) {
                        throw new EasyQueryInvalidOperationException("select alias relation type different property:[" + aliasNavigateMetadata.getPropertyName() + "]");
                    }
                    includeParseContext.setNavigatePropertyName(aliasNavigateMetadata.getPropertyName());
                    includeParseContext.setNavigatePropertyType(aliasNavigateMetadata.getNavigatePropertyType());
                    includeParseContext.setNavigateOriginalPropertyType(aliasNavigateMetadata.getNavigateOriginalPropertyType());
                    includeParseContext.setNavigatePropertySetter(aliasNavigateMetadata.getSetter());
                    includeParseContext.setNavigatePropertyGetter(aliasNavigateMetadata.getGetter());
                    SQLFuncExpression<ClientQueryable<?>> includeQueryableExpression = includeParseContext.getIncludeQueryableExpression();
                    if (columnIncludeExpression.getIncludeSelectorExpression() == null) {
//                        includeParseContext.setIncludeQueryable(includeQueryable.select(aliasNavigateMetadata.getNavigatePropertyType()));
                        includeParseContext.setIncludeQueryableExpression(() -> {
                            ClientQueryable<?> includeQueryable = includeQueryableExpression.apply();
                            EntityQueryExpressionBuilder sqlEntityExpressionBuilder = includeQueryable.getSQLEntityExpressionBuilder();
                            Class<?> aliasClassType = includeParseContext.getIncludeNavigateParams().getFlatClassType() == null ? aliasNavigateMetadata.getNavigatePropertyType() : includeParseContext.getIncludeNavigateParams().getFlatClassType();
                            includeQueryable.select(aliasClassType, t -> {
                                t.columnAll();
                                EasySQLExpressionUtil.appendTargetExtraTargetProperty(selfNavigateMetadata, sqlEntityExpressionBuilder, t.getSQLNative(), t.getTable());
                            });
                            return includeQueryable;
                        });
                    } else {
//                        ClientQueryable<?> selectIncludeQueryable = includeQueryable.select(aliasNavigateMetadata.getNavigatePropertyType(), t -> {
//                            columnIncludeExpression.getIncludeSelectorExpression().apply(t.getAsSelector());
//                        });
//                        includeParseContext.setIncludeQueryable(selectIncludeQueryable);
                        includeParseContext.setIncludeQueryableExpression(() -> {
                            ClientQueryable<?> includeQueryable = includeQueryableExpression.apply();
                            EntityQueryExpressionBuilder sqlEntityExpressionBuilder = includeQueryable.getSQLEntityExpressionBuilder();
                            Class<?> aliasClassType = includeParseContext.getIncludeNavigateParams().getFlatClassType() == null ? aliasNavigateMetadata.getNavigatePropertyType() : includeParseContext.getIncludeNavigateParams().getFlatClassType();

                            return includeQueryable.select(aliasClassType, t -> {
                                columnIncludeExpression.getIncludeSelectorExpression().apply(t.getAsSelector());
                                EasySQLExpressionUtil.appendSelfExtraTargetProperty(sqlEntityExpressionBuilder, t.getSQLNative(), t.getTable());
                                EasySQLExpressionUtil.appendTargetExtraTargetProperty(selfNavigateMetadata, sqlEntityExpressionBuilder, t.getSQLNative(), t.getTable());
                            });
                        });
                    }
                    return;
                }
            }
        } else {
            SQLFuncExpression<ClientQueryable<?>> includeQueryableExpression = includeParseContext.getIncludeQueryableExpression();
            includeParseContext.setIncludeQueryableExpression(() -> {
                ClientQueryable<?> includeQueryable = includeQueryableExpression.apply();
                EntityQueryExpressionBuilder sqlEntityExpressionBuilder = includeQueryable.getSQLEntityExpressionBuilder();
                NavigateMetadata navigateMetadata = includeNavigateParams.getNavigateMetadata();
                if (sqlEntityExpressionBuilder.getProjects().isEmpty()) {
                    Class<?> aliasClassType = includeParseContext.getIncludeNavigateParams().getFlatClassType();
                    if (aliasClassType != null) {
                        return includeQueryable.select(aliasClassType, t -> {
                            t.columnAll();
                            EasySQLExpressionUtil.appendTargetExtraTargetProperty(navigateMetadata, sqlEntityExpressionBuilder, t.getSQLNative(), t.getTable());
                        });
                    } else {
                        return includeQueryable.select(t -> {
                            t.columnAll();
                            EasySQLExpressionUtil.appendTargetExtraTargetProperty(navigateMetadata, sqlEntityExpressionBuilder, t.getSQLNative(), t.getTable());
                        });
                    }
                } else {
                    ColumnSelector<?> columnSelector = includeQueryable.getSQLExpressionProvider1().getColumnSelector(sqlEntityExpressionBuilder.getProjects());
                    EasySQLExpressionUtil.appendTargetExtraTargetProperty(navigateMetadata, sqlEntityExpressionBuilder, columnSelector.getSQLNative(), columnSelector.getTable());
                    return includeQueryable;
                }
            });
        }
        if (!aliasEntity || includeNavigateParams.isMappingFlat()) {
            NavigateMetadata navigateMetadata = includeNavigateParams.getNavigateMetadata();
            includeParseContext.setNavigatePropertyName(navigateMetadata.getPropertyName());
            includeParseContext.setNavigateOriginalPropertyType(navigateMetadata.getNavigateOriginalPropertyType());
            includeParseContext.setNavigatePropertyType(navigateMetadata.getNavigatePropertyType());
            includeParseContext.setNavigatePropertySetter(navigateMetadata.getSetter());
            includeParseContext.setNavigatePropertyGetter(navigateMetadata.getGetter());
        }
    }

//    private void appendTargetExtraTargetProperty(NavigateMetadata selfNavigateMetadata, ExpressionContext includeExpressionContext, ColumnAsSelector<?,?> columnAsSelector){
//        QueryRuntimeContext runtimeContext = includeExpressionContext.getRuntimeContext();
//        Map<String, RelationExtraColumn> relationExtraColumnMap = new HashMap<>();
//        String targetPropertyOrPrimary = selfNavigateMetadata.getTargetPropertyOrPrimary(runtimeContext);
//        String alias = "__relation__" + targetPropertyOrPrimary;
//        ColumnMetadata columnMetadata = columnAsSelector.getTable().getEntityMetadata().getColumnNotNull(targetPropertyOrPrimary);
//        RelationExtraColumn relationExtraColumn = relationExtraColumnMap.putIfAbsent(alias, new RelationExtraColumn(targetPropertyOrPrimary, alias, columnMetadata));
//        if (relationExtraColumn == null) {
//            columnAsSelector.sqlNativeSegment("{0}", c -> {
//                c.expression(targetPropertyOrPrimary);
//                c.setAlias(alias);
//            });
//        }
//        includeExpressionContext.setRelationExtraMetadata(new RelationExtraMetadata(relationExtraColumnMap));
//    }
//    private void appendTargetExtraTargetProperty(NavigateMetadata selfNavigateMetadata, ExpressionContext includeExpressionContext, ColumnSelector<?> columnSelector){
//        QueryRuntimeContext runtimeContext = includeExpressionContext.getRuntimeContext();
//        Map<String, RelationExtraColumn> relationExtraColumnMap = new HashMap<>();
//        String targetPropertyOrPrimary = selfNavigateMetadata.getTargetPropertyOrPrimary(runtimeContext);
//        String alias = "__relation__" + targetPropertyOrPrimary;
//        ColumnMetadata columnMetadata = columnSelector.getTable().getEntityMetadata().getColumnNotNull(targetPropertyOrPrimary);
//        RelationExtraColumn relationExtraColumn = relationExtraColumnMap.putIfAbsent(alias, new RelationExtraColumn(targetPropertyOrPrimary, alias, columnMetadata));
//        if (relationExtraColumn == null) {
//            columnSelector.sqlNativeSegment("{0}", c -> {
//                c.expression(targetPropertyOrPrimary);
//                c.setAlias(alias);
//            });
//        }
//        includeExpressionContext.setRelationExtraMetadata(new RelationExtraMetadata(relationExtraColumnMap));
//    }


    private String getColumnNameByColumnSegments(EntityQueryExpressionBuilder entityQueryExpressionBuilder, TableAvailable table, String selfProperty) {

        for (SQLSegment sqlSegment : entityQueryExpressionBuilder.getProjects().getSQLSegments()) {
            if (sqlSegment instanceof ColumnSegment) {
                ColumnSegment columnSegment = (ColumnSegment) sqlSegment;
                if (Objects.equals(columnSegment.getTable(), table) && Objects.equals(columnSegment.getPropertyName(), selfProperty)) {
                    if (columnSegment.getAlias() != null) {
                        return columnSegment.getAlias();
                    } else {
                        return columnSegment.getTable().getColumnName(selfProperty);
                    }
                }
            }
        }
        return null;
    }

    private String getColumnNameByQueryExpressionBuilder(EntityQueryExpressionBuilder entityQueryExpressionBuilder, TableAvailable table, String propertyName) {
        String columnName = null;
        for (EntityTableExpressionBuilder entityQueryExpressionBuilderTable : entityQueryExpressionBuilder.getTables()) {
            if (columnName != null) {
                break;
            }
            if (Objects.equals(entityQueryExpressionBuilderTable.getEntityTable(), table)) {
                columnName = getColumnNameByColumnSegments(entityQueryExpressionBuilder, table, propertyName);
            }
            if (columnName == null) {
                if (entityQueryExpressionBuilderTable instanceof AnonymousEntityTableExpressionBuilder) {
                    AnonymousEntityTableExpressionBuilder anonymousEntityTableExpressionBuilder = (AnonymousEntityTableExpressionBuilder) entityQueryExpressionBuilderTable;
                    EntityQueryExpressionBuilder anonymousEntityQueryExpressionBuilder = anonymousEntityTableExpressionBuilder.getEntityQueryExpressionBuilder();
                    columnName = getColumnNameByQueryExpressionBuilder(anonymousEntityQueryExpressionBuilder, table, propertyName);
                }
            }
        }
        return columnName;
    }
}
