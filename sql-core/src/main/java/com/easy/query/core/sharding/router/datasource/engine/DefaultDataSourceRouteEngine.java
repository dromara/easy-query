package com.easy.query.core.sharding.router.datasource.engine;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.executor.parser.descriptor.TableParseDescriptor;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.sharding.EasyQueryDataSource;
import com.easy.query.core.sharding.router.descriptor.RouteDescriptor;
import com.easy.query.core.sharding.router.descriptor.RouteDescriptorFactory;
import com.easy.query.core.sharding.router.manager.DataSourceRouteManager;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * create time 2023/4/11 13:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultDataSourceRouteEngine implements DataSourceRouteEngine{
    private final EasyQueryDataSource easyDataSource;
    private final DataSourceRouteManager dataSourceRouteManager;
    private final RouteDescriptorFactory routeDescriptorFactory;

    public DefaultDataSourceRouteEngine(EasyQueryDataSource easyDataSource, DataSourceRouteManager dataSourceRouteManager, RouteDescriptorFactory routeDescriptorFactory){

        this.easyDataSource = easyDataSource;
        this.dataSourceRouteManager = dataSourceRouteManager;
        this.routeDescriptorFactory = routeDescriptorFactory;
    }
    @Override
    public DataSourceRouteResult route(TableParseDescriptor tableParseDescriptor) {
        Map<TableAvailable, Set<String>> dataSourceMaps = new HashMap<>();
        Set<TableAvailable> tables = tableParseDescriptor.getTables();
        for (TableAvailable shardingTable : tables) {
            EntityMetadata entityMetadata = shardingTable.getEntityMetadata();
            if(!entityMetadata.isMultiDataSourceMapping()){
                HashSet<String> defDataSource = new HashSet<>();
                defDataSource.add(easyDataSource.getDefaultDataSourceName());
                dataSourceMaps.put(shardingTable,defDataSource);
            }
            RouteDescriptor routeDescriptor = routeDescriptorFactory.createRouteDescriptor(shardingTable,tableParseDescriptor);
            Collection<String> dataSources = dataSourceRouteManager.routeTo(routeDescriptor);
            Set<String> entityDataSources = dataSourceMaps.get(shardingTable);
            if(entityDataSources==null){
                dataSourceMaps.put(shardingTable,new HashSet<>(dataSources));
            }else {
                entityDataSources.addAll(dataSources);
            }
        }

        if(dataSourceMaps.isEmpty()){
            throw new EasyQueryInvalidOperationException("data source route mot match:");
        }
        if(dataSourceMaps.size()==1){
            Set<String> firstDataSources = dataSourceMaps.values().iterator().next();
            return new DataSourceRouteResult(firstDataSources);
        }
        Set<String> intersectDataSources = EasyCollectionUtil.getIntersection(dataSourceMaps.values());
        return new DataSourceRouteResult(intersectDataSources);
    }

}
