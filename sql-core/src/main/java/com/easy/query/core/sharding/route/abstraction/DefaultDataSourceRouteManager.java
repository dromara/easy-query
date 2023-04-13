package com.easy.query.core.sharding.route.abstraction;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.sharding.EasyDataSource;
import com.easy.query.core.sharding.route.datasource.DataSourceRoute;
import com.easy.query.core.util.ClassUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * create time 2023/4/11 13:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultDataSourceRouteManager implements DataSourceRouteManager{
    private final Map<Class<?>, DataSourceRoute> entityRouteCache= new ConcurrentHashMap<>();
    private final EntityMetadataManager entityMetadataManager;
    private final EasyDataSource virtualDataSource;

    public DefaultDataSourceRouteManager(EntityMetadataManager entityMetadataManager, EasyDataSource virtualDataSource){

        this.entityMetadataManager = entityMetadataManager;
        this.virtualDataSource = virtualDataSource;
    }
    @Override
    public List<String> routeTo(Class<?> entityClass, DataSourceRouteParams dataSourceRouteParams) {
        if(!entityMetadataManager.isShardingDataSource(entityClass)){
            return new ArrayList<>(Collections.singletonList(virtualDataSource.getDefaultDataSourceName()));
        }
        DataSourceRoute dataSourceRoute = getRoute(entityClass);
        if(dataSourceRouteParams.getEntityExpression()!=null){
            dataSourceRoute.routeWithPredicate(dataSourceRouteParams.getEntityExpression());
        }
        if(dataSourceRouteParams.getShardingKeyValue()!=null){
            String dataSource = dataSourceRoute.routeWithValue(dataSourceRouteParams.getShardingKeyValue());
            return new ArrayList<>(Collections.singletonList(dataSource));
        }
        throw new EasyQueryInvalidOperationException(ClassUtil.getSimpleName(entityClass)+" cant route to ");
    }

    @Override
    public DataSourceRoute getRoute(Class<?> entityClass) {
        DataSourceRoute dataSourceRoute = entityRouteCache.get(entityClass);
        if(dataSourceRoute==null){
            throw new EasyQueryInvalidOperationException(ClassUtil.getSimpleName(entityClass) +" not found data source route");
        }
        return dataSourceRoute;
    }
}
