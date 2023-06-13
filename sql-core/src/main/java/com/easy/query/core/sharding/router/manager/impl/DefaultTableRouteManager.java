package com.easy.query.core.sharding.router.manager.impl;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.sharding.router.datasource.engine.DataSourceRouteResult;
import com.easy.query.core.sharding.router.descriptor.RouteDescriptor;
import com.easy.query.core.sharding.router.manager.TableRouteManager;
import com.easy.query.core.sharding.router.table.TableRouteUnit;
import com.easy.query.core.sharding.router.table.TableRouter;
import com.easy.query.core.sharding.route.table.TableRoute;
import com.easy.query.core.util.EasyClassUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * create time 2023/4/20 17:09
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultTableRouteManager implements TableRouteManager {
    private final Map<Class<?>, TableRoute<?>> entityRouteCache = new ConcurrentHashMap<>();
    private final TableRouter tableRouter;

    public DefaultTableRouteManager(TableRouter tableRouter){
        this.tableRouter = tableRouter;
    }

    @Override
    public Collection<TableRouteUnit> routeTo(DataSourceRouteResult dataSourceRouteResult, RouteDescriptor routeDescriptor) {
        TableRoute<?> route = getRoute(routeDescriptor.getTable().getEntityClass());
        return tableRouter.route(route,dataSourceRouteResult,routeDescriptor);
    }

    @Override
    public TableRoute<?> getRoute(Class<?> entityClass) {

        TableRoute<?> tableRoute = entityRouteCache.get(entityClass);
        if(tableRoute==null){
            throw new EasyQueryInvalidOperationException(EasyClassUtil.getSimpleName(entityClass) +" not found table route");
        }
        return tableRoute;
    }

    @Override
    public boolean addRoute(TableRoute<?> tableRoute) {
        TableRoute<?> oldTableRoute = entityRouteCache.get(tableRoute.entityClass());
        if(oldTableRoute==null){
            entityRouteCache.put(tableRoute.entityClass(),tableRoute);
            return true;
        }
        return false;
    }
}
