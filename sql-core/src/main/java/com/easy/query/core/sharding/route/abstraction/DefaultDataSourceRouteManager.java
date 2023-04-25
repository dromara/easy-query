package com.easy.query.core.sharding.route.abstraction;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.sharding.EasyDataSource;
import com.easy.query.core.sharding.route.datasource.DataSourceRoute;
import com.easy.query.core.sharding.route.datasource.ShardingDataSourceRoute;
import com.easy.query.core.sharding.rule.datasource.DataSourceRouteRule;
import com.easy.query.core.util.ClassUtil;

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
public class DefaultDataSourceRouteManager implements DataSourceRouteManager{
    private final Map<Class<?>, DataSourceRouteRule> entityRouteRuleCache= new ConcurrentHashMap<>();
    private final DataSourceRoute dataSourceRoute;
    private final EntityMetadataManager entityMetadataManager;
    private final EasyDataSource virtualDataSource;

    public DefaultDataSourceRouteManager(EntityMetadataManager entityMetadataManager, EasyDataSource virtualDataSource){

        this.entityMetadataManager = entityMetadataManager;
        this.virtualDataSource = virtualDataSource;
        dataSourceRoute=new ShardingDataSourceRoute(entityMetadataManager);
    }
    @Override
    public Collection<String> routeTo(Class<?> entityClass, PrepareParseResult prepareParseResult) {
        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entityClass);
        if(!entityMetadata.isMultiDataSourceMapping()){
            return Collections.singletonList(virtualDataSource.getDefaultDataSourceName());
        }
        DataSourceRouteRule routeRule = getRouteRule(entityClass);
        return dataSourceRoute.route(routeRule, prepareParseResult);
    }

    @Override
    public DataSourceRouteRule getRouteRule(Class<?> entityClass) {
        DataSourceRouteRule dataSourceRouteRule = entityRouteRuleCache.get(entityClass);
        if(dataSourceRouteRule==null){
            throw new EasyQueryInvalidOperationException(ClassUtil.getSimpleName(entityClass) +" not found data source route rule");
        }
        return dataSourceRouteRule;
    }

    @Override
    public boolean addRouteRule(DataSourceRouteRule dataSourceRouteRule) {
        return false;
    }
}
