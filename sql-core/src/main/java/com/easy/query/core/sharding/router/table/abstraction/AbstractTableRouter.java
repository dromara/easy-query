package com.easy.query.core.sharding.router.table.abstraction;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.ActualTable;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.sharding.router.datasource.engine.DataSourceRouteResult;
import com.easy.query.core.sharding.router.descriptor.RouteDescriptor;
import com.easy.query.core.sharding.router.table.TableRouter;
import com.easy.query.core.sharding.router.table.TableRouteUnit;
import com.easy.query.core.sharding.route.table.TableRoute;

import java.util.Collection;

/**
 * create time 2023/4/18 23:08
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractTableRouter implements TableRouter {


    @Override
    public <T> Collection<TableRouteUnit> route(TableRoute<T> tableRoute, DataSourceRouteResult dataSourceRouteResult, RouteDescriptor routeDescriptor) {
        TableAvailable table = routeDescriptor.getTable();
        EntityMetadata entityMetadata = table.getEntityMetadata();
        Collection<ActualTable> actualTables = entityMetadata.getActualTables();
        Collection<ActualTable> beforeFilterTableNames = tableRoute.beforeFilterTableName(actualTables);
        Collection<TableRouteUnit> tableRouteUnits = route0(tableRoute,dataSourceRouteResult, beforeFilterTableNames, routeDescriptor);
        return  tableRoute.afterFilterTableName(actualTables,beforeFilterTableNames,tableRouteUnits);

    }
    public abstract <T> Collection<TableRouteUnit> route0(TableRoute<T> tableRoute, DataSourceRouteResult dataSourceRouteResult, Collection<ActualTable> beforeTableNames, RouteDescriptor routeDescriptor);

}
