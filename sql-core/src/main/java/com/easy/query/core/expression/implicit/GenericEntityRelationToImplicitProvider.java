package com.easy.query.core.expression.implicit;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.PartitionOrderEnum;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.DefaultRelationTableKey;
import com.easy.query.core.expression.ManyConfiguration;
import com.easy.query.core.expression.PartitionByRelationTableKey;
import com.easy.query.core.expression.RelationTableKey;
import com.easy.query.core.expression.builder.core.PropagationValueFilter;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.include.getter.EqualsDirectToOneGetter;
import com.easy.query.core.expression.include.getter.EqualsManyToManyMappingOrderRowsGetter;
import com.easy.query.core.expression.include.getter.EqualsManyToManyMappingRowsGetter;
import com.easy.query.core.expression.include.getter.EqualsManyToManyNoMappingRowsGetter;
import com.easy.query.core.expression.include.getter.EqualsManyToOneGetter;
import com.easy.query.core.expression.include.getter.EqualsOneToManyGetter;
import com.easy.query.core.expression.include.getter.EqualsOneToOneGetter;
import com.easy.query.core.expression.include.getter.RelationIncludeGetter;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.SimpleEntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.parser.core.base.impl.ColumnOrderSelectorImpl;
import com.easy.query.core.expression.segment.builder.OrderBySQLBuilderSegment;
import com.easy.query.core.expression.segment.builder.OrderBySQLBuilderSegmentImpl;
import com.easy.query.core.expression.sql.builder.AnonymousManyJoinEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.include.RelationExtraEntity;
import com.easy.query.core.func.def.PartitionBySQLFunction;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EndNavigateParams;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.metadata.NavigateOrderProp;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyRelationalUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;
import com.easy.query.core.util.EasySQLUtil;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * create time 2025/3/19 16:17
 * 文件说明
 *
 * @author xuejiaming
 */
public class GenericEntityRelationToImplicitProvider implements EntityRelationPropertyProvider, EntityRelationToImplicitGroupProvider, EntityRelationToImplicitPartitionByProvider {

    public static final EntityRelationPropertyProvider INSTANCE = new GenericEntityRelationToImplicitProvider();

    @Override
    public TableAvailable toImplicitJoin(EntityExpressionBuilder entityExpressionBuilder, TableAvailable leftTable, String property) {
        return EasyRelationalUtil.getRelationTable(entityExpressionBuilder, leftTable, property);
    }

    @Override
    public <T> ClientQueryable<T> toImplicitSubQuery(EntityExpressionBuilder entityExpressionBuilder, TableAvailable leftTable, NavigateMetadata navigateMetadata, QueryRuntimeContext runtimeContext) {

        ClientQueryable<?> clientQueryable = runtimeContext.getSQLClientApiFactory().createSubQueryable(navigateMetadata.getNavigatePropertyType(), runtimeContext, entityExpressionBuilder.getExpressionContext());

        if (navigateMetadata.getRelationType() == RelationTypeEnum.ManyToMany && navigateMetadata.getMappingClass() != null) {
            ClientQueryable<?> mappingQueryable = runtimeContext.getSQLClientApiFactory().createSubQueryable(navigateMetadata.getMappingClass(), runtimeContext, entityExpressionBuilder.getExpressionContext());
            clientQueryable.where(x -> {
                x.and(() -> {
                    ClientQueryable<?> subMappingQueryable = mappingQueryable.where(m -> {
                        m.multiEq(true, x, navigateMetadata.getTargetMappingProperties(), navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext));
                        m.multiEq(true, new SimpleEntitySQLTableOwner<>(leftTable), navigateMetadata.getSelfMappingProperties(), navigateMetadata.getSelfPropertiesOrPrimary());
                        navigateMetadata.predicateMappingClassFilterApply(m);
                    }).limit(1);
                    x.exists(subMappingQueryable);
                    navigateMetadata.predicateFilterApply(x);
                });
            });
        } else {
            clientQueryable.where(t -> {
                t.and(() -> {
                    t.multiEq(true, new SimpleEntitySQLTableOwner<>(leftTable), navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext), navigateMetadata.getSelfPropertiesOrPrimary());
                    navigateMetadata.predicateFilterApply(t);
                });
            });
        }
        return EasyObjectUtil.typeCastNullable(clientQueryable);
    }


    @Override
    public String getName() {
        return "";
    }


    @Override
    public AnonymousManyJoinEntityTableExpressionBuilder toImplicitGroup(EntityExpressionBuilder entityExpressionBuilder, TableAvailable leftTable, NavigateMetadata navigateMetadata, ManyConfiguration manyConfiguration) {
        return EasyRelationalUtil.getManyJoinRelationTable(entityExpressionBuilder, leftTable, navigateMetadata, manyConfiguration);
    }

    @Override
    public <T1> AnonymousManyJoinEntityTableExpressionBuilder toImplicitPartitionBy(Class<T1> entityClass, EntityExpressionBuilder entityExpressionBuilder, TableAvailable leftTable, NavigateMetadata navigateMetadata, int index, QueryRuntimeContext runtimeContext, SQLActionExpression1<ClientQueryable<T1>> clientQueryableSQLExpression) {
        //获取表达式配置信息
        ManyConfiguration manyConfiguration = entityExpressionBuilder.getManyConfiguration(new DefaultRelationTableKey(leftTable, navigateMetadata.getPropertyName()));
        //创建分区分组查询表达式
        ClientQueryable<?> clientQueryable = createPartitionQueryable(entityClass, entityExpressionBuilder, navigateMetadata, manyConfiguration, clientQueryableSQLExpression);

        String queryableKey = EasySQLUtil.toQueryableKey(clientQueryable);
        RelationTableKey partitionByRelationTableKey = new PartitionByRelationTableKey(leftTable, navigateMetadata.getPropertyName(), index, queryableKey);

        return EasyRelationalUtil.getManySingleJoinRelationTable(partitionByRelationTableKey, entityExpressionBuilder, leftTable, navigateMetadata, index, clientQueryable);

    }


    private <T1> ClientQueryable<?> createPartitionQueryable(Class<T1> entityClass, EntityExpressionBuilder entityExpressionBuilder, NavigateMetadata navigateMetadata, ManyConfiguration manyConfiguration, SQLActionExpression1<ClientQueryable<T1>> clientQueryableSQLExpression) {
        QueryRuntimeContext runtimeContext = entityExpressionBuilder.getRuntimeContext();
        String[] targetPropertiesOrPrimary = navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext);

        ClientQueryable<T1> clientQueryable = runtimeContext.getSQLClientApiFactory().createSubQueryable(entityClass, runtimeContext, entityExpressionBuilder.getExpressionContext());
        SQLFuncExpression1<ClientQueryable<?>, ClientQueryable<?>> queryableSQLFuncExpression1 = Optional.ofNullable(manyConfiguration).map(x -> x.getConfigureExpression()).orElse(null);
        if (queryableSQLFuncExpression1 != null) {
            clientQueryable = EasyObjectUtil.typeCastNotNull(queryableSQLFuncExpression1.apply(clientQueryable));
        }


        if (navigateMetadata.getRelationType() == RelationTypeEnum.ManyToMany && navigateMetadata.getMappingClass() != null) {
            clientQueryable.innerJoin(navigateMetadata.getMappingClass(), (target, middle) -> {
                        target.multiEq(true, middle, navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext), navigateMetadata.getTargetMappingProperties());
                    })
                    .where((target, middle) -> {
                        navigateMetadata.predicateMappingClassFilterApply(middle);
                        navigateMetadata.predicateFilterApply(target);
                    }).select(Map.class, (target, middle) -> {
                        EntityMetadata middleEntityMetadata = middle.getEntityMetadata();
                        for (String selfMappingProperty : navigateMetadata.getSelfMappingProperties()) {
                            ColumnMetadata columnMetadata = middleEntityMetadata.getColumnNotNull(selfMappingProperty);
                            middle.columnAs(selfMappingProperty, "__" + columnMetadata.getName() + "__");
                        }
                    });
        } else {
            clientQueryable.where(t -> {
                navigateMetadata.predicateFilterApply(t);

            });
        }
        clientQueryableSQLExpression.apply(clientQueryable);


        OrderBySQLBuilderSegmentImpl orderBySQLBuilderSegment = new OrderBySQLBuilderSegmentImpl();
        EasySQLExpressionUtil.appendPartitionByOrderSegment(clientQueryable, new EndNavigateParams(navigateMetadata,null), orderBySQLBuilderSegment);

        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = clientQueryable.getSQLEntityExpressionBuilder();
        return clientQueryable.select(Map.class, x -> {
            x.columnAll();


            PartitionBySQLFunction partitionBySQLFunction = runtimeContext.fx().rowNumberOver(s -> {
                if (navigateMetadata.getRelationType() == RelationTypeEnum.ManyToMany && navigateMetadata.getMappingClass() != null) {
                    EntityTableExpressionBuilder recentlyTableExpressionBuilder = sqlEntityExpressionBuilder.getRecentlyTable();
                    TableAvailable entityTable = recentlyTableExpressionBuilder.getEntityTable();

                    for (String selfMappingProperty : navigateMetadata.getSelfMappingProperties()) {
                        s.column(entityTable, selfMappingProperty);
                    }
                } else {
                    for (String column : targetPropertiesOrPrimary) {
                        s.column(column);
                    }
                }
            });


            if (EasySQLSegmentUtil.isNotEmpty(orderBySQLBuilderSegment)) {
                partitionBySQLFunction.addOrder(orderBySQLBuilderSegment);
            }
            x.sqlFuncAs(partitionBySQLFunction, "__row__");

        });
    }

    @Override
    public void relationMultiIdsFetcherPredicate(WherePredicate<?> targetWherePredicate, String[] targetProps, List<List<Object>> relationIds, Integer groupSize) {
        if (groupSize != null && relationIds.size() > groupSize) {
            List<List<List<Object>>> partitionRelationIds = EasyCollectionUtil.partition(relationIds, groupSize);
            targetWherePredicate.and(() -> {
                for (List<List<Object>> partitionRelationId : partitionRelationIds) {
                    targetWherePredicate.relationIn(targetProps, partitionRelationId).or();
                }
            });
        } else {
            targetWherePredicate.relationIn(targetProps, relationIds);
        }


    }

    @Override
    public void relationMultiIdFetcherPredicate(WherePredicate<?> targetWherePredicate, String[] targetProps, List<Object> relationIds) {
        targetWherePredicate.multiEq(true, targetProps, relationIds);
    }


    @Override
    public RelationIncludeGetter getOneToOneGetter(QueryRuntimeContext runtimeContext, NavigateMetadata navigateMetadata, String[] selfRelationColumn, Collection<RelationExtraEntity> entities) {
        return new EqualsOneToOneGetter(runtimeContext, navigateMetadata, selfRelationColumn, entities);
    }

    @Override
    public RelationIncludeGetter getDirectToOneGetter(QueryRuntimeContext runtimeContext, NavigateMetadata navigateMetadata, List<RelationExtraEntity> includes, List<Object> mappingRow) {
        return new EqualsDirectToOneGetter(runtimeContext, navigateMetadata, includes, mappingRow);
    }

    @Override
    public RelationIncludeGetter getManyToOneGetter(QueryRuntimeContext runtimeContext, NavigateMetadata navigateMetadata, String[] targetPropertyNames, List<RelationExtraEntity> includes) {
        return new EqualsManyToOneGetter(runtimeContext, navigateMetadata, targetPropertyNames, includes);
    }

    @Override
    public RelationIncludeGetter getOneToManyGetter(QueryRuntimeContext runtimeContext, NavigateMetadata navigateMetadata, String[] targetPropertyNames, List<RelationExtraEntity> includes) {
        return new EqualsOneToManyGetter(navigateMetadata, targetPropertyNames, includes);
    }

    @Override
    public RelationIncludeGetter getManyToManyGetter(QueryRuntimeContext runtimeContext, NavigateMetadata navigateMetadata, String[] targetPropertyNames, List<RelationExtraEntity> includes, List<Object> mappingRows, boolean hasOrder) {
        if (navigateMetadata.getMappingClass() == null) {
            return new EqualsManyToManyNoMappingRowsGetter(navigateMetadata, targetPropertyNames, includes);
        }
        if (hasOrder) {
            return new EqualsManyToManyMappingOrderRowsGetter(runtimeContext, navigateMetadata, targetPropertyNames, includes, mappingRows);
        }
        return new EqualsManyToManyMappingRowsGetter(runtimeContext, navigateMetadata, targetPropertyNames, includes, mappingRows);
    }
}
