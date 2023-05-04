package com.easy.query.core.sharding.route.datasource.engine;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.sharding.EasyQueryDataSource;
import com.easy.query.core.sharding.route.abstraction.DataSourceRouteManager;
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
    private final EntityMetadataManager entityMetadataManager;
    private final DataSourceRouteManager dataSourceRouteManager;

    public DefaultDataSourceRouteEngine(EasyQueryDataSource easyDataSource, EntityMetadataManager entityMetadataManager, DataSourceRouteManager dataSourceRouteManager){

        this.easyDataSource = easyDataSource;
        this.entityMetadataManager = entityMetadataManager;
        this.dataSourceRouteManager = dataSourceRouteManager;
    }
    @Override
    public DataSourceRouteResult route(PrepareParseResult prepareParseResult) {
        Map<Class<?>, Set<String>> dataSourceMaps = new HashMap<>();
        for (Class<?> shardingEntity : prepareParseResult.getShardingEntities()) {
            EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(shardingEntity);
            if(!entityMetadata.isMultiDataSourceMapping()){
                HashSet<String> defDataSource = new HashSet<String>() {{
                    add(easyDataSource.getDefaultDataSourceName());
                }};
                dataSourceMaps.put(shardingEntity,defDataSource);
            }
            Collection<String> dataSources = dataSourceRouteManager.routeTo(shardingEntity,prepareParseResult);
            Set<String> entityDataSources = dataSourceMaps.get(shardingEntity);
            if(entityDataSources==null){
                dataSourceMaps.put(shardingEntity,new HashSet<>(dataSources));
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
