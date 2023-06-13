package com.easy.query.core.sharding.router.manager.impl;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.sharding.EasyQueryDataSource;
import com.easy.query.core.sharding.router.datasource.DataSourceRouter;
import com.easy.query.core.sharding.router.descriptor.RouteDescriptor;
import com.easy.query.core.sharding.router.manager.DataSourceRouteManager;
import com.easy.query.core.sharding.route.datasource.DataSourceRoute;
import com.easy.query.core.util.EasyClassUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * create time 2023/4/11 13:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultDataSourceRouteManager implements DataSourceRouteManager {
    private final Map<Class<?>, DataSourceRoute<?>> entityRouteCache = new ConcurrentHashMap<>();
    private final DataSourceRouter dataSourceRouter;
    private final EasyQueryDataSource easyDataSource;

    public DefaultDataSourceRouteManager(EasyQueryDataSource easyDataSource, DataSourceRouter dataSourceRouter) {

        this.easyDataSource = easyDataSource;
        this.dataSourceRouter = dataSourceRouter;
    }

    @Override
    public Collection<String> routeTo(RouteDescriptor routeDescriptor) {
        TableAvailable table = routeDescriptor.getTable();
        EntityMetadata entityMetadata = table.getEntityMetadata();
        if (!entityMetadata.isMultiDataSourceMapping()) {
            return Collections.singletonList(easyDataSource.getDefaultDataSourceName());
        }
        DataSourceRoute<?> route = getRoute(table.getEntityClass());
        return dataSourceRouter.route(route, routeDescriptor);
    }

    @Override
    public DataSourceRoute<?> getRoute(Class<?> entityClass) {
        DataSourceRoute<?> dataSourceRoute = entityRouteCache.get(entityClass);
        if (dataSourceRoute == null) {
            throw new EasyQueryInvalidOperationException(EasyClassUtil.getSimpleName(entityClass) + " not found data source route");
        }
        return dataSourceRoute;
    }

    @Override
    public boolean addRoute(DataSourceRoute<?> dataSourceRoute) {
        DataSourceRoute<?> oldDataSourceRoute = entityRouteCache.get(dataSourceRoute.entityClass());
        if (oldDataSourceRoute == null) {
            entityRouteCache.put(dataSourceRoute.entityClass(), dataSourceRoute);
            return true;
        }
        return false;
    }
}
