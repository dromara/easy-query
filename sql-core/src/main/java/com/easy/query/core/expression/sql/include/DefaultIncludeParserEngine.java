package com.easy.query.core.expression.sql.include;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.IncludeNavigateParams;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;
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
    @Override
    public <TR> IncludeParserResult process(EntityQueryExpressionBuilder mainEntityQueryExpressionBuilder, EntityMetadata entityMetadata, List<TR> result, SQLFuncExpression1<IncludeNavigateParams, ClientQueryable<?>> includeExpression) {
        IncludeNavigateParams includeNavigateParams = new IncludeNavigateParams();
        ClientQueryable<?> clientQueryable = includeExpression.apply(includeNavigateParams);
        NavigateMetadata navigateMetadata = includeNavigateParams.getNavigateMetadata();
        if (navigateMetadata == null) {
            throw new EasyQueryInvalidOperationException("navigateMetadata is null");
        }
        IncludeParseContext includeParseContext = new IncludeParseContext(includeNavigateParams);
        includeParseContext.setIncludeQueryable(clientQueryable);
        includeParseContext.setIncludeMappingQueryable(includeNavigateParams.getMappingQueryable());

        boolean aliasEntity = !Objects.equals(entityMetadata.getEntityClass(), navigateMetadata.getEntityMetadata().getEntityClass());
        if (aliasEntity) {
            String selfPropertyOrPrimary = navigateMetadata.getSelfPropertyOrPrimary();
            String columnName = getColumnNameByQueryExpressionBuilder(mainEntityQueryExpressionBuilder, includeNavigateParams.getTable(), navigateMetadata.getSelfPropertyOrPrimary());
            if (columnName == null) {
                throw new EasyQueryInvalidOperationException("not found relation self property:[" + selfPropertyOrPrimary + "] in result");
            }
            includeParseContext.setSelfProperty(entityMetadata.getPropertyNameNotNull(columnName));
        } else {
            includeParseContext.setSelfProperty(navigateMetadata.getSelfPropertyOrPrimary());
        }
        ExpressionContext expressionContext = mainEntityQueryExpressionBuilder.getExpressionContext();
        //映射到目标哪个属性值上
        //如果存在映射关系 是否调用了columnInclude
        confirmNavigateProperty(aliasEntity, expressionContext, entityMetadata, includeNavigateParams, includeParseContext);
        if (EasyStringUtil.isBlank(includeParseContext.getNavigatePropertyName())) {
            throw new EasyQueryInvalidOperationException("not found relation navigate property");
        }

        //获取当前对象的关联id集合
        ColumnMetadata selfRelationColumn = entityMetadata.getColumnNotNull(includeParseContext.getSelfProperty());
        Property<Object, ?> relationPropertyGetter = selfRelationColumn.getGetterCaller();
        List<Object> relationIds = result.stream().map(relationPropertyGetter::apply)
                .distinct()
                .collect(Collectors.toList());


        QueryRuntimeContext runtimeContext = expressionContext.getRuntimeContext();
        EasyQueryOption easyQueryOption = runtimeContext.getQueryConfiguration().getEasyQueryOption();
        if (RelationTypeEnum.ManyToMany == navigateMetadata.getRelationType()) {
            confirmMappingRows(easyQueryOption, includeParseContext, relationIds);
            EntityMetadata mappingEntityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(navigateMetadata.getMappingClass());
            ColumnMetadata mappingTargetColumnMetadata = mappingEntityMetadata.getColumnNotNull(navigateMetadata.getTargetMappingProperty());
            String targetColumnName = mappingTargetColumnMetadata.getName();
            List<Object> targetIds = includeParseContext.getMappingRows().stream()
                    .map(o -> o.get(targetColumnName)).filter(Objects::nonNull)
                    .distinct()
                    .collect(Collectors.toList());
            relationIds.clear();
            relationIds.addAll(targetIds);
        }

        //导航属性追踪与否
        List<?> includeResult = queryableGroupExecute(easyQueryOption, includeParseContext.getIncludeQueryable(), includeNavigateParams, relationIds, Query::toList);
        if (aliasEntity) {
            if (!Objects.equals(navigateMetadata.getNavigatePropertyType(), includeParseContext.getIncludeQueryable().queryClass())) {

                EntityQueryExpressionBuilder sqlEntityExpressionBuilder = includeParseContext.getIncludeQueryable().getSQLEntityExpressionBuilder();
                TableAvailable aliasTable = getTableByEntityClass(sqlEntityExpressionBuilder, includeParseContext.getIncludeNavigateParams().getNavigateMetadata().getNavigatePropertyType());
                if (aliasTable == null) {
                    throw new EasyQueryInvalidOperationException("not found relation target table:[" + EasyClassUtil.getSimpleName(includeParseContext.getIncludeQueryable().queryClass()) + "] in result");
                }
                String targetPropertyOrPrimary = navigateMetadata.getTargetPropertyOrPrimary(runtimeContext);
                String aliasColumnName = getColumnNameByQueryExpressionBuilder(sqlEntityExpressionBuilder, aliasTable, targetPropertyOrPrimary);
                if (EasyStringUtil.isBlank(aliasColumnName)) {
                    throw new EasyQueryInvalidOperationException("not found relation target property:[" + targetPropertyOrPrimary + "] in result");
                }
                String targetProperty = aliasTable.getEntityMetadata().getPropertyNameNotNull(aliasColumnName);
                includeParseContext.setTargetProperty(targetProperty);
            } else {
                includeParseContext.setTargetProperty(navigateMetadata.getTargetProperty());
            }

        } else {
            includeParseContext.setTargetProperty(navigateMetadata.getTargetProperty());
        }

        return new DefaultIncludeParserResult(entityMetadata, navigateMetadata.getRelationType(),
                includeParseContext.getNavigatePropertyName(),
                includeParseContext.getNavigateOriginalPropertyType(),
                includeParseContext.getNavigatePropertyType(),
                includeParseContext.getSelfProperty(),
                includeParseContext.getTargetProperty(),
                includeParseContext.getIncludeNavigateParams().getNavigateMetadata().getMappingClass(),
                includeParseContext.getIncludeNavigateParams().getNavigateMetadata().getSelfMappingProperty(),
                includeParseContext.getIncludeNavigateParams().getNavigateMetadata().getTargetMappingProperty(),
                EasyObjectUtil.typeCastNullable(includeResult),
                includeParseContext.getMappingRows(),
                includeParseContext.getNavigatePropertySetter());
    }

    private TableAvailable getTableByEntityClass(EntityQueryExpressionBuilder sqlEntityExpressionBuilder, Class<?> entityClass) {
        for (EntityTableExpressionBuilder table : sqlEntityExpressionBuilder.getTables()) {
            if (Objects.equals(table.getEntityClass(), entityClass)) {
                if (EasySQLSegmentUtil.isNotEmpty(sqlEntityExpressionBuilder.getProjects())) {
                    return table.getEntityTable();
                }
            }
            if (table instanceof AnonymousEntityTableExpressionBuilder) {
                AnonymousEntityTableExpressionBuilder anonymousEntityTableExpressionBuilder = (AnonymousEntityTableExpressionBuilder) table;
                EntityQueryExpressionBuilder entityQueryExpressionBuilder = anonymousEntityTableExpressionBuilder.getEntityQueryExpressionBuilder();
                TableAvailable anonymousTable = getTableByEntityClass(entityQueryExpressionBuilder, entityClass);
                if (anonymousTable != null) {
                    return anonymousTable;
                }
            }
        }
        return null;

    }

    private <T> void confirmMappingRows(EasyQueryOption easyQueryOption, IncludeParseContext includeParseContext, List<T> relationIds) {

        IncludeNavigateParams includeNavigateParams = includeParseContext.getIncludeNavigateParams();
        List<Map<String, Object>> mappingRows = queryableGroupExecute(easyQueryOption, includeNavigateParams.getMappingQueryable(), includeNavigateParams, relationIds, Query::toMaps);
        includeParseContext.setMappingRows(mappingRows);
    }

    private <TR, TProperty> List<TR> queryableGroupExecute(EasyQueryOption easyQueryOption, ClientQueryable<?> includeQueryable, IncludeNavigateParams includeNavigateParams, List<TProperty> relationIds, SQLFuncExpression1<ClientQueryable<?>, List<TR>> produce) {
        int queryRelationGroupSize = includeNavigateParams.getQueryRelationGroupSize(easyQueryOption.getRelationGroupSize());

        if (relationIds.size() <= queryRelationGroupSize) {
            includeNavigateParams.getRelationIds().addAll(relationIds);
            return executeQueryableAndClearParams(includeQueryable, includeNavigateParams, produce);
        } else {
            ArrayList<TR> result = new ArrayList<>(relationIds.size());
            int i = 0;
            for (TProperty relationId : relationIds) {
                i++;
                includeNavigateParams.getRelationIds().add(relationId);
                if ((i % queryRelationGroupSize) == 0) {
                    List<TR> r = executeQueryableAndClearParams(includeQueryable, includeNavigateParams, produce);
                    result.addAll(r);
                }
            }
            if (EasyCollectionUtil.isNotEmpty(includeNavigateParams.getRelationIds())) {
                List<TR> r = executeQueryableAndClearParams(includeQueryable, includeNavigateParams, produce);
                result.addAll(r);
            }
            return result;
        }
    }

    private <T> List<T> executeQueryableAndClearParams(ClientQueryable<?> mappingQueryable, IncludeNavigateParams includeNavigateParams, SQLFuncExpression1<ClientQueryable<?>, List<T>> produce) {
        List<T> result = produce.apply(mappingQueryable);
        includeNavigateParams.getRelationIds().clear();
        return result;
    }

    private void confirmNavigateProperty(boolean aliasEntity, ExpressionContext expressionContext, EntityMetadata entityMetadata, IncludeNavigateParams includeNavigateParams, IncludeParseContext includeParseContext) {

        //映射到目标哪个属性值上
        //如果存在映射关系 是否调用了columnInclude
        boolean hasColumnIncludeMaps = expressionContext.hasColumnIncludeMaps();
        if (hasColumnIncludeMaps) {
            Map<String, ColumnIncludeExpression> propertyColumnIncludeExpressionMap = expressionContext.getColumnIncludeMaps().get(includeNavigateParams.getTable());
            if (propertyColumnIncludeExpressionMap != null) {
                ColumnIncludeExpression columnIncludeExpression = propertyColumnIncludeExpressionMap.get(includeParseContext.getSelfProperty());
                if (columnIncludeExpression != null) {
                    NavigateMetadata aliasNavigateMetadata = entityMetadata.getNavigateNotNull(columnIncludeExpression.getAliasProperty());
                    if (includeParseContext.getIncludeNavigateParams().getNavigateMetadata().getRelationType() != aliasNavigateMetadata.getRelationType()) {
                        throw new EasyQueryInvalidOperationException("select alias relation type different property:[" + aliasNavigateMetadata.getPropertyName() + "]");
                    }
                    includeParseContext.setNavigatePropertyName(aliasNavigateMetadata.getPropertyName());
                    includeParseContext.setNavigatePropertyType(aliasNavigateMetadata.getNavigatePropertyType());
                    includeParseContext.setNavigateOriginalPropertyType(aliasNavigateMetadata.getNavigateOriginalPropertyType());
                    includeParseContext.setNavigatePropertySetter(aliasNavigateMetadata.getSetter());
                    ClientQueryable<?> includeQueryable = includeParseContext.getIncludeQueryable();
                    if (columnIncludeExpression.getIncludeSelectorExpression() == null) {
                        includeParseContext.setIncludeQueryable(includeQueryable.select(aliasNavigateMetadata.getNavigatePropertyType()));
                    } else {
                        ClientQueryable<?> selectIncludeQueryable = includeQueryable.select(aliasNavigateMetadata.getNavigatePropertyType(), t -> {
                            columnIncludeExpression.getIncludeSelectorExpression().apply(t.getAsSelector());
                        });
                        includeParseContext.setIncludeQueryable(selectIncludeQueryable);
                    }
                    return;
                }
            }
        }
        if (!aliasEntity) {
            NavigateMetadata navigateMetadata = includeNavigateParams.getNavigateMetadata();
            includeParseContext.setNavigatePropertyName(navigateMetadata.getPropertyName());
            includeParseContext.setNavigateOriginalPropertyType(navigateMetadata.getNavigateOriginalPropertyType());
            includeParseContext.setNavigatePropertyType(navigateMetadata.getNavigatePropertyType());
            includeParseContext.setNavigatePropertySetter(navigateMetadata.getSetter());
        }
    }


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
