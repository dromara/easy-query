package com.easy.query.core.sharding.route.datasource.abstraction;

import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.expression.executor.parser.descriptor.TableParseDescriptor;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.sharding.route.datasource.DataSourceRoute;
import com.easy.query.core.sharding.route.descriptor.RouteDescriptor;
import com.easy.query.core.sharding.rule.datasource.DataSourceRouteRule;

import java.util.Collection;

/**
 * create time 2023/4/19 13:10
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractDataSourceRoute implements DataSourceRoute {
    @Override
    public <T> Collection<String> route(DataSourceRouteRule<T> dataSourceRouteRule, RouteDescriptor routeDescriptor) {
        TableAvailable table = routeDescriptor.getTable();
        EntityMetadata entityMetadata = table.getEntityMetadata();
        Collection<String> dataSources = entityMetadata.getDataSources();
        Collection<String> beforeFilterDataSource = dataSourceRouteRule.beforeFilterDataSource(dataSources);
        Collection<String> filterDataSources = route0(dataSourceRouteRule, beforeFilterDataSource, routeDescriptor);
        return  dataSourceRouteRule.afterFilterDataSource(dataSources,beforeFilterDataSource,filterDataSources);

    }
    public abstract <T> Collection<String> route0(DataSourceRouteRule<T> dataSourceRouteRule,Collection<String> beforeTableNames, RouteDescriptor routeDescriptor);

}
