package com.easy.query.core.expression.sql.include;

import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.IncludeLimitModeEnum;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.implicit.EntityRelationPropertyProvider;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.NavigateInclude;
import com.easy.query.core.expression.parser.core.base.impl.NavigateIncludeImpl;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.OrderBySegment;
import com.easy.query.core.expression.segment.SQLNativeSegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.builder.OrderBySQLBuilderSegment;
import com.easy.query.core.expression.segment.builder.OrderBySQLBuilderSegmentImpl;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.func.def.PartitionBySQLFunction;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.metadata.IncludeNavigateExpression;
import com.easy.query.core.metadata.IncludeNavigateParams;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyArrayUtil;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyOptionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * create time 2025/3/1 18:09
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultIncludeProvider implements IncludeProvider {

    @Override
    public <TProperty> void include(@Nullable TableAvailable table, EntityMetadata entityMetadata, ExpressionContext expressionContext, SQLFuncExpression1<NavigateInclude, ClientQueryable<TProperty>> navigateIncludeSQLExpression) {

        QueryRuntimeContext runtimeContext = expressionContext.getRuntimeContext();
        IncludeNavigateParams includeNavigateParams = new IncludeNavigateParams();
        Integer groupSize = expressionContext.getGroupSize();
        Boolean printNavSQL = EasyOptionUtil.isPrintNavSQL(expressionContext);
        EasyQueryOption easyQueryOption = runtimeContext.getQueryConfiguration().getEasyQueryOption();
        int relationGroupSize = groupSize != null ? groupSize : easyQueryOption.getRelationGroupSize();
        includeNavigateParams.setRelationGroupSize(relationGroupSize);
        NavigateInclude navigateInclude = new NavigateIncludeImpl(table, entityMetadata, expressionContext.getRuntimeContext(), includeNavigateParams, expressionContext);
        ClientQueryable<TProperty> clientQueryable = navigateIncludeSQLExpression.apply(navigateInclude);
        boolean hasOrder = EasySQLSegmentUtil.isNotEmpty(clientQueryable.getSQLEntityExpressionBuilder().getOrder());
//        int i=1;
//        for (SQLSegment sqlSegment : clientQueryable.getSQLEntityExpressionBuilder().getOrder().getSQLSegments()) {
//            if(sqlSegment instanceof OrderBySegment){
//                OrderBySegment orderBySegment = (OrderBySegment) sqlSegment;
//                boolean asc = orderBySegment.isAsc();
//                SQLNativeSegment columnSegment = ((SQLNativeSegment)orderBySegment).cloneSQLColumnSegment();
//                columnSegment.setAlias(String.format("__relation__%s__%s__",i,asc?"asc":"desc"));
//                clientQueryable.getSQLEntityExpressionBuilder().getProjects().append(columnSegment);
//            }
//            i++;
//        }
        includeNavigateParams.setHasOrder(hasOrder);
        boolean hasLimit = clientQueryable.getSQLEntityExpressionBuilder().hasLimit();
        NavigateMetadata navigateMetadata = includeNavigateParams.getNavigateMetadata();
        if (!Objects.equals(navigateMetadata.getNavigatePropertyType(), clientQueryable.queryClass())) {
            throw new EasyQueryInvalidOperationException(EasyClassUtil.getSimpleName(entityMetadata.getEntityClass()) + " include query navigate error return type should:[" + EasyClassUtil.getSimpleName(navigateMetadata.getNavigatePropertyType()) + "] actual:[" + EasyClassUtil.getSimpleName(clientQueryable.queryClass()) + "]");
        }
        boolean directMapping = EasyArrayUtil.isNotEmpty(navigateMetadata.getDirectMapping());
        SQLFuncExpression<ClientQueryable<?>> queryableExpression = () -> {
            return getIncludeQueryable(navigateMetadata, includeNavigateParams, hasLimit, clientQueryable, printNavSQL, directMapping, runtimeContext);
        };
        boolean replace = navigateInclude.getIncludeNavigateParams().isReplace();
        if (replace) {
            expressionContext.getIncludes().put(includeNavigateParams.getNavigateMetadata(), new IncludeNavigateExpression(includeNavigateParams, queryableExpression));
        } else {
            expressionContext.getIncludes().putIfAbsent(includeNavigateParams.getNavigateMetadata(), new IncludeNavigateExpression(includeNavigateParams, queryableExpression));
        }

    }


    private <TProperty> ClientQueryable<TProperty> getIncludeQueryable(NavigateMetadata navigateMetadata, IncludeNavigateParams includeNavigateParams, boolean hasLimit, ClientQueryable<TProperty> clientQueryable, Boolean printNavSQL, boolean directMapping, QueryRuntimeContext runtimeContext) {
        List<List<Object>> relationIds = includeNavigateParams.getRelationIds();
        if (hasLimit) {
            EasyQueryOption easyQueryOption = runtimeContext.getQueryConfiguration().getEasyQueryOption();
            if (easyQueryOption.getIncludeLimitMode() == IncludeLimitModeEnum.PARTITION) {
                EntityQueryExpressionBuilder sqlEntityExpressionBuilder = clientQueryable.cloneQueryable().getSQLEntityExpressionBuilder();
                long offset = sqlEntityExpressionBuilder.getOffset();
                long rows = sqlEntityExpressionBuilder.getRows();
                ClientQueryable<TProperty> noLimitQueryable = clientQueryable.cloneQueryable().limit(0, 0);
                OrderBySQLBuilderSegment order = noLimitQueryable.getSQLEntityExpressionBuilder().getOrder();
                OrderBySQLBuilderSegmentImpl orderBySQLBuilderSegment = new OrderBySQLBuilderSegmentImpl();
                order.copyTo(orderBySQLBuilderSegment);
                order.clear();

                EntityMetadataManager entityMetadataManager = runtimeContext.getEntityMetadataManager();
                EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(navigateMetadata.getNavigatePropertyType());
                ClientQueryable<?> partitionQueryable = noLimitQueryable.where(o -> {
                            o.and(() -> {
                                EntityRelationPropertyProvider relationPropertyProvider = navigateMetadata.getEntityRelationPropertyProvider();

                                if (directMapping) {
                                    relationPropertyProvider.relationMultiIdsFetcherPredicate(o, navigateMetadata.getDirectTargetPropertiesOrPrimary(runtimeContext), relationIds,includeNavigateParams.getQueryRelationGroupSize());
                                } else {
                                    relationPropertyProvider.relationMultiIdsFetcherPredicate(o, navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext), relationIds,includeNavigateParams.getQueryRelationGroupSize());
                                }
                                navigateMetadata.predicateFilterApply(o);
//                        navigateMetadata.predicateFilterApply(o);
                            });
                        }).select(Map.class, x -> {
                            x.columnAll();
//                            for (Map.Entry<String, ColumnMetadata> columnMetadataKv : x.getTable().getEntityMetadata().getProperty2ColumnMap().entrySet()) {
//
//                                x.columnAs(columnMetadataKv.getKey(),columnMetadataKv.getValue().getName());
//                            }


                            PartitionBySQLFunction partitionBySQLFunction = runtimeContext.fx().rowNumberOver(s -> {

                                if (directMapping) {
                                    String[] directTargetPropertiesOrPrimary = navigateMetadata.getDirectTargetPropertiesOrPrimary(runtimeContext);
                                    for (String column : directTargetPropertiesOrPrimary) {
                                        s.column(column);
                                    }
//                    o.multiEq(true, navigateMetadata.getDirectTargetPropertiesOrPrimary(runtimeContext), relationId);
                                } else {
                                    String[] targetPropertiesOrPrimary = navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext);
                                    for (String column : targetPropertiesOrPrimary) {
                                        s.column(column);
                                    }

                                }
                            });
                            if (EasySQLSegmentUtil.isNotEmpty(orderBySQLBuilderSegment)) {
                                partitionBySQLFunction.addOrder(orderBySQLBuilderSegment);
                            }
                            x.sqlFuncAs(partitionBySQLFunction, "__row__");

                        }).where(m -> m.ge("__row__", offset + 1).le("__row__", offset + rows))
                        .select(navigateMetadata.getNavigatePropertyType(), o -> {
                            for (Map.Entry<String, ColumnMetadata> columnMetadataEntry : entityMetadata.getProperty2ColumnMap().entrySet()) {
//                            o.column(columnMetadataEntry.getValue().getName());
                                o.sqlNativeSegment("{0}", c -> {
                                    c.expression(columnMetadataEntry.getValue().getName());
                                    c.setAlias(columnMetadataEntry.getValue().getName());
                                });
                            }
                        });
                return EasyObjectUtil.typeCastNullable(partitionQueryable);

            } else if (easyQueryOption.getIncludeLimitMode() == IncludeLimitModeEnum.UNION_ALL) {
                ClientQueryable<TProperty> unionAllRelationLimitQueryable = getUnionAllRelationLimitQueryable(navigateMetadata, includeNavigateParams, clientQueryable, printNavSQL, directMapping, runtimeContext);
                if (unionAllRelationLimitQueryable != null) {
                    return unionAllRelationLimitQueryable;
                }
            } else {
                throw new EasyQueryInvalidOperationException("include limit mode error:" + easyQueryOption.getIncludeLimitMode());
            }
        }
        //                        navigateMetadata.predicateFilterApply(o);
        return clientQueryable.cloneQueryable().configure(s -> {
            s.setPrintSQL(printNavSQL);
            s.setPrintNavSQL(printNavSQL);
        }).where(o -> {
            o.and(() -> {
                EntityRelationPropertyProvider relationPropertyProvider = navigateMetadata.getEntityRelationPropertyProvider();

                if (directMapping) {
                    relationPropertyProvider.relationMultiIdsFetcherPredicate(o, navigateMetadata.getDirectTargetPropertiesOrPrimary(runtimeContext), relationIds,includeNavigateParams.getQueryRelationGroupSize());
                } else {
                    relationPropertyProvider.relationMultiIdsFetcherPredicate(o, navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext), relationIds,includeNavigateParams.getQueryRelationGroupSize());
                }
                navigateMetadata.predicateFilterApply(o);
//                        navigateMetadata.predicateFilterApply(o);
            });
        });
    }

    private <TProperty> ClientQueryable<TProperty> getUnionAllRelationLimitQueryable(NavigateMetadata navigateMetadata, IncludeNavigateParams includeNavigateParams, ClientQueryable<TProperty> clientQueryable, Boolean printNavSQL, boolean directMapping, QueryRuntimeContext runtimeContext) {
        List<List<Object>> relationIds = includeNavigateParams.getRelationIds();
        if (EasyCollectionUtil.isNotEmpty(relationIds) && EasyCollectionUtil.isNotSingle(relationIds)) {
            Iterator<List<Object>> iterator = relationIds.iterator();
            List<Object> firstRelationId = iterator.next();
            ClientQueryable<TProperty> firstQueryable = getRelationLimitQueryable(clientQueryable, navigateMetadata, firstRelationId, directMapping, runtimeContext);
            ArrayList<ClientQueryable<TProperty>> otherQueryable = new ArrayList<>();
            while (iterator.hasNext()) {
                List<Object> nextRelationId = iterator.next();
                ClientQueryable<TProperty> nextQueryable = getRelationLimitQueryable(clientQueryable, navigateMetadata, nextRelationId, directMapping, runtimeContext);
                otherQueryable.add(nextQueryable);
            }
            return firstQueryable.configure(s -> {
                s.setPrintSQL(printNavSQL);
                s.setPrintNavSQL(printNavSQL);
            }).unionAll(otherQueryable);
        }
        return null;
    }


    private <TProperty> ClientQueryable<TProperty> getRelationLimitQueryable(ClientQueryable<TProperty> clientQueryable, NavigateMetadata navigateMetadata, List<Object> relationId, boolean directMapping, QueryRuntimeContext runtimeContext) {

        ClientQueryable<TProperty> firstQueryable = clientQueryable.cloneQueryable();
        firstQueryable.where(o -> {
            o.and(() -> {
                EntityRelationPropertyProvider relationPropertyProvider = navigateMetadata.getEntityRelationPropertyProvider();

                if (directMapping) {
                    relationPropertyProvider.relationMultiIdFetcherPredicate(o, navigateMetadata.getDirectTargetPropertiesOrPrimary(runtimeContext), relationId);
//                    o.multiEq(true, navigateMetadata.getDirectTargetPropertiesOrPrimary(runtimeContext), relationId);
                } else {
                    relationPropertyProvider.relationMultiIdFetcherPredicate(o, navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext), relationId);

                }
                navigateMetadata.predicateFilterApply(o);
            });
        });
        return firstQueryable;
    }

}
