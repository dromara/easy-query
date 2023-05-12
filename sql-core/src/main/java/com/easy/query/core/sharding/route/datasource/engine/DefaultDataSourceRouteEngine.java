package com.easy.query.core.sharding.route.datasource.engine;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.sharding.EasyQueryDataSource;
import com.easy.query.core.sharding.route.manager.DataSourceRouteManager;
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

    public DefaultDataSourceRouteEngine(EasyQueryDataSource easyDataSource,  DataSourceRouteManager dataSourceRouteManager){

        this.easyDataSource = easyDataSource;
        this.dataSourceRouteManager = dataSourceRouteManager;
    }
    @Override
    public DataSourceRouteResult route(PrepareParseResult prepareParseResult) {
        Map<Class<?>, Set<String>> dataSourceMaps = new HashMap<>();
        for (TableAvailable shardingTable : prepareParseResult.getShardingTables()) {
            EntityMetadata entityMetadata = shardingTable.getEntityMetadata();
            if(!entityMetadata.isMultiDataSourceMapping()){
                HashSet<String> defDataSource = new HashSet<String>() {{
                    add(easyDataSource.getDefaultDataSourceName());
                }};
                dataSourceMaps.put(shardingTable.getEntityClass(),defDataSource);
            }
            Collection<String> dataSources = dataSourceRouteManager.routeTo(shardingTable,prepareParseResult);
            Set<String> entityDataSources = dataSourceMaps.get(shardingTable.getEntityClass());
            if(entityDataSources==null){
                dataSourceMaps.put(shardingTable.getEntityClass(),new HashSet<>(dataSources));
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
