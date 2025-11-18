package com.easy.query.core.util;

import com.easy.query.core.api.dynamic.executor.query.ConfigureArgument;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.IncludeLimitModeEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.builder.OrderSelector;
import com.easy.query.core.expression.implicit.EntityRelationPropertyProvider;
import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.extra.ExtraAutoIncludeConfigure;
import com.easy.query.core.expression.segment.builder.OrderBySQLBuilderSegmentImpl;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.PartitionBySQLFunction;
import com.easy.query.core.func.def.enums.OrderByModeEnum;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EndNavigateParams;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.metadata.IncludeNavigateParams;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.metadata.NavigateOrderProp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * create time 2024/10/14 11:19
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyNavigateUtil {

    public static void checkProperties(Class<?> entityClass, String property, String[] selfProperties, String[] selfMappingProperties, Class<?> mappingClass, String[] targetMappingProperties, String[] targetProperties) {
        if (selfProperties == null) {
            throw new IllegalArgumentException("selfProperties is null");
        }
        if (targetProperties == null) {
            throw new IllegalArgumentException("targetProperties is null");
        }
        if (mappingClass == null || Objects.equals(Object.class, mappingClass)) {
            checkSameLength(entityClass, property, selfProperties, targetProperties);
        } else {
            if (selfMappingProperties == null) {
                throw new IllegalArgumentException("selfMappingProperties is null");
            }
            if (targetMappingProperties == null) {
                throw new IllegalArgumentException("targetMappingProperties is null");
            }
            checkSameLength(entityClass, property, selfProperties, selfMappingProperties);
            checkSameLength(entityClass, property, targetMappingProperties, targetProperties);
        }
    }

    private static void checkSameLength(Class<?> entityClass, String property, String[] self, String[] target) {
        if (self.length > 1 || target.length > 1) {
            if (self.length != target.length) {
                throw new EasyQueryInvalidOperationException(EasyClassUtil.getSimpleName(entityClass) + ":[" + property + "] navigate properties self.length:" + self.length + " != target.length:" + target.length);
            }
        }
    }

    public static <T> ClientQueryable<T> navigateOrderBy(ClientQueryable<T> clientQueryable, EndNavigateParams endNavigateParams, IncludeNavigateParams includeNavigateParams, @Nullable EntityMetadata navigateEntityMetadata, @Nullable ConfigureArgument configureArgument, QueryRuntimeContext runtimeContext) {
        if (configureArgument != null) {
            clientQueryable.configure(o -> o.setConfigureArgument(configureArgument.getArg()));
        }
        if (navigateEntityMetadata != null && navigateEntityMetadata.getExtraAutoIncludeConfigure() != null) {
            ExtraAutoIncludeConfigure extraAutoIncludeConfigure = navigateEntityMetadata.getExtraAutoIncludeConfigure();
            if (extraAutoIncludeConfigure.getExtraConfigure() != null) {
                extraAutoIncludeConfigure.getExtraConfigure().configure(clientQueryable);
            }
            if (extraAutoIncludeConfigure.getExtraWhere() != null) {
                clientQueryable.where(extraAutoIncludeConfigure.getExtraWhere()::where);
            }
            if (extraAutoIncludeConfigure.isIgnoreNavigateConfigure()) {
                return clientQueryable;
            }
        }
        return navigateOrderBy0(clientQueryable, endNavigateParams, includeNavigateParams, runtimeContext);
    }

    private static <T> ClientQueryable<T> navigateOrderBy0(ClientQueryable<T> clientQueryable, EndNavigateParams endNavigateParams, IncludeNavigateParams includeNavigateParams, QueryRuntimeContext runtimeContext) {
        boolean hasLimit = clientQueryable.getSQLEntityExpressionBuilder().hasLimit();
        ClientQueryable<T> tClientQueryable = clientQueryable.limit(!hasLimit, endNavigateParams.getOffset(), endNavigateParams.getLimit()).orderBy(EasyCollectionUtil.isNotEmpty(endNavigateParams.getOrderProps()), o -> {
            TableAvailable table = o.getTable();
            OrderSelector orderSelector = o.getOrderSelector();
            for (NavigateOrderProp orderProp : endNavigateParams.getOrderProps()) {
                orderSelector.setAsc(orderProp.isAsc());
                OrderByModeEnum nullsModeEnum = orderProp.getMode();
                if (nullsModeEnum != null) {
                    SQLFunc fx = runtimeContext.fx();
                    SQLFunction orderByNullsModeFunction = fx.orderByNullsMode(table, orderProp.getProperty(), orderProp.isAsc(), nullsModeEnum);
                    orderSelector.func(table, orderByNullsModeFunction, false);
                } else {
                    orderSelector.column(table, orderProp.getProperty());
                }
            }
        }, true);
        if (tClientQueryable.getSQLEntityExpressionBuilder().hasLimit()) {

            EasyQueryOption easyQueryOption = runtimeContext.getQueryConfiguration().getEasyQueryOption();
            if (easyQueryOption.getIncludeLimitMode() == IncludeLimitModeEnum.PARTITION) {
                return getNavigateLimitPartitionByQueryable(endNavigateParams, includeNavigateParams, tClientQueryable, runtimeContext);
            }
        }


        return tClientQueryable;
    }

    public static <T> ClientQueryable<T> getNavigateLimitPartitionByQueryable(EndNavigateParams endNavigateParams, IncludeNavigateParams includeNavigateParams, ClientQueryable<T> tClientQueryable, QueryRuntimeContext runtimeContext) {

        boolean directMapping = EasyArrayUtil.isNotEmpty(endNavigateParams.getEntityNavigateMetadata().getDirectMapping());
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = tClientQueryable.cloneQueryable().getSQLEntityExpressionBuilder();
        long offset = sqlEntityExpressionBuilder.getOffset();
        long rows = sqlEntityExpressionBuilder.getRows();
        ClientQueryable<T> noLimitQueryable = tClientQueryable.cloneQueryable().limit(0, 0);
//                noLimitQueryable.getSQLEntityExpressionBuilder().getProjects().clear();
        OrderBySQLBuilderSegmentImpl orderBySQLBuilderSegment = new OrderBySQLBuilderSegmentImpl();
        EasySQLExpressionUtil.appendPartitionByOrderSegment(noLimitQueryable, endNavigateParams, orderBySQLBuilderSegment);


        EntityMetadataManager entityMetadataManager = runtimeContext.getEntityMetadataManager();
        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(endNavigateParams.getEntityNavigateMetadata().getNavigatePropertyType());
        ClientQueryable<?> partitionQueryable = noLimitQueryable.where(o -> {
                    o.and(() -> {
                        //延迟处理partition by的内部where 用于提高性能 因为当前处理的话includeNavigateParams.getRelationIds()还没有数据
                        SQLActionExpression navigatePartitionByWhereExpression = includeNavigateParams.getNavigatePartitionByWhereExpression();
                        if (navigatePartitionByWhereExpression != null) {
                            throw new EasyQueryInvalidOperationException("partition by where expression is not null");
                        }
                        includeNavigateParams.setNavigatePartitionByWhereExpression(() -> {
                            EntityRelationPropertyProvider relationPropertyProvider = endNavigateParams.getEntityNavigateMetadata().getEntityRelationPropertyProvider();

                            if (directMapping) {
                                relationPropertyProvider.relationMultiIdsFetcherPredicate(o, endNavigateParams.getEntityNavigateMetadata().getDirectTargetPropertiesOrPrimary(runtimeContext), includeNavigateParams.getRelationIds(), includeNavigateParams.getQueryRelationGroupSize());
                            } else {
                                relationPropertyProvider.relationMultiIdsFetcherPredicate(o, endNavigateParams.getEntityNavigateMetadata().getTargetPropertiesOrPrimary(runtimeContext), includeNavigateParams.getRelationIds(), includeNavigateParams.getQueryRelationGroupSize());
                            }
                            endNavigateParams.getEntityNavigateMetadata().predicateFilterApply(o);
                        });
                    });
                }).select(Map.class, x -> {
                    x.columnAll();

                    PartitionBySQLFunction partitionBySQLFunction = runtimeContext.fx().rowNumberOver(s -> {

                        if (directMapping) {
                            String[] directTargetPropertiesOrPrimary = endNavigateParams.getEntityNavigateMetadata().getDirectTargetPropertiesOrPrimary(runtimeContext);
                            for (String column : directTargetPropertiesOrPrimary) {
                                s.column(column);
                            }
//                    o.multiEq(true, navigateMetadata.getDirectTargetPropertiesOrPrimary(runtimeContext), relationId);
                        } else {
                            String[] targetPropertiesOrPrimary = endNavigateParams.getEntityNavigateMetadata().getTargetPropertiesOrPrimary(runtimeContext);
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
                .select(endNavigateParams.getEntityNavigateMetadata().getNavigatePropertyType(), o -> {
                    for (Map.Entry<String, ColumnMetadata> columnMetadataEntry : entityMetadata.getProperty2ColumnMap().entrySet()) {
//                            o.column(columnMetadataEntry.getValue().getName());
                        o.sqlNativeSegment("{0}", c -> {
                            c.expression(columnMetadataEntry.getValue().getName());
                            c.setAlias(columnMetadataEntry.getValue().getName());
                        });
                    }
                });
        partitionQueryable.getSQLEntityExpressionBuilder().getExpressionContext().getRelationExtraMetadata().getRelationExtraColumnMap().clear();
        partitionQueryable.getSQLEntityExpressionBuilder().getExpressionContext().getRelationExtraMetadata().getRelationExtraColumnList().clear();
        return EasyObjectUtil.typeCastNullable(partitionQueryable);
    }
}
