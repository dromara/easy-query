package com.easy.query.core.util;

import com.easy.query.core.api.dynamic.executor.query.ConfigureArgument;
import com.easy.query.core.api.dynamic.executor.query.SelectAutoIncludeConfigurable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.common.OffsetLimitEntry;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.builder.OrderSelector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.enums.OrderByModeEnum;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.metadata.NavigateOrderProp;

import java.util.List;
import java.util.function.Function;

/**
 * create time 2024/10/14 11:19
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyNavigateUtil {
    public static void checkProperties(String[] selfProperties, String[] targetProperties) {
        if (selfProperties == null) {
            throw new IllegalArgumentException("selfProperties is null");
        }
        if (targetProperties == null) {
            throw new IllegalArgumentException("targetProperties is null");
        }
        if (selfProperties.length > 1 || targetProperties.length > 1) {
            if (selfProperties.length != targetProperties.length) {
                throw new EasyQueryInvalidOperationException("selfProperties.length != targetProperties.length");
            }
        }
    }

    public static <T> ClientQueryable<T> navigateOrderBy(ClientQueryable<T> clientQueryable, OffsetLimitEntry offsetLimit, List<NavigateOrderProp> navigateOrderProps, EntityMetadata navigateEntityMetadata, ConfigureArgument configureArgument, QueryRuntimeContext runtimeContext) {
        if (SelectAutoIncludeConfigurable.class.isAssignableFrom(navigateEntityMetadata.getEntityClass())) {
            SelectAutoIncludeConfigurable selectAutoIncludeConfigurable = (SelectAutoIncludeConfigurable) navigateEntityMetadata.getBeanConstructorCreator().get();

            ClientQueryable<T> configureQueryable = selectAutoIncludeConfigurable.configure(clientQueryable, configureArgument);
            if (!selectAutoIncludeConfigurable.isInheritedBehavior()) {
                return configureQueryable;
            }
            return navigateOrderBy0(configureQueryable, offsetLimit, navigateOrderProps, runtimeContext);
        }
        return navigateOrderBy0(clientQueryable, offsetLimit, navigateOrderProps, runtimeContext);
    }
    private static <T> ClientQueryable<T> navigateOrderBy0(ClientQueryable<T> clientQueryable, OffsetLimitEntry offsetLimit, List<NavigateOrderProp> navigateOrderProps, QueryRuntimeContext runtimeContext) {
        return clientQueryable.limit(offsetLimit.offset, offsetLimit.limit).orderBy(EasyCollectionUtil.isNotEmpty(navigateOrderProps), o -> {
            TableAvailable table = o.getTable();
            OrderSelector orderSelector = o.getOrderSelector();
            for (NavigateOrderProp orderProp : navigateOrderProps) {
                orderSelector.setAsc(orderProp.isAsc());
                OrderByModeEnum nullsModeEnum = orderProp.getMode();
                if (nullsModeEnum != null) {
                    SQLFunc fx = runtimeContext.fx();
                    SQLFunction orderByNullsModeFunction = fx.orderByNullsMode(table,orderProp.getProperty(), orderProp.isAsc(), nullsModeEnum);
                    orderSelector.func(table, orderByNullsModeFunction, false);
                } else {
                    orderSelector.column(table, orderProp.getProperty());
                }
            }
        }, true);
    }

    public static List<NavigateOrderProp> getNavigateOrderProps(List<NavigateOrderProp> resultNavigateOrderProps, List<NavigateOrderProp> entityNavigateOrderProps) {
        if (EasyCollectionUtil.isNotEmpty(resultNavigateOrderProps)) {
            return resultNavigateOrderProps;
        }
        return entityNavigateOrderProps;
    }

    public static OffsetLimitEntry getNavigateLimit(NavigateMetadata resultNavigateMetadata, NavigateMetadata entityNavigateMetadata) {
        if (resultNavigateMetadata.getLimit() > 0) {
            return new OffsetLimitEntry(resultNavigateMetadata.getOffset(), resultNavigateMetadata.getLimit());
        }
        if (entityNavigateMetadata.getLimit() > 0) {
            return new OffsetLimitEntry(entityNavigateMetadata.getOffset(), entityNavigateMetadata.getLimit());
        }
        return OffsetLimitEntry.EMPTY;
    }
}
