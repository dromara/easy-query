package com.easy.query.core.sharding.router.datasource.abstraction;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.sharding.router.datasource.DataSourceRouter;
import com.easy.query.core.sharding.router.descriptor.RouteDescriptor;
import com.easy.query.core.sharding.route.datasource.DataSourceRoute;

import java.util.Collection;

/**
 * create time 2023/4/19 13:10
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractDataSourceRouter implements DataSourceRouter {
    @Override
    public <T> Collection<String> route(DataSourceRoute<T> dataSourceRoute, RouteDescriptor routeDescriptor) {
        TableAvailable table = routeDescriptor.getTable();
        EntityMetadata entityMetadata = table.getEntityMetadata();
        Collection<String> dataSources = entityMetadata.getDataSources();
        Collection<String> beforeFilterDataSource = dataSourceRoute.beforeFilterDataSource(dataSources);
        Collection<String> filterDataSources = route0(dataSourceRoute, beforeFilterDataSource, routeDescriptor);
        return  dataSourceRoute.afterFilterDataSource(dataSources,beforeFilterDataSource,filterDataSources);

    }
    public abstract <T> Collection<String> route0(DataSourceRoute<T> dataSourceRoute, Collection<String> beforeTableNames, RouteDescriptor routeDescriptor);

}
